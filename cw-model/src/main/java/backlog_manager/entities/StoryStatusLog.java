package backlog_manager.entities;

import backlog_manager.enums.StoryStatus;
import br.org.tutty.Equalization;
import cw.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 06/05/15.
 */
@Entity
@Table(name = "story_status", catalog = "cw")
@SequenceGenerator(name = "StoryStatusSequence", sequenceName = "story_status_seq", initialValue = 1, allocationSize = 1)
public class StoryStatusLog implements Serializable{

    @Id
    @GeneratedValue(generator = "StoryStatusSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Equalization(name = "status")
    @Enumerated(EnumType.STRING)
    private StoryStatus storyStatus;

    @Equalization(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Story story;

    public StoryStatusLog() {
    }

    public StoryStatusLog(StoryStatus storyStatus, User user, Story story) {
        this.storyStatus = storyStatus;
        this.story = story;
        this.date = new Date();
        this.user = user;
    }

    public StoryStatus getStoryStatus() {
        return storyStatus;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
