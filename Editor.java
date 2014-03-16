import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Editor
{
    JFrame frame = new JFrame();
    Cursor pen;
    Cursor cross;
    Cursor move;
    
    public Editor()
    {
        frame.setSize(906, 928);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.GRAY);
        frame.setTitle("Editor");
        frame.setLocation(100, 50);
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        Image drawImage = toolkit.getImage(Shapemaker.class.getResource("Cursors/pen.png"));
        Point penSpot = new Point(0, 0);
        pen = toolkit.createCustomCursor(drawImage, penSpot, "Pen");
        
        Image crossImage = toolkit.getImage(Shapemaker.class.getResource("Cursors/cross.png"));
        Point crossSpot = new Point(10, 10);
        cross = toolkit.createCustomCursor(crossImage, crossSpot, "Cross");
        
        Image moveImage = toolkit.getImage(Shapemaker.class.getResource("Cursors/move.png"));
        Point moveSpot = new Point(10, 10);
        move = toolkit.createCustomCursor(moveImage, moveSpot, "Move");
    }
    
    public JFrame getFrame()
    {
        return frame;
    }
}