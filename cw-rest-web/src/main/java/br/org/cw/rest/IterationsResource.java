package br.org.cw.rest;

import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import br.org.tutty.collaborative_whiteboard.cw.dto.CurrentIteration;
import br.org.tutty.collaborative_whiteboard.cw.dto.IterationBasicData;
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
    public String currentProgressData(){
        CurrentIteration currentIteration;

        try {
            currentIteration = iterationService.fetchCurrentIterationData();
            return new Gson().toJson(currentIteration);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(Boolean.FALSE);
        }
    }


    @GET
    @Path("/fetch/basicList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchBasicList(){
        List<IterationBasicData> currentIteration;

        try {
            currentIteration = iterationService.fetchBasicDataIterations();
            return new Gson().toJson(currentIteration);

        } catch (DataNotFoundException e) {
            return new Gson().toJson(Boolean.FALSE);
        }
    }

    @GET
    @Path("/fetch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchIterationData(@QueryParam("iterationName") String iterationName){
        // TODO Implementar busca por dados da iteração.
            return new Gson().toJson("TEU CU");
    }
}
