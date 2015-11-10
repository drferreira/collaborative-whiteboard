package br.org.cw.websocket;

import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import cw.exceptions.DataNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by drferreira on 19/05/15.
 */
@ApplicationScoped
@ServerEndpoint(value = "/whiteboard", configurator = GetHttpSessionConfigurator.class)
public class WhiteboardWS{

    @Inject
    private WhiteboardService whiteboardService;

    @Inject
    private WhiteboardHandler whiteboardHandler;

    @OnOpen
    public void open(Session websocketSession, EndpointConfig endpointConfig) throws DataNotFoundException {
        whiteboardHandler.addSession(websocketSession);
        whiteboardService.refreshAllWhiteboards();
    }

    @OnClose
    public void close(Session websocketSession) throws IOException {
        whiteboardHandler.removeSession(websocketSession);
        whiteboardService.refreshWhiteboard(websocketSession);
    }
}
