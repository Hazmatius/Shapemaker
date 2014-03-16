import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import java.util.ArrayList;
//import java.util.Random;

public class Shapemaker
{
    final static JFrame frame1 = new JFrame();
    final static Picture p = new Picture();
    final static Controller controller = new Controller(p);
    final static Rectangle background = new Rectangle(-1000, -1000, 4000, 4000);
    final static JFrame frame2 = new JFrame();
    final static JTextField nameField = new JTextField(45);
    final static JTextField xField = new JTextField(45);
    final static JTextField yField = new JTextField(45);
    final static JTextArea nameDesc = new JTextArea(1, 3);
    final static JTextArea xDesc = new JTextArea(1, 3);
    final static JTextArea yDesc = new JTextArea(1, 3);
    final static JTextArea textArea = new JTextArea(30, 50);
    final static JScrollPane scrollPane = new JScrollPane(textArea);
    final static JFrame controlFrame = new JFrame();
    final static JTextArea simpParamTag = new JTextArea(1, 3);
    final static JTextField simpParam = new JTextField(10);
    
    public static void setPrintText()
    {
        textArea.setText("");
        p.setName(nameField.getText());
        p.setXMod(xField.getText());
        p.setYMod(yField.getText());
        textArea.append(p.getString());
    }
    
    public static void main(String[] args)
    {
        controller.set(textArea, p, nameField, xField, yField);
        
        //The main control frame
        
        frame1.setSize(906, 928);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setBackground(Color.GRAY);
        frame1.setTitle("Polygon Input");
        frame1.setLocation(100, 50);
        controlFrame.setLocation(640, 0);
        
        //The secondary output frame
        
        
        frame2.setSize(640, 650);
        frame2.setResizable(false);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Polygon Output");
        frame2.setLayout(new FlowLayout());
        
        //The input fields
        
        nameField.setText("shape");
        
        xField.setText("#x");
        
        yField.setText("#y");
        
        //The input tags
        
        nameDesc.setEditable(false);
        nameDesc.setText("name:");
        nameDesc.setOpaque(false);
        nameDesc.setEnabled(false);
        
        
        xDesc.setEditable(false);
        xDesc.setText("      x:");
        xDesc.setOpaque(false);
        xDesc.setEnabled(false);
        
        
        yDesc.setEditable(false);
        yDesc.setText("      y:");
        yDesc.setOpaque(false);
        yDesc.setEnabled(false);
        
        //The output text
        
        textArea.setEditable(false);
        
        
        frame2.add(nameDesc);
        frame2.add(nameField);
        frame2.add(xDesc);
        frame2.add(xField);
        frame2.add(yDesc);
        frame2.add(yField);
        frame2.add(scrollPane);
        
        //The control frame
        
        controlFrame.setSize(500, 250);
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setTitle("Control input");
        controlFrame.setLayout(new FlowLayout());
        
        
        simpParamTag.setEditable(false);
        simpParamTag.setText("Simplification Constant:");
        simpParamTag.setOpaque(false);
        simpParamTag.setEnabled(false);
        
        simpParam.setText("0");
        
        controlFrame.add(simpParamTag);
        controlFrame.add(simpParam);
        
        
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();  
        
        Image drawImage = toolkit.getImage(Shapemaker.class.getResource("Cursors/pen.png"));
        Point penSpot = new Point(0, 0);
        final Cursor pen = toolkit.createCustomCursor(drawImage, penSpot, "Pen");
        
        Image crossImage = toolkit.getImage(Shapemaker.class.getResource("Cursors/cross.png"));
        Point crossSpot = new Point(10, 10);
        final Cursor cross = toolkit.createCustomCursor(crossImage, crossSpot, "Cross");
        
        Image moveImage = toolkit.getImage(Shapemaker.class.getResource("Cursors/move.png"));
        Point moveSpot = new Point(10, 10);
        final Cursor move = toolkit.createCustomCursor(moveImage, moveSpot, "Move");
        
        
        
        final JFrame help = new JFrame();
        help.setSize(500, 500);
        help.setTitle("Help");
        help.setLocation(300, 300);
        JTextArea helpText = new JTextArea();
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setMargin(new Insets(10,10,10,10));
        helpText.setText("Welcome to Shapemaker by Baranski\n\n\nH - opens the help window\n\nD - activates the draw tool. The draw tool lets you place individual points one at a time simply by clicking. It can also place many points if you drag the tool across the screen.\n\nS - activates the select tool. The select tool, if clicked at one spot, will select the nearest point to where the tool was clicked. If you press down, drag, and release, you will instead select any points within the selection box.\n\nM - activates the move tool. The move tool lets you move selected points around the screen. You can also do this using the arrow keys.\n\nG - turns off all tools, allowing you to click on and select separate polygons on the screen.\n\nN - creates a new polygon for you to work on.\n\n(+/-) - changes the order of the polygon currently being worked on, allowing you to rearrange the \"stacking\" of the polygons on the screen.\n\nDelete - deletes selected points. If the polygon you were just working on has no points (either because you never added any or because you deleted them), and you grab another polygon or create another polygon, the polygon you were just working on (the one with no points) will be deleted.\n\nEscape - clears your selection.\n\nC - copies the selected points into a new polygon.\n\nP - prints out the code you would use to generate the selected polygon. You can type in the name of the polygon in the Polygon Output window. Anything you want to do (change the size, orientation, position, or angle of the polygon) to the x and y values of the points for the polygon you can configure in the Polygon Output window as well. When typing in the coordinate formulae, use \"#x\" and \"#y\" for the x and y values.\n\nR - Shapemaker automatically shifts the position of your polygon as close to (0, 0) as it can. However, it can do this so that each polygon is considered on its own (relative off) or in the context of the other polygons (relative on). This is important in images with more than one polygon. If relative is ON, the Polygon Output will take into consideration that the polygons are part of one image, and will preserve the relationships between the polygons.\n\nW - Sometimes your polygon can have TO MUCH detail. When that happens, Shapemaker allows you to simplify your polygon by deleting points a minimum distance from each other, a value you can set in the \"Control Input\" screen at the \"Simplification Constant\" box. Once you've set the value, just press the \"W\" key.");
        
        final JScrollPane helpScroll = new JScrollPane(helpText);
        
        final Menu m = new Menu(controller);
        controller.setMenu(m);
        
        try
        {
            Robot robot = new Robot();
            int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
            int y = (int)MouseInfo.getPointerInfo().getLocation().getY();
            robot.mouseMove(50, 50);
        }
        catch(AWTException e)
        {
            
        }
        
        
        
        class Comp extends JComponent
        {
            public void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(Color.GRAY);
                g2.fill(background);
                p.draw(g2);
                controller.draw(g2);
                p.drawInfo(g2);
                m.draw(g2);
                //System.out.println(controlFrame.getX() + ", " + controlFrame.getY());
            }
        }
        
