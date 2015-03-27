package me.tomassetti;
 
import static spark.Spark.get;
import static spark.Spark.post;
import spark.Request;
import spark.Response;
import spark.Route;

public class BlogService 
{
    public static void main( String[] args) {
        get("/posts", new Route(){
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "Hello Sparkingly World!";
            }
        });
    }
}
