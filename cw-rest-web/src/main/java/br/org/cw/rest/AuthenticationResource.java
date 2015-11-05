package br.org.cw.rest;

import br.org.cw.dtos.messages.AuthenticationMessage;
import br.org.tutty.collaborative_whiteboard.cw.service.SecurityService;
import com.google.gson.Gson;
import cw.dtos.Security;
import cw.exceptions.EncryptedException;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by drferreira on 15/10/19.
 */

@Path("/authentication")
public class AuthenticationResource {

    @Inject
    private HttpSession httpSession;

    @Inject
    private SecurityService securityService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(String loginData) {
        try {
            Security security = getSecurity(loginData);
            securityService.login(security);

            AuthenticationMessage authenticationMessage = AuthenticationMessage.create(AuthenticationMessage.Type.SUCCESS);
            return new Gson().toJson(authenticationMessage);

        } catch (Exception e) {
            AuthenticationMessage authenticationMessage = AuthenticationMessage.create(AuthenticationMessage.Type.AUTHENTICATION_ERROR);
            return new Gson().toJson(authenticationMessage);
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public String logout() {
        securityService.logout(httpSession);
        httpSession.invalidate();

        return new Gson().toJson(Boolean.TRUE);
    }

    public Security getSecurity(String loginData) throws EncryptedException {
        Map values = new Gson().fromJson(loginData, Map.class);

        String email = values.get("email").toString();
        String password = values.get("password").toString();

        Security security = new Security(httpSession, email, password);

        return security;
    }
}

