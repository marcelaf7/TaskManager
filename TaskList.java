import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskList
{
	private ArrayList<Task> tasks;
	private int nextTaskID = 0;

	public TaskList()
	{
		tasks = new ArrayList<Task>();
	}

	private void printCommandsList()
	{
		System.out.println("Commands:");
		System.out.println("\thelp: print a list of possible commands");
		System.out.println("\tadd: add a task to the task list");
		System.out.println("\tall: list all tasks in the task list");
		System.out.println("\tnow: print the highest priority task");
		System.out.println("\tq: exits the program");
	}

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

	private void userAddTask()
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter task description");
		String taskDesc = reader.next();

		int[] dateArr = null;
		while(dateArr == null)
		{
			System.out.println("Enter due date (mm/dd/yyyy)");
			String dueDate = reader.next();
			dateArr = parseDate(dueDate);
		}

		int[] time = null;
		while(time == null)
		{
			System.out.println("Enter due time in 24 hour time (hh:mm)");
			String dueTime = reader.next();
			time = parseTime(dueTime);
		}

		Calendar date = Calendar.getInstance();
		date.set(dateArr[2], dateArr[0], dateArr[1], time[0], time[1]);

		System.out.println("Enter estimated amount of hours to complete task");
		int complHrs = reader.nextInt();

		Task newTask = new Task(nextTaskID, taskDesc, date, complHrs);
		tasks.add(newTask);
		nextTaskID++;
	}

	private void listAllTasks()
	{
		for(int i = 0; i < tasks.size(); i++)
		{
			System.out.println(tasks.get(i).toString());
		}
	}

	private void getNowTask()
	{
		Task now = new Task(-1, null, null, 0);

		for(int i = 0; i < tasks.size(); i++)
		{
			if(now.getPrioPoints() < tasks.get(i).getPrioPoints())
			{
				now = tasks.get(i);
			}
		}

		if(now.getTaskID() == -1)
		{
			System.out.println("Error: Failed to get now task. Maybe not enough tasks entered.");
			return;
		}

		System.out.println("Now task:");
		System.out.println(now);
	}

	private void useCommand(String command)
	{
		switch(command)
		{
			case "help":
				printCommandsList();
				break;
			case "add":
				userAddTask();
				break;
			case "all":
				listAllTasks();
				break;
			case "now":
				getNowTask();
				break;
			case "q":
				System.exit(0);
				break;
			default:
				System.out.println("Command not recognized");
		}
	}

	public void getCommand(boolean help)
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a command");
		if(help)
			System.out.println("Enter \"help\" for a list of commands");
		String command = reader.next();
		useCommand(command);
	}
}