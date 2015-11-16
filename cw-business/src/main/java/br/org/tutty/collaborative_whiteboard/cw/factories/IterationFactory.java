package br.org.tutty.collaborative_whiteboard.cw.factories;

import backlog_manager.entities.Iteration;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationDto;

public class IterationFactory {

    public IterationDto create(Iteration iteration) {
        try {
            IterationDto iterationDto = new IterationDto();
            Equalizer.equalize(iteration, iterationDto);
            iterationDto.setFinished(iteration.isFinished());
            iterationDto.setInProgress(iteration.inProgress());

            return iterationDto;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new IterationDto();
        }
    }
}
