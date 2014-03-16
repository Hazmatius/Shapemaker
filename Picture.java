import java.util.*;
import java.awt.*;

public class Picture
{
    private Hedron h = new Hedron(this);
    private ArrayList<Hedron> shapes = new ArrayList<Hedron>();
    private boolean relative = true;
    
    public Picture()
    {
        h.toggle(true);
        shapes.add(h);
    }
    
    public void addHedron()
    {
        Hedron newHedron = new Hedron(this);
        newHedron.toggle(true);
        h.toggle(false);
        shapes.add(newHedron);
        h = newHedron;
        clearHedrons();
    }
    
    public Hedron getHedron()
    {
        return h;
    }
    
    public void addHedron(ArrayList<Point> points)
    {
        Hedron newHedron = new Hedron(points, this);
        newHedron.toggle(true);
        h.toggle(false);
        shapes.add(newHedron);
        h = newHedron;
        clearHedrons();
    }
    
    public void clickHedron(int x, int y)
    {
        Hedron clickedHedron = h;
        for(int i=0; i<shapes.size(); i++)
        {
            if(shapes.get(i).getPolygon().contains(x, y))
            {
                clickedHedron = shapes.get(i);
            }
        }
        h.toggle(false);
        clickedHedron.toggle(true);
        h = clickedHedron;
        clearHedrons();
    }
    
    public void toggleRelative()
    {
        relative = !relative;
        for(int i=0; i<shapes.size(); i++)
        {
            shapes.get(i).setRelative(relative);
        }
    }
    
    public void addPoint(int x, int y)
    {
        h.addPoint(x, y);
    }
    
    public String getString()
    {
        return h.toString();
    }
    
    public void insertPoint(int x, int y, int i)
    {
        h.insertPoint(x, y, i);
    }
    
    public ArrayList<Point> getPoints()
    {
        return h.getPoints();
    }
    
    public void clear()
    {
        h.clear();
    }
    
    public void setName(String n)
    {
        h.setName(n);
    }
    
    public void setXMod(String x)
    {
        h.setXMod(x);
    }
    
    public void setYMod(String y)
    {
        h.setYMod(y);
    }
    
    public void simplify(double simpNum)
    {
        h.simplify(simpNum);
    }
    
    public void delete(ArrayList<Point> deletePoints)
    {
        h.delete(deletePoints);
    }
    
    public ArrayList<Point> selectPoints(Polygon p)
    {
        return h.selectPoints(p);
    }
    
    public Point selectPoint(int x, int y)
    {
        return h.selectPoint(x, y);
    }
    
    public int getMinX()
    {
        int minX = Integer.MAX_VALUE;
        for(int i=0; i<shapes.size(); i++)
        {
            if(shapes.get(i).getBounds().getX()<minX)
            {
                minX = (int)shapes.get(i).getBounds().getX();
            }
        }
        return minX;
    }
    
    public int getMinY()
    {
        int minY = Integer.MAX_VALUE;
        for(int i=0; i<shapes.size(); i++)
        {
            if(shapes.get(i).getBounds().getY()<minY)
            {
                minY = (int)shapes.get(i).getBounds().getY();
            }
        }
        return minY;
    }
    
    public void clearHedrons()
    {
        for(int i=0; i<shapes.size(); i++)
        {
            if(shapes.get(i).getPoints().size()==0 && !shapes.get(i).isSelected())
            {
                shapes.remove(i);
                i--;
            }
        }
    }
    
    public void reOrder(int i)
    {
        int index = shapes.indexOf(h);
        if(index+i!=-1 && index+i!=shapes.size())
        {
            index+=i;
            shapes.remove(h);
            shapes.add(index, h);
        }
    }
    
    public void draw(Graphics2D g2)
    {
        for(int i=0; i<shapes.size(); i++)
        {
            shapes.get(i).draw(g2);
        }
    }
    
    public void drawInfo(Graphics2D g2)
    {
        //g2.setColor(Color.WHITE);
        //g2.drawString("H to open help", 5, 15);
        //g2.drawString("D to draw", 5, 30);
        //g2.drawString("S to select", 5, 45);
        //g2.drawString("M to move", 5, 60);
        //g2.drawString("G to grab", 5, 75);
        //g2.drawString("N for new", 5, 90);
        //g2.drawString("+/- to reorder", 5, 105);
        //g2.drawString("Delete to delete", 5, 120);
        //g2.drawString("Escape to clear", 5, 135);
        //g2.drawString("C to copy", 5, 150);
        //g2.drawString("P to Print", 5, 165);
        g2.setColor(Color.WHITE);
        if(relative)
        {
            g2.drawString("Relative(on)", 5, 60);
        }
        else
        {
            g2.drawString("Relative(off)", 5, 60);
        }
    }
}