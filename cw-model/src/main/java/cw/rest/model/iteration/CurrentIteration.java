package cw.rest.model.iteration;

import backlog_manager.entities.Story;

import java.util.Date;
import java.util.List;

public class CurrentIteration {
    private Float percentageOfFinalizedStories;
    private String currentIterationName;
    private Date initDateCurrentIteration;
    private Date endDateCurrentIteration;
    private Integer storiesIntoIteration;
    private Integer finalizedStoriesIntoIteration;
    private Integer storyPointsIntoIteration = 0;

    public void setCurrentIterationName(String currentIterationName) {
        this.currentIterationName = currentIterationName;
    }

    public void setInitDateCurrentIteration(Date initDateCurrentIteration) {
        this.initDateCurrentIteration = initDateCurrentIteration;
    }

    public void setEndDateCurrentIteration(Date endDateCurrentIteration) {
        this.endDateCurrentIteration = endDateCurrentIteration;
    }

    public void setStoriesIntoIteration(Integer storiesIntoIteration) {
        this.storiesIntoIteration = storiesIntoIteration;
    }

    public void setFinalizedStoriesIntoIteration(Integer finalizedStoriesIntoIteration) {
        this.finalizedStoriesIntoIteration = finalizedStoriesIntoIteration;
    }

    public void calcPercentageOfFinalizedStories() {
        this.percentageOfFinalizedStories = new Float((finalizedStoriesIntoIteration * 100) / storiesIntoIteration);
    }

    public void setStoryPointsIntoIteration(List<Story> storyPointsIntoIteration) {
        storyPointsIntoIteration.forEach(story -> calcStoryPoints(story.getStoryPoints()));
    }

    private void calcStoryPoints(Integer storyPoints){
        storyPointsIntoIteration += storyPoints;
    }
}
