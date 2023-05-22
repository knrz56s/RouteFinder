package routefinder.imageprocessing;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class ImageToArray{
    private BufferedImage image;
    private int imageHeight, imageWidth;
    private Color color;

    public ImageToArray(BufferedImage image, int imageHeight, int imageWidth, Color color){
        this.image = image;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.color = color;
    }

    private boolean checkSimilarColor(Color x){
        double distance = Math.pow((color.getRed() - x.getRed()), 2) + Math.pow(color.getGreen() - x.getGreen(), 2) + Math.pow(color.getBlue() - x.getBlue(), 2);
        if (distance <= 10) return true;
        return false;
    }

    public int[][] conversionProcess(){
        int[][] resultArray = new int[imageHeight][imageWidth];
        Color pixel;
        for(int y = 0; y < imageHeight; y++){
            for(int x = 0; x < imageWidth; x++){
                pixel = new Color(image.getRGB(x, y));
                resultArray[y][x] = (checkSimilarColor(pixel) ? 1 : 0);
            }
        }
        return resultArray;
    }
}