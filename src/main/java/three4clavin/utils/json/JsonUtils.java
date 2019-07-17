package three4clavin.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * Central utils to make working with JSON/GSON a little easier
 */
public class JsonUtils {
    /**
     * Convert a map into a JSON string representation
     *
     * @param input Map (possibly a Map of Maps) to convert
     * @return JSON String representation
     */
    public static String toJson(Map<String, Object> input) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(input);
        return json;
    }

    /**
     * Convert a JSON string into a Map of Maps representation
     * @param input JSON String to convert
     * @return Map representation
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> fromJson(String input) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(input, Map.class);
    }
}
