package routefinder.gui;

import routefinder.gui.GraphicUserInterfacePanel;
import java.awt.Dimension;
import javax.swing.JFrame;

public class GraphicUserInterface extends JFrame{

    public GraphicUserInterface(){
        super("Route Finder");
        GraphicUserInterfacePanel windowPanel = new GraphicUserInterfacePanel(); //create window's content
        add(windowPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(650, 550));
        setResizable(false); pack();
        setLocationRelativeTo(null); setVisible(true);
    }
}