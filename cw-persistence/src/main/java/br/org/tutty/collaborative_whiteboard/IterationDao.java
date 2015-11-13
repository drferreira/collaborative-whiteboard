package br.org.tutty.collaborative_whiteboard;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import cw.exceptions.DataNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
public interface IterationDao extends Dao{
    List<Story> fetchStories(Iteration iteration) throws DataNotFoundException;

    Boolean existIterationInRange(Date init, Date end);

    Iteration getCurrentIteration() throws DataNotFoundException;

    List<Iteration> fetchIterations() throws DataNotFoundException;

    List<Story> fetchFinalizedStories(Iteration iteration) throws DataNotFoundException;

    Iteration fetchIterationByName(String iterationName) throws DataNotFoundException;
}