//1. Увести рядки з файлу, записати їх у стек. Вивести рядки у файл у зворотному порядку.

package lab31;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

class EmptyFileException extends Exception
{
    public EmptyFileException(){};
    
    @Override
    public String toString()
    {
        return "File should not be empty!\nRestarting...";
    }
}

public class Lab31 
{
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
    
    public static void main(String[] args) 
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
