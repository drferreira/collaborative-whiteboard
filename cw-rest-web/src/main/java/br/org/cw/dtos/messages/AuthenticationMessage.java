package br.org.cw.dtos.messages;

/**
 * Created by diogoferreira on 19/10/15.
 */
public class AuthenticationMessage {
     private String status;
    private String message;

    public Type type;

    protected AuthenticationMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static AuthenticationMessage create(Type type){
        AuthenticationMessage authenticationMessage = new AuthenticationMessage(type.status, type.message);

       return authenticationMessage;
    }

    public static enum Type {
        SUCCESS("SUCCESS", "Success"),
        AUTHENTICATION_ERROR("ERROR", "Authentication Error");

        String status;
        String message;

        Type (String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}

