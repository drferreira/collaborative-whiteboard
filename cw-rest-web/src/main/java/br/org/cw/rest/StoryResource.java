package br.org.cw.rest;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import br.org.tutty.collaborative_whiteboard.cw.dto.StoryDto;
import br.org.tutty.collaborative_whiteboard.cw.factories.StoryFactory;
import com.google.gson.Gson;
import cw.exceptions.DataNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/story")
public class StoryResource {
    @Inject
    private BacklogManagerService backlogManagerService;
    @Inject
    private IterationService iterationService;


    @GET
    @Path("/fetch/open")
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchOpenStories() {
        List<Story> stories;
        List<StoryDto> storyDtos = new ArrayList<>();

        try {
            stories = backlogManagerService.fetchAnalyzedStories();
            stories.stream().forEach(story -> storyDtos.add(StoryFactory.create(story)));

            return new Gson().toJson(storyDtos);
        } catch (DataNotFoundException e) {
            return new Gson().toJson(storyDtos);
        }

    }

    @GET
    @Path("/fetch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchByIteration(@QueryParam(value = "iterationName") String iterationName) {
        List<StoryDto> storiesDtos = new ArrayList<>();
        try {
            Iteration iteration = iterationService.fetchByName(iterationName);
            List<Story> stories = iterationService.fetchStories(iteration);

            stories.stream().forEach(story -> storiesDtos.add(StoryFactory.create(story)));
            return new Gson().toJson(storiesDtos);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(storiesDtos);
        }
    }
}
