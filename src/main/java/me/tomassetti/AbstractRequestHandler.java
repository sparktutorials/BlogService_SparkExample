package me.tomassetti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.tomassetti.model.Model;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequestHandler<V extends Validable> implements RequestHandler<V>, Route {

    private Class<V> valueClass;
    protected Model model;

    private static final int HTTP_BAD_REQUEST = 400;

    public AbstractRequestHandler(Class<V> valueClass, Model model){
        this.valueClass = valueClass;
        this.model = model;
    }

    private static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
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

    @Override
    public Object handle(Request request, Response response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        V value = objectMapper.readValue(request.body(), valueClass);
        if (!value.isValid()) {
            response.status(HTTP_BAD_REQUEST);
            return "";
        }
        Map<String, String> queryParams = new HashMap<>();
        Answer answer = process(value, queryParams, shouldReturnHtml(request));
        response.status(answer.getCode());
        if (shouldReturnHtml(request)) {
            response.type("text/html");
        } else {
            response.type("application/json");
        }
        response.body(answer.getBody());
        return answer.getBody();
    }

}
