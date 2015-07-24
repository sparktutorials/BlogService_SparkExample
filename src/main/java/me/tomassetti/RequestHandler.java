package me.tomassetti;

import java.util.Map;

public interface RequestHandler<V extends Validable> {

    Answer process(V value, Map<String, String> queryParams, boolean shouldReturnHtml);

}
