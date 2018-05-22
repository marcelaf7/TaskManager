/*
 *This class handles prompting the user for input, taking user input, and handling all parts of the tasklist
 */

import java.util.ArrayList;
import java.util.Calendar;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class TaskList{
	private ArrayList<Task> tasks; //holds all the Tasks in the TaskList
	private int nextTaskId = 0; //holds the id value of the next Task that will be constructed
	private String saveFile = "file.json";

	//Constructor
	//Creates an empty TaskList
	public TaskList(){
		tasks = new ArrayList<Task>();
	}

	//returns the size of the task list
	public int size(){
		return tasks.size();
	}

	//saves all information from tasks to the specified by saveFile
	public boolean save(){
		JSONArray json = new JSONArray();

		for(int i = 0; i < tasks.size(); i++){
			JSONObject task = new JSONObject();
			JSONObject time = new JSONObject();

			time.put("minutes", tasks.get(i).getDeadln().get(Calendar.MINUTE));
			time.put("hours", tasks.get(i).getDeadln().get(Calendar.HOUR_OF_DAY));
			time.put("day", tasks.get(i).getDeadln().get(Calendar.DATE));
			time.put("month", tasks.get(i).getDeadln().get(Calendar.MONTH));
			time.put("year", tasks.get(i).getDeadln().get(Calendar.YEAR));

			task.put("deadln", time);
			task.put("desc", tasks.get(i).getDesc());
			task.put("complHrs", tasks.get(i).getComplHrs());

			json.add(task);
		}

		try{
			File f = new File(saveFile);
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(json.toString());
			bw.close();
		} catch (IOException ioe){
			System.err.println("There was an error saving the file");
	   		return false;
		} catch(Exception ex){
	       	System.err.println("There may have been an error saving the file");
	       	return false;
		}

		return false;
	}

	public boolean load() {



		return false;
	}

	//adds task to task list and returns whether or not it was successful
	public boolean addTask(String desc, Calendar deadln, int complHrs){
		Task newTask = new Task(nextTaskId, desc, deadln, complHrs);
		if(newTask == null){
			return false;
		}

		tasks.add(newTask);
		nextTaskId++;
		save();
		return true;
	}

	//Returns whether a task with id is in the task list
	public boolean inTaskList(int id){
		for(int i = 0; i < tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				return true;
			}
		}
		return false;
	}

	//takes an id as a paramete
	//returns a task from the tasklist with that id
	public Task getTaskById(int id){
		for(int i = 0; i < tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				return tasks.get(i);
			}
		}
		return null;
	}

	//Edits the deadline of task with id
	public boolean editTask(int id, Calendar deadln){
		Task idTask = getTaskById(id);
		if(idTask == null || deadln == null){
			return false;
		}

		idTask.setDeadln(deadln);
		save();
		return true;
	}

	//Edits description of task with id
	public boolean editTask(int id, String desc){
		Task idTask = getTaskById(id);
		if(idTask == null || desc == null || desc == ""){
			return false;
		}

		idTask.setDesc(desc);
		save();
		return true;
	}

	//Edits completion hours of task with id
	public boolean editTask(int id, int complHrs){
		Task idTask = getTaskById(id);
		if(idTask == null || complHrs < 0){
			return false;
		}

		idTask.setComplHrs(complHrs);
		save();
		return true;
	}

	public boolean deleteTask(int id){
		for(int i = 0; i < tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				tasks.remove(i);
				save();
				return true;
			}
		}
		return false;
	}

	//returns the highest priority task currently in the list
	private Task getHighestPrio(){
		int maxPrio = -1;
		for(int i = 0; i < tasks.size(); i++){
			if(tasks.get(i).getPriority() > maxPrio){
				maxPrio = tasks.get(i).getPriority();
			}
		}
		
		ArrayList<Task> prioTasks = new ArrayList<>();
		for(int i = 0; i < tasks.size(); i++){
			if(tasks.get(i).getPriority() == maxPrio){
				prioTasks.add(tasks.get(i));
			}
		}
		
		if(prioTasks.size() <= 0){
			return null;
		}

		if(prioTasks.size() == 1){
			return prioTasks.get(0);
		}
		
		Task prioTask = new Task(-1, null, null, 0);
		for(int i = 0; i < prioTasks.size(); i++){
			if(prioTask.getDeadln() == null){
				 prioTask = prioTasks.get(i);
				 continue;
			}else if(prioTasks.get(i).getDeadln().get(Calendar.DATE) < prioTask.getDeadln().get(Calendar.DATE)){
				prioTask = tasks.get(i);
			}
		}

		if(prioTask.getId() == -1){
			return null;
		}
		return prioTask;
	}

	//Returns the highest priority task as a String
	public String now(){
		Task now = getHighestPrio();
		if(now == null){
			return "ERROR: Highest priority task could not be found.";
		}
		return now.toString();
	}

	//Returns a string of all tasks currently in task list
	@Override
	public String toString(){
		if(tasks.size() <= 0){
			return "No tasks in task list";
		}

		String str = "";
		for(int i = 0; i < tasks.size(); i++){
			str += "\n" + tasks.get(i).toString();
		}
		return str;
	}
}