import java.util.*;
import java.awt.*;

public class Hedron
{
    private ArrayList<Point> points;
    private String name;
    private String xMod;
    private String yMod;
    
    private boolean relative = true;
    private boolean selected = false;
    
    private Picture context;
    
    public Hedron(Picture context)
    {
        points = new ArrayList<Point>();
        this.context = context;
    }
    
    public Hedron(ArrayList<Point> newPoints, Picture context)
    {
        points = new ArrayList<Point>();
        for(int i=0; i<newPoints.size(); i++)
        {
            points.add(new Point((int)newPoints.get(i).getX()+20, (int)newPoints.get(i).getY()+20));
        }
        this.context = context;
    }
    
    public void setRelative(boolean r)
    {
        relative = r;
    }
    
    public void toggle(boolean t)
    {
        selected = t;
    }
    
    public boolean isSelected()
    {
        return selected;
    }
    
    public void addPoint(int x, int y)
    {
        points.add(new Point(x, y));
    }
    
    public void insertPoint(int x, int y, int i)
    {
        points.add(i, new Point(x, y));
    }
    
    public void addPoint(Point p)
    {
        points.add(p);
    }
    
    public void setName(String s)
    {
        name = s;
    }
    
    public void setXMod(String s)
    {
        xMod = s;
    }
    
    public void setYMod(String s)
    {
        yMod = s;
    }
    
    public void clear()
    {
        points.clear();
    }
    
    public ArrayList<Point> selectPoints(Polygon r)
    {
        ArrayList<Point> sPoints = new ArrayList<Point>();
        for(int i=0; i<points.size(); i++)
        {
            if(r.contains(points.get(i)))
            {
                sPoints.add(points.get(i));
            }
        }
        return sPoints;
    }
    
    public Point selectPoint(int x, int y)
    {
        double r = Double.MAX_VALUE;
        Point sp = null;
        for(int i=0; i<points.size(); i++)
        {
            double dx = points.get(i).getX()-x;
            double dy = points.get(i).getY()-y;
            if(Math.sqrt(dx*dx+dy*dy)<r)
            {
                r = Math.sqrt(dx*dx+dy*dy);
                sp = points.get(i);
            }
        }
        return sp;
    }
    
    public void delete(ArrayList<Point> deletePoints)
    {
        for(int i=0; i<deletePoints.size(); i++)
        {
            points.remove(deletePoints.get(i));
        }
    }
    
    public Rectangle getBounds()
    {
        return getPolygon().getBounds();
    }
    
    public String toString()
    {
        Polygon p = getPolygon();
        Rectangle r = p.getBounds();
        //int x = (int)r.getX();
        //int y = (int)r.getY();
        int x = 0;
        int y = 0;
        if(relative)
        {
            x = context.getMinX();
            y = context.getMinY();
        }
        String s = "";
        
        for(int i=0; i<points.size(); i++)
        {
            String xval = "" + ((int)points.get(i).getX()-x);
            String yval = "" + ((int)points.get(i).getY()-y);
            String xComp = xMod.replaceAll("#x", xval);
            xComp = xComp.replaceAll("#y", yval);
            String yComp = yMod.replaceAll("#y", yval);
            yComp = yComp.replaceAll("#x", xval);
            s+=name+".addPoint("+ xComp + ", " + yComp + ");\n";
        }
        return s;
    }
    
    public ArrayList<Point> getPoints()
    {
        return points;
    }
    
    public Point getPoint(int i)
    {
        return points.get(i);
    }
    
    public Point getPointBefore(Point p)
    {
        int index = points.indexOf(p);
        if(index!=0)
        {
            return points.get(index-1);
        }
        else
        {
            return points.get(points.size()-1);
        }
    }
    
    public Point getPointAfter(Point p)
    {
        int index = points.indexOf(p);
        if(index!=points.size()-1)
        {
            return points.get(index+1);
        }
        else
        {
            return points.get(0);
        }
    }
    
    public int getIndexOf(Point p)
    {
        return points.indexOf(p);
    }
    
    public Polygon getPolygon()
    {
        Polygon p = new Polygon();
        for(int i=0; i<points.size(); i++)
        {
            p.addPoint((int)points.get(i).getX(), (int)points.get(i).getY());
        }
        return p;
    }
    
    public double getR(Point p1, Point p2)
    {
        double dx = p2.getX()-p1.getX();
        double dy = p2.getY()-p1.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    public void simplify(double simpNum)
    {
        if(points.size()>=3)
        {
            int i=0;
            int j=1;
            while(i<points.size()-1)
            {
                while(j<points.size())
                {
                    if(getR(points.get(i), points.get(j))<simpNum)
                    {
                        points.remove(j);
                    }
                    else
                    {
                        j = points.size();
                    }
                }
                i++;
                j=i+1;
            }
        }
        simpNum = simpNum*1.25;
    }
    
    public void draw(Graphics2D g2)
    {
        if(selected)
        {
            g2.setColor(Color.ORANGE);
        }
        else
        {
            g2.setColor(Color.BLUE);
        }
        g2.fill(getPolygon());
        g2.setColor(Color.BLACK);
        g2.draw(getPolygon());
    }
}