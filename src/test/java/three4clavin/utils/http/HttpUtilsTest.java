package three4clavin.utils.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HttpUtilsTest {
    private PoolingHttpClientConnectionManager connManager;
    private HttpClient client;

    public HttpUtilsTest() {
        connManager = null;
        client = null;
    }

    @Before
    public void beforeTest() {
        connManager = new PoolingHttpClientConnectionManager();
        client = HttpClients.custom().build();
    }

    @After
    public void afterTest() {
        client = null;
        connManager.close();
        connManager = null;
    }

    @Test
    public void testPostCreation() {
        // ToDo - make Utils into a proper interface and mock things

        String url = "http://www.google.com";

        HttpUtils utils = new HttpUtils();
        HttpPost post = utils.createPost(url);

        RequestConfig.Builder config = RequestConfig.copy(RequestConfig.DEFAULT);
        config.setConnectionRequestTimeout(5000); // 5s
        config.setSocketTimeout(5000); // 5s
        HttpPost expected = new HttpPost(url);
        expected.setConfig(config.build());

        // HttpPost and it's ancestors don't implement equals(), so we don't
        // have a great way of asserting the expectation.  For now, just rely
        // on the toString(), which includes the method and the URL, but will
        // ignore headers and such
        Assert.assertEquals(expected.toString(), post.toString());
    }

    // ToDo - Setup proper mocking to test sendPost

    // ToDo - Test sub-components (HttpHeaders, etc)

}
