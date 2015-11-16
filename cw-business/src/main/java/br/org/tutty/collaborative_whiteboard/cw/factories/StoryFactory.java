package br.org.tutty.collaborative_whiteboard.cw.factories;

import backlog_manager.entities.Story;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.cw.dto.StoryDto;

public class StoryFactory {

    public static StoryDto create(Story story){
        StoryDto storyDto = new StoryDto();
        try {
            Equalizer.equalize(story,storyDto);
            return storyDto;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new StoryDto();
        }
    }
}
