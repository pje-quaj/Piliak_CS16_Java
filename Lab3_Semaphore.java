package lab3_semaphore;

import java.io.*;
import java.util.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

//------------------------------------------------------------------------------------------

class EmptyStringException extends Exception
{
    public EmptyStringException(){};
    
    @Override
    public String toString()
    {
        return "This should not be empty!\nRestarting...";
    }
}

class NotANumberException extends Exception
{
    public NotANumberException(){};
    
    @Override
    public String toString()
    {
        return "This is not a number!\nRestarting...";
    }
}

//------------------------------------------------------------------------------------------

//=========Shared Resourse============

class shared
{
    private static String str = "Shared Text";
    
    public static String getSharedString()
    {
        return str;
    }
    
    public static void setSharedString(String value)
    {
        str = value;
    }
}

//================Lab1================
class lab1 extends Thread
{
    private Thread t;
    
    public static void app() throws IOException, EmptyStringException, NotANumberException
    {
        System.out.println("------------Lab1------------");
        EmptyStringException exString = new EmptyStringException();
        NotANumberException exNaN = new NotANumberException();
        BufferedReader num = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = new String();
        int n;
        while(true)
        {
            System.out.print("Enter n: ");
            line = num.readLine();
            if (line.equals("")) 
                throw exString;
            else if(!line.matches("^[0-9]+$")) 
                throw exNaN;
            else 
                break;
        }
        n = Integer.parseInt(line);
        ArrayList<String> strings = new ArrayList<>(n);
        
        short i = 0;
        for(i = 0; i < n; i++)
        {
            System.out.println("Enter string(" + (i + 1) + "):");   
            while (true)
            {
                line = br.readLine();                
                if (line.equals("")) throw exString;
                else break;
            }
            strings.add(line);
        }
		
        int avg = 0;
        for(i = 0; i < n; i++)
        {
            avg = avg + strings.get(i).length();
        }
        avg = avg/n;
		
        System.out.println("\nStrings shorter than average: ");
        for(i = 0; i < n; i++) 
            if (strings.get(i).length() < avg)
            	System.out.println(strings.get(i));
    }
    
    @Override
    public void run() 
	{
            while (true)
            {                
                try 
                {
                    app();
                    break;
                } 
                catch (IOException|EmptyStringException|NotANumberException e) 
                {
                    System.out.println(e.toString());
                }
            }
            
        }  
}

//================Lab2================
class lab2 extends Thread
{ 
    private Thread t;
    static Semaphore sem;
    
    public lab2(Semaphore sem_param)
    {
        sem = sem_param;
    }
    
    public static void app() throws IOException, EmptyStringException 
    {
        System.out.println("------------Lab2------------");
        EmptyStringException exString = new EmptyStringException();
        System.out.println("Color code validity check. Type 'x' to exit");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try
        {
            sem.acquire();
        }
        catch(InterruptedException e)
        {
            System.out.println(e);
        }
            
        while(true)
        {
            System.out.print("Enter color code to check: ");
            line = br.readLine();
            if (line.equals(""))
                throw exString;
            else if(line.equalsIgnoreCase("x"))
                break;
            else if (line.matches("#([0-9]|[A-F]|[a-f]){6}"))
            {
                shared.setSharedString("This is a color code");
                System.out.println(shared.getSharedString());   
            }
            else
            {
                shared.setSharedString("This is not a color code");
                System.out.println(shared.getSharedString());
            }
        } 

        shared.setSharedString("Unlocked");
        sem.release();   
    };
    
    @Override
    public void run() 
    {
        while (true)
        {            
            try 
            {
                app();
                break;
            } 
            catch (IOException|EmptyStringException e) 
            {
                System.out.println(e.toString());
            }
        }        
    }
    
}

//================Lab4================
class lab4 extends JPanel implements ActionListener, Runnable
{
    private Thread t;
    
    Timer timer;
    Random rand = new Random();
    public static String myText = "MovingText";
    private final int x = 250;
    private int y = 240;
    private int counter = 0;
    boolean reverse = false;
    private Color color;
    private final Color ColorArray[] = {Color.CYAN, Color.MAGENTA, Color.BLUE, Color.RED, Color.ORANGE};
    static Semaphore sem;
    
    public lab4(Semaphore sem_param)
    {
        this.setLayout(null);
        timer = new Timer(20, this);
        timer.setInitialDelay(190);
        timer.start();
        sem = sem_param;
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
        
        //Moving horizontally
        
        counter++;
        if(!reverse) 
        {
            y++;
            if(y == 440) 
            {
                reverse = true;
                color = ColorArray[rand.nextInt(5)];
            }
        }
        else 
        {
            y--;
            if(y == 20) 
            {
                reverse = false;
                color = ColorArray[rand.nextInt(5)];
            }
        }
        
        if(counter == 500)
        {
            if(sem.tryAcquire())
                try
                {
                    myText = shared.getSharedString();
                } finally
                {
                    sem.release();
                }
            else
                JOptionPane.showMessageDialog(null, "Resourse is locked");
            counter = 0;
        }  
        
        g2.setColor(color);
        g2.drawString(myText, x, y);
    }
    
    @Override
    public void run() 
    {
        JFrame frame = new JFrame("lab4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new lab4(sem));

        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    void start()
    {
        t = new Thread (this, "lab1");
        t.start();
    }
}

//--------------------------------------------------------------------------------------

public class Lab3_Semaphore
{

    public static void main(String[] args)
    {        
        Semaphore SEM = new Semaphore(1);
        
        lab1 thread_lab1 = new lab1();
        lab2 thread_lab2 = new lab2(SEM);
        lab4 thread_lab4 = new lab4(SEM);
        
        thread_lab4.start();
        
        try
        {
            thread_lab2.start();
            thread_lab2.join();
            thread_lab1.start();
            thread_lab1.join();
        } 
        catch(InterruptedException e)
        {
            JOptionPane.showMessageDialog(null, "Interrupted Exception");
        }
        
        System.exit(0); 
    }
    
}
