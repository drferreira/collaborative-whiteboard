package br.org.tutty.collaborative_whiteboard.cw.factories;

import br.org.tutty.Equalizer;
import cw.rest.model.iteration.Iteration;

public class IterationFactory {

    public Iteration create(backlog_manager.entities.Iteration iteration) {
        try {
            Iteration iterationDto = new Iteration();
            Equalizer.equalize(iteration, iterationDto);
            iterationDto.setFinished(iteration.isFinished());
            iterationDto.setInProgress(iteration.inProgress());

            return iterationDto;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new Iteration();
        }
    }
}
