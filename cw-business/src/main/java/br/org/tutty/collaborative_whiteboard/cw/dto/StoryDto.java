package br.org.tutty.collaborative_whiteboard.cw.dto;

import br.org.tutty.Equalization;

import java.util.Date;

public class StoryDto {

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

    private IterationDto iteration;

    public void setIteration(IterationDto iteration) {
        this.iteration = iteration;
    }
}
