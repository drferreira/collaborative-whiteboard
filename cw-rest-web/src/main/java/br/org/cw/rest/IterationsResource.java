package br.org.cw.rest;

import backlog_manager.exceptions.IterationNotFoundException;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import cw.rest.model.iteration.AddStoryToIteration;
import cw.rest.model.iteration.CurrentIteration;
import cw.rest.model.iteration.Iteration;
import cw.rest.model.backlog.Story;
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
        List<Iteration> currentIteration;

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
        AddStoryToIteration addStoryToIteration = new Gson().fromJson(changedAction, AddStoryToIteration.class);
        Story storyDto = addStoryToIteration.getStory();
        Iteration iterationDto = addStoryToIteration.getIteration();

        try {
            backlog_manager.entities.Story story = new backlog_manager.entities.Story();
            backlog_manager.entities.Iteration iteration = new backlog_manager.entities.Iteration();

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
        AddStoryToIteration addStoryToIteration = new Gson().fromJson(changedAction, AddStoryToIteration.class);
        Story storyDto = addStoryToIteration.getStory();

        try {
            backlog_manager.entities.Story story = new backlog_manager.entities.Story();
            Equalizer.equalize(storyDto, story);

            iterationService.removeStory(story.getCode());
        } catch (IllegalAccessException | NoSuchFieldException | DataNotFoundException e) {
            e.printStackTrace();
        }
    }
}
