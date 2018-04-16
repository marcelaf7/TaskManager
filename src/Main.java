import java.util.Scanner;

class Main 
{
    public static void main(String[] args) 
    {
    	Scanner sc = new Scanner(System.in);
    	TaskList tl = new TaskList();
    	boolean help = true; //prints help command on first user prompt
    	boolean keepRunning = true;
    	while(keepRunning) //continues prompting the user until the program exits
    	{
    		keepRunning = tl.getCommand(help, sc);
    		help = false; //stops prompting for help command after first prompt
    	}
    	sc.close();
    }
}