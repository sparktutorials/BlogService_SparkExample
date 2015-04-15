package me.tomassetti;
 
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import me.tomassetti.model.Model;
import me.tomassetti.sql2omodel.Sql2oModel;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Logger;

public class BlogService 
{

    private static final Logger logger = Logger.getLogger(BlogService.class.getCanonicalName());

    private static final int HTTP_BAD_REQUEST = 400;

    @Data
    static class NewPostPayload {
        private String title;
        private List<String> categories = new LinkedList<>();
        private String content;

        public boolean isValid() {
            return title != null && !title.isEmpty() && content != null && !content.isEmpty();
        }
    }

    @Data
    static class NewCommentPayload {
        private String author;
        private String content;

        public boolean isValid() {
            return author != null && !author.isEmpty() && content != null && !content.isEmpty();
        }
    }
    
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
    
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

        // insert a post (using HTTP post method)
        post("/posts", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            NewPostPayload creation = mapper.readValue(request.body(), NewPostPayload.class);
            if (!creation.isValid()) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            UUID id = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
            response.status(200);
            response.type("application/json");
            return id;
        });

        // get all post (using HTTP get method)
        get("/posts", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllPosts());
        });

        post("/posts/:uuid/comments", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            NewCommentPayload creation = mapper.readValue(request.body(), NewCommentPayload.class);
            if (!creation.isValid()) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            UUID post = UUID.fromString(request.params(":uuid"));
            if (!model.existPost(post)){
                response.status(400);
                return "";
            }
            UUID id = model.createComment(post, creation.getAuthor(), creation.getContent());
            response.status(200);
            response.type("application/json");
            return id;
        });

        get("/posts/:uuid/comments", (request, response) -> {
            UUID post = UUID.fromString(request.params(":uuid"));
            if (!model.existPost(post)) {
                response.status(400);
                return "";
            }
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllCommentsOn(post));
        });
    }
}
