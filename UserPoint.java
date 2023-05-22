package routefinder.imageprocessing;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class UserPoint{
    private BufferedImage image;
    public UserPoint(BufferedImage image){
        this.image = image;
    }

    public void addPoint(JLabel container, int x, int y){
        Graphics2D canvas = image.createGraphics();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvas.setRenderingHints(hints);
        canvas.setColor(Color.RED);
        canvas.setStroke(new BasicStroke(2));
        canvas.drawLine(x - 3, y, x + 3, y);
        canvas.drawLine(x, y - 3, x, y + 3);
        container.setIcon(new ImageIcon(image));
        canvas.dispose();
    }

    public void addPath(JLabel container, int x, int y){
        Graphics2D canvas = image.createGraphics();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvas.setRenderingHints(hints);
        canvas.setColor(Color.GREEN);
        canvas.setStroke(new BasicStroke(3));
        canvas.drawLine(x, y, x, y);
        container.setIcon(new ImageIcon(image));
        canvas.dispose();
    }
}