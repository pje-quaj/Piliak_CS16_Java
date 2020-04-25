package lab2_threads;

import java.io.*;
import java.util.*;

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

class EmptyFileException extends Exception
{
    public EmptyFileException(){};
    
    @Override
    public String toString()
    {
        return "File should not be empty!\nRestarting...";
    }
}

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

//------------------------------------------------------------------------------------------

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
    
    public static void app() throws IOException, EmptyStringException 
    {
        System.out.println("------------Lab2------------");
        EmptyStringException exString = new EmptyStringException();
        System.out.println("Color code validity check. Type 'x' to exit");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(true)
        {
            System.out.print("Enter color code to check: ");
            line = br.readLine();
            if (line.equals(""))
                throw exString;
            else if(line.equalsIgnoreCase("x"))
                break;
            else if (line.matches("#([0-9]|[A-F]|[a-f]){6}"))
                System.out.println("This is a color code");
            else
                System.out.println("This is not a color code");
        }
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

//================Lab3================
class lab3 extends Thread
{
    private Thread t;
    
    public static void app() throws IOException, EmptyFileException
    {    
        
        while (true)
        {         
            EmptyFileException exFile = new EmptyFileException();
            System.out.println("------------Lab3------------");
            Stack stk = new Stack();
            File source = new File("source.txt");
            if(source.length() == 0)
                throw exFile;
            BufferedReader br = new BufferedReader(new FileReader(source));
            
            String CurLine;	
            
            //Чтение файла и запись в стек
            
            System.out.println("Contents of the file: ");
            while((CurLine = br.readLine()) != null)
            {
                System.out.println(CurLine);
                stk.push(CurLine);
            }
        
            PrintStream output = new PrintStream(new FileOutputStream("source.txt"));
            PrintStream stdout = System.out;
            System.setOut(output);
        
            //Запись в файл обратно со стека
        
            while(!stk.empty())
            {
                System.out.println(stk.pop());
            }
        
            System.setOut(stdout);
            System.out.println("Order of lines has been reversed");  
        
            System.out.println("Launch again?[y/n]");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            try 
            {
                do
                {                    
                    line = br1.readLine();
                } while (!(line.equalsIgnoreCase("y")||line.equalsIgnoreCase("n")));
                if(line.equals("n"))
                    break;
            }
            catch(IOException e)
            {
                System.out.println("IOException occured");
            }
        }
    }
    
    @Override
    public void run() 
    {          
        while(true)
        {
            try 
            {
                app();
                break;
            }
            catch (IOException|EmptyFileException e) 
            {
                JOptionPane.showMessageDialog(null, "Data not found");
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
    public static String myText;
    private int x = 320;
    private final int y = 240;
    boolean reverse = false;
    private Color color;
    private final Color ColorArray[] = {Color.CYAN, Color.MAGENTA, Color.BLUE, Color.RED, Color.ORANGE};
    
    public lab4()
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
                color = ColorArray[rand.nextInt(5)];
            }
        }
        else 
        {
            x--;
            if(x == 0) 
            {
                reverse = false;
                color = ColorArray[rand.nextInt(5)];
            }
        }
        
        g2.setColor(color);
        g2.drawString(myText, x, y);
    }
    
    @Override
    public void run() 
    {
        SWINGEmptyStringException swExString = new SWINGEmptyStringException();
        JFrame frame = new JFrame("lab4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new lab4());
        
        while (true)
        {            
            try
            {
                myText = JOptionPane.showInputDialog("Write your text here");
                if (myText.equals(""))
                    throw swExString;
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
    
    void start()
    {
        t = new Thread (this, "lab1");
        t.start();
    }
}

//--------------------------------------------------------------------------------------

public class Lab2_Threads
{

    public static void main(String[] args)
    {
        lab1 thread_lab1 = new lab1();
        lab2 thread_lab2 = new lab2();
        lab3 thread_lab3 = new lab3();
        lab4 thread_lab4 = new lab4();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice = "";
        boolean correctValue;
        
        thread_lab4.start();
        do
        {            
            try
            {
                System.out.println("#########################\nSelect a lab to run(1, 2, 3) or 'x' to exit: "); 
                
                do
                {                    
                    choice = br.readLine();
                    correctValue = choice.equals("1")||choice.equals("2")||choice.equals("3")||choice.equals("x");
                } while (!correctValue);
                
                switch(choice)
                {
                    case "1":
                        thread_lab1.start();
                        thread_lab1.join();
                        break;
                    case "2":
                        thread_lab2.start();
                        thread_lab2.join();
                        break;
                    case "3":
                        thread_lab3.start();
                        thread_lab3.join();
                        break;
                } 
            } 
            catch(InterruptedException e)
            {
                JOptionPane.showMessageDialog(null, "Interrupted Exception");
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(null, "IOException");
            }
        }
        while (!choice.equalsIgnoreCase("x"));
        System.exit(0); 
    }
    
}
