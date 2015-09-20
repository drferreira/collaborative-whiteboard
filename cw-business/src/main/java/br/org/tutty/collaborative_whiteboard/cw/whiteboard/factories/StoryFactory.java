package br.org.tutty.collaborative_whiteboard.cw.whiteboard.factories;

import backlog_manager.entities.Story;
import br.org.tutty.Equalizer;
import dtos.StoryMailable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 20/09/15.
 */
public class StoryFactory {
	private List<Story> stories;

	public StoryFactory(List<Story> stories) {
		this.stories = stories;
	}

	public List<StoryMailable> getStoryMailables(){
		List<StoryMailable> storyMailable = new ArrayList<>();

		for (Story story : stories){
			try {
				storyMailable.add(buildStoryMailable(story));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return storyMailable;
	}

	public List<Story> getStories() {
		return stories;
	}

	public StoryMailable buildStoryMailable(Story story) throws NoSuchFieldException, IllegalAccessException {
		StoryMailable storyMailable = new StoryMailable();
		Equalizer.equalize(story, storyMailable);

		return storyMailable;
	}
}
