package br.org.cw.rest;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.StoryStatusLog;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import cw.rest.model.backlog.Story;
import br.org.tutty.collaborative_whiteboard.cw.factories.StoryFactory;
import com.google.gson.Gson;
import cw.exceptions.DataNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
        List<backlog_manager.entities.Story> stories;
        List<Story> storyDtos = new ArrayList<>();

        try {
            stories = backlogManagerService.fetchAnalyzedStories();
            stories.stream().forEach(new Consumer<backlog_manager.entities.Story>() {
                @Override
                public void accept(backlog_manager.entities.Story story) {
                    try {
                        StoryStatusLog currentStoryStatusLog = backlogManagerService.getCurrentStoryStatusLog(story);
                        storyDtos.add(StoryFactory.create(story, currentStoryStatusLog));
                    } catch (DataNotFoundException e) {
                    }
                }
            });

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
        List<Story> storiesDtos = new ArrayList<>();
        try {
            Iteration iteration = iterationService.fetchByName(iterationName);
            List<backlog_manager.entities.Story> stories = iterationService.fetchStories(iteration);

            stories.stream().forEach(new Consumer<backlog_manager.entities.Story>() {
                @Override
                public void accept(backlog_manager.entities.Story story) {
                    try {
                        StoryStatusLog currentStoryStatusLog = backlogManagerService.getCurrentStoryStatusLog(story);
                        StoryFactory.create(story, currentStoryStatusLog);
                        storiesDtos.add(StoryFactory.create(story, currentStoryStatusLog));
                    } catch (DataNotFoundException e) {
                    }
                }
            });
            return new Gson().toJson(storiesDtos);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(storiesDtos);
        }
    }

    @GET
    @Path("/fetch/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchAll() {
        List<Story> storiesDtos = new ArrayList<>();
        try {
            backlogManagerService.fetchAllStories().stream().forEach(new Consumer<backlog_manager.entities.Story>() {
                @Override
                public void accept(backlog_manager.entities.Story story) {
                    try {
                        StoryStatusLog currentStoryStatusLog = backlogManagerService.getCurrentStoryStatusLog(story);
                        StoryFactory.create(story, currentStoryStatusLog);
                        storiesDtos.add(StoryFactory.create(story, currentStoryStatusLog));

                    } catch (DataNotFoundException e) {
                    }
                }
            });
            return new Gson().toJson(storiesDtos);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(storiesDtos);
        }
    }
}
