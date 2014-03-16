import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Controller
{
    private Picture image;
    
    private boolean drawing;
    private boolean selecting;
    private boolean moving;
    
    private boolean metaRelative;
    
    private boolean help;
    private boolean subHelp;
    
    private boolean shift = false;
    
    private ArrayList<Point> selectedPoints = new ArrayList<Point>();
    
    private Point p1;
    private Point p2;
    
    private int iindex = 0;
    
    private double simpNum;
    
    private Menu m;
    
    private JTextArea textArea;
    private Picture p;
    private JTextField nameField;
    private JTextField xField;
    private JTextField yField;
    
    public void set(JTextArea textArea, Picture p, JTextField nameField, JTextField xField, JTextField yField)
    {
        this.textArea = textArea;
        this.p = p;
        this.nameField = nameField;
        this.xField = xField;
        this.yField = yField;
    }
    
    public void setPrintText()
    {
        textArea.setText("");
        p.setName(nameField.getText());
        p.setXMod(xField.getText());
        p.setYMod(yField.getText());
        textArea.append(p.getString());
    }
    
    public Controller(Picture p)
    {
        image = p;
    }
    
    public void setMenu(Menu m)
    {
        this.m = m;
    }
    
    public void nullP1P2()
    {
        p1 = null;
        p2 = null;
    }
    
    public void takeClickDown(MouseEvent e)
    {
        if(drawing && selectedPoints.size()==0)
        {
            image.addPoint(e.getX(), e.getY()-22);
        }
        if(drawing && selectedPoints.size()==1)
        {
            image.insertPoint(e.getX(), e.getY()-22, iindex);
            iindex++;
        }
        if(selecting)
        {
            p1 = new Point(e.getX(), e.getY()-22);
            p2 = new Point(e.getX(), e.getY()-22);
        }
        if(moving)
        {
            p1 = new Point(e.getX(), e.getY()-22);
            p2 = new Point(e.getX(), e.getY()-22);
        }
        if(!moving && !selecting && !drawing)
        {
            image.clickHedron(e.getX(), e.getY()-22);
            selectedPoints.clear();
        }
    }
    
    public void takeClickDrag(MouseEvent e)
    {
        if(drawing && selectedPoints.size()==0 && !m.isOn(e.getX(), e.getY()-22))
        {
            image.addPoint(e.getX(), e.getY()-22);
        }
        if(drawing && selectedPoints.size()==1 && !m.isOn(e.getX(), e.getY()-22))
        {
            image.insertPoint(e.getX(), e.getY()-22, iindex);
            iindex++;
        }
        if(selecting)
        {
            p2 = new Point(e.getX(), e.getY()-22);
        }
        if(moving)
        {
            p1 = p2;
            p2 = new Point(e.getX(), e.getY()-22);
            for(int i=0; i<selectedPoints.size(); i++)
            {
                selectedPoints.get(i).translate((int)(p2.getX()-p1.getX()), (int)(p2.getY()-p1.getY()));
            }
            if(selectedPoints.size()==0)
            {
                for(int i=0; i<image.getPoints().size(); i++)
                {
                    image.getPoints().get(i).translate((int)(p2.getX()-p1.getX()), (int)(p2.getY()-p1.getY()));
                }
            }
        }
    }
    
    public void takeClickUp(MouseEvent e)
    {
        if(selecting && p1!=null && p2!=null)
        {
            p2 = new Point(e.getX(), e.getY()-22);
            if(p1.getX()==p2.getX() && p1.getY()==p2.getY())
            {
                Point sPoint = image.selectPoint((int)p1.getX(), (int)p2.getY());
                if(shift && sPoint!=null && selectedPoints.indexOf(sPoint)==-1)
                {
                    selectedPoints.add(sPoint);
                }
                if(!shift && sPoint!=null)
                {
                    selectedPoints.clear();
                    selectedPoints.add(sPoint);
                }
                iindex = image.getPoints().indexOf(sPoint) + 1;
            }
            else
            {
                Polygon rectangle = new Polygon();
                rectangle.addPoint((int)p1.getX(), (int)p1.getY());
                rectangle.addPoint((int)p1.getX(), (int)p2.getY());
                rectangle.addPoint((int)p2.getX(), (int)p2.getY());
                rectangle.addPoint((int)p2.getX(), (int)p1.getY());
                ArrayList<Point> sPoints = image.selectPoints(rectangle);
                if(shift)
                {
                    for(int i=0; i<sPoints.size(); i++)
                    {
                        if(selectedPoints.indexOf(sPoints.get(i))==-1)
                        {
                            selectedPoints.add(sPoints.get(i));
                        }
                    }
                }
                else if(!shift)
                {
                    selectedPoints = sPoints;
                }
            }
            p1 = null;
            p2 = null;
        }
        if(moving)
        {
            p1 = null;
            p2 = null;
        }
    }
    
    public boolean isDrawing()
    {
        return drawing;
    }
    
    public boolean isSelecting()
    {
        return selecting;
    }
    
    public boolean isMoving()
    {
        return moving;
    }
    
    public void takeKeyDown(KeyEvent k)
    {
        if(k.getKeyCode()==KeyEvent.VK_E)
        {
            metaRelative = !metaRelative;
        }
        if(k.getKeyCode()==KeyEvent.VK_BACK_SPACE || k.getKeyCode()==KeyEvent.VK_DELETE)
        {
            image.delete(selectedPoints);
            selectedPoints.clear();
        }
        if(k.getKeyCode()==KeyEvent.VK_D)
        {
            drawing = true;
            selecting = false;
            moving = false;
            m.clickButton("pen");
        }
        if(k.getKeyCode()==KeyEvent.VK_S)
        {
            drawing = false;
            selecting = true;
            moving = false;
            m.clickButton("cross");
        }
        if(k.getKeyCode()==KeyEvent.VK_M)
        {
            drawing = false;
            selecting = false;
            moving = true;
            m.clickButton("move");
        }
        if(k.getKeyCode()==KeyEvent.VK_G)
        {
            drawing = false;
            selecting = false;
            moving = false;
            m.clickButton("grab");
        }
        if(k.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
            selectedPoints.clear();
            drawing = false;
            selecting = false;
            moving = false;
        }
        
        if(k.getKeyCode()==KeyEvent.VK_SHIFT)
        {
            shift = true;
        }
        
        if(k.getKeyCode()==KeyEvent.VK_UP)
        {
            for(int i=0; i<selectedPoints.size(); i++)
            {
                selectedPoints.get(i).translate(0, -1);
            }
        }
        if(k.getKeyCode()==KeyEvent.VK_DOWN)
        {
            for(int i=0; i<selectedPoints.size(); i++)
            {
                selectedPoints.get(i).translate(0, 1);
            }
        }
        if(k.getKeyCode()==KeyEvent.VK_LEFT)
        {
            for(int i=0; i<selectedPoints.size(); i++)
            {
                selectedPoints.get(i).translate(-1, 0);
            }
        }
        if(k.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            for(int i=0; i<selectedPoints.size(); i++)
            {
                selectedPoints.get(i).translate(1, 0);
            }
        }
        if(k.getKeyCode()==KeyEvent.VK_N)
        {
            image.addHedron();
            selectedPoints.clear();
        }
        if(k.getKeyCode()==KeyEvent.VK_MINUS)
        {
            image.reOrder(-1);
        }
        if(k.getKeyCode()==KeyEvent.VK_EQUALS)
        {
            image.reOrder(1);
        }
        if(k.getKeyCode()==KeyEvent.VK_H)
        {
            subHelp = !help;
        }
        if(k.getKeyCode()==KeyEvent.VK_C)
        {
            copy();
        }
        if(k.getKeyCode()==KeyEvent.VK_R)
        {
            image.toggleRelative();
            m.clickButton("relative");
        }
        if(k.getKeyCode()==KeyEvent.VK_W)
        {
            image.simplify(simpNum);
        }
        if(k.getKeyCode()==KeyEvent.VK_P)
        {
            setPrintText();
        }
    }
    
    public void takeButtonCommand(String command)
    {
        if(command.equals("delete"))
        {
            image.delete(selectedPoints);
            selectedPoints.clear();
        }
        if(command.equals("pen"))
        {
            drawing = true;
            selecting = false;
            moving = false;
        }
        if(command.equals("cross"))
        {
            drawing = false;
            selecting = true;
            moving = false;
        }
        if(command.equals("move"))
        {
            drawing = false;
            selecting = false;
            moving = true;
        }
        if(command.equals("grab"))
        {
            drawing = false;
            selecting = false;
            moving = false;
        }
        
        if(command.equals("new"))
        {
            image.addHedron();
            selectedPoints.clear();
        }
        if(command.equals("minus"))
        {
            image.reOrder(-1);
        }
        if(command.equals("plus"))
        {
            image.reOrder(1);
        }
        if(command.equals("help"))
        {
            subHelp = !help;
        }
        if(command.equals("copy"))
        {
            copy();
        }
        if(command.equals("relative"))
        {
            image.toggleRelative();
        }
        if(command.equals("simp"))
        {
            image.simplify(simpNum);
        }
        if(command.equals("print"))
        {
            setPrintText();
        }
    }
    
    public void setSimpNum(double d)
    {
        simpNum = d;
    }
    
    public void copy()
    {
        image.addHedron(selectedPoints);
        selectedPoints.clear();
        selectedPoints.addAll(image.getPoints());
    }
    
    public void helpOff()
    {
        subHelp = false;
    }
    
    public boolean help()
    {
        return help;
    }
    
    public void takeKeyUp(KeyEvent k)
    {
        if(k.getKeyCode()==KeyEvent.VK_SHIFT)
        {
            shift = false;
        }
    }
    
    public void draw(Graphics2D g2)
    {
        help = subHelp;
        if(selectedPoints!=null && selectedPoints.size()>0)
        {
            for(int i=0; i<selectedPoints.size(); i++)
            {
                double x = selectedPoints.get(i).getX()-1;
                double y = selectedPoints.get(i).getY()-1;
                Rectangle.Double r = new Rectangle.Double(x, y, 2, 2);
                g2.setColor(Color.BLACK);
                g2.draw(r);
            }
        }
        if(selecting && p1!=null && p2!=null)
        {
            Polygon rectangle = new Polygon();
            rectangle.addPoint((int)p1.getX(), (int)p1.getY());
            rectangle.addPoint((int)p1.getX(), (int)p2.getY());
            rectangle.addPoint((int)p2.getX(), (int)p2.getY());
            rectangle.addPoint((int)p2.getX(), (int)p1.getY());
            g2.setColor(new Color(0, 255, 0, 255/2));
            g2.fill(rectangle);
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(3));
            g2.draw(rectangle);
            g2.setStroke(new BasicStroke(1));
        }
        if(selectedPoints.size()==1 && drawing && image.getPoints().size()>1)
        {
            int i1 = iindex-1;
            int i2 = iindex;
            if(i2>=image.getPoints().size())
            {
                i2 = 0;
            }
            Rectangle.Double dot = new Rectangle.Double(image.getPoints().get(i1).getX()-1, image.getPoints().get(i1).getY()-1, 2, 2);
            Polygon line = new Polygon();
            line.addPoint((int)image.getPoints().get(i1).getX(), (int)image.getPoints().get(i1).getY());
            line.addPoint((int)image.getPoints().get(i2).getX(), (int)image.getPoints().get(i2).getY());
            g2.setColor(Color.RED);
            g2.draw(dot);
            g2.fill(dot);
            g2.draw(line);
            g2.fill(line);
        }
        if(selectedPoints.size()==1)
        {
            Point p = selectedPoints.get(0);
            int pointXVal = (int)(p.getX()-image.getHedron().getBounds().getX());
            int pointYVal = (int)(p.getY()-image.getHedron().getBounds().getY());
            g2.setColor(Color.GRAY.brighter().brighter());
            g2.drawString("("+pointXVal+", "+pointYVal+")", (int)p.getX(), (int)p.getY());
            if(metaRelative)
            {
                Point p1 = image.getHedron().getPointBefore(p);
                Point p2 = image.getHedron().getPointAfter(p);
                
                double x1 = p1.getX()-1;
                double y1 = p1.getY()-1;
                Rectangle.Double r1 = new Rectangle.Double(x1, y1, 2, 2);
                g2.setColor(Color.RED);
                g2.draw(r1);
                g2.drawString("("+(int)(p.getX()-p1.getX())+", "+(int)(p.getY()-p1.getY())+")", (int)p.getX(), (int)p.getY()-15);
                
                double x2 = p2.getX()-1;
                double y2 = p2.getY()-1;
                Rectangle.Double r2 = new Rectangle.Double(x2, y2, 2, 2);
                g2.setColor(Color.BLUE);
                g2.draw(r2);
                g2.drawString("("+(int)(p.getX()-p2.getX())+", "+(int)(p.getY()-p2.getY())+")", (int)p.getX(), (int)p.getY()+15);
                
            }
        }
    }
}