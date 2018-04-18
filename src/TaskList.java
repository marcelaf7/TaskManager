/*
 *This class handles prompting the user for input, taking user input, and handling all parts of the tasklist
 */

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

	//Returns whether a task with id is in the task list
	public boolean inTaskList(int id)
	{
		for(int i = 0; i < tasks.size(); i++)
		{
			if(tasks.get(i).getId() == id)
			{
				return true;
			}
		}
		return false;
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

	//prints out all tasks currently in task list
	@Override
	public String toString()
	{
		if(tasks.size() <= 0)
		{
			return null;
		}

		String str = "";
			
		for(int i = 0; i < tasks.size(); i++)
		{
			str += "\n" + tasks.get(i).toString();
		}

		return str;
	}
	
}