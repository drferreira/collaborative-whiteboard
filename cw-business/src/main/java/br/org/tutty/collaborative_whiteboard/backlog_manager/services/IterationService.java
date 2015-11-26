package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import backlog_manager.exceptions.IterationAlreadySetException;
import backlog_manager.exceptions.IterationNotFoundException;
import cw.rest.model.iteration.CurrentIteration;
import cw.rest.model.iteration.Iteration;
import cw.exceptions.DataNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
public interface IterationService {

    void addStory(Story story, backlog_manager.entities.Iteration iteration) throws IterationNotFoundException;
    void addStory(String storyCode, String iterationName) throws IterationNotFoundException;

    void removeStory(Story story);
    void removeStory(String storyCode) throws DataNotFoundException;

    List<backlog_manager.entities.Iteration> fetchIterations();

    backlog_manager.entities.Iteration getCurrentIteration() throws DataNotFoundException;

    List<Story> fetchStoriesAvailableForIteration();

    List<Story> fetchStories(backlog_manager.entities.Iteration iteration) throws DataNotFoundException;

    void create(List<Story> stories, String name, Date init, Date end) throws IterationAlreadySetException;

    Float getProgressIteration(backlog_manager.entities.Iteration iteration);

    Long fetchIterationPoints(backlog_manager.entities.Iteration iteration);

    CurrentIteration fetchCurrentIterationData() throws DataNotFoundException;

    List<Iteration> listIterations() throws DataNotFoundException;

    backlog_manager.entities.Iteration fetchByName(String iterationName) throws DataNotFoundException;
}
