package mp3renamer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.FILES_AND_DIRECTORIES;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends javax.swing.JFrame {
 
    Mp3Object[] mp3Files;
    JFileChooser filechooser;
    boolean openFileInExp = false;  // флаг, до этого открыта папка или файл
    Thread calcThread;              // поток создания строк вывода
    boolean isCalculations = false; // флаг потока создания строк вывода
      
    public MainFrame() {
        initComponents();
        getContentPane().setBackground(new Color(230,230,230));
        setIconImage( new ImageIcon( getClass().getResource("logo.png") ).getImage() );
        // Центрирование поля
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();       
        int xCenter = (screenSize.width - this.getWidth())/2;
        int yCenter = (screenSize.height - this.getHeight())/2;
        setBounds(xCenter, yCenter, this.getWidth() , this.getHeight());
        // выбор места открытия проводника
        String openFolderPath = System.getProperty("user.home") + "\\Desktop";
        if(new File(openFolderPath).list().length < 2){
            File[] roots = File.listRoots(); // список дисков
            openFolderPath = roots[0].getAbsolutePath();
            for(File file: roots){ 
                if(file.getAbsolutePath().equals("D:\\")){ 
                    openFolderPath = "D:\\"; 
                    break; 
                }
            }
        }
        // FileChooser
        filechooser = new JFileChooser(openFolderPath);
        filechooser.setFileSelectionMode(FILES_AND_DIRECTORIES);
        filechooser.setAcceptAllFileFilterUsed(false);
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Музыка (mp3)", "mp3"));        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openButton = new javax.swing.JButton();
        outTextAreaScrollArea = new javax.swing.JScrollPane();
        outTextArea = new javax.swing.JTextArea();
        albumFlag = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mp3 Renamer");

        openButton.setText("Открыть");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        outTextArea.setColumns(20);
        outTextArea.setRows(5);
        outTextAreaScrollArea.setViewportView(outTextArea);

        albumFlag.setText("Альбом");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outTextAreaScrollArea, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(albumFlag)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openButton)
                    .addComponent(albumFlag))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outTextAreaScrollArea, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /** Поток переименований */
    private Thread Renaming(File file){
        return new Thread(()->{
            if(file.isFile()){
                if(!openFileInExp) outTextArea.setText("");
                openFileInExp = true;
                outTextArea.append("Открыт файл " + file.getAbsolutePath() + "\n");
                mp3Files = new Mp3Object[1];
                try {
                    mp3Files[0] = new Mp3Object(file, albumFlag.isSelected());
                } catch (IOException | UnsupportedTagException | InvalidDataException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(mp3Files[0].isEdited()){
                    outTextArea.append("Переименован в " + mp3Files[0].getFile().getAbsolutePath() + "\n\n");
                }
            }
            else{
                outTextArea.setText("");
                openFileInExp = false;
                outTextArea.append("Открыта папка \"" + file.getAbsolutePath() + "\". ");
                int size = file.listFiles().length;
                outTextArea.append(size + " файлов\n");
                mp3Files = new Mp3Object[size];
                for(File el : file.listFiles()){
                    if(this.getExtension(el).equals(".mp3")){
                        outTextArea.append(el.getName() + "\n");
                    }
                }
                File el;
                String oldFileName;
                for(int i=0; i<size; i++){
                    el = file.listFiles()[i];
                    oldFileName = el.getName();
                    if(getExtension(el).equals(".mp3")){
                        try {
                            mp3Files[i] = new Mp3Object(el, albumFlag.isSelected());
                        } catch (IOException | UnsupportedTagException | InvalidDataException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(!mp3Files[i].getFile().getName().equals(oldFileName)){
                            if(!isCalculations){
                                isCalculations = true;
                                outTextArea.append("Переименованы:\n");
                            }
                            outTextArea.append(mp3Files[i].getFile().getName() + "\n");
                        }
                    } 
                }
            }           
        });
    }
    // Слушатель кнопки "Открыть"
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        int fileChoiceResp = filechooser.showDialog(this, "Открыть файл");
        if (fileChoiceResp == JFileChooser.APPROVE_OPTION) {
            Renaming(filechooser.getSelectedFile()).start();
        }
    }//GEN-LAST:event_openButtonActionPerformed
     // Получить расширение файла
    private String getExtension(File file){
        String fileName = file.getName();
        return fileName.substring(fileName.length()- 4);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox albumFlag;
    private javax.swing.JButton openButton;
    private javax.swing.JTextArea outTextArea;
    private javax.swing.JScrollPane outTextAreaScrollArea;
    // End of variables declaration//GEN-END:variables
}
