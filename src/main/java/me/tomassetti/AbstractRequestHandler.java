package me.tomassetti;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequestHandler<V extends Validable> implements RequestHandler<V>, Route {

    private Class<V> valueClass;

    public AbstractRequestHandler(Class<V> valueClass){
        this.valueClass = valueClass;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        V value = objectMapper.readValue(request.body(), valueClass);
        if (!value.isValid()) {
            response.status(400);
            return "";
        }
        Map<String, String> queryParams = new HashMap<>();
        Answer answer = process(value, queryParams);
        response.status(answer.getCode());
        response.body(answer.getBody());
        return answer.getBody();
    }

}
