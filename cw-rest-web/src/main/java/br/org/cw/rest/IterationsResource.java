package br.org.cw.rest;

import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import br.org.tutty.collaborative_whiteboard.cw.dto.CurrentIteration;
import com.google.gson.Gson;
import cw.exceptions.DataNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/iteration")
public class IterationsResource {

    @Inject
    private IterationService iterationService;


    @GET
    @Path("/current/progress")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String currentProgressData(){
        CurrentIteration currentIteration;

        try {
            currentIteration = iterationService.fetchCurrentIterationData();
            return new Gson().toJson(currentIteration);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(Boolean.FALSE);
        }
    }
}
