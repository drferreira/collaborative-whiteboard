package cw.rest.model.backlog;

import br.org.tutty.Equalization;
import cw.rest.model.iteration.Iteration;

import java.util.Date;

public class Story {

    @Equalization(name = "story_code")
    private String code;

    @Equalization(name = "story_branch")
    private String branch;

    @Equalization(name = "story_points")
    private Integer storyPoints;

    @Equalization(name = "story_priority")
    private Integer priority;

    @Equalization(name = "story_creation_date")
    private Date creationDate;

    @Equalization(name = "story_subject")
    private String subject;

    @Equalization(name = "story_description")
    private String description;

    @Equalization(name = "story_wiki")
    private String wikiPage;

    private StoryStatusLog currentStatusLog;

    private Iteration iteration;

    public void setIteration(Iteration iteration) {
        this.iteration = iteration;
    }

    public void setCurrentStatusLog(StoryStatusLog currentStatusLog) {
        this.currentStatusLog = currentStatusLog;
    }
}
