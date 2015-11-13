package br.org.tutty.collaborative_whiteboard.cw.factories;

import backlog_manager.entities.Iteration;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationBasicData;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationData;

public class IterationFactory {

    public IterationBasicData createBasicData(Iteration iteration) {
        try {
            IterationBasicData iterationBasicData = new IterationBasicData();
            Equalizer.equalize(iteration, iterationBasicData);
            iterationBasicData.setFinished(iteration.isFinished());
            iterationBasicData.setInProgress(iteration.inProgress());

            return iterationBasicData;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new IterationData();
        }
    }
}
