package br.org.tutty.collaborative_whiteboard.cw.dto;

import java.util.Date;

public class CurrentIteration {
    private Float percentageOfFinalizedStories;
    private String currentIterationName;
    private Date initDateCurrentIteration;
    private Date endDateCurrentIteration;
    private Integer storiesIntoIteration;
    private Integer finalizedStoriesIntoIteration;

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
}
