class Main 
{
    public static void main(String[] args) 
    {
    	TaskList tl = new TaskList();
    	boolean help = true; //prints help command on first user prompt
    	while(true) //continues prompting the user until the program exits
    	{
    		tl.getCommand(help);
    		help = false; //stops prompting for help command after first prompt
    	}
    }
}