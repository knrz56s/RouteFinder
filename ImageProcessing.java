package routefinder.imageprocessing;

import javax.swing.JLabel;
import javax.swing.JFileChooser;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Color;

public class ImageProcessing extends JLabel{
    private static JFileChooser fileChooser = new JFileChooser();
    private String imagePath;
    private BufferedImage userImage;
    private ImageIcon imageIcon;
    private int imageWidth, imageHeight, iconWidth, iconHeight;
    private BufferedImage bufferedImage;

    public ImageProcessing(JLabel container, int containerWidth, int containerHeight){
        init();
        getInputImage();
        if(imageIcon != null){
            imageHeight = imageIcon.getIconHeight();
            imageWidth = imageIcon.getIconWidth();
            imageIcon = resizeImage(containerWidth, containerHeight);
        }
        container.setIcon(imageIcon);
    }

    public void init(){
        imagePath = null;
        imageIcon = null;
    }

    public int getImageWidth(){
        return imageWidth;
    }

    public int getImageHeight(){
        return imageHeight;
    }

    public boolean iconError(){
        return imageIcon == null;
    }

    public String getPath(){
        return imagePath;
    }

    private void getInputImage(){
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", new String[]{"png", "jpg", "jpeg"}));
        fileChooser.setAcceptAllFileFilterUsed(false);
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            imagePath = fileChooser.getSelectedFile().toString();            
            try{
                bufferedImage = ImageIO.read(new File(imagePath));
                imageIcon = new ImageIcon(bufferedImage);
            }
            catch(Exception exception){
                imageIcon = null;
            }
        }
    }

    private ImageIcon resizeImage(int width, int height){
        ImageIcon newIcon = imageIcon;
        
        iconWidth = newIcon.getIconWidth();
        iconHeight = newIcon.getIconHeight();

        //adjust for small-sized images
        while(iconWidth < 200 || iconHeight < 200){
            iconWidth *= 2;
            iconHeight *= 2;
            break;
        }

        int tempWidth, tempHeight;
        //adjust for large-sized images
        if(iconWidth > width){
            iconHeight = (width * iconHeight) / iconWidth;
            iconWidth = width;
        }
        if(iconHeight > height){
            iconWidth = (height * iconWidth) / iconHeight;
            iconHeight = height;
        }
        return new ImageIcon(newIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
    }

    public BufferedImage getBufferedImage(){
        bufferedImage = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.createGraphics();
        imageIcon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();
        return bufferedImage;
    }


    public void saveResult(BufferedImage image){
        File saveFile = new File("RouteFinder.png");
        String fileExtension;
        fileChooser.setSelectedFile(saveFile);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            saveFile = fileChooser.getSelectedFile();
            fileExtension = saveFile.toString().substring(saveFile.toString().lastIndexOf('.') + 1, saveFile.toString().length());
            if(fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")){
                saveFile = new File(saveFile.toString().substring(0, saveFile.toString().lastIndexOf('.')) + ".png");
            }
            else{
                saveFile = new File(saveFile.toString() + ".png");
            }

            try{
                BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                newImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
                ImageIO.write(newImage, "png", saveFile);
            }
            catch(IOException ioException){}
        }
    }
}