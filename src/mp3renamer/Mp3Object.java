package mp3renamer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;

/**
 *
 * Mp3 песня
 */
public class Mp3Object {
    private File file;
    private Mp3File mp3file;  
    private boolean isAlbum;   // флаг альбома
    private boolean isEdited = false;  // флаг измененного имени
    
    public Mp3Object(File _file, boolean _isAlbum) throws IOException, UnsupportedTagException, InvalidDataException{
        // Проверка формата
        String fileName = _file.getName();
        String ext = fileName.substring(fileName.length()- 4);
        if(!ext.equals(".mp3")){
            System.err.println("Открыт не mp3-файл");
            System.exit(42);
        }
        
        file = _file;
        isAlbum = _isAlbum;
        mp3file = new Mp3File(file.getAbsolutePath());
        correctFileName();
    }
    
    public File getFile(){
        return file;
    }
    
    public void setAlbum(){
        isAlbum = true;
    }
    
    public boolean isEdited(){
        return isEdited;
    }
    
    // Корректирует имена файлов
    private boolean correctFileName(){
        String fileName = file.getAbsolutePath();
        String newFileName = fileName.substring(0);
        String name = file.getName();
        
        String songName = mp3file.getId3v2Tag().getTitle();
        String artist = mp3file.getId3v2Tag().getArtist();
        String number = mp3file.getId3v2Tag().getTrack(); 
        if(songName==null) songName="";
        if(artist==null) artist="";
        if(number==null) number="";

        // Формирование название из тегов, если есть оба
        if(!songName.equals("") && !artist.equals("")){
            // Если песня из альбома
            if(isAlbum){
                newFileName = fileName.substring(0, fileName.length()-name.length());
                newFileName += number + ". " + artist + " - " + songName + ".mp3";
            }
            else{
                newFileName = fileName.substring(0, fileName.length()-name.length());
                newFileName += artist + " - " + songName + ".mp3";           
            }
        }
        else{
            // проверка на наличие пробелов между исполнителем и названием
            if(fileName.contains("-") && !fileName.contains(" - ")){
                newFileName = fileName.substring(0, fileName.indexOf("-"));
                newFileName += " - ";
                newFileName += fileName.substring(fileName.indexOf("-")+1);
            }
            // проверка на -kissvk.com
            if(newFileName.contains("-kissvk")){
                int endIndex = newFileName.length() - 15;
                newFileName = newFileName.substring(0, endIndex);
                newFileName += ".mp3";
            }
        }
        boolean res = false;
        if(!file.getAbsolutePath().equals(newFileName)){
            isEdited = true;
            File result = new File(newFileName);
            if(!newFileName.equals(fileName)) res = file.renameTo(result);
            file = result;
        }
        return res;
    }
}
