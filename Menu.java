import java.util.*;
import java.awt.*;

public class Menu
{
    private ArrayList<Button> buttons = new ArrayList<Button>();
    Controller c;
    
    public Menu(Controller c)
    {
        this.c = c;
        addButton("help", false, false, false);
        addButton("pen", true, false, true);
        addButton("cross", true, false, true);
        addButton("move", true, false, true);
        addButton("grab", true, false, true);
        addButton("copy", false, false, false);
        addButton("print", false, false, false);
        addButton("delete", false, false, false);
        addButton("plus", false, false, false);
        addButton("minus", false, false, false);
        addButton("new", false, false, false);
        addButton("relative", true, false, false);
        addButton("simp", false, false, false);
        
        for(int i=0; i<buttons.size(); i++)
        {
            buttons.get(i).setMenu(this);
        }
        
    }
    
    public void takeToolSettings(boolean drawing, boolean moving, boolean selecting)
    {
        
    }
    
    public void addButton(String st, boolean b, boolean o, boolean s)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Menu.class.getResource("Buttons/"+st+".png"));
        buttons.add(new Button(image, st, b, o, s));
    }
    
    public Controller getController()
    {
        return c;
    }
    
    public void unClick(Button b)
    {
        for(int i=0; i<buttons.size(); i++)
        {
            if(buttons.get(i).isSticky() && buttons.get(i)!=b)
            {
                buttons.get(i).deselect();
            }
        }
    }
    
    public void takeDownClick(int x, int y)
    {
        for(int i=0; i<buttons.size(); i++)
        {
            buttons.get(i).clickDown(this, buttons.get(i).getWidth()*i+5+i*5, 5, x, y);
        }
    }
    
    public void takeUpClick(int x, int y)
    {
        for(int i=0; i<buttons.size(); i++)
        {
            buttons.get(i).clickUp(this, buttons.get(i).getWidth()*i+5+i*5, 5, x, y);
            if(!buttons.get(i).isToggleable())
            {
                buttons.get(i).deselect();
            }
        }
    }
    
    public void clickButton(String s)
    {
        for(int i=0; i<buttons.size(); i++)
        {
            if(s.equals(buttons.get(i).getName()))
            {
                buttons.get(i).clickDown(this);
                buttons.get(i).clickUp(this);
            }
        }
    }
    
    public void deselectOthers(Button b)
    {
        for(int i=0; i<buttons.size(); i++)
        {
            if(buttons.get(i)!=b && !buttons.get(i).getOther())
            {
                buttons.get(i).deselect();
            }
        }
    }
    
    public boolean isOn(int x, int y)
    {
        Rectangle r = new Rectangle(0, 0, buttons.size()*(21+15)+5, 41);
        return r.contains(x, y);
    }
    
    public void draw(Graphics2D g2)
    {
        Rectangle r = new Rectangle(0, 0, buttons.size()*(21+15)+5, 41);
        g2.setColor(new Color(245, 245, 245));
        g2.fill(r);
        g2.setColor(Color.BLACK);
        g2.draw(r);
        for(int i=0; i<buttons.size(); i++)
        {
            buttons.get(i).draw(g2, buttons.get(i).getWidth()*i+5+i*5, 5);
        }
    }
}