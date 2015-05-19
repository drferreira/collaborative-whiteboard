package br.org.tutty.collaborative_whiteboard.cw.service;

import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 19/05/15.
 */
public interface WhiteboardService {
    List<Stage> fetchStages() throws DataNotFoundException;

    void createStage(Stage stage);

    void removeStage(Stage stage);
}
