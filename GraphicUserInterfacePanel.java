package routefinder.gui;

import routefinder.algorithm.*;
import routefinder.imageprocessing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics2D;
import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GraphicUserInterfacePanel extends JPanel implements ItemListener, ActionListener{
    private LanguageDatabase languageDatabase;
    private GridBagConstraints gridConstraints;
    private JLabel informationLabel;
    private int currentInformationText, clickCount;
    private JComboBox languageOptions;
    private JButton uploadButton, resetButton, saveButton, closeButton;
    private JLabel userImageLabel;
    private ImageProcessing userImage;
    private JOptionPane messagePanel;
    private BufferedImage bufferedUserImage, temporaryImage;
    private Node startPoint, endPoint;
    private Color startPointClr, endPointClr;
    private UserPoint drawPoints;

    public GraphicUserInterfacePanel(){
        setLanguage(); //set default language
        setWindow();
    }

    private void setLanguage(){
        languageDatabase = new LanguageDatabase(); 
    }

    public void setText(String language){
        languageDatabase.setLanguage(language);
        informationLabel.setText(languageDatabase.getInformationText(currentInformationText));
        uploadButton.setText(languageDatabase.getUploadButtonText());
        saveButton.setText(languageDatabase.getSaveButtonText());
        resetButton.setText(languageDatabase.getResetButtonText());
        closeButton.setText(languageDatabase.getCloseButtonText());
    }

    private void setWindow(){
        setLayout(new GridBagLayout()); //layout style
        gridConstraints = new GridBagConstraints(); //elements' parameters
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;

        createFirstLevel();
        createSecondLevel();
        createThirdLevel();
    }

    private void createFirstLevel(){
        gridConstraints.gridy = 0; //element row position
        createInformationLabel();
        
        //readjust contraints
        gridConstraints.gridx = 2; //element column position
        gridConstraints.gridheight = 1;//reset
        gridConstraints.ipady = 5; //internal padding on y-axis
        gridConstraints.weightx = 0; //reset
        createComboBox();

        //readjust constraints
        gridConstraints.gridy = 1; //element row position
        createUploadButton();
    }

    private void createInformationLabel(){
        currentInformationText = 0;
        informationLabel = new JLabel(languageDatabase.getInformationText(currentInformationText));
        informationLabel.setBorder(new CompoundBorder( //label's outer border
            BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK),
            BorderFactory.createEmptyBorder(7, 7, 7, 7)
        ));
        // gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridConstraints.insets = new Insets(10, 10, 0, 0); //top, left, bottom, right
        gridConstraints.gridwidth = 2; //width = 2 columns
        gridConstraints.gridheight = 2; //height = 2 rows
        gridConstraints.ipady = 20; //internal padding on y-axis
        gridConstraints.weightx = 0.5; //element weight on x-axis
        add(informationLabel, gridConstraints);
    }

    private void createComboBox(){
        languageOptions = new JComboBox(languageDatabase.getAvailableLanguage());
        languageOptions.setSelectedIndex(0); //set as default
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        languageOptions.addItemListener(this);
        add(languageOptions, gridConstraints);
    }

    private void createUploadButton(){
        uploadButton = new JButton(languageDatabase.getUploadButtonText());
        gridConstraints.insets = new Insets(0, 10, 0, 10);
        uploadButton.addActionListener(this);
        add(uploadButton, gridConstraints);
    }

    private void createSecondLevel(){
        gridConstraints.fill = GridBagConstraints.BOTH;
        gridConstraints.gridy = 2; //element row position
        gridConstraints.gridx = 0; //element column position
        gridConstraints.gridwidth = 4; //width = 4 columns
        gridConstraints.weighty = 1; //element weight on y-axis
        gridConstraints.insets = new Insets(10, 10, 0, 10); //top, left, bottom, right
        
        insertImageLabel();
    }

    private void insertImageLabel(){
        userImageLabel = new JLabel();
        userImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userImageLabel.setBackground(Color.WHITE); //set label color
        userImageLabel.setOpaque(true);

        userImageLabel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent mouseEvent){
                try{
                    initImage(1);
                    if(getClickCount() == 1){
                        setStartPoint(mouseEvent.getX(), mouseEvent.getY());
                    }
                    else if(getClickCount() == 2){
                        setEndPoint(mouseEvent.getX(), mouseEvent.getY());
                    }
                }
                catch(Exception exception){
                    if(temporaryImage != null) {
                        errorDetected("outOfBound");
                        initImage(0);
                    }
                }
            }
        });

        add(userImageLabel, gridConstraints);
    }

    private void createThirdLevel(){
        gridConstraints.gridy = 3; //element row position
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;

        //readjust constraints
        gridConstraints.anchor = GridBagConstraints.PAGE_END; //lock on bottom page
        gridConstraints.gridwidth = 1; //reset
        gridConstraints.ipadx = 20; //internal padding on x-axis
        gridConstraints.ipady = 20; //internal padding on y-axis
        gridConstraints.weightx = 0; //reset
        gridConstraints.weighty = 0; //reset

        gridConstraints.gridx = 0; //element column position
        createResetButton();
        gridConstraints.gridx = 2; //element column position
        createSaveButton();
        gridConstraints.gridx = 3; //element column position
        createCloseButton();
    }

    private void createResetButton(){
        resetButton = new JButton(languageDatabase.getResetButtonText());
        gridConstraints.insets = new Insets(10, 10, 10, 0);
        resetButton.addActionListener(this);
        add(resetButton, gridConstraints);
    }

    private void createSaveButton(){
        saveButton = new JButton(languageDatabase.getSaveButtonText());
        gridConstraints.insets = new Insets(10, 0, 10, 0);
        saveButton.addActionListener(this);
        add(saveButton, gridConstraints);
    }

    private void createCloseButton(){
        closeButton = new JButton(languageDatabase.getCloseButtonText());
        gridConstraints.insets = new Insets(10, 0, 10, 10);
        closeButton.addActionListener(this);
        add(closeButton, gridConstraints);
    }

    private void initImage(int code){
        if(code == 0){ //initialization
            clickCount = 0;
            startPoint = null;
            endPoint = null;
            temporaryImage = new BufferedImage(bufferedUserImage.getWidth(), bufferedUserImage.getHeight(), bufferedUserImage.getType());
            Graphics canvas = temporaryImage.getGraphics();
            canvas.drawImage(bufferedUserImage, 0, 0, null);
            canvas.dispose();
            userImageLabel.setIcon(new ImageIcon(temporaryImage));
            informationLabel.setText(languageDatabase.getInformationText(currentInformationText = 1));
        }
        else{
            clickCount += 1; //add number of clicks
        }
    }

    private void errorDetected(String errorMessage){
        JOptionPane.showMessageDialog(this, languageDatabase.getErrorMessage(errorMessage));
    }

    private int getClickCount(){
        return clickCount;
    }
    
    private void drawPoint(int x, int y){
        drawPoints = new UserPoint(temporaryImage);
        drawPoints.addPoint(userImageLabel, x, y);
    }

    private boolean checkSimilarColor(Color a, Color b){
        double distance = Math.pow((a.getRed() - b.getRed()), 2) + Math.pow(a.getGreen() - b.getGreen(), 2) + Math.pow(a.getBlue() - b.getBlue(), 2);
        if (distance <= 10) return true;
        return false;
    }

    private void setStartPoint(int x, int y){
        x -= (userImageLabel.getWidth() - temporaryImage.getWidth())/ 2;
        y -= (userImageLabel.getHeight() - temporaryImage.getHeight()) / 2;

        startPoint = new Node(y, x);
        startPoint.setStartingNode();
        startPointClr = new Color(temporaryImage.getRGB(x, y));
        drawPoint(x, y);
        informationLabel.setText(languageDatabase.getInformationText(currentInformationText = 2));
    }

    private void setEndPoint(int x, int y){
        x -= (userImageLabel.getWidth() - temporaryImage.getWidth())/ 2;
        y -= (userImageLabel.getHeight() - temporaryImage.getHeight()) / 2;
        endPointClr = new Color(temporaryImage.getRGB(x, y));
        endPoint = new Node(y, x);
        endPoint.setEndingNode();
        drawPoint(x,y);
        if(checkSimilarColor(startPointClr, endPointClr)){
            findShortestRoute();
            informationLabel.setText(languageDatabase.getInformationText(currentInformationText = 3));
        }
        else{
            errorDetected("outOfBound");
            initImage(0);
        }
    }

    private void drawPath(int x, int y){
        drawPoints = new UserPoint(temporaryImage);
        drawPoints.addPath(userImageLabel, x, y);
    }

    private void findShortestRoute(){
        ImageToArray maze = new ImageToArray(bufferedUserImage, bufferedUserImage.getHeight(), bufferedUserImage.getWidth(), startPointClr);
        Algorithm findRoute = new Algorithm(maze.conversionProcess());
        findRoute.convertIntToMaze(startPoint, endPoint);
        findRoute.searchPath();

        if(findRoute.checkPathFound()){
            int[][] shortestPath = findRoute.pathResult();
            for(int y = 0; y < shortestPath.length; y++){
                for(int x = 0; x < shortestPath[0].length; x++){
                    if(shortestPath[y][x] == 2) drawPath(x, y);
                }
            }
        }
        else{
            errorDetected("noRoute");
            initImage(0);
        }
    }

    private void saveImage(){
        BufferedImage finalImage = temporaryImage;
        if(finalImage == null) return;
        if(finalImage.getWidth() < userImage.getImageWidth() && finalImage.getHeight() < userImage.getImageHeight()){
            finalImage = new BufferedImage(userImage.getImageWidth(), userImage.getImageHeight(), finalImage.getType());
            Graphics2D g2d = finalImage.createGraphics();
            g2d.drawImage(temporaryImage, 0,  0, userImage.getImageWidth(), userImage.getImageHeight(), null);
            g2d.dispose();
        }
        
        userImage.saveResult(finalImage);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent){
        setText(itemEvent.getItem().toString());
    }
    
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(actionEvent.getSource() == closeButton){
            System.exit(1);
        }
        else if(actionEvent.getSource() == uploadButton){
            userImage = new ImageProcessing(userImageLabel, 600, 350); //JLabel size = 630 x 367
            if(userImage.getPath() != null){
                if(userImage.iconError() == true){
                    informationLabel.setText(languageDatabase.getInformationText(currentInformationText = 0));
                    errorDetected("unreadableFile");
                }
                else{
                    bufferedUserImage = userImage.getBufferedImage();
                    initImage(0); //initialize
                }
            }
            else{
                informationLabel.setText(languageDatabase.getInformationText(currentInformationText = 0));
                temporaryImage = null;
            }
        }
        else if(actionEvent.getSource() == resetButton){
            if(temporaryImage != null) initImage(0); 
        }
        else if(actionEvent.getSource() == saveButton){
            if(temporaryImage != null) saveImage();
        }
    }
}