package br.org.tutty.collaborative_whiteboard.cw.service;

import backlog_manager.entities.Task;
import br.org.tutty.backlog_manager.StoryDao;
import br.org.tutty.backlog_manager.TaskDao;
import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import br.org.tutty.collaborative_whiteboard.cw.whiteboard.factories.WhiteboardFactory;
import br.org.tutty.collaborative_whiteboard.cw.filters.StageFilters;
import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;
import dtos.WhiteboardMailable;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
@Stateless
@Local(WhiteboardService.class)
public class WhiteboardServiceBean implements WhiteboardService, Serializable {

    @Inject
    private WhiteboardHandler whiteboardHandler;

    @Inject
    public WhiteboardDao whiteboardDao;

    @Inject
    public TaskDao taskDao;

    @Inject
    public StoryDao storyDao;

    @Override
    public Set<Stage> fetchStages() throws DataNotFoundException {
        return whiteboardDao.fetchAllStages();
    }

    public WhiteboardMailable fetchWhiteboardMailable(){
        WhiteboardFactory whiteboardFactory = new WhiteboardFactory(whiteboardDao, taskDao);
        List<Task> tasks = new ArrayList<>();
        Set<Stage> stages = new HashSet<>();

        stages.addAll(whiteboardDao.fetchAllStages());
        tasks.addAll(taskDao.fetchForWhiteboard());

        return whiteboardFactory.builderMailableWhiteboard();
    }

    @Override
    public void createStage(Stage stage) throws DataNotFoundException {
        Long count = whiteboardDao.count(Stage.class);
        stage.setPosition(count);
        whiteboardDao.persist(stage);

        refreshAllWhiteboards();
    }

    @Override
    public void removeStage(Stage stage) {
        whiteboardDao.remove(stage);
    }

    @Override
    public Stage fetchPreviousStage(Stage stageReference) throws DataNotFoundException {
        Set<Stage> stages = fetchStages();
        return  StageFilters.fetchLastBefore(stageReference, stages);
    }

    @Override
    public Stage fetchNextStage(Stage stageReference) throws DataNotFoundException {
        Set<Stage> stages = fetchStages();
        return  StageFilters.fetchFirstAfter(stageReference, stages);
    }

    @Override
    public Stage fetchLastStage() throws DataNotFoundException {
        return whiteboardDao.fetchLastStage();
    }

    @Override
    public void refreshAllWhiteboards() {
        WhiteboardMailable whiteboardMailable = fetchWhiteboardMailable();
        whiteboardHandler.broadcast(whiteboardMailable);
    }

    @Override
    public void refreshWhiteboard(Session target) {
        WhiteboardMailable whiteboardMailable = fetchWhiteboardMailable();
        whiteboardHandler.send(whiteboardMailable, target);
    }
}
