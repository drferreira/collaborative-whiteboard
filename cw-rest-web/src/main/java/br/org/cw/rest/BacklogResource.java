package br.org.cw.rest;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/story")
public class BacklogResource {


    @POST
    @Path("/fetch/open")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchOpenStories(String userCreationData) {
        return new Gson().toJson(Boolean.FALSE);
    }

    @POST
    @Path("/fetch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchByIteration(@QueryParam(value = "iterationName") String iterationName) {
        return new Gson().toJson(Boolean.FALSE);
    }
}
