package cw.rest.model.backlog;

import java.util.Date;

public class Task {
    private String code;
    private String branch;
    private Integer storyPoints;
    private Integer priority;
    private Date creationDate;
    private String subject;
    private String description;
    private String wikiPage;

    public String getCode() {
        return code;
    }
}
