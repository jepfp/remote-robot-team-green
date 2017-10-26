package ch.loewenfels.robot;

import java.net.URI;
import java.util.logging.Level;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
public class App {
    private static HttpServer httpServer;
    public static final URI BASE_URI = URI.create("http://0.0.0.0:8090/api");

    public static void main(final String[] args) {
        start();
    }

    public static void start() {
        Grizzly.logger(HttpServer.class).setLevel(Level.ALL);
        final ResourceConfig rc = new ResourceConfig().packages("ch.loewenfels.robot.rest");
        httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
}
