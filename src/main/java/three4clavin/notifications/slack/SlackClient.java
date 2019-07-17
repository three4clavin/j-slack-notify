package three4clavin.notifications.slack;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import three4clavin.utils.http.HttpHeaders;
import three4clavin.utils.http.HttpUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Interface with Slack API
 */
public class SlackClient {
    private static final Logger LOG = LoggerFactory.getLogger(SlackClient.class);

    public static final String DEFAULT_SLACK_URL_BASE = "https://hooks.slack.com/services";

    private String slackUrlBase;
    private String slackAuthToken;

    private HttpUtils http;

    /**
     * Constructor leveraging default Slack API URL base
     *
     * @param authToken Auth token to interact with Slack
     */
    public SlackClient(String authToken) {
        init(authToken, DEFAULT_SLACK_URL_BASE);
    }

    /**
     * Constructor supplying Slack API URL base
     *
     * @param authToken Auth token to interact with Slack
     * @param urlBase URL Base for Slack API
     */
    public SlackClient(String authToken, String urlBase) {
        init(authToken, urlBase);
    }

    private void init(String authToken, String urlBase) {
        setSlackAuthToken(authToken);
        setSlackUrlBase(urlBase);

        http = new HttpUtils();
    }

    /**
     * Send message to Slack API
     *
     * @param message Message to send
     * @throws SlackNotificationException if message can't be sent for any reason
     */
    public void sendMessage (SlackMessage message) throws SlackNotificationException {
        HttpPost post = http.createPost(slackUrlBase + slackAuthToken);

        LOG.info("-------------- Posting to "+post.toString());

        HttpHeaders headers = new HttpHeaders(
                "Accept", "application/json",
                "Content-type", "application/json"
        );
        try {
            StringEntity entity = new StringEntity(message.toJson());
            post.setEntity(entity);
            http.sendPost(post, headers);

            LOG.debug("Message sent: "+entity+" / "+message.toJson());
        }
        catch(UnsupportedEncodingException uee) {
            throw new SlackNotificationException("Could not encode message: "+uee.getMessage());
        }
        catch(IOException ioe) {
            throw new SlackNotificationException("Could not post message: "+ioe.getMessage());
        }

    }

    // ---- Generated Methods ----

    public String getSlackUrlBase() {
        return slackUrlBase;
    }

    public void setSlackUrlBase(String slackUrlBase) {
        this.slackUrlBase = slackUrlBase;
    }

    public String getSlackAuthToken() {
        return slackAuthToken;
    }

    public void setSlackAuthToken(String slackAuthToken) {
        this.slackAuthToken = slackAuthToken;
    }
}
