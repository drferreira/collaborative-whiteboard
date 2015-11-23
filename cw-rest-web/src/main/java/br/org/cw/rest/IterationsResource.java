package br.org.cw.rest;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import backlog_manager.exceptions.IterationNotFoundException;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import br.org.tutty.collaborative_whiteboard.cw.dto.AddStoryToIterationDto;
import br.org.tutty.collaborative_whiteboard.cw.dto.CurrentIteration;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationDto;
import br.org.tutty.collaborative_whiteboard.cw.dto.StoryDto;
import com.google.gson.Gson;
import cw.exceptions.DataNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/iteration")
public class IterationsResource {

    @Inject
    private IterationService iterationService;


    @GET
    @Path("/current/progress")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String currentProgressData() {
        CurrentIteration currentIteration;

        try {
            currentIteration = iterationService.fetchCurrentIterationData();
            return new Gson().toJson(currentIteration);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(Boolean.FALSE);
        }
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchBasicList() {
        List<IterationDto> currentIteration;

        try {
            currentIteration = iterationService.listIterations();
            return new Gson().toJson(currentIteration);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(Boolean.FALSE);
        }
    }

    @POST
    @Path("/add/story")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addStory(String changedAction){
        AddStoryToIterationDto addStoryToIterationDto = new Gson().fromJson(changedAction, AddStoryToIterationDto.class);
        StoryDto storyDto = addStoryToIterationDto.getStory();
        IterationDto iterationDto = addStoryToIterationDto.getIteration();

        try {
            Story story = new Story();
            Iteration iteration = new Iteration();

            Equalizer.equalize(storyDto, story);
            Equalizer.equalize(iterationDto, iteration);

            iterationService.addStory(story.getCode(), iteration.getName());
        } catch (IllegalAccessException | NoSuchFieldException | IterationNotFoundException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/remove/story")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void removeStory(String changedAction){
        AddStoryToIterationDto addStoryToIterationDto = new Gson().fromJson(changedAction, AddStoryToIterationDto.class);
        StoryDto storyDto = addStoryToIterationDto.getStory();

        try {
            Story story = new Story();
            Equalizer.equalize(storyDto, story);

            iterationService.removeStory(story.getCode());
        } catch (IllegalAccessException | NoSuchFieldException | DataNotFoundException e) {
            e.printStackTrace();
        }
    }
}
