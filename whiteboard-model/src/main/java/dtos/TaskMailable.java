package dtos;

import br.org.tutty.Equalization;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 10/07/15.
 */
public class TaskMailable implements Serializable{
    @Equalization(name = "taks_code")
    private String code;

    @Equalization(name = "taks_estimated_time")
    private Date estimatedTime;

    @Equalization(name = "taks_subject")
    private String subject;

    @Equalization(name = "taks_description")
    private String description;

    private TaskStatusMailable taskStatus;

    private String username;

    private String profilePicture;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Date estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatusMailable getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatusMailable taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setUsername(String username){
	this.username = username;
    }

    public void setProfilePicture(String profilePicture){
	this.profilePicture = profilePicture;
    }
}
