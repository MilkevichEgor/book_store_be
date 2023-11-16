package com.example.bookstorebe.socket;

import com.example.bookstorebe.service.SocketService;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SocketEmitter {

    private final SocketService socketService;

    @Autowired
    public SocketEmitter(SocketService socketService) {
        this.socketService = socketService;
    }

    public void broadcast(String text) {
        socketService.broadcast(text);
    }

    public void socketStart() {
        final ServerWrapper serverWrapper = new ServerWrapper("127.0.0.1", 8081, null);

        try {
            serverWrapper.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SocketIoServer server = serverWrapper.getSocketIoServer();
        SocketIoNamespace ns = server.namespace("/");

        ns.on("connection", args -> {
            SocketIoSocket socket = (SocketIoSocket) args[0];
            System.out.println("Client " + socket.getId() + " (" + socket.getInitialHeaders().get("remote_addr") + ") has connected.");

            // Вызывайте методы сервиса для управления соединениями
            socketService.addSocket(socket);

        });

        ns.on("disconnect", args -> {
            SocketIoSocket socket = (SocketIoSocket) args[0];
            System.out.println("Client " + socket.getId() + " has disconnected.");

            // Вызывайте методы сервиса для управления соединениями
            socketService.removeSocket(socket);

        });
    }
}
