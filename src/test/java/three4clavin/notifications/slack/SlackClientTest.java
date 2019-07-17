package three4clavin.notifications.slack;

import org.junit.Test;
import three4clavin.notifications.slack.SlackClient;

public class SlackClientTest {
    public SlackClientTest() {
    }

    @Test
    public void testClient() {
        // ToDo - Need to make proper interfaces in order to mock
        SlackClient client = new SlackClient("foo");
    }

    // ToDo - Test sub-components (SlackMessage, SlackMessageAttachment, etc)
}
