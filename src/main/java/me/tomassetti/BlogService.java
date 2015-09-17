package me.tomassetti;
 
import com.beust.jcommander.JCommander;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import me.tomassetti.handlers.CommentsCreateHandler;
import me.tomassetti.handlers.CommentsListHandler;
import me.tomassetti.handlers.PostsCreateHandler;
import me.tomassetti.handlers.PostsIndexHandler;
import me.tomassetti.model.Model;
import me.tomassetti.sql2omodel.Sql2oModel;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.UUID;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class BlogService 
{

    private static final Logger logger = Logger.getLogger(BlogService.class.getCanonicalName());

    public static void main( String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);

        logger.finest("Options.debug = " + options.debug);
        logger.finest("Options.database = " + options.database);
        logger.finest("Options.dbHost = " + options.dbHost);
        logger.finest("Options.dbUsername = " + options.dbUsername);
        logger.finest("Options.dbPort = " + options.dbPort);
        logger.finest("Options.servicePort = " + options.servicePort);

        port(options.servicePort);

        Sql2o sql2o = new Sql2o("jdbc:postgresql://" + options.dbHost + ":" + options.dbPort + "/" + options.database,
                options.dbUsername, options.dbPassword, new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                converters.put(UUID.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(BlogService.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        // insert a post (using HTTP post method)
        post("/posts", new PostsCreateHandler(model));

        // get all post (using HTTP get method)
        get("/posts", new PostsIndexHandler(model));

        post("/posts/:uuid/comments", new CommentsCreateHandler(model));

        get("/posts/:uuid/comments", new CommentsListHandler(model));

        get("/alive", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "ok";
            }
        });
    }
}
