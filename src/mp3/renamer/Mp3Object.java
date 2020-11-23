package mp3.renamer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Mp3 песня
 */
public class Mp3Object {
    private File file;
    private String songName;   // 30 байт, смещение 3 байта
    private String artist;     // 30 байт, смещение 33 байта
    private String number;     // 1 байт,  смещение 123   
    private boolean isAlbum;   // флаг альбома
    
    public Mp3Object(File file, boolean isAlbum){
        // Проверка формата
        String ext = getExtension(file.getName());
        if(!ext.equals(".mp3")){
            System.err.println("Открыт не mp3-файл");
            System.exit(42);
        }
        
        this.file = file;
        this.isAlbum = isAlbum;
        addTags();
        correctFileName();
    }
    
    public File getFile(){
        return file;
    }
    
    public void setAlbum(){
        isAlbum = true;
    }
    
    //Заполняет теги файла
    private void addTags(){
        byte[] buffer;
        FileInputStream fin;
        try {
            fin = new FileInputStream(file.getAbsolutePath());
            int length = fin.available();
            byte[] byteArray = new byte[length];
            fin.read(byteArray, 0, length);
            fin.close();
            int startID3v1 = length -125; // начало ID3v1
            
            // Название
            buffer = new byte[30];
            for(int i=0; i<30; i++) buffer[i] = byteArray[startID3v1+i];
            songName = FileNameSymbols.getStringFromBytes(buffer);
            // Артист
            buffer = new byte[30];
            for(int i=0; i<30; i++) buffer[i] = byteArray[startID3v1+30+i];
            artist = FileNameSymbols.getStringFromBytes(buffer);
            // Номер трека
            int num = byteArray[startID3v1+123];
            number = num<10 ? "0"+num : Integer.toString(num);               
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mp3Object.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Mp3Object.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    // Корректирует имена файлов
    private boolean correctFileName(){
        String fileName = file.getAbsolutePath();
        String newFileName = fileName.substring(0);
        String name = file.getName();        

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
        File result = new File(newFileName);
        boolean res = false;
        if(!newFileName.equals(fileName)) res = file.renameTo(result);
        file = result;
        return res;
    }
    
    // возвращает расширение
    private String getExtension(String fileName){
        return fileName.substring(fileName.length()- 4);
    }
}
