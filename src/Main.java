package src;

class Main 
{
    public static void main(String[] args) 
    {
    	TaskList tl = new TaskList();
    	boolean help = true;
    	while(true)
    	{
    		tl.getCommand(help);
    		help = false;
    	}
    }
}