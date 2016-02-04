package br.org.tutty.backlog_manager;

import backlog_manager.entities.*;
import backlog_manager.enums.StoryStatus;
import backlog_manager.enums.TaskStatus;
import br.org.tutty.collaborative_whiteboard.GenericDao;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by drferreira on 11/03/15.
 */
@Stateless
@Local(StoryDao.class)
public class StoryDaoBean extends GenericDao implements StoryDao {

    @Override
    public List<Story> fetchAllStories() throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.addOrder(Order.desc("priority"));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public Story fetchByCode(String code) throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("code", code));

        return (Story) uniqueResultNotWaitingEmpty(criteria);
    }


    @Override
    public Long getNextSequenceStory(Project project){
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("project", project));

        criteria.setProjection(Projections.rowCount());

        return ((Long)criteria.list().get(0)).longValue();
    }

    @Override
    public List<Story> fetchStories(ProjectArea projectArea) throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("projectArea", projectArea));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public List<Story> fetchStories(StoryStatus storyStatus) throws DataNotFoundException {
        List<Story> result = new ArrayList<>();
        List<Story> stories = fetchAllStories();

        stories.forEach(new Consumer<Story>() {
            @Override
            public void accept(Story story) {
                    try {
                        if(StoryStatus.ANALYZED.equals(getStoryStatusLog(story).getStoryStatus())){
                           result.add(story);
                        }
                    } catch (DataNotFoundException e) {}
            }
        });

        return result;
    }

    @Override
    public StoryStatusLog getStoryStatusLog(Story story) throws DataNotFoundException {
        Criteria criteria = createCriteria(StoryStatusLog.class);
        criteria.add(Restrictions.eq("story", story));
        criteria.addOrder(Order.desc("date"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);

        return (StoryStatusLog) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public Analysis getLastStoryAnalysis(Story story) throws DataNotFoundException {
        Criteria criteria = createCriteria(Analysis.class);
        criteria.add(Restrictions.eq("story", story));
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);

        return (Analysis) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public List<UploadedFile> fetchFiles(Story selectedStory) throws DataNotFoundException {
        Criteria criteria = createCriteria(UploadedFile.class);
        criteria.add(Restrictions.eq("story", selectedStory));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public UploadedFile fetchFile(Story story, String fileName) throws DataNotFoundException {
        Criteria criteria = createCriteria(UploadedFile.class);
        criteria.add(Restrictions.eq("story", story));
        criteria.add(Restrictions.eq("fileName", fileName));

        return (UploadedFile) uniqueResultNotWaitingEmpty(criteria);
    }
}
