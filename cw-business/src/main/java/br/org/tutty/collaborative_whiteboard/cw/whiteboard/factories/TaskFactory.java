package br.org.tutty.collaborative_whiteboard.cw.whiteboard.factories;

import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import backlog_manager.enums.TaskStatus;
import br.org.tutty.Equalizer;
import br.org.tutty.backlog_manager.TaskDao;
import cw.entities.User;
import dtos.TaskMailable;
import dtos.TaskStatusMailable;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by drferreira on 19/09/15.
 */
public class TaskFactory {
	private TaskDao taskDao;
	private List<Task> tasks;

	public TaskFactory(List<Task> tasks, TaskDao taskDao) {
		this.tasks = tasks;
		this.taskDao = taskDao;
	}

	public List<TaskMailable> getTaskMailables() {
		List<TaskMailable> taskMailables = new ArrayList<>();

		for(Task task : tasks){
			try{
				taskMailables.add(buildTaskMailable(task));
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return taskMailables;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public TaskMailable buildTaskMailable(Task task) throws NoSuchFieldException, IllegalAccessException {
		TaskMailable taskMailable = new TaskMailable();

		try {
			TaskStatusLog taskStatusLog = taskDao.fetchTaskStatusLog(task);

			taskMailable.setTaskStatus(buildTaskStatusMailable(taskStatusLog));
			taskMailable.setProfilePicture(buildImageUser(taskStatusLog));
		}catch (Exception e){
			e.printStackTrace();
		}

		Equalizer.equalize(task, taskMailable);

		return taskMailable;
	}

	private TaskStatusMailable buildTaskStatusMailable(TaskStatusLog taskStatusLog){
		TaskStatusMailable taskStatusMailable = new TaskStatusMailable();

		try{
			Equalizer.equalize(taskStatusLog, taskStatusMailable);
			taskStatusMailable.setValue(taskStatusLog.getTaskStatus().name());

			return taskStatusMailable;
		}catch (Exception e){
			e.printStackTrace();
			return taskStatusMailable;
		}
	}

	private String buildImageUser(TaskStatusLog taskStatusLog){
		if(TaskStatus.BUSY.equals(taskStatusLog.getTaskStatus())){
			User user = taskStatusLog.getUser();

			return convertImage(user.getProfilePicture());
		}

		return "";
	}


	private String convertImage(byte[] image){
		return Base64.getEncoder().encodeToString(image);
	}
}