        class Physics implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    controller.setSimpNum(Double.parseDouble(simpParam.getText()));
                }
                catch(NumberFormatException exc)
                {
                    controller.setSimpNum(0);
                }
                
                if(!help.isVisible())
                {
                    controller.helpOff();
                }
                if(controller.help())
                {
                    help.setVisible(true);
                }
                
                if(controller.isDrawing())
                {
                    frame1.setCursor(pen);
                }
                else if(controller.isMoving())
                {
                    frame1.setCursor(move);
                }
                else if(controller.isSelecting())
                {
                    frame1.setCursor(cross);
                }
                else
                {
                    frame1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                frame1.repaint();
            }
        }
        
        class Mouse extends MouseAdapter
        {
            public void mouseClicked(MouseEvent e)
            {
                
            }
            
            public void mouseReleased(MouseEvent e)
            {
                if(!m.isOn(e.getX(), e.getY()-22))
                {
                    controller.takeClickUp(e);
                }
                m.takeUpClick(e.getX(), e.getY()-22);
                controller.nullP1P2();
            }
            
            public void mousePressed(MouseEvent e)
            {
                if(!m.isOn(e.getX(), e.getY()-22))
                {
                    controller.takeClickDown(e);
                }
                m.takeDownClick(e.getX(), e.getY()-22);
            }
        }
        
        class MouseDrag extends MouseMotionAdapter
        {
            public void mouseDragged(MouseEvent e)
            {
                controller.takeClickDrag(e);
            }
        }
        
        class Keyboard extends KeyAdapter
        {
            public void keyPressed(KeyEvent k)
            {
                
                controller.takeKeyDown(k);
                
            }
            
            public void keyReleased(KeyEvent k)
            {
                controller.takeKeyUp(k);
            }
            
            public void keyTyped(KeyEvent k)
            {
                
            }
        }
        
        Comp comp = new Comp();
        frame1.add(comp);
        MouseAdapter clicker = new Mouse();
        frame1.addMouseListener(clicker);
        KeyAdapter keyer = new Keyboard();
        frame1.addKeyListener(keyer);
        MouseMotionListener dragger = new MouseDrag();
        frame1.addMouseMotionListener(dragger);
        
        Physics time = new Physics();
        Timer t = new Timer(0, time);
        t.start();
        
        controlFrame.setVisible(true);
        
        frame2.setVisible(true);
        
        frame1.setVisible(true);
        
        help.add(helpScroll);
    }
}