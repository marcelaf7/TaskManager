/*
 *This class handles prompting the user for input, taking user input, and handling all parts of the tasklist
 */


import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskList
{
	private ArrayList<Task> tasks; //holds all the Tasks in the TaskList
	private int nextTaskID = 0; //holds the id value of the next Task that will be constructed

	//Constructor
	//Creates an empty TaskList
	public TaskList()
	{
		tasks = new ArrayList<Task>();
		Calendar date = Calendar.getInstance();
		date.set(0, 0, 0, 0, 0);
		Task task = new Task(nextTaskID, "Code Task Manager", date, 5);
		tasks.add(task);
		
	}

	//prints out the list of possible commands for the user
	private void printCommandsList()
	{
		System.out.println("Commands:");
		System.out.println("\thelp: print a list of possible commands");
		System.out.println("\tadd: add a task to the task list");
		System.out.println("\tedit: edit an already existing task");
		System.out.println("\tlist: list all tasks in the task list");
		System.out.println("\tnow: print the highest priority task");
		System.out.println("\tq: exits the program");
	}

	//parses the time string into an int[] of size 2
	private int[] parseTime(String time)
	{
		String[] strArr = time.split(":");
		int[] timeArr = new int[2];
		if(strArr.length == 2)
		{
			try
			{
				for(int i = 0; i < strArr.length; i++)
				{
					timeArr[i] = Integer.parseInt(strArr[i]);
				}
			}
			catch(NumberFormatException e)
			{
				return null;
			}
			catch(NullPointerException e)
			{
				return null;
			}

			if(timeArr[0] >= 0 && timeArr[0] <= 24 && timeArr[1] >= 0 && timeArr[1] <= 60)
			{
				return timeArr;
			}
		}
		return null;
	}

	//parses date string into an int[] of size 3
	private int[] parseDate(String dateStr)
	{
		String[] strArr = dateStr.split("/");
		int[] dateArr = new int[3];
		if(strArr.length == 3)
		{
			try
			{
				for(int i = 0; i < strArr.length; i++)
				{
					dateArr[i] = Integer.parseInt(strArr[i]);
				}
			}
			catch(NumberFormatException e)
			{
				return null;
			}
			catch(NullPointerException e)
			{
				return null;
			}
			if(dateArr[0] >= 1 && dateArr[0] <= 12 && dateArr[1] >= 0 && dateArr[1] <= 31 && dateArr[2] >= 2000 && dateArr[2] <= 10000)
			{
				return dateArr;
			}
		}
		return null;
	}

	//prompts the user for a new description for a task
	private String getTaskDesc(Scanner sc)
	{
		sc = new Scanner(System.in);
		System.out.println("Enter task description");
		String taskDesc = sc.nextLine();
		return taskDesc;
	}
	
	//prompts the user for a date and time for the due date of a task
	private Calendar getDeadln(Scanner sc)
	{		
		int[] dateArr = null;
		while(dateArr == null)
		{
			System.out.println("Enter due date (mm/dd/yyyy)");
			String dueDate = sc.next();
			dateArr = parseDate(dueDate);
		}

		int[] time = null;
		while(time == null)
		{
			System.out.println("Enter due time in 24 hour time (hh:mm)");
			String dueTime = sc.next();
			time = parseTime(dueTime);
		}

		Calendar date = Calendar.getInstance();
		date.set(dateArr[2], dateArr[0], dateArr[1], time[0], time[1]);
		
		return date;
	}
	
	//prompts user for a completion hours for the task
	private int getComplHrs(Scanner sc)
	{
		int complHrs = -1;
		while(complHrs == -1)
		{
			System.out.println("Enter estimated amount of hours to complete task");
			if(sc.hasNextInt())
			{
				complHrs = sc.nextInt();
			}
		}
		
		return complHrs;
	}
	
	//prompts the user for all information needed to add a task to the TaskList
	//then adds that task to the TaskList
	private void userAddTask(Scanner sc)
	{		
		String taskDesc = getTaskDesc(sc);
		Calendar date = getDeadln(sc);
		int complHrs = getComplHrs(sc);
		
		Task newTask = new Task(nextTaskID, taskDesc, date, complHrs);
		tasks.add(newTask);
		nextTaskID++;
	}

	//prints out all tasks currently in task list
	private void listAllTasks()
	{
		if(tasks.size() <= 0)
		{
			System.out.println("No tasks entered");
			return;
		}
			
		for(int i = 0; i < tasks.size(); i++)
		{
			System.out.println(tasks.get(i).toString());
		}
	}

	//prints out the highest priority task currently in the list
	private void getHighestPrio()
	{
		int maxPrio = -1;
		
		for(int i = 0; i < tasks.size(); i++)
		{
			if(tasks.get(i).getPriority() > maxPrio)
			{
				maxPrio = tasks.get(i).getPriority();
			}
		}
		
		ArrayList<Task> prioTasks = new ArrayList<>();
		
		for(int i = 0; i < tasks.size(); i++)
		{
			if(tasks.get(i).getPriority() == maxPrio)
			{
				prioTasks.add(tasks.get(i));
			}
		}
		
		if(prioTasks.size() <= 0)
		{
			System.out.println("Error: Failed to get now task. Maybe not enough tasks entered.");
			return;
		}
		
		Task prioTask = new Task(-1, null, null, 0);

		for(int i = 0; i < prioTasks.size(); i++)
		{
			if(prioTask.getDeadln() == null)
			{
				 prioTask = prioTasks.get(i);
				 continue;
			}
			else if(prioTasks.get(i).getDeadln().get(Calendar.DATE) < prioTask.getDeadln().get(Calendar.DATE))
			{
				prioTask = tasks.get(i);
			}
		}

		if(prioTask.getId() == -1)
		{
			System.out.println("Error: Failed to get now task. Maybe not enough tasks entered.");
			return;
		}

		System.out.println("Now task:");
		System.out.println(prioTask);
	}

	
	//decides what to do with the user's command for editing a task
	private boolean useEditCommand(String editCmd, int id, Scanner sc)
	{
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
		return true;
	}
	
	//prompts the user for which task they want to edit and what part of the task to edit
	private void editTask(Scanner sc)
	{
		System.out.println("Enter a task ID");
		int id = sc.nextInt();
		Task task = null;
		for(int i = 0; i < tasks.size(); i++)
		{
			if(tasks.get(i).getId() == id)
			{
				task = tasks.get(i);
			}
		}
		if(task == null)
		{
			System.out.println("Could not find a task by that id");
			return;
		}

		sc = new Scanner(System.in);
		System.out.println("What do you want to edit?");
		System.out.println("You can edit 'description', 'deadline', 'completion hours'");
		String editCmd = sc.nextLine();
		
		boolean command = useEditCommand(editCmd, id, sc);
		
		if(!command)
		{
			System.out.println("Not recognized");
		}
	}
	
	//decides what to do with the command the user entered
	private void useCommand(String command, Scanner sc)
	{
		switch(command)
		{
			case "help":
				printCommandsList();
				break;
			case "add":
				userAddTask(sc);
				break;
			case "list":
				listAllTasks();
				break;
			case "now":
				getHighestPrio();
				break;
			case "edit":
				editTask(sc);
				break;
			case "q":
				System.exit(0);
				break;
			default:
				System.out.println("Command not recognized");
		}
	}

	//prompts the user for a command and calls useCommand() once a command is entered
	public boolean getCommand(boolean help, Scanner sc)
	{
		System.out.println("Enter a command");
		if(help)
			System.out.println("Enter \"help\" for a list of commands");
		String command = sc.next();
		useCommand(command, sc);
		return true;
	}
}