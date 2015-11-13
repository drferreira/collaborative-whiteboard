package br.org.tutty.collaborative_whiteboard.cw.factories;

import backlog_manager.entities.Story;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.cw.dto.StoryBasicData;

public class StoryFactory {

    public static StoryBasicData createBasicData(Story story){
        StoryBasicData storyBasicData = new StoryBasicData();
        try {
            Equalizer.equalize(story,storyBasicData);
            return storyBasicData;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new StoryBasicData();
        }
    }
}
