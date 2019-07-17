package three4clavin.utils.json;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonUtilsTest {
    public JsonUtilsTest() {
    }

    @Test
    public void testTo() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> l1 = new HashMap<String, Object>();
        l1.put("b", "c");
        map.put("a", l1);
        String json = JsonUtils.toJson(map);

        String expected = "{\n" +
                "  \"a\": {\n" +
                "    \"b\": \"c\"\n" +
                "  }\n" +
                "}";

        Assert.assertEquals(expected, json);
    }

    @Test
    public void testFrom() {
        String json = "{\n" +
                "  \"a\": {\n" +
                "    \"b\": \"c\"\n" +
                "  }\n" +
                "}";

        Map<String, Object> expected = new HashMap<String, Object>();
        Map<String, Object> l1 = new HashMap<String, Object>();
        l1.put("b", "c");
        expected.put("a", l1);

        Map<String, Object> map = JsonUtils.fromJson(json);

        Assert.assertEquals(expected, map);
    }
}
