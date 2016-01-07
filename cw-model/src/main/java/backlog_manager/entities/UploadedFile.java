package backlog_manager.entities;

import br.org.tutty.Equalization;
import br.org.tutty.util.PropertyMonitor;
import cw.entities.User;
import org.apache.commons.io.IOUtils;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 15/06/15.
 */
@Entity
@Table(name = "uploaded_file", catalog = "cw")
@SequenceGenerator(name = "UploadedFileSequence", sequenceName = "uploaded_file_seq", initialValue = 1, allocationSize = 1)
public class UploadedFile implements Serializable {

    @Id
    @GeneratedValue(generator = "UploadedFileSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Equalization(name = "updaloadDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Equalization(name = "fileName")
    @Column(name = "file_name")
    private String fileName;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Story story;

    @Lob
    private byte[] file;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public UploadedFile() {
    }

    public UploadedFile(User owner, Story story, InputStream inputStream, String fileName) throws IOException {
        this.date = new Date();
        this.fileName = fileName;
        this.owner = owner;
        this.story = story;
        this.file = IOUtils.toByteArray(inputStream);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long oldValue = this.id;
        this.id = id;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("id", oldValue, id);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Date oldValue = this.date;
        this.date = date;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("date", oldValue, date);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        String oldValue = this.fileName;
        this.fileName = fileName;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("fileName", oldValue, fileName);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        User oldValue = this.owner;
        this.owner = owner;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("owner", oldValue, owner);
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        Story oldValue = this.story;
        this.story = story;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("story", oldValue, story);
    }

    public InputStream getStreamFile(){
        return new ByteArrayInputStream(file);
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
