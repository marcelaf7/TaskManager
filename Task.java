public class Task
{
	private int taskID;
	private String taskDesc;
	private int[] dueDate;
	private int[] dueTime;
	private int complHrs;
	private int prioPoints;


	public Task(int startTaskID, String startTaskDesc, int[] startDueDate, int[] startDueTime, int startComplHrs)
	{
		taskID = startTaskID;
		taskDesc = startTaskDesc;
		dueDate = startDueDate;
		dueTime = startDueTime;
		complHrs = startComplHrs;
		calcPrioPoints();
	}

	private void calcPrioPoints()
	{
		prioPoints = -1;
	}

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

	public void setDueDate(int[] newDueDate)
	{
		dueDate = newDueDate;
	}

	public int[] getDueDate()
	{
		return dueDate;
	}

	public void setDueTime(int[] newDueTime)
	{
		dueTime = newDueTime;
	}

	public int[] getDueTime()
	{
		return dueTime;
	}


	public void setComplHrs(int newComplHrs)
	{
		complHrs = newComplHrs;
	}

	public int getComplHrs()
	{
		return complHrs;
	}

	@Override
	public String toString()
	{
		return 
			"taskID: " + taskID + "\tTask Description: " + taskDesc 
			+ 
			"\t Due: " + dueDate[0] + "/" + dueDate[1] + "/" + dueDate[2] 
			+ 
			" at " + dueTime[0] + ":" + dueTime[1] 
			+ 
			"\tCompletion Hours: " + complHrs;
	}

}