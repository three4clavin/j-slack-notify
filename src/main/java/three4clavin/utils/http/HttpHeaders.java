package three4clavin.utils.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple utility class to provide headers for an HTTP request
 */
public class HttpHeaders extends ArrayList<HttpHeader> {
    // private List<HttpHeader> headers;

    /**
     * Basic constructor with strings.  Array should be even length with
     * value following each key, e.g.
     *     ("key1", "value1", "key2", "value2", ...)
     *
     * @param headerStrings Key-Value pairs representing header values
     */
    public HttpHeaders (String ... headerStrings) {
        for(int i=0; i<headerStrings.length; i+=2) {
            String key = headerStrings[i];
            String value = "";
            if(i+1 < headerStrings.length){
                value = headerStrings[i+1];
            }
            HttpHeader header = new HttpHeader(key, value);
            this.add(header);
        }
    }

    /**
     * Basic constructor with a list of headers
     *
     * @param headers List of headers
     */
    public HttpHeaders (List<HttpHeader> headers) {
        this.clear();
        this.addAll(headers);
    }
}
