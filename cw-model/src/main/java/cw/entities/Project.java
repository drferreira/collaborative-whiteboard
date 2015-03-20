package cw.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 19/12/14.
 */
@Entity
@Table(name = "project", catalog = "cw")
public class Project implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long projectId;

    @Transient
    private String identificationCode;

    @ManyToOne
    @NotNull
    private User owner;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(nullable = false)
    private String nameProject;

    public Project(String nameProject, User owner) {
        this.nameProject = nameProject;
        this.creationDate = new Date();
        this.owner = owner;
    }

    protected Project() {}

    public Long getId() {
        return projectId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (creationDate != null ? !creationDate.equals(project.creationDate) : project.creationDate != null)
            return false;
        if (identificationCode != null ? !identificationCode.equals(project.identificationCode) : project.identificationCode != null)
            return false;
        if (nameProject != null ? !nameProject.equals(project.nameProject) : project.nameProject != null) return false;
        if (owner != null ? !owner.equals(project.owner) : project.owner != null) return false;
        if (projectId != null ? !projectId.equals(project.projectId) : project.projectId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId != null ? projectId.hashCode() : 0;
        result = 31 * result + (identificationCode != null ? identificationCode.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (nameProject != null ? nameProject.hashCode() : 0);
        return result;
    }
}
