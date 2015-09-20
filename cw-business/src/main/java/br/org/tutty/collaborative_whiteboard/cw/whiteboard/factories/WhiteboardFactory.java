package br.org.tutty.collaborative_whiteboard.cw.whiteboard.factories;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import br.org.tutty.backlog_manager.TaskDao;
import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import cw.entities.Stage;
import dtos.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by drferreira on 13/07/15.
 */
public class WhiteboardFactory {

	private StageFactory stageFactory;
	private TaskFactory taskFactory;
	private StoryFactory storyFactory;

	private WhiteboardDao whiteboardDao;
	private TaskDao taskDao;

	private Set<Stage> allStages;
	private List<Task> allTasks;
	private List<Story> allStories;

	public WhiteboardFactory(WhiteboardDao whiteboardDao, TaskDao taskDao) {
		this.whiteboardDao = whiteboardDao;
		this.taskDao = taskDao;
		this.allStories = new ArrayList<>();

		allStages = whiteboardDao.fetchAllStages();
		allTasks = taskDao.fetchForWhiteboard();

		allTasks.forEach(new Consumer<Task>() {
			@Override
			public void accept(Task task) {
				if (!allStories.stream().anyMatch(story -> story.getCode().equals(task.getStory().getCode()))) {
					allStories.add(task.getStory());
				}
			}
		});

		stageFactory = new StageFactory(allStages);
		taskFactory = new TaskFactory(allTasks, taskDao);
		storyFactory = new StoryFactory(allStories);
	}

	public WhiteboardMailable builderMailableWhiteboard() {
		try {
			return mount();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private WhiteboardMailable mount() throws NoSuchFieldException, IllegalAccessException {
		WhiteboardMailable whiteboardMailable = new WhiteboardMailable();

		for (Stage stage: allStages){
			StagesMailable stageMailable = stageFactory.builderStage(stage);

			for (Task task: allTasks) {
				if (stage.getName().equals(task.getStage().getName())) {
					TaskMailable taskMailable = taskFactory.buildTaskMailable(task);

					Story story = task.getStory();

					if (!stageMailable.existStory(story.getCode())) {
						StoryMailable storyMailable = storyFactory.buildStoryMailable(story);
						storyMailable.addTask(taskMailable);

						stageMailable.addStory(storyMailable);

					} else {
						StoryMailable existingStory = stageMailable.getStoryByCode(story.getCode());
						existingStory.addTask(taskMailable);
					}
				}
			}

			whiteboardMailable.addStage(stageMailable);
		}

		return whiteboardMailable;
	}
}
