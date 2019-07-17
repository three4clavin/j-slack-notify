package three4clavin.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A basic abstraction to make HTTP calls
 */
public class HttpUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private PoolingHttpClientConnectionManager connManager;

    /**
     * Basic constructor
     */
    public HttpUtils () {
        connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(100);

        SocketConfig sc = SocketConfig.custom()
                .setSoTimeout(5000) // 5s
                .build();

        connManager.setDefaultSocketConfig(sc);
    }

    /**
     * Creates a new client from the pooling connection manager
     *
     * @return HTTP client
     */
    private HttpClient createClient () {
        HttpClient client = HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();
        return client;
    }

    /**
     * Creates an HTTP POST object to work with
     *
     * @param uri URI to post to
     * @return HTTP POST object to extend or call
     */
    public HttpPost createPost (String uri) {
        RequestConfig.Builder config = RequestConfig.copy(RequestConfig.DEFAULT);
        config.setConnectionRequestTimeout(5000); // 5s
        config.setSocketTimeout(5000); // 5s

        HttpPost post = new HttpPost(uri);
        post.setConfig(config.build());

        return post;
    }

    /**
     * Sends a POST object against it's defined URI
     *
     * @param post HTTP POST object to call
     * @throws IOException If anything can't be properly called
     */
    public void sendPost (HttpPost post, HttpHeaders headers) throws IOException {
        HttpClient client = createClient();

        // ToDo - switch to debug or trace
        LOG.trace("Post body: "+ EntityUtils.toString(post.getEntity(), "UTF-8"));

        for(HttpHeader header : headers) {
            post.setHeader(header.getKey(), header.getValue());
        }

        HttpResponse response = client.execute(post);

        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

        LOG.info("RESPONSE: "+responseString);

        if(response.getStatusLine().getStatusCode() != 200){
            throw new HttpException("Status code not 200: "+response.getStatusLine().getStatusCode()+" : "+response.getStatusLine().getReasonPhrase());
        }
    }


    // ---- Generated Methods ----

    public PoolingHttpClientConnectionManager getConnManager() {
        return connManager;
    }

    public void setConnManager(PoolingHttpClientConnectionManager connManager) {
        this.connManager = connManager;
    }

    @Override
    public String toString() {
        return "HttpUtils{" +
                "connManager=" + connManager +
                '}';
    }
}
