package br.org.cw.rest;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.entities.UploadedFile;
import br.org.tutty.Equalizer;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import cw.rest.model.backlog.Story;
import br.org.tutty.collaborative_whiteboard.cw.factories.StoryFactory;
import com.google.gson.Gson;
import cw.exceptions.DataNotFoundException;
import cw.rest.model.backlog.Task;
import cw.rest.model.backlog.UploadedFileDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Base64;
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
    @Path("/files")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String fetchFiles(@QueryParam(value = "storyCode") String code) {
        List<UploadedFileDto> uploadedDtos = new ArrayList<>();

        try {
            backlog_manager.entities.Story story = backlogManagerService.fetchByCode(code);
            List<UploadedFile> uploadedFiles = backlogManagerService.fetchFiles(story);

            uploadedFiles.forEach(new Consumer<UploadedFile>() {
                @Override
                public void accept(UploadedFile uploadedFile) {
                    UploadedFileDto uploadedFileDtoDto = new UploadedFileDto();
                    try {
                        Equalizer.equalize(uploadedFile, uploadedFileDtoDto);
                        uploadedDtos.add(uploadedFileDtoDto);

                    } catch (IllegalAccessException | NoSuchFieldException e) {
                    }

                }
            });

        } catch (DataNotFoundException e) {
        }

        return new Gson().toJson(uploadedDtos);
    }

    @GET
    @Path("/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchTasks(@QueryParam(value = "storyCode") String code) {
        List<Task> tasksDtos = new ArrayList<>();

        try {
            backlog_manager.entities.Story story = backlogManagerService.fetchByCode(code);
            List<backlog_manager.entities.Task> tasks = backlogManagerService.fetchTasks(story);

            tasks.forEach(new Consumer<backlog_manager.entities.Task>() {
                @Override
                public void accept(backlog_manager.entities.Task task) {
                    Task taskDto = new Task();

                    try {
                        Equalizer.equalize(task, taskDto);
                        tasksDtos.add(taskDto);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                    }
                }
            });


        } catch (DataNotFoundException e) {
        }

        return new Gson().toJson(tasksDtos);
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

    @GET
    @Path("/file/download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fileDownload(@QueryParam("storyCode") String storyCode, @QueryParam("fileName") String fileName) {
        try {
            UploadedFile uploadedFile = backlogManagerService.fetchFile(storyCode, fileName);
            UploadedFileDto uploadedFileDto = new UploadedFileDto();

            try {
                Equalizer.equalize(uploadedFile, uploadedFileDto);
                uploadedFileDto.setFile(Base64.getEncoder().encodeToString(uploadedFile.getFile()));
                return new Gson().toJson(uploadedFileDto);

            } catch (IllegalAccessException | NoSuchFieldException e) {
                return new Gson().toJson(e);
            }

        } catch (DataNotFoundException e) {
            return new Gson().toJson(e);
        }
    }
}
