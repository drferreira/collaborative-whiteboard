package cw.rest.model.iteration;

import cw.rest.model.backlog.Story;
import cw.rest.model.iteration.Iteration;

public class AddStoryToIteration {
    private Iteration iteration;
    private Story story;

    public Iteration getIteration() {
        return iteration;
    }

    public Story getStory() {
        return story;
    }
}
