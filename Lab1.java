package lab1;

import java.io.*;
import java.util.*;

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

public class Lab1
{
    
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

    public static void main(String args[]) 
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