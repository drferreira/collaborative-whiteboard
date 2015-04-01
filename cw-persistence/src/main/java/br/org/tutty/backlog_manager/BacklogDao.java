package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.Dao;
import cw.entities.Project;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public interface BacklogDao extends Dao {
    List<Story> fetchAllStories() throws DataNotFoundException;

    Long getNextSequenceStory(Project project);
}
