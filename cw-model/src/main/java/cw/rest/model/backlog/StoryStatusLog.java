package cw.rest.model.backlog;

import backlog_manager.enums.StoryStatus;
import br.org.tutty.Equalization;

import java.util.Date;

public class StoryStatusLog {
    @Equalization(name = "status")
    private StoryStatus storyStatus;

    @Equalization(name = "date")
    private Date date;
}
