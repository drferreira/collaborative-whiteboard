package cw.rest.model.backlog;

import br.org.tutty.Equalization;

import java.util.Date;

public class Task {
    @Equalization(name = "taks_code")
    private String code;

    private String branch;
    private Integer storyPoints;
    private Integer priority;
    private Date creationDate;

    @Equalization(name = "taks_subject")
    private String subject;

    @Equalization(name = "taks_description")
    private String description;

    private String wikiPage;

    public String getCode() {
        return code;
    }
}
