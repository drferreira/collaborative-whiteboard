package br.org.cw.rest;

import cw.rest.model.backlog.Task;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskActionService;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/whiteboard")
public class WhiteboardControlResource {

    @Inject
    private TaskActionService taskActionService;


    @POST
    @Path("/task/play")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String playTask(String task) {
        Task convertedTask = new Gson().fromJson(task, Task.class);
        try {
            taskActionService.init(convertedTask.getCode());
            return new Gson().toJson(Boolean.TRUE);

        } catch (Exception e) {
            return new Gson().toJson(e);
        }
    }

    @POST
    @Path("/task/stop")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String stopTask(String task) {
        Task convertedTask = new Gson().fromJson(task, Task.class);
        try {
            taskActionService.stop(convertedTask.getCode());
            return new Gson().toJson(Boolean.TRUE);

        } catch (Exception e) {
            return new Gson().toJson(e);
        }
    }


    @POST
    @Path("/task/goForward")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String goForwardTask(String task) {
        Task convertedTask = new Gson().fromJson(task, Task.class);
        try {
            taskActionService.forward(convertedTask.getCode());
            return new Gson().toJson(Boolean.TRUE);

        } catch (Exception e) {
            return new Gson().toJson(e);
        }
    }

    @POST
    @Path("/task/goBack")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String goBackTask(String task) {
        Task convertedTask = new Gson().fromJson(task, Task.class);
        try {
            taskActionService.backward(convertedTask.getCode());
            return new Gson().toJson(Boolean.TRUE);

        } catch (Exception e) {
            return new Gson().toJson(e);
        }
    }

    @POST
    @Path("/task/finalize")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String finalizeTask(String task) {
        Task convertedTask = new Gson().fromJson(task, Task.class);
        try {
            taskActionService.end(convertedTask.getCode());
            return new Gson().toJson(Boolean.TRUE);

        } catch (Exception e) {
            return new Gson().toJson(e);
        }
    }
}
