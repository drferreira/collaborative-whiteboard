package br.org.tutty.collaborative_whiteboard.cw.dto;

import br.org.tutty.Equalization;

import java.util.Date;

public class StoryBasicData {

    @Equalization(name = "story_code")
    private String code;

    @Equalization(name = "story_points")
    private Integer storyPoints;

    @Equalization(name = "story_creation_date")
    private Date creationDate;

    @Equalization(name = "story_priority")
    private Integer priority;
}
