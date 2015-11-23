package br.org.tutty.collaborative_whiteboard.cw.dto;

public class AddStoryToIterationDto {
    private IterationDto iteration;
    private StoryDto story;

    public IterationDto getIteration() {
        return iteration;
    }

    public StoryDto getStory() {
        return story;
    }
}
