package com.example.bookstorebe.service;

import com.example.bookstorebe.security.JwtUtils;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoSocket;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing sockets.
 */
@Service
public class SocketService {

  private final JwtUtils jwtUtils;
  private SocketIoNamespace namespace;

  @Autowired
  public SocketService(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  public void setNamespace(SocketIoNamespace namespace) {
    this.namespace = namespace;
  }

  private final Map<String, SocketIoSocket> sockets = new HashMap<>();


  /**
   * Adds a socket to the sockets map.
   *
   * @param token  The token used to identify the user.
   * @param socket The SocketIoSocket object to be added.
   */
  public void addSocket(String token, SocketIoSocket socket) {

    String userEmail = jwtUtils.getUserNameFromJwtToken(token);
    sockets.put(userEmail, socket);
  }

  /**
   * Removes a socket from the sockets map based on the provided token.
   *
   * @param socket The SocketIoSocket object to remove.
   */
  public void removeSocket(SocketIoSocket socket) {
    String tokenToRemove = socket.getInitialQuery().get("token");

    sockets.entrySet().removeIf(entry -> {
      String token = entry.getValue().getInitialQuery().get("token");
      return tokenToRemove.equals(token);
    });
  }

  /**
   * Broadcasts a text message to all connected sockets.
   *
   * @param text the text message to broadcast
   */
  public void broadcast(String text, String senderEmail) {
    for (Map.Entry<String, SocketIoSocket> entry : sockets.entrySet()) {
      String userEmail = entry.getKey();
      if (!userEmail.equals(senderEmail)) {
        SocketIoSocket socket = entry.getValue();

        socket.send("comment:save", "{\"text\": \"" + text + "\"}");
      }
    }

    this.namespace.broadcast("/", "comment:save", text);
  }

}