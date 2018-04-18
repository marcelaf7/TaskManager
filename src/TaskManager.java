/*
 *This class handles all user input and input
 */


import java.util.Scanner;
import java.util.Calendar;

class TaskManager
{
	private TaskList tl = new TaskList();

	public TaskList getTl()
	{
		return tl;
	}

	//Array holding all the commands the user can enter
	public String[][] cmds = 
	{
		{"help", "print a list of possible commands"},
		{"add", "add a task to the task list"},
		{"edit", "edit a task already in the task list"},
		{"list", "list all tasks in the task list"},
		{"now", "print the highest priority task"},
		{"q", "exits the program"}
	};

	//Print out the list of commands users can enter
	public void printCmds()
	{
		System.out.println("\nCommands:");
		for(int i = 0; i < cmds.length; i++)
		{
			System.out.println("\t'" + cmds[i][0] + "':\t" + cmds[i][1]);
		}
	}

	//Prompt the user to enter a command
	//Return the entered command
	public String promptUserCmd(Scanner sc)
	{
		System.out.println("\nPlease enter a command");
		System.out.println("Enter 'help' for a list of commands");

		return sc.nextLine();
	}


	//Parse date string and return Calendar set to that date
	//return Calendar
	public Calendar parseDate(String dateStr, Calendar date)
	{
		String[] strs = dateStr.split("/");

		if(strs.length < 3)
		{
			return null;
		}

		int[] ints = new int[3];

		try
		{
			ints[0] = Integer.parseInt(strs[0]);
			ints[1] = Integer.parseInt(strs[1]);
			ints[2] = Integer.parseInt(strs[2]);
		}
		catch(NumberFormatException e)
		{
			return null;
		}

		date.set(ints[2], ints[0], ints[1]);

		return date;
	}

	//Parse time string and return Calendar set to that time
	//return Calendar
	public Calendar parseTime(String timeStr, Calendar date)
	{
		String[] strs = timeStr.split(":");

		if(strs.length < 2)
		{
			return null;
		}

		int[] ints = new int[3];

		try
		{
			ints[0] = Integer.parseInt(strs[0]);
			ints[1] = Integer.parseInt(strs[1]);
		}
		catch(NumberFormatException e)
		{
			return null;
		}

		date.set(Calendar.HOUR_OF_DAY, ints[0]);
		date.set(Calendar.MINUTE, ints[1]);

		return date;
	}

	//Prompt user for date and time
	//Return Calendar with that information
	public Calendar promptUserDeadln(Scanner sc)
	{
		Calendar date = Calendar.getInstance();

		System.out.println("Enter deadline date of task (mm/dd/yyyy or mm/dd/yy)");
		String dateStr = sc.nextLine();
		date = parseDate(dateStr, date);

		System.out.println("Enter deadline time of task (hh:mm)");
		String timeStr = sc.nextLine();
		date = parseTime(timeStr, date);

		return date;
	}

	//Prompt user for estimated hours to complete
	//Return string value
	public int promptUserComplHrs(Scanner sc)
	{
		String hrsStr = "";
		int hrs = -1;
		while(hrsStr == null || hrsStr.equals("") || hrsStr.equals(" "))
		{
			System.out.println("Enter estimated hours to complete task");
			hrsStr = sc.nextLine();
			
			try
			{
				hrs = Integer.parseInt(hrsStr);
			}
			catch(NumberFormatException e)
			{
				hrs = -1;
			}
		}

		return hrs;
	}

	//Prompt user for a description
	//Return description
	public String promptUserDesc(Scanner sc)
	{
		String desc = "";
		while(desc == null || desc.equals("") || desc.equals(" "))
		{
			System.out.println("Enter description of task");
			desc = sc.nextLine();

			if(desc == null || desc.equals("") || desc.equals(" "))
			{
				System.err.println("ERROR: description invalid");
			}

		}

		return desc;

	}

	//Prompt user for all the information needed to add a task to TaskList
	//Return true if successful
	public boolean userAddTask(Scanner sc)
	{
		String desc = promptUserDesc(sc);
		Calendar date = promptUserDeadln(sc);
		int complHrs = promptUserComplHrs(sc);

		if(desc != null && date != null && complHrs >= 0)
		{
			System.out.println("Theoretically added: " + desc + " Due: " + date + " Estimated: " + complHrs);

			//tl.addTask(promptUserDesc(sc), promptUserDeadln(sc), promptUserComplHrs(sc));
			return true;
		}

		return false;
	}

	//TODO this code actually does nothing
	public boolean userEditTask(Scanner sc)
	{
/*		System.out.println("Enter a task ID");
		int id = sc.nextInt();
		

		sc = new Scanner(System.in);
		System.out.println("What do you want to edit?");
		System.out.println("You can edit 'description', 'deadline', 'completion hours'");
		String editCmd = sc.nextLine();
		
		boolean command = useEditCommand(editCmd, id, sc);
		
		if(!command)
		{
			System.out.println("Not recognized");
		}

		String editCmd = "";

		switch (editCmd)
		{
			case "description":
				String newDesc = getTaskDesc(sc);
				tasks.get(id).setDesc(newDesc);
				break;
			case "deadline":
				Calendar newDeadln = getDeadln(sc);
				tasks.get(id).setDeadln(newDeadln);
				break;
			case "completion hours":
				int newComplHrs = getComplHrs(sc);
				tasks.get(id).setComplHrs(newComplHrs);
				break;
			default:
				return false;
		}
		return true;*/

		return false;
	}

	//main function
	//loops until user enters "q" to quit the program
	public static void main(String[] args) 
    {
    	TaskManager tm = new TaskManager();
    	Scanner sc = new Scanner(System.in);

    	boolean keepRunning = true;

    	while(keepRunning)
    	{
    		String userCmd = tm.promptUserCmd(sc);
			switch(userCmd)
			{
				case "help":
					tm.printCmds();
					break;
				case "add":
					tm.userAddTask(sc);
					break;
				case "edit":
					tm.userEditTask(sc);
					break;
				case "list":
					System.out.println(tm.getTl());
					break;
				case "now":
					break;
				case "q":
					System.out.println("Exiting");
					System.exit(0);
					break;
				default:
					System.out.println("Command not recognized");
					break;
			}
		}

    	sc.close();
    }

}