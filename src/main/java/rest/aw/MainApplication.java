package rest.aw;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import rest.aw.connection.Base;

import java.net.URI;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {


    public static final String BASE_URI = "http://localhost:8080/";

    public static Server startServer() {

        final ResourceConfig config = new ResourceConfig().packages("rest.aw.endpoint");

        final Server server =
                JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);

        return server;

    }

    public static void main(String[] args) {

        try {

            final Server server = startServer();
            //creating database
            Base base = new Base();
            base.establishConnection();
//            base.dropTable("courses");
//            base.dropTable("schedule");
//            base.createCoursesTable();
//            base.createScheduleTable();
//            base.descTable("courses");
            base.closeConnection();

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the application...");
                    server.stop();
                    System.out.println("Done, exit.");
                } catch (Exception e) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, e);
                }
            }));

            System.out.println(
                    String.format("Application just started.%nStop using CTRL+C"));

            Thread.currentThread().join();


        } catch (InterruptedException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
