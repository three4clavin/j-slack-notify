package three4clavin.utils.http;

/**
 * Simple utility class to provide a name-value pair for setting headers on
 * an HTTP request
 */
public class HttpHeader {
    private String key;
    private String value;

    /**
     * Basic constructor
     *
     * @param key Header key/name
     * @param value Header value
     */
    public HttpHeader (String key, String value) {
        this.key = key;
        this.value = value;
    }

    // ---- Generated Methods ----

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
