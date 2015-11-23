package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import backlog_manager.exceptions.IterationAlreadySetException;
import backlog_manager.exceptions.IterationNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.dto.CurrentIteration;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationDto;
import br.org.tutty.collaborative_whiteboard.cw.dto.StoryDto;
import cw.exceptions.DataNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
public interface IterationService {

    void addStory(Story story, Iteration iteration) throws IterationNotFoundException;
    void addStory(String storyCode, String iterationName) throws IterationNotFoundException;

    void removeStory(Story story);
    void removeStory(String storyCode) throws DataNotFoundException;

    List<Iteration> fetchIterations();

    Iteration getCurrentIteration() throws DataNotFoundException;

    List<Story> fetchStoriesAvailableForIteration();

    List<Story> fetchStories(Iteration iteration) throws DataNotFoundException;

    void create(List<Story> stories, String name, Date init, Date end) throws IterationAlreadySetException;

    Float getProgressIteration(Iteration iteration);

    Long fetchIterationPoints(Iteration iteration);

    CurrentIteration fetchCurrentIterationData() throws DataNotFoundException;

    List<IterationDto> listIterations() throws DataNotFoundException;

    Iteration fetchByName(String iterationName) throws DataNotFoundException;
}
