package dtos;

import br.org.tutty.Equalization;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 31/07/15.
 */
public class TaskStatusMailable implements Serializable {

    private String value;

    @Equalization(name = "date")
    private Date date;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
