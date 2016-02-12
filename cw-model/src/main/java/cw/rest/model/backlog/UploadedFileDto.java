package cw.rest.model.backlog;

import br.org.tutty.Equalization;

import java.util.Date;

public class UploadedFileDto {
    @Equalization(name = "updaloadDate")
    private Date date;

    @Equalization(name = "fileName")
    private String fileName;

    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
