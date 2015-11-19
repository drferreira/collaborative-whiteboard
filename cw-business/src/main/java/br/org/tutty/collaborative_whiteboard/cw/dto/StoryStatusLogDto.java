package br.org.tutty.collaborative_whiteboard.cw.dto;

import backlog_manager.enums.StoryStatus;
import br.org.tutty.Equalization;

import java.util.Date;

public class StoryStatusLogDto {
    @Equalization(name = "status")
    private StoryStatus storyStatus;

    @Equalization(name = "date")
    private Date date;
}
