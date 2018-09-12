import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.text.PlainDocument;

public class SDBGUI extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    /**
     * This are global variables for the components of the GUI
     */
    public static JList<Song> playList;
    public static JScrollPane playListScroller;
    public static JPanel mainJPanel;
    public static JPanel databaseControlPanel;
    public static JPanel reproductionPanel;
    public static JRadioButton filterArtist;
    public static JRadioButton filterGenre;
    public static JRadioButton filterTitle;
    public static JRadioButton filterTimesPlayed;
    public static JButton resetButton;
    public static JButton playButton;
    public static JButton pauseButton;
    public static JButton modifyButton;
    public static JButton deleteButton;
    public static JTextField titleSong;
    public static JTextField artistSong;
    public static JTextField genreSong;
    public static JTextField urlSong;
    public static JTextField timesPlayedSong;
    public static JButton addButton;
    public static JButton loadDatabaseButton;
    public static JButton saveDatabaseButton;
    public static JFileChooser loadDatabase;
    public static JFileChooser saveDatabase;
    public static JLabel fillAllFields;
    public static DefaultListModel<Song> playListModel;

    public SDBGUI(String frameTitle, Song[] songList) throws IOException
    {   
        setTitle(frameTitle);
        playListModel = new DefaultListModel<Song>(); 
        for (Song aSong : songList)
        {
            playListModel.addElement(aSong);            
        }        
        playList = new JList<Song>();
        playList.setModel(playListModel);
        playList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playList.setLayoutOrientation(JList.VERTICAL);
        playList.setFixedCellHeight(50);
        playListScroller = new JScrollPane(playList);
        playListScroller.setPreferredSize(new Dimension(485, 0));
        mainJPanel = new JPanel(new GridBagLayout());
        databaseControlPanel = new JPanel(new GridBagLayout());
        reproductionPanel = new JPanel(new GridBagLayout());      
        playList.setBackground(Color.BLACK);   
        getContentPane().add(mainJPanel);        
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.gridx = 0; 
        gridBag.gridy = 0;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5; 
        gridBag.gridheight = 1;
        mainJPanel.add(playListScroller, gridBag);
        /**
         * Added new panel -- FILTERS
        */
        gridBag.fill = GridBagConstraints.BOTH;        
        gridBag.gridx = 1; 
        gridBag.gridy = 0;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridheight = 1;
        gridBag.ipady = 0;
        gridBag.anchor = GridBagConstraints.NORTH;
        mainJPanel.add(databaseControlPanel , gridBag);
        /**
         * Here we start adding the buttons and the label about the filters
        */
        gridBag.fill = GridBagConstraints.VERTICAL;     
        gridBag.gridx = 0; 
        gridBag.gridy = 0;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipady = 0;
        gridBag.insets = new Insets(3, 2, 5, 2);
        databaseControlPanel.add(new JLabel("<html><font size='5'>FILTERS</html>"), gridBag);          
        gridBag.gridx = 0; 
        gridBag.gridy = 1;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 1;
        filterArtist = new JRadioButton();
        filterArtist.addActionListener(this);
        filterArtist.setText("<html><font size='4'>ARTISTS</html>");
        filterArtist.setVerticalTextPosition(SwingConstants.BOTTOM);
        filterArtist.setHorizontalTextPosition(SwingConstants.CENTER);
        databaseControlPanel.add(filterArtist, gridBag); 
        gridBag.gridx = 1; 
        gridBag.gridy = 1;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;  
        filterGenre = new JRadioButton();
        filterGenre.addActionListener(this);
        filterGenre.setText("<html><font size='4'>GENRE</html>");
        filterGenre.setVerticalTextPosition(SwingConstants.BOTTOM);
        filterGenre.setHorizontalTextPosition(SwingConstants.CENTER);
        databaseControlPanel.add(filterGenre , gridBag);       
        gridBag.gridx = 2; 
        gridBag.gridy = 1;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.ipady = 4;
        filterTitle = new JRadioButton();
        filterTitle.addActionListener(this);
        filterTitle.setText("<html><font size='4'>TITLE</html>");
        filterTitle.setVerticalTextPosition(SwingConstants.BOTTOM);
        filterTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        databaseControlPanel.add(filterTitle , gridBag);      
        gridBag.gridx = 3; 
        gridBag.gridy = 1;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        filterTimesPlayed = new JRadioButton();
        filterTimesPlayed.addActionListener(this);
        filterTimesPlayed.setText("<html><font size='4'>TIMES PLAYED</html>");
        filterTimesPlayed.setVerticalTextPosition(SwingConstants.BOTTOM);
        filterTimesPlayed.setHorizontalTextPosition(SwingConstants.CENTER);
        databaseControlPanel.add(filterTimesPlayed , gridBag);
        gridBag.gridx = 0; 
        gridBag.gridy = 2;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.insets = new Insets(3, 2, 0, 2);
        resetButton = new JButton("<html><font size='4'>RESET</html>");
        resetButton.addActionListener(this);
        resetButton.setPreferredSize(new Dimension(200,30));
        databaseControlPanel.add(resetButton , gridBag);
        /**
         * Added new panel -- PLAY AND STOP
        */
        gridBag.fill = GridBagConstraints.NONE; 
        gridBag.anchor = GridBagConstraints.SOUTHWEST;                
        gridBag.gridx = 0; 
        gridBag.gridy = 4;
        gridBag.weightx = 0;
        gridBag.weighty = 0;
        gridBag.gridwidth = 1;
        gridBag.insets = new Insets(2, 1, 2, 1);
        mainJPanel.add(reproductionPanel, gridBag);
        /**
         * Here we start adding the buttons of play and stop
        */
        gridBag.gridx = 0; 
        gridBag.gridy = 0;
        gridBag.weightx = 0;
        gridBag.weighty = 0;
        Image playImage = ImageIO.read(new File("playButton.png"));
        playImage = playImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH); 
        Icon playIcon = new ImageIcon(playImage);
        playButton = new JButton("Play", playIcon);
        playButton.addActionListener(this);
        reproductionPanel.add(playButton , gridBag);
        gridBag.gridx = 1; 
        gridBag.gridy = 0;
        gridBag.weightx = 0;
        gridBag.weighty = 0;
        Image pauseImage = ImageIO.read(new File("pauseButton.png"));
        pauseImage = pauseImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH); 
        Icon pauseIcon = new ImageIcon(pauseImage);        
        pauseButton = new JButton("Pause", pauseIcon);
        pauseButton.addActionListener(this);
        reproductionPanel.add(pauseButton , gridBag);              
        /**
         * Here we start adding the buttons to control songs
        */ 
        gridBag.anchor = GridBagConstraints.CENTER; 
        gridBag.fill = GridBagConstraints.VERTICAL;     
        gridBag.gridx = 0; 
        gridBag.gridy = 3;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipady = 0;
        gridBag.insets = new Insets(40, 2, 5, 2);
        databaseControlPanel.add(new JLabel("<html><font size='5'>OPTIONS</html>"), gridBag);
        gridBag.anchor = GridBagConstraints.CENTER; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 4;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 2;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(10, 2, 5, 2);
        modifyButton = new JButton("<html><font size='4'>MODIFY</html>");
        modifyButton.addActionListener(this);
        modifyButton.setPreferredSize(new Dimension(200,40));
        databaseControlPanel.add(modifyButton, gridBag);
        gridBag.anchor = GridBagConstraints.CENTER; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 2; 
        gridBag.gridy = 4;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 2;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(10, 2, 5, 2);
        deleteButton = new JButton("<html><font size='4'>DELETE</html>");
        deleteButton.addActionListener(this);
        deleteButton.setPreferredSize(new Dimension(200,40));
        databaseControlPanel.add(deleteButton, gridBag);
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 5;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 1;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(5, 2, 5, 2); 
        databaseControlPanel.add(new JLabel("<html><font size='5'>TITLE</html>"), gridBag);
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 6;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(4, 2, 5, 2);
        titleSong = new JTextField();
        titleSong.setPreferredSize(new Dimension(400, 20)); 
        databaseControlPanel.add(titleSong, gridBag);      
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 7;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 1;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(5, 2, 5, 2); 
        databaseControlPanel.add(new JLabel("<html><font size='5'>ARTIST</html>"), gridBag);
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 8;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(4, 2, 5, 2);
        artistSong = new JTextField();
        artistSong.setPreferredSize(new Dimension(400, 20)); 
        databaseControlPanel.add(artistSong, gridBag);      
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 9;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 1;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(5, 2, 5, 2); 
        databaseControlPanel.add(new JLabel("<html><font size='5'>GENRE</html>"), gridBag);
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 10;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(4, 2, 5, 2);
        genreSong = new JTextField();
        genreSong.setPreferredSize(new Dimension(400, 20)); 
        databaseControlPanel.add(genreSong, gridBag);      
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 11;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 1;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(5, 2, 5, 2); 
        databaseControlPanel.add(new JLabel("<html><font size='5'>URL</html>"), gridBag);
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 12;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(4, 2, 5, 2);
        urlSong = new JTextField();
        urlSong.setPreferredSize(new Dimension(400, 20)); 
        databaseControlPanel.add(urlSong, gridBag);      
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 13;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 1;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(5, 2, 5, 2); 
        databaseControlPanel.add(new JLabel("<html><font size='5'>TIMES PLAYED</html>"), gridBag);
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 14;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(4, 2, 5, 2);
        timesPlayedSong = new JTextField();
        timesPlayedSong .setPreferredSize(new Dimension(400, 20));
        PlainDocument timesPlayedDoc = (PlainDocument) timesPlayedSong.getDocument();
        timesPlayedDoc.setDocumentFilter(new TimesPlayedFilter());
        databaseControlPanel.add(timesPlayedSong, gridBag);
        gridBag.anchor = GridBagConstraints.PAGE_START; 
        gridBag.fill = GridBagConstraints.BOTH;     
        gridBag.gridx = 0; 
        gridBag.gridy = 16;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 1;
        gridBag.ipadx = 10;
        gridBag.insets = new Insets(5, 2, 5, 2);
        fillAllFields = new JLabel("<html><font size='3'>FILL ALL THE FIELDS</html>");
        databaseControlPanel.add(fillAllFields, gridBag);
        fillAllFields.setVisible(false);
        gridBag.anchor = GridBagConstraints.CENTER; 
        gridBag.fill = GridBagConstraints.VERTICAL;     
        gridBag.gridx = 0; 
        gridBag.gridy = 17;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 4;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(10, 2, 5, 2);
        addButton = new JButton("<html><font size='4'>ADD</html>");
        addButton.addActionListener(this);
        addButton.setPreferredSize(new Dimension(200,40));
        databaseControlPanel.add(addButton , gridBag);
        gridBag.anchor = GridBagConstraints.CENTER; 
        gridBag.fill = GridBagConstraints.BOTH; 
        gridBag.gridx = 0; 
        gridBag.gridy = 18;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 2;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(13, 2, 5, 2);
        loadDatabaseButton = new JButton("LOAD DATABASE");
        loadDatabaseButton.addActionListener(this);
        loadDatabase = new JFileChooser();
        loadDatabase.setFileSelectionMode(JFileChooser.FILES_ONLY);
        databaseControlPanel.add(loadDatabaseButton , gridBag);
        gridBag.anchor = GridBagConstraints.CENTER; 
        gridBag.fill = GridBagConstraints.BOTH; 
        gridBag.gridx = 2; 
        gridBag.gridy = 18;
        gridBag.weightx = 0.5;
        gridBag.weighty = 0.5;
        gridBag.gridwidth = 2;
        gridBag.ipadx = 0;
        gridBag.insets = new Insets(13, 2, 5, 2);
        saveDatabaseButton = new JButton("SAVE DATABASE");
        saveDatabaseButton.addActionListener(this);
        saveDatabase = new JFileChooser();
        databaseControlPanel.add(saveDatabaseButton , gridBag); 
        setDefaultCloseOperation(3);       
        pack();
        setMinimumSize(getSize()); 
    }
    public void actionPerformed(ActionEvent action)
    {
        if (action.getSource() == filterArtist)
        {
            if (filterArtist.isSelected())
            {
                filterMethod(" Artist Name ", this);                
                filterArtist.setEnabled(false);
            }            
        }
        else if (action.getSource() == filterGenre)
        {
            if (filterGenre.isSelected())
            {
                filterGenre.setEnabled(false);
                filterMethod(" Genre ", this);                
            }
        }
        else if (action.getSource() == filterTitle)
        {
            if (filterTitle.isSelected())
            {
                filterMethod(" Title ", this);
                filterTitle.setEnabled(false);
            }
        }
        else if (action.getSource() == filterTimesPlayed)
        {                     
            if (filterTimesPlayed.isSelected())
            {
                filterMethod(" Number of Times Played ", this);
                filterTimesPlayed.setEnabled(false);
            }
        }
        else if (action.getSource() == resetButton)
        {
            if (filterTimesPlayed.isSelected())
            {
                filterTimesPlayed.setEnabled(true);
                filterTimesPlayed.doClick();
            }
            if (filterTitle.isSelected())
            {
                filterTitle.setEnabled(true);
                filterTitle.doClick();
            }    
            if (filterArtist.isSelected())
            {
                filterArtist.setEnabled(true);
                filterArtist.doClick();
            }    
            if (filterGenre.isSelected())
            {
                filterGenre.setEnabled(true);
                filterGenre.doClick();
            }    
            playListModel.clear();
            for (Song aSong : playListDatabase.noFilters()) 
            {
                playListModel.addElement(aSong);                          
            }
            playListDatabase.removeFilter();      
        }
        else if (action.getSource() == playButton)
        {
            try 
            {
                Song songToPlay = playList.getSelectedValue();
                mp3Player.play(songToPlay.getURL());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        }
        else if (action.getSource() == pauseButton)
        {
            mp3Player.stop();
        }
        else if (action.getSource() == modifyButton)
        {
            if (playList.getSelectedValue() != null)
                modifyMethod(playList.getSelectedValue(), this);
        }
        else if (action.getSource() == deleteButton)
        {
            playListModel.removeElement(playList.getSelectedValue());
        }
        else if (action.getSource() == addButton)
        {
            try
            {
            if (titleSong.getText().isEmpty() || artistSong.getText().isEmpty()
                || genreSong.getText().isEmpty() || urlSong.getText().isEmpty()
                || timesPlayedSong.getText().isEmpty())
            {
                fillAllFields.setVisible(true);
                mainFrame.pack();
            }
            else
            {
                Song songToAdd = playListDatabase.addNewSong(titleSong.getText() + "\t" + artistSong.getText() + "\t" 
                                                            + genreSong.getText() + "\t" + urlSong.getText() 
                                                            + "\t" + timesPlayedSong.getText());
                if (songToAdd != null)
                {
                    playListModel.addElement(songToAdd);
                }
                fillAllFields.setVisible(false);    
            }
            }
            catch(SongException songException)
            {
                System.out.println(songException.getMessage());
            }
        }
        else if (action.getSource() == loadDatabaseButton)
        {
            try
            {
                int returnVal = loadDatabase.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File songDatabase = loadDatabase.getSelectedFile();
                    BufferedReader readDatabase = new BufferedReader(new FileReader(songDatabase));
                    String songInformationTitle = readDatabase.readLine();
                    int createAnewPlaylist = JOptionPane.showConfirmDialog(mainFrame,
                                                "Do you want to create a new playlist?",
                                                "New playlist confirmation",
                                                JOptionPane.YES_NO_OPTION);
                    if (createAnewPlaylist == JOptionPane.YES_OPTION)
                    {                            
                        mainFrame.setTitle(songInformationTitle);                    
                        playListDatabase = new SongDatabase();                    
                        playListModel.clear();
                    }                    
                    for (Song aSong : playListDatabase.load(readDatabase)) 
                    {
                        playListModel.addElement(aSong);                          
                    }  
                }
            }
            catch(FileNotFoundException exception)
            {
                System.out.println(exception.getMessage());
            }
            catch(IOException exception)
            {
                System.out.println(exception.getMessage());
            }
            catch(SongException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
        else if (action.getSource() == filterButton)
        {
            filterDialog.setModalityType(Dialog.ModalityType.MODELESS);
            if (!filterField.getText().isEmpty())
            {   
                switch (filteringNow)
                {
                  case " Number of Times Played ":
                      playListModel.clear();
                      for (Song aSong : playListDatabase.filterSong(3, filterField.getText())) 
                      {
                          playListModel.addElement(aSong);                          
                      }                                        
                      break;
                  case " Title ":
                      playListModel.clear();
                      for (Song aSong : playListDatabase.filterSong(exactMatch? 4:0, filterField.getText())) 
                      {
                          playListModel.addElement(aSong);                          
                      }    
                      break;
                  case " Genre ":
                      playListModel.clear();                    
                      for (Song aSong : playListDatabase.filterSong(exactMatch? 6:2, filterField.getText())) 
                      {
                          playListModel.addElement(aSong);                          
                      }
                      break;
                  case " Artist Name ":
                      playListModel.clear();
                      for (Song aSong : playListDatabase.filterSong(exactMatch? 5:1, filterField.getText())) 
                      {
                          playListModel.addElement(aSong);                          
                      }
                      break;                                                                                
                  default:
                      break;
                }
            }
            filterDialog.dispose();                
        }
        else if (action.getSource() == exactFilterButton)
        {
            if (exactFilterButton.isSelected())
                exactMatch = true;
            else
                exactMatch = false;
        }                
        else if (action.getSource() == saveDatabaseButton)
        {            
            try
            {
                int returnVal = saveDatabase.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File songDatabase = saveDatabase.getSelectedFile();
                    PrintWriter writeDatabase = new PrintWriter(new FileOutputStream(songDatabase),true);
                    writeDatabase.write(mainFrame.getTitle());                                  
                    playListDatabase.save(writeDatabase);
                    writeDatabase.close();  
                }
            }
            catch(FileNotFoundException exception)
            {
                System.out.println(exception.getMessage());
            }                          
        }
        else if (action.getSource() == dialogModifyButton)
        {
            if (dialogTitleSong.getText().isEmpty() || dialogArtistSong.getText().isEmpty()
                || dialogGenreSong.getText().isEmpty() || dialogUrlSong.getText().isEmpty()
                || dialogTimesPlayedSong.getText().isEmpty())
            {
                dialogFillAllFields.setVisible(true);
                modifyDialog.pack();
            }
            else
            {                
                songToModify.modifySongArtist(dialogArtistSong.getText());
                songToModify.modifySongGenre(dialogGenreSong.getText());
                songToModify.modifySongTitle(dialogTitleSong.getText());
                songToModify.modifySongURL(dialogUrlSong.getText());
                songToModify.modifySongTimesPlayed(dialogTimesPlayedSong.getText());
                modifyDialog.dispose();
                mainJPanel.updateUI();
            }            
        }
        else
        {
            System.out.println("NO IMPLEMENTED"); 
        }               
    }

    private static JButton filterButton;
    private static JTextField filterField;
    private static JCheckBox exactFilterButton;
    private static String filteringNow;
    private static Boolean exactMatch = false;
    private static JDialog filterDialog;
    private static void filterMethod(String FilterOption, SDBGUI actionListener)
    {
        filteringNow = FilterOption;
        filterDialog = new JDialog(mainFrame);
        filterDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        filterDialog.setTitle("Filter Input");
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterDialog.getContentPane().add(filterPanel);
        GridBagConstraints gridFilter = new GridBagConstraints();
        gridFilter.anchor = GridBagConstraints.CENTER;
        gridFilter.fill = GridBagConstraints.BOTH;
        gridFilter.gridx = 0; 
        gridFilter.gridy = 0;
        gridFilter.insets = new Insets(13, 2, 5, 2);
        filterPanel.add(new JLabel("<html><font size='4'>Insert the" + FilterOption + "to use</html>"),gridFilter);
        gridFilter.gridx = 0; 
        gridFilter.gridy = 1;
        gridFilter.insets = new Insets(4, 2, 5, 2);
        filterField = new JTextField();
        filterField.setPreferredSize(new Dimension(400, 40));
        if (FilterOption == " Number of Times Played ")
        {
            PlainDocument timesPlayedDoc = (PlainDocument) filterField.getDocument();
            timesPlayedDoc.setDocumentFilter(new TimesPlayedFilter());            
        }
        filterPanel.add(filterField,gridFilter);
        gridFilter.anchor = GridBagConstraints.CENTER;
        gridFilter.fill = GridBagConstraints.BOTH;
        gridFilter.gridx = 0; 
        gridFilter.gridy = 2; 
        gridFilter.gridwidth = 1;
        gridFilter.insets = new Insets(4, 2, 5, 6);
        exactFilterButton = new JCheckBox("<html><font size='4'>EXACT MATCH</html>");
        exactFilterButton.addActionListener(actionListener);
        filterPanel.add(exactFilterButton,gridFilter);
        if (FilterOption == " Number of Times Played ")
            exactFilterButton.setEnabled(false);
        gridFilter.anchor = GridBagConstraints.CENTER;
        gridFilter.fill = GridBagConstraints.VERTICAL;
        gridFilter.gridx = 0; 
        gridFilter.gridy = 3; 
        gridFilter.gridwidth = 2;
        gridFilter.insets = new Insets(6, 2, 5, 13);
        filterButton = new JButton("<html><font size='5'>ACCEPT</html>");
        filterButton.addActionListener(actionListener);
        filterPanel.add(filterButton,gridFilter);
        filterDialog.pack();     
        filterDialog.setMinimumSize(filterDialog.getSize());
        filterDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        filterDialog.addWindowListener(
        new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
              filterDialog.dispose();
              switch (FilterOption)
              {
                  case " Number of Times Played ":
                      filterTimesPlayed.setEnabled(true);
                      filterTimesPlayed.doClick();                    
                      break;
                  case " Title ":
                      filterTitle.setEnabled(true);
                      filterTitle.doClick();
                      break;
                  case " Genre ":
                      filterGenre.setEnabled(true);
                      filterGenre.doClick();
                      break;
                  case " Artist Name ":
                      filterArtist.setEnabled(true);
                      filterArtist.doClick();
                      break;                                                                                
                  default:
                      break;
              }
            }
        });
        filterDialog.setVisible(true);     
    }

    private static JButton  dialogModifyButton;
    public static JTextField dialogTitleSong;
    public static JTextField dialogArtistSong;
    public static JTextField dialogGenreSong;
    public static JTextField dialogUrlSong;
    public static JTextField dialogTimesPlayedSong;
    public static JLabel dialogFillAllFields;
    private static JDialog modifyDialog;
    private static Song songToModify;
    private static void modifyMethod(Song selectedSong, SDBGUI actionListener)
    {
        songToModify = selectedSong;
        modifyDialog = new JDialog(mainFrame);
        modifyDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modifyDialog.setTitle("Song Modification");
        JPanel modifyPanel = new JPanel(new GridBagLayout());
        modifyDialog.getContentPane().add(modifyPanel);
        GridBagConstraints gridModify = new GridBagConstraints();
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 5;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 1;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(5, 2, 5, 2); 
        modifyPanel.add(new JLabel("<html><font size='5'>TITLE</html>"), gridModify);
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 6;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 4;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(4, 2, 5, 2);
        dialogTitleSong = new JTextField();
        dialogTitleSong.setPreferredSize(new Dimension(400, 20));
        dialogTitleSong.setText(selectedSong.getTitle());
        modifyPanel.add(dialogTitleSong, gridModify);      
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 7;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 1;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(5, 2, 5, 2); 
        modifyPanel.add(new JLabel("<html><font size='5'>ARTIST</html>"), gridModify);
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 8;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 4;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(4, 2, 5, 2);
        dialogArtistSong = new JTextField();
        dialogArtistSong.setPreferredSize(new Dimension(400, 20));
        dialogArtistSong.setText(selectedSong.getArtist()); 
        modifyPanel.add(dialogArtistSong, gridModify);      
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 9;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 1;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(5, 2, 5, 2); 
        modifyPanel.add(new JLabel("<html><font size='5'>GENRE</html>"), gridModify);
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 10;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 4;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(4, 2, 5, 2);
        dialogGenreSong = new JTextField();
        dialogGenreSong.setPreferredSize(new Dimension(400, 20));
        dialogGenreSong.setText(selectedSong.getGenre()); 
        modifyPanel.add(dialogGenreSong, gridModify);      
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 11;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 1;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(5, 2, 5, 2); 
        modifyPanel.add(new JLabel("<html><font size='5'>URL</html>"), gridModify);
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 12;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 4;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(4, 2, 5, 2);
        dialogUrlSong = new JTextField();
        dialogUrlSong.setPreferredSize(new Dimension(400, 20));
        dialogUrlSong.setText(selectedSong.getURL()); 
        modifyPanel.add(dialogUrlSong, gridModify);      
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 13;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 1;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(5, 2, 5, 2); 
        modifyPanel.add(new JLabel("<html><font size='5'>TIMES PLAYED</html>"), gridModify);
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 14;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 4;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(4, 2, 5, 2);
        dialogTimesPlayedSong = new JTextField();
        dialogTimesPlayedSong .setPreferredSize(new Dimension(400, 20));
        PlainDocument timesPlayedDoc = (PlainDocument) dialogTimesPlayedSong.getDocument();
        timesPlayedDoc.setDocumentFilter(new TimesPlayedFilter());
        dialogTimesPlayedSong.setText(selectedSong.getTimesPlayed());
        modifyPanel.add(dialogTimesPlayedSong, gridModify);
        gridModify.anchor = GridBagConstraints.PAGE_START; 
        gridModify.fill = GridBagConstraints.BOTH;     
        gridModify.gridx = 0; 
        gridModify.gridy = 16;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 1;
        gridModify.ipadx = 10;
        gridModify.insets = new Insets(5, 2, 5, 2);
        dialogFillAllFields = new JLabel("<html><font size='3'>FILL ALL THE FIELDS</html>");
        modifyPanel.add(dialogFillAllFields, gridModify);
        dialogFillAllFields.setVisible(false);
        gridModify.anchor = GridBagConstraints.CENTER; 
        gridModify.fill = GridBagConstraints.VERTICAL;     
        gridModify.gridx = 0; 
        gridModify.gridy = 17;
        gridModify.weightx = 0.5;
        gridModify.weighty = 0.5;
        gridModify.gridwidth = 4;
        gridModify.ipadx = 0;
        gridModify.insets = new Insets(10, 2, 5, 2);
        dialogModifyButton = new JButton("<html><font size='4'>MODIFY</html>");
        dialogModifyButton.addActionListener(actionListener);
        dialogModifyButton.setPreferredSize(new Dimension(200,40));
        modifyPanel.add(dialogModifyButton , gridModify);
        modifyDialog.pack();
        modifyDialog.setMinimumSize(modifyDialog.getSize());
        modifyDialog.setVisible(true);     
    }
    
    private static SongDatabase playListDatabase;
    private static MP3Player mp3Player;
    private static JFrame mainFrame;
    public static void main(String[] args) throws SongException
    {  
        BufferedReader readDatabase = null;      
        try 
        {   
            playListDatabase = new SongDatabase();
            mp3Player = new MP3Player();          
            readDatabase = new BufferedReader(new FileReader("sampleSongDatabase.mdb"));                     
            String songInformationTitle = readDatabase.readLine();  
            Song[] songsList = playListDatabase.load(readDatabase); 
            mainFrame = new SDBGUI(songInformationTitle, songsList);                                                    
            mainFrame.setVisible(true);
        } 
        catch (IOException exception) 
        {
            System.out.println("There was an error when trying to read the database");
            System.out.println(exception.getMessage());
        }  
    }
}