package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.entities.Task;
import backlog_manager.entities.UploadedFile;
import backlog_manager.enums.StoryStatus;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public interface BacklogManagerService {
    List<Story> fetchAllStories() throws DataNotFoundException;

    List<Story> fetchAnalyzedStories() throws DataNotFoundException;

    List<Story> fetch(ProjectArea projectArea) throws DataNotFoundException;

    Story fetchByCode(String code) throws DataNotFoundException;

    void updateBacklog(List<Story> stories);

    Boolean projectAreaIsAssignedToStory(ProjectArea projectArea);

    void createStory(Story story);

    void updateStory(Story story);

    void updateTask(Task task);

    void restoreStory(Story story);

    void removeStory(Story story);

    void provideStory(Story story);

    void finalizeStory(Story story);

    StoryStatusLog getCurrentStoryStatusLog(Story story) throws DataNotFoundException;

    StoryStatus getCurrentStatus(Story story) throws DataNotFoundException;

    void changeStoryStatus(Story story, StoryStatus storyStatus);

    void createTask(Task task);

    List<Task> fetchTasks(Story selectedStory) throws DataNotFoundException;

    void removeTask(Task selectedTask);

    void initAnalyzeStory(Story selectedStory);

    void endAnalyzeStory(Story story) throws DataNotFoundException;

    void uploadFile(Story story, InputStream inputStream, String fileName) throws IOException;

    List<UploadedFile> fetchFiles(Story selectedStory) throws DataNotFoundException;

    void removeFile(UploadedFile uploadedFile);

    UploadedFile fetchFile(String storyCode, String fileName) throws DataNotFoundException;

    void removeFile(String storyCode, String fileName) throws DataNotFoundException;
}
