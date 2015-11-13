package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.enums.StoryStatus;
import backlog_manager.exceptions.IterationAlreadySetException;
import backlog_manager.exceptions.IterationNotFoundException;
import br.org.tutty.collaborative_whiteboard.IterationDao;
import br.org.tutty.collaborative_whiteboard.cw.dto.CurrentIteration;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationBasicData;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationData;
import br.org.tutty.collaborative_whiteboard.cw.factories.IterationFactory;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by drferreira on 23/06/15.
 */

@Stateless
@Local(IterationService.class)
public class IterationServiceBean implements IterationService {

    @Inject
    private IterationDao iterationDao;

    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private IterationFactory iterationFactory;

    @Override
    public void addStory(Story story, Iteration iteration) throws IterationNotFoundException {
        if (iteration != null) {
            story.setIteration(iteration);
            iterationDao.update(story);
        } else {
            throw new IterationNotFoundException();
        }
    }

    @Override
    public void removeStory(Story story) {
        story.setIteration(null);
        iterationDao.update(story);
    }

    @Override
    public List<Iteration> fetchIterations() {
        try {
            return iterationDao.fetchIterations();
        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }


    @Override
    public Iteration getCurrentIteration() throws DataNotFoundException {
        return iterationDao.getCurrentIteration();
    }

    @Override
    public List<Story> fetchStoriesAvailableForIteration() {
        try {
            List<Story> analyzedStories = backlogManagerService.fetchAnalyzedStories();

            return analyzedStories.stream().filter(story -> story.getIteration() == null).collect(Collectors.toList());
        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Story> fetchStories(Iteration iteration) throws DataNotFoundException {
        return iterationDao.fetchStories(iteration);
    }

    @Override
    public void create(List<Story> stories, String name, Date init, Date end) throws IterationAlreadySetException {
        if (!iterationDao.existIterationInRange(init, end)) {
            Iteration iteration = new Iteration(name, init, end);

            iterationDao.persist(iteration);
            iterationDao.merge(iteration);

            stories.forEach(new Consumer<Story>() {
                @Override
                public void accept(Story story) {
                    story.setIteration(iteration);
                    iterationDao.update(story);
                }
            });
        } else {
            throw new IterationAlreadySetException();
        }
    }

    @Override
    public Float getProgressIteration(Iteration iteration) {
        try {
            List<Story> stories = iterationDao.fetchStories(iteration);

            Integer total = stories.size();
            Integer numberOfFinalized = 0;

            for (Story story : stories) {
                try {
                    StoryStatusLog storyStatus = backlogManagerService.getCurrentStoryStatusLog(story);

                    if (StoryStatus.FINALIZED.equals(storyStatus.getStoryStatus())) {
                        numberOfFinalized++;
                    }

                } catch (DataNotFoundException e) {
                }
            }

            return new Float((numberOfFinalized * 100) / total);
        } catch (DataNotFoundException e) {
            return 0f;
        }
    }


    @Override
    public Long fetchIterationPoints(Iteration iteration) {
        try {
            return fetchStories(iteration).stream().mapToLong(Story::getStoryPoints).sum();

        } catch (DataNotFoundException e) {
            return 0l;
        }
    }

    @Override
    public CurrentIteration fetchCurrentIterationData() throws DataNotFoundException {
        CurrentIteration currentIteration = new CurrentIteration();

        Iteration iteration = getCurrentIteration();
        List<Story> storiesIntoIteration = iterationDao.fetchStories(iteration);
        List<Story> stories = iterationDao.fetchFinalizedStories(iteration);

        currentIteration.setStoriesIntoIteration(storiesIntoIteration.size());
        currentIteration.setFinalizedStoriesIntoIteration(stories.size());
        currentIteration.setCurrentIterationName(iteration.getName());
        currentIteration.setInitDateCurrentIteration(iteration.getInitDate());
        currentIteration.setEndDateCurrentIteration(iteration.getEndDate());
        currentIteration.setStoryPointsIntoIteration(storiesIntoIteration);

        currentIteration.calcPercentageOfFinalizedStories();
        return currentIteration;
    }

    @Override
    public List<IterationBasicData> listIterations() throws DataNotFoundException {
        List<IterationBasicData> iterationBasicData = new ArrayList<>();

        List<Iteration> iterations = iterationDao.fetchIterations();
        iterations.stream().forEach(iteration -> iterationBasicData.add(iterationFactory.createBasicData(iteration)));

        return iterationBasicData;
    }
}
