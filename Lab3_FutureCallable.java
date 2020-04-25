package lab3_futurecallable;

import java.io.*;
import java.util.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        return "This should not be empty!";
    }
}

//------------------------------------------------------------------------------------------

//================Lab2================
class lab2 implements Callable<String>
{ 
    private Thread t;
    
    public static String app() throws IOException, EmptyStringException 
    {
        System.out.println("------------Lab2------------");
        EmptyStringException exString = new EmptyStringException();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line, answer;

        System.out.print("Enter color code to check: ");
        line = br.readLine();
        if (line.equals(""))
            throw exString;
        else if (line.matches("#([0-9]|[A-F]|[a-f]){6}"))
            answer = "This is a color code";
        else
            answer = "This is not a color code";
        System.out.println(answer);
        return answer;     
    };
    
    @Override
    public String call() 
    {
        String result = "String should not be empty";            
        try 
        {
            result = app();
        } 
        catch (IOException|EmptyStringException e) 
        {
            System.out.println(e.toString());
        }
        return result;    
    }
    
}

//================Lab4================
class lab4 extends JPanel implements ActionListener, Runnable
{
    private Thread t;
    
    Timer timer;
    Random rand = new Random();
    private final int x = 150;
    private int y = 240;
    private int counter = 0;
    boolean reverse = false;
    private Color color;
    private final Color ColorArray[] = {Color.CYAN, Color.MAGENTA, Color.BLUE, Color.RED, Color.ORANGE};
    String MovingText;
    
    public lab4(String UserMovingText)
    {
        this.setLayout(null);
        timer = new Timer(20, this);
        timer.setInitialDelay(190);
        timer.start();
        MovingText = UserMovingText;
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
        
        //Moving vertically
        
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

        g2.setColor(color);
        g2.drawString(MovingText, x, y);
    }
    
    @Override
    public void run() 
    {
        JFrame frame = new JFrame("lab4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new lab4(MovingText));    
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

public class Lab3_FutureCallable
{

    public static void main(String[] args)
    {
        ExecutorService es = Executors.newFixedThreadPool(1);
        
        Future<String> fString;
        
        fString = es.submit(new lab2());
        
        try
        {
            lab4 t_lab4 = new lab4(fString.get());
            Thread thread_lab4 = new Thread(t_lab4);
            thread_lab4.start();
            while (true)          
                thread_lab4.join();
        } catch (InterruptedException e)
        {
            JOptionPane.showMessageDialog(null, "Interrupted Exception");
        } catch (ExecutionException e)
        {
            JOptionPane.showMessageDialog(null, "Execution Exception");
        }
        
        es.shutdown();
        System.exit(0); 
    }
    
}
