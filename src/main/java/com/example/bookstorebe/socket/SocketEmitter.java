package com.example.bookstorebe.socket;

import com.example.bookstorebe.service.SocketService;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class is a wrapper class for SocketIoServer that allows adding custom handlers.
 */
@Component
public class SocketEmitter {

  private final SocketService socketService;
  private final Logger logger = LoggerFactory.getLogger(SocketEmitter.class);

  @Autowired
  public SocketEmitter(SocketService socketService) {
    this.socketService = socketService;
  }

  /**
   * Starts the SocketIoServer.
   */
  public void socketStart() {
    final ServerWrapper serverWrapper = new ServerWrapper("127.0.0.1", 8081, null);

    try {
      serverWrapper.startServer();
    } catch (Exception e) {
      logger.error("SocketIoServer failed to start", e);
      return;
    }

    SocketIoServer server = serverWrapper.getSocketIoServer();
    SocketIoNamespace ns = server.namespace("/");

    socketService.setNamespace(ns);

    ns.on("connection", args -> {
      SocketIoSocket socket = (SocketIoSocket) args[0];
      logger.info(String
              .format("Client %s (%s) has connected.", socket.getId(), socket.getInitialHeaders()
                      .get("remote_addr")));

      String token = socket.getInitialQuery().get("token");
      socketService.addSocket(token, socket);

      ns.on("disconnect", args1 -> {
        logger.info(String
                .format("Client %s (%s) has disconnected.", socket.getId(), socket.getInitialHeaders()
                        .get("remote_addr")));

        socketService.removeSocket(socket);

      });
    });
  }
}
