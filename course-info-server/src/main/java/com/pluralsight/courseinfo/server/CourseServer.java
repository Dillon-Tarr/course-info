package com.pluralsight.courseinfo.server;

import com.pluralsight.courseinfo.repository.CourseRepository;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.LogManager;

public class CourseServer {

    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }

    public static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
    private static final String DATABASE_FILENAME = loadServerProperty("course-info.database", "Could not load database filename.");
    private static final String BASE_URI = loadServerProperty("course-info.base-uri", "Could not load base URI.");

    public static void main(String... args) {
        LOG.info("Starting HTTP server with database \"{}\" and base URI \"{}\"", DATABASE_FILENAME, BASE_URI);
        CourseRepository courseRepository = CourseRepository.openCourseRepository(DATABASE_FILENAME);
        ResourceConfig config = new ResourceConfig().register(new CourseResource((courseRepository)));

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    private static String loadServerProperty(String property, String exceptionMessage){
        try (InputStream propertiesStream =
                     CourseServer.class.getResourceAsStream("/server.properties")) {
            Properties properties = new Properties();
            properties.load(propertiesStream);
            return properties.getProperty(property);
        } catch (IOException e) {
            throw new IllegalStateException(exceptionMessage);
        }
    }
}
