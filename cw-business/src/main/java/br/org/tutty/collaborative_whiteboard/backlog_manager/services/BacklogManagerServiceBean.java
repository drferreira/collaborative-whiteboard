package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import br.org.tutty.backlog_manager.BacklogDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.dtos.LoggedUser;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
@Local(BacklogManagerService.class)
@Stateless
public class BacklogManagerServiceBean implements BacklogManagerService {
    @Inject
    private SessionContext sessionContext;

    @Inject
    private BacklogDao backlogDao;

    @Override
    public List<Story> fetchAllStories() throws DataNotFoundException {
        return backlogDao.fetchStories();
    }

    @Override
    public Story getEmptyStory() {
        LoggedUser loggedUser = sessionContext.getLoggedUser();
        Story story = new Story(loggedUser.getUser());
        story.setCode("TESTE CODE");
        return story;
    }
}
