package br.org.tutty.collaborative_whiteboard;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.backlog_manager.StoryDao;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * Created by drferreira on 23/06/15.
 */
@Stateless
@Local(IterationDao.class)
public class IterationDaoBean extends GenericDao implements IterationDao {

    @Inject
    private StoryDao storyDao;

    @Override
    public List<Story> fetchStories(Iteration iteration) throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("iteration", iteration));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public Boolean existIterationInRange(Date init, Date end){
        List<Date> daysBetweenDates = getDaysBetweenDates(init, end);

        for (Date date : daysBetweenDates){
            try {
                fetchIterationBy(date);
                return Boolean.TRUE;
            } catch (DataNotFoundException e) {}
        }

        return Boolean.FALSE;
    }

    public Iteration fetchIterationBy(Date date) throws DataNotFoundException {
        Criteria criteria = createCriteria(Iteration.class);
        criteria.add(Restrictions.lt("initDate", date));
        criteria.add(Restrictions.gt("endDate", date));
        return (Iteration) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public Iteration getCurrentIteration() throws DataNotFoundException {
      return fetchIterationBy(new Date());
    }

    @Override
    public List<Iteration> fetchIterations() throws DataNotFoundException {
        Criteria criteria = createCriteria(Iteration.class);
        return listNotWaitingEmpty(criteria);
    }

    private List<Date> getDaysBetweenDates(Date init, Date end)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(init);

        while (calendar.getTime().before(end))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    @Override
    public List<Story> fetchFinalizedStories(Iteration iteration) throws DataNotFoundException {
        List<Story> stories = fetchStories(iteration);
        List<Story> finalizedStoriesIntoIteration = new ArrayList<>();

        for (Story story : stories) {
            try {
                StoryStatusLog storyStatus = storyDao.getStoryStatusLog(story);

                if (StoryStatus.FINALIZED.equals(storyStatus.getStoryStatus())) {
                    finalizedStoriesIntoIteration.add(story);
                }

            } catch (DataNotFoundException e) {}
        }

        return finalizedStoriesIntoIteration;
    }

    @Override
    public Iteration fetchIterationByName(String iterationName) throws DataNotFoundException {
        Criteria criteria = createCriteria(Iteration.class);
        criteria.add(Restrictions.eq("name", iterationName));
        return (Iteration) uniqueResultNotWaitingEmpty(criteria);
    }
}
