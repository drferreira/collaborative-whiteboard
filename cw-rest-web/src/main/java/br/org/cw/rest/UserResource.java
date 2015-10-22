package br.org.cw.rest;

import br.org.cw.util.ResourcesReader;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import com.google.gson.Gson;
import cw.entities.User;
import cw.exceptions.EncryptedException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by diogoferreira on 19/10/15.
 */

@Path("/user")
public class UserResource {
    private static String DEFAULT_IMAGE_URL = "app/resources/img/user-male.png";

    @Inject
    private UserService userService;

    @Inject
    private ResourcesReader resourcesReader;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String userCreationData){
        try {
            User user = getUser(userCreationData);
            userService.create(user);
            return new Gson().toJson(Boolean.TRUE);

        } catch (Exception e) {
            return new Gson().toJson(Boolean.FALSE);
        }
    }

    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String emailValidation(String userCreationValidationData){
        Map values = new Gson().fromJson(userCreationValidationData, Map.class);

        String email = values.get("emailRegister").toString();
        return new Gson().toJson(!userService.isAlreadyRegistered(email));
    }

    private User getUser(String userCreationData) throws ParseException, IOException, EncryptedException {
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        Map values = new Gson().fromJson(userCreationData, Map.class);

        String email = values.get("emailRegister").toString();
        String password = values.get("password_register").toString();
        String firstName = values.get("firstNameRegister").toString();
        String lastName = values.get("surnameRegister").toString();
        Date birthday = sourceFormat.parse(values.get("birthdayRegister").toString());
        InputStream profilePicture = resourcesReader.fetchWebAppFile(DEFAULT_IMAGE_URL);

        return new User(email, password, firstName, lastName, birthday, profilePicture);
    }
}
