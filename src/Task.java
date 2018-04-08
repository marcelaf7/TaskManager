package src;

import java.util.Calendar;
import java.util.Date;

public class Task
{
	private int taskID;
	private String taskDesc;
	private Calendar dueDate;
	private int complHrs;
	private int prioPoints;

	/**
	 *Constructs a Task object
	 *
	 *@param taskID A unique ID number that corresponds to that task
	 *@param taskDesc A description of the task
	 *@param dueDate Date the task is due as Calendar object
	 *@param ocmplHrs Estimated amount of hours it will take to complete task
	 */
	public Task(int taskID, String taskDesc, Calendar dueDate, int complHrs)
	{
		this.taskID = taskID;
		this.taskDesc = taskDesc;
		this.dueDate = dueDate;
		this.complHrs = complHrs;
		calcPrioPoints();
	}

	private void calcPrioPoints()
	{
		if(dueDate == null)
		{
			prioPoints = -1;
			return;
		}
		Calendar currDate = Calendar.getInstance();

		int diffYr = dueDate.get(Calendar.YEAR) - currDate.get(Calendar.YEAR);
		int diffMnth = dueDate.get(Calendar.MONTH) - currDate.get(Calendar.MONTH);
		int diffDay = dueDate.get(Calendar.DAY_OF_MONTH) - currDate.get(Calendar.DAY_OF_MONTH);
		int diffHr = dueDate.get(Calendar.HOUR_OF_DAY) - currDate.get(Calendar.HOUR_OF_DAY);
		int diffMin = dueDate.get(Calendar.MINUTE) - currDate.get(Calendar.MINUTE);

		if(diffMin < 0)
		{
			diffMin += 60;
			diffHr--;
		}

		if(diffHr < 0)
		{
			diffHr += 24;
			diffDay--;
		}

		if(diffDay < 0)
		{
			diffDay += 31;
			diffMnth--;
		}

		if(diffMnth < 0)
		{
			diffMnth += 12;
			diffYr--;
		}

		if(diffYr < 0)
		{
			prioPoints = -1;
		}

		diffHr += diffDay * 24;
		diffHr += diffMnth * 720;
		diffHr += diffYr * 8640;

		prioPoints = (complHrs / diffHr) * 100;
	}

	/**
	 *Returns the taskID for the task
	 *taskID is an integer identifier for that task
	 *
	 *@return taskID
	 */
	public int getTaskID()
	{
		return taskID;
	}

	public void setTaskDesc(String newTaskDesc)
	{
		taskDesc = newTaskDesc;
	}

	public String getTaskDesc()
	{
		return taskDesc;
	}

	public void setDueDate(Calendar newDueDate)
	{
		dueDate = newDueDate;
	}

	public Calendar getDueDate()
	{
		return dueDate;
	}

	public void setComplHrs(int newComplHrs)
	{
		complHrs = newComplHrs;
	}

	public int getComplHrs()
	{
		return complHrs;
	}

	public double getPrioPoints()
	{
		return prioPoints;
	}

	@Override
	public String toString()
	{
		return 
			"taskID: " + taskID + "\tTask Description: " + taskDesc 
			+ 
			"\n\t Due: " + dueDate.getTime() + "\tCompletion Hours: " + complHrs;
	}

}