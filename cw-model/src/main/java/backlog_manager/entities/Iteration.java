package backlog_manager.entities;

import br.org.tutty.Equalization;
import br.org.tutty.util.PropertyMonitor;
import cw.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
@Entity
@Table(name = "iteration", catalog = "cw")
@SequenceGenerator(name = "IterationSequence", sequenceName = "iteration_seq", initialValue = 1, allocationSize = 1)
public class Iteration implements Serializable {

    @Id
    @GeneratedValue(generator = "IterationSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Equalization(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date initDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public Iteration(String name, Date init, Date end) {
        this.name = name;
        this.initDate = init;
        this.endDate = end;
    }

    public Iteration() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("name", oldValue, name);
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        Date oldValue = this.initDate;
        this.initDate = initDate;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("initDate", oldValue, initDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Date oldValue = this.endDate;
        this.endDate = endDate;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("endDate", oldValue, endDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long oldValue = this.id;
        this.id = id;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("id", oldValue, id);
    }

    public Boolean isFinished(){
        return !DateUtil.inRange(LocalDate.now(), DateUtil.toLocalDate(initDate), DateUtil.toLocalDate(endDate));
    }
}
