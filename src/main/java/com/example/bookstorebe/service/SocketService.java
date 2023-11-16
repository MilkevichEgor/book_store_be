package com.example.bookstorebe.service;

import io.socket.socketio.server.SocketIoSocket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocketService {

    private final List<SocketIoSocket> sockets = new ArrayList<>();

    public void addSocket(SocketIoSocket socket) {
        sockets.add(socket);
    }

    public void removeSocket(SocketIoSocket socket) {
        sockets.remove(socket);
    }

    public void broadcast(String text) {
        for (SocketIoSocket socket : sockets) {
            socket.send("comment:save", "{\"text\": \"" + text + "\"}");
        }
    }
}