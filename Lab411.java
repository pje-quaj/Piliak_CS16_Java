/*
11. Створити апплет з рядком, що рухається горизонтально, 
відбиваючись від границь апплета й міняючи при цьому свій колір, на колір обраний зі списку, що випадає.
*/

package lab411;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class SWINGEmptyStringException extends Exception
{
    public SWINGEmptyStringException(){};
    
    @Override
    public String toString()
    {
        JOptionPane.showMessageDialog(null, "This should not be empty!\nRestarting...");
        return "This should not be empty!\nRestarting...";
    }
}

public class Lab411 extends JPanel implements ActionListener
{
    Timer timer;
    public static String myText;
    private int x = 320;
    private final int y = 240;
    boolean reverse = false;
    private Color color;
    private final Object options[] = {"Red", "Magenta", "Blue"};
    
    public Lab411()
    {
        this.setLayout(null);
        timer = new Timer(20, this);
        timer.setInitialDelay(190);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        repaint();
    }
    
    @Override
    public void paint(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; 
        g.setFont(new Font("Arial", Font.BOLD, 30));
        
        if(!reverse) 
        {
            x++;
            if(x == 450) 
            {
                reverse = true;
                int n = JOptionPane.showOptionDialog(this, "Change color:", "Color", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                switch (n)
                {
                    case JOptionPane.YES_OPTION:
                        color = Color.RED;
                        break;
                    case JOptionPane.NO_OPTION:
                        color = Color.MAGENTA;
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        color = Color.BLUE;
                        break;
                    default:
                        break;
                }
            }
        }
        else 
        {
            x--;
            if(x == 0) 
            {
                reverse = false;
                int n = JOptionPane.showOptionDialog(this, "Change color:", "Color", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                switch (n)
                {
                    case JOptionPane.YES_OPTION:
                        color = Color.RED;
                        break;
                    case JOptionPane.NO_OPTION:
                        color = Color.MAGENTA;
                        break;
                    default:
                        color = Color.BLUE;
                        break;
                }
            }
        }
        g2.setColor(color);
        g2.drawString(myText, x, y);
    }
    
    public static void main(String[] args) 
    {
        SWINGEmptyStringException swExString = new SWINGEmptyStringException();
        JFrame frame = new JFrame("Lab411");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Lab411());
        
        while (true)
        {            
            try
            {
                myText = JOptionPane.showInputDialog("Write your text here");
                if (myText.equals(""))
                {
                    throw swExString;
                }
                break;
            } catch (SWINGEmptyStringException e)
            {
                e.toString();
            }
        }
        
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
