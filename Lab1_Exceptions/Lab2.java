/*
5.  Написати регулярне вираження, що визначає чи є даний рядок шестнадцатиричным ідентифікатором кольору в HTML. 
    Де #FFFFFF для білого, #000000 для чорного, #FF0000 для червоного і т.д.
    Приклад правильних виражень: #FFFFFF,  #FF3421, #00ff00.
    Приклад неправильних виражень: 232323, f#fddee, #fd2.
 */

package lab2;

import java.io.*;

class EmptyStringException extends Exception
{
    public EmptyStringException(){};
    
    @Override
    public String toString()
    {
        return "This should not be empty!\nRestarting...";
    }
}

public class Lab2 
{ 

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
    
    public static void main(String[] args) 
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


//if(line.matches("#([0-9]|[A-F]|[a-f]){2}([0-9]|[A-F]|[a-f]){2}([0-9]|[A-F]|[a-f]){2}"))
//"#([0-9]|[A-F]|[a-f]){2}([0-9]|[A-F]|[a-f]){2}([0-9]|[A-F]|[a-f]){2}"
        
        
         //{8,} does not work
        //System.out.println(line.matches("(?=[A-Z]+)(?=[a-z]+)(?=[0-9]+){8,}"));
        //Example: ^(?=.*one)(?=.*two)(?=.*three).*$
        //^(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*[0-9]).*${8,}
        //"^(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*[0-9]){8,}.*$"
