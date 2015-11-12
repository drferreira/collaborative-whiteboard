package br.org.tutty.collaborative_whiteboard.cw.dto;

import br.org.tutty.Equalization;

public class IterationBasicData {
    @Equalization(name = "name")
    private String name;

    private Boolean status;

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }
}
