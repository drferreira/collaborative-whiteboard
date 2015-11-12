package br.org.tutty.collaborative_whiteboard.cw.factories;

import backlog_manager.entities.Iteration;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationBasicData;

import java.util.ArrayList;
import java.util.List;

public class IterationFactory {
    private List<Iteration> iterations;
    private List<IterationBasicData> iterationBasicData;

    public IterationFactory() {
        iterations = new ArrayList<>();
        iterationBasicData = new ArrayList<>();
    }

    public void addIteration(Iteration iteration) {
        iterations.add(iteration);
    }

    public List<IterationBasicData> createIterationsBasicData() {
        iterations.stream().forEach(iteration -> iterationBasicData.add(createBasicData(iteration)));

        return iterationBasicData;
    }

    private IterationBasicData createBasicData(Iteration iteration) {
        try {
            IterationBasicData iterationBasicData = new IterationBasicData();
            Equalizer.equalize(iteration, iterationBasicData);
            iterationBasicData.setStatus(iteration.isFinished());

            return iterationBasicData;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }
}
