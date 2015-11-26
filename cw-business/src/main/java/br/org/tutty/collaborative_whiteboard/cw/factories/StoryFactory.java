package br.org.tutty.collaborative_whiteboard.cw.factories;

import br.org.tutty.Equalizer;
import cw.rest.model.iteration.Iteration;
import cw.rest.model.backlog.Story;
import cw.rest.model.backlog.StoryStatusLog;

public class StoryFactory {

    public static Story create(backlog_manager.entities.Story story, backlog_manager.entities.StoryStatusLog currentStoryStatusLog){
        Story storyDto = new Story();
        try {
            StoryStatusLog storyStatusLog = new StoryStatusLog();
            Equalizer.equalize(currentStoryStatusLog, storyStatusLog);
            Equalizer.equalize(story,storyDto);

            storyDto.setIteration(buildIteration(story));
            storyDto.setCurrentStatusLog(storyStatusLog);
            return storyDto;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new Story();
        }
    }

    private static Iteration buildIteration(backlog_manager.entities.Story story){
        backlog_manager.entities.Iteration iteration = story.getIteration();
        Iteration iterationDto = new Iteration();

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
