package br.org.tutty.collaborative_whiteboard.cw.factories;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationDto;
import br.org.tutty.collaborative_whiteboard.cw.dto.StoryDto;

public class StoryFactory {

    public static StoryDto create(Story story){
        StoryDto storyDto = new StoryDto();
        try {
            Equalizer.equalize(story,storyDto);

            storyDto.setIteration(buildIteration(story));
            return storyDto;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new StoryDto();
        }
    }

    private static IterationDto buildIteration(Story story){
        Iteration iteration = story.getIteration();
        IterationDto iterationDto = new IterationDto();

        if (iteration != null){
            try {
                Equalizer.equalize(iteration, iterationDto);
                return iterationDto;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                return null;
            }

        }else {
            return null;
        }
    }
}
