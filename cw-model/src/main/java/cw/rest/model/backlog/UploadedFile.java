package cw.rest.model.backlog;

import br.org.tutty.Equalization;

import java.util.Date;

public class UploadedFile {
    @Equalization(name = "updaloadDate")
    private Date date;

    @Equalization(name = "fileName")
    private String fileName;
}
