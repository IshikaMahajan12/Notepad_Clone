package notepadclone;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;

//Save option working

public class NotepadClone {

    JFrame frame;
    JTextArea textArea;
    MenuBar mb;
    Menu File,Edit,Format,View,Help,Zoom;
    MenuItem New,Open,Save,SaveAs,Exit;
    MenuItem cut,copy,paste,delete,td;
    MenuItem wordwrap,font;
    MenuItem zoomin,zoomout,restore;
    MenuItem about,viewhelp;
    File currentFile;
    JFileChooser fileChooser;
            
    private int fontSize = 20;
    private String fontName = "Serif";
    private int fontStyle = 0;
    
    NotepadClone()
   {    
       //frame created
       frame = new JFrame("Notepad Clone");
       frame.setSize(800, 600);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       textArea = new JTextArea();
       textArea.setFont(new Font(fontName,Font.PLAIN,fontSize));
       frame.add(new JScrollPane(textArea));
       
       fileChooser = new JFileChooser();
       fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
       
       //Menu created
       mb=new MenuBar();
       frame.setMenuBar(mb);
       
       File=new Menu("File");
       New=new MenuItem("New");
       Open=new MenuItem("Open");
       Save=new MenuItem("Save");
       SaveAs=new MenuItem("Save As");
       Exit=new MenuItem("Exit");
       mb.add(File);
       File.add(New);
       File.add(Open);
       File.add(Save);
       File.add(SaveAs);
       File.add(Exit);

       Edit=new Menu("Edit");
       cut=new MenuItem("Cut");
       copy=new MenuItem("Copy");
       paste=new MenuItem("Paste");
       delete=new MenuItem("Delete");
       td=new MenuItem("Time/Date");
       mb.add(Edit);
       Edit.add(cut);
       Edit.add(copy);
       Edit.add(paste);
       Edit.add(delete);
       Edit.add(td);
       
       Format=new Menu("Format");
       wordwrap=new MenuItem("WordWrap");
       font=new MenuItem("Font");
       mb.add(Format);
       Format.add(wordwrap);
       Format.add(font);
       
       View=new Menu("View");
       Zoom=new Menu("Zoom");
       zoomin=new MenuItem("Zoom In");
       zoomout=new MenuItem("Zoom Out");
       restore=new MenuItem("Restore Default Zoom");
       mb.add(View);
       View.add(Zoom);
       Zoom.add(zoomin);
       Zoom.add(zoomout);
       Zoom.add(restore);
       
       Help=new Menu("Help");
       viewhelp=new MenuItem("View Help");
       about=new MenuItem("About");
       mb.add(Help);
       Help.add(viewhelp);
       Help.add(about);

       // New file
        New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                currentFile = null;
            }
        });
            
        
        //Open
         Open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    currentFile = fileChooser.getSelectedFile();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(currentFile));
                        textArea.setText("");
                        String line;
                        while ((line = br.readLine()) != null) {
                            textArea.append(line + "\n");
                        }
                        br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
         
        //Save File
         Save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null) {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile));
                        bw.write(textArea.getText());
                        bw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } 
                //TODO: fix this 
                else {
                      saveAs();
                }
            }
        });
        
        //Save As option
        SaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });
         
        //Cut option
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               textArea.cut();
            }
        });
        
        //Copy option
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               textArea.copy();
            }
        });
        
        //Paste option
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               textArea.paste();
            }
        });
        
        //Delete option
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              int start = textArea.getSelectionStart();
                int end = textArea.getSelectionEnd();
                textArea.replaceRange("", start, end);
            }
        });
        
        //Time and Date
        td.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();  
            textArea.append(dtf.format(now))
        ;  
            }
        });
        
         // Zoom In
        zoomin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fontSize = Math.min(84, fontSize + 2);
               textArea.setFont(new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize));
            }
        });
        // Zoom Out
        zoomout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fontSize = Math.min(84, fontSize - 2);
               textArea.setFont(new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize));
            }
        });
         // Restore Zoom 
        restore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fontSize = 20;
               textArea.setFont(new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize));
            }
        });
        
        // exit application
        Exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               showAboutDialog();
            }
        });

       
       frame.setVisible(true);

   }
    public void saveAs()
    {
        int returnVal = fileChooser.showSaveDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    currentFile = fileChooser.getSelectedFile();
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile));
                        bw.write(textArea.getText());
                        bw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
    }
    public void showAboutDialog() {
        JOptionPane.showMessageDialog(frame,
                "Notepad Clone\nVersion 1.0\nCreated by Ishika Mahajan",
                "About Notepad Clone",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args) {
        NotepadClone obj= new NotepadClone();
    }
    
}
