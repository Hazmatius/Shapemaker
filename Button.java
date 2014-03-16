import java.util.*;
import java.awt.*;
import java.awt.geom.*;

public class Button
{
    private Image icon;
    private int buffer = 5;
    private boolean selected;
    private String name;
    //'toggle' indicates that the button is toggleable, that is, you have to click it once to
    //activate it, and a second time to deactivate it.
    private boolean toggle;
    private boolean other;
    private boolean sticky;
    private Menu menu;
    
    public Button(Image i, String name, boolean b, boolean o, boolean s)
    {
        icon = i;
        this.name = name;
        toggle = b;
        other = o;
        sticky = s;
    }
    
    public void setMenu(Menu m)
    {
        menu = m;
    }
    
    public void sendCommand(Controller c)
    {
        c.takeButtonCommand(name);
    }
    
    public boolean isSticky()
    {
        return sticky;
    }
    
    public int getWidth()
    {
        return icon.getWidth(null)+2*buffer;
    }
    
    public int getHeight()
    {
        return icon.getHeight(null)+2*buffer;
    }
    
    public void toggle(boolean b)
    {
        selected = b;
    }
    
    public boolean getOther()
    {
        return other;
    }
    
    public boolean isSelected()
    {
        return selected;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean isToggleable()
    {
        return toggle;
    }
    
    public void clickDown(Menu m, int x, int y, int clickX, int clickY)
    {
        RoundRectangle2D.Double r = new RoundRectangle2D.Double(x, y, getWidth(), getHeight(), 5, 5);
        if(r.contains(clickX, clickY))
        {
            if(toggle && !sticky)
            {
                selected = !selected;
                if(!name.equals("relative"))
                {
                    m.deselectOthers(this);
                }
            }
            else
            {
                selected = true;
            }
            sendCommand(m.getController());
            if(sticky && !name.equals("relative"))
            {
                menu.unClick(this);
            }
        }
    }
    
    public void clickDown(Menu m)
    {
        if(toggle && !sticky)
        {
            selected = !selected;
            if(!name.equals("relative"))
            {
                m.deselectOthers(this);
            }
        }
        else
        {
            selected = true;
        }
        if(sticky && !name.equals("relative"))
        {
            menu.unClick(this);
        }
    }
    
    public void clickUp(Menu m, int x, int y, int clickX, int clickY)
    {
        RoundRectangle2D.Double r = new RoundRectangle2D.Double(x, y, getWidth(), getHeight(), 5, 5);
        if(r.contains(clickX, clickY))
        {
            if(!toggle)
            {
                selected = false;
            }
        }
    }
    
    public void clickUp(Menu m)
    {
        if(!toggle)
        {
            selected = false;
        }
    }
    
    public void deselect()
    {
        selected = false;
    }
    
    public void draw(Graphics2D g2, int x, int y)
    {
        RoundRectangle2D.Double r = new RoundRectangle2D.Double(x, y, getWidth(), getHeight(), 5, 5);
        g2.setColor(new Color(245, 245, 245));
        g2.fill(r);
        if(selected)
        {
            if(name.equals("relative"))
            {
                g2.setColor(Color.GRAY);
            }
            else
            {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(2));
            }
        }
        else
        {
            if(name.equals("relative"))
            {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(2));
            }
            else
            {
                g2.setColor(Color.GRAY);
            }
        }
        g2.draw(r);
        g2.setStroke(new BasicStroke(1));
        g2.drawImage(icon, x+buffer, y+buffer, null);
    }
}