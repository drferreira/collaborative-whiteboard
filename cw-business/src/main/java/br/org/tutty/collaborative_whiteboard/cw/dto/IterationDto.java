package br.org.tutty.collaborative_whiteboard.cw.dto;

import br.org.tutty.Equalization;

import java.util.Date;

public class IterationDto {
    @Equalization(name = "name")
    private String name;

    @Equalization(name = "finished")
    private Boolean finished;

    private Boolean inProgress;

    @Equalization(name = "init_date")
    private Date init;

    @Equalization(name = "end_date")
    private Date end;

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }
    public void setName(String name) {
        this.name = name;
    }
}
