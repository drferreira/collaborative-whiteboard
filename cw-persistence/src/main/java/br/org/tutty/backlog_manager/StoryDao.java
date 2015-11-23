package br.org.tutty.backlog_manager;

import backlog_manager.entities.*;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.Dao;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public interface StoryDao extends Dao {
    List<Story> fetchAllStories() throws DataNotFoundException;

    Story fetchByCode(String code) throws DataNotFoundException;

    Long getNextSequenceStory(Project project);

    List<Story> fetchStories(ProjectArea projectArea) throws DataNotFoundException;

    List<Story> fetchStories(StoryStatus storyStatus) throws DataNotFoundException;

    StoryStatusLog getStoryStatusLog(Story story) throws DataNotFoundException;

    Analysis getLastStoryAnalysis(Story story) throws DataNotFoundException;

    List<UploadedFile> fetchFiles(Story selectedStory) throws DataNotFoundException;
}
