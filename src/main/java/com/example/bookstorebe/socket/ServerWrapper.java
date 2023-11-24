package com.example.bookstorebe.socket;

import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoServerOptions;
import io.socket.engineio.server.JettyWebSocketHandler;
import io.socket.socketio.server.SocketIoServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter;

/**
 * The class is a wrapper class for Server that allows adding custom handlers.
 */
public final class ServerWrapper {
  private AtomicInteger portStart = null;

  private final Integer mainPort;
  private final Server mainServer;
  private final EngineIoServerOptions eioOptions;
  private final EngineIoServer mainEngineIoServer;
  private final SocketIoServer mainSocketIoServer;
  private static final String SOCKET_ENDPOINT = "/socket.io/";

  /**
   * Creates a new ServerWrapper instance.
   */
  public ServerWrapper(String ip, int port, String[] allowedCorsOrigins) {
    portStart = new AtomicInteger(port);

    mainPort = portStart.getAndIncrement();
    var qwe = new InetSocketAddress(ip, mainPort);
    mainServer = new Server(qwe);
    eioOptions = EngineIoServerOptions.newFromDefault();
    eioOptions.setAllowedCorsOrigins(allowedCorsOrigins);

    mainEngineIoServer = new EngineIoServer(eioOptions);
    mainSocketIoServer = new SocketIoServer(mainEngineIoServer);


    ServletContextHandler servletContextHandler =
            new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContextHandler.setContextPath("/");
    servletContextHandler.addFilter(RemoteAddrFilter.class,
            SOCKET_ENDPOINT, EnumSet.of(DispatcherType.REQUEST));


    servletContextHandler.addServlet(new ServletHolder(new HttpServlet() {
      @Override
      protected void service(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        mainEngineIoServer.handleRequest(new HttpServletRequestWrapper(request) {
          @Override
          public boolean isAsyncSupported() {
            return false;
          }
        }, response);
      }
    }), SOCKET_ENDPOINT);

    try {
      WebSocketUpgradeFilter webSocketUpgradeFilter =
              WebSocketUpgradeFilter.configureContext(servletContextHandler);
      webSocketUpgradeFilter.addMapping(
              new ServletPathSpec(SOCKET_ENDPOINT),
              (servletUpgradeRequest, servletUpgradeResponse) ->
                      new JettyWebSocketHandler(mainEngineIoServer));
    } catch (ServletException ex) {
      ex.printStackTrace();
    }

    HandlerList handlerList = new HandlerList();
    handlerList.setHandlers(new Handler[]{servletContextHandler});
    mainServer.setHandler(handlerList);
  }

  public void startServer() throws Exception {
    mainServer.start();
  }

  public SocketIoServer getSocketIoServer() {
    return mainSocketIoServer;
  }

}
