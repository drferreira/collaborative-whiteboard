package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.*;
import backlog_manager.enums.StoryStatus;
import backlog_manager.enums.TaskStatus;
import br.org.tutty.backlog_manager.StoryDao;
import br.org.tutty.backlog_manager.TaskDao;
import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import br.org.tutty.collaborative_whiteboard.backlog_manager.factories.CodeFactory;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.entities.Stage;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by drferreira on 11/03/15.
 */
@Stateless
@Local(BacklogManagerService.class)
public class BacklogManagerServiceBean implements BacklogManagerService {
    @Inject
    private SessionContext sessionContext;

    @Inject
    private WhiteboardDao whiteboardDao;

    @Inject
    private StoryDao storyDao;

    @Inject
    private TaskDao taskDao;

    @Override
    public List<Story> fetchAllStories() throws DataNotFoundException {
        return storyDao.fetchAllStories();
    }

    @Override
    public List<Story> fetchAnalyzedStories() throws DataNotFoundException {
        return storyDao.fetchStories(StoryStatus.ANALYZED);
    }

    @Override
    public List<Story> fetch(ProjectArea projectArea) throws DataNotFoundException {
        return storyDao.fetchStories(projectArea);
    }

    @Override
    public Story fetchByCode(String code) throws DataNotFoundException {
        return storyDao.fetchByCode(code);
    }

    @Override
    public void updateBacklog(List<Story> stories) {
        updateStories(stories);
    }

    @Override
    public Boolean projectAreaIsAssignedToStory(ProjectArea projectArea) {
        try {
            storyDao.fetchStories(projectArea);
            return Boolean.TRUE;

        } catch (DataNotFoundException e) {
            return Boolean.FALSE;
        }
    }

    public Story populateStoryCode(Story story) {
        Project project = story.getProject();
        ProjectArea projectArea = story.getProjectArea();

        if (story.getCode() == null) {
            String code = CodeFactory.story(storyDao, project, projectArea);
            story.setCode(code);
        }

        return story;
    }

    public Story populateBranchCode(Story story) {
        if (story.getBranch() == null || story.getBranch().isEmpty()) {
            String code = story.getCode();
            Project project = story.getProject();

            story.setBranch(CodeFactory.branch(project, code));
            return story;
        }

        return story;
    }

    private void updateStories(List<Story> stories) {
        stories.forEach(new Consumer<Story>() {
            @Override
            public void accept(Story story) {
                updateStory(story);
            }
        });
    }

    /**
     * Metodo responsavel por cria uma nova estoria.
     * Cria novo codigo para estoria e verifica necessidade de popular branch
     *
     * @param story
     */
    @Override
    public void createStory(Story story) {
        Story storyWithNewCode = populateStoryCode(story);
        Story storyWithBranch = populateBranchCode(storyWithNewCode);

        storyDao.persist(storyWithBranch);
        storyDao.persist(new StoryStatusLog(StoryStatus.WAITING, story.getAuthor(), story));
    }

    @Override
    public void createTask(Task task) {
        Task populatedTask = populateTask(task);
        TaskStatusLog taskStatusLog = new TaskStatusLog(TaskStatus.AVAILABLE, sessionContext.getLoggedUser().getUser(), populatedTask);

        taskDao.persist(populatedTask);
        taskDao.persist(taskStatusLog);
    }

    private Task populateTask(Task task) {
        User author = sessionContext.getLoggedUser().getUser();
        Stage stage = whiteboardDao.fetchInitialStage();
        String code = CodeFactory.task(taskDao, task.getStory());

        task.setStage(stage);
        task.setCode(code);
        task.setAuthor(author);

        return task;
    }

    @Override
    public List<Task> fetchTasks(Story selectedStory) throws DataNotFoundException {
        return taskDao.fetchByStory(selectedStory);
    }

    @Override
    public void removeTask(Task selectedTask) {
        taskDao.remove(selectedTask);
    }

    /**
     * Metodo para atualizar uma estoria existente.
     *
     * @param story
     */
    @Override
    public void updateStory(Story story) {
        Story storyWithNewCode = populateStoryCode(story);
        Story storyWithDefaultBranch = populateBranchCode(storyWithNewCode);

        storyDao.update(storyWithDefaultBranch);
    }

    @Override
    public void updateTask(Task task) {
        storyDao.update(task);
    }


    @Override
    public void restoreStory(Story story) {
        changeStoryStatus(story, StoryStatus.WAITING);
    }

    @Override
    public void removeStory(Story story) {
        changeStoryStatus(story, StoryStatus.REMOVED);
    }

    @Override
    public void provideStory(Story story) {
        changeStoryStatus(story, StoryStatus.AVAILABLE);
    }

    @Override
    public void finalizeStory(Story story) {
        changeStoryStatus(story, StoryStatus.FINALIZED);
    }

    @Override
    public StoryStatusLog getCurrentStoryStatusLog(Story story) throws DataNotFoundException {
        return storyDao.getStoryStatusLog(story);
    }

    @Override
    public StoryStatus getCurrentStatus(Story story) throws DataNotFoundException {
        StoryStatusLog storyStatusLog;
        storyStatusLog = getCurrentStoryStatusLog(story);
        return storyStatusLog.getStoryStatus();
    }

    @Override
    public void changeStoryStatus(Story story, StoryStatus storyStatus) {
        User user = sessionContext.getLoggedUser().getUser();
        StoryStatusLog storyStatusLog = new StoryStatusLog(storyStatus, user, story);
        storyDao.persist(storyStatusLog);
    }

    @Override
    public void initAnalyzeStory(Story story) {
        User user = sessionContext.getLoggedUser().getUser();
        Analysis analysis = new Analysis(user, story);

        changeStoryStatus(story, StoryStatus.IN_ANALYSIS);
        storyDao.persist(analysis);
    }

    @Override
    public void endAnalyzeStory(Story story) throws DataNotFoundException {
        User user = sessionContext.getLoggedUser().getUser();
        Analysis lastStoryAnalysis = storyDao.getLastStoryAnalysis(story);

        lastStoryAnalysis.finish(user);

        changeStoryStatus(story, StoryStatus.ANALYZED);
        storyDao.update(lastStoryAnalysis);
    }

    @Override
    public void uploadFile(Story story, InputStream inputStream, String fileName) throws IOException {
        User user = sessionContext.getLoggedUser().getUser();
        UploadedFile uploadedFile = new UploadedFile(user, story, inputStream, fileName);
        storyDao.persist(uploadedFile);
    }

    @Override
    public void removeFile(UploadedFile uploadedFile) {
        storyDao.remove(uploadedFile);
    }

    @Override
    public List<UploadedFile> fetchFiles(Story selectedStory) throws DataNotFoundException {
        return storyDao.fetchFiles(selectedStory);
    }
}
