package three4clavin.notifications.slack;

import three4clavin.utils.json.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Slack message.  A message can contain:
 *   Raw message - String representing a non-serialized version of the message
 *   Header - Basic message to convey
 *   Include detail - Boolean whether attachments should also be displayed
 *   Attachments - list of Slack message attachments with additional detail
 */
public class SlackMessage {
    private String  rawMessage;
    private String  header;
    private boolean includeDetail;
    private List<SlackMessageAttachment> attachments;

    /**
     * Basic slack message, no additional detail attachments
     *
     * @param rawMessage Raw message string
     * @param header Basic message detail
     * @param includeDetail Whether additional detail fields should be
     *                      displayed (if present)
     */
    public SlackMessage (String rawMessage,
                         String header,
                         boolean includeDetail) {
        this.rawMessage    = rawMessage;
        this.header        = header;
        this.includeDetail = includeDetail;
        attachments = new ArrayList<SlackMessageAttachment>();
    }

    /**
     * Basic slack message, no additional detail attachments
     *
     * @param rawMessageMap Raw message as a HashMap
     * @param header Basic message detail
     * @param includeDetail Whether additional detail fields should be
     *                      displayed (if present)
     */
    public SlackMessage (Map<String, Object> rawMessageMap,
                         String header,
                         boolean includeDetail) {
        this.rawMessage = JsonUtils.toJson(rawMessageMap);
        this.header = header;
        this.includeDetail = includeDetail;
        attachments = new ArrayList<SlackMessageAttachment>();
    }

    /**
     * Adds a message detail attachment
     *
     * @param attachment Message detail
     */
    public void addAttachment(SlackMessageAttachment attachment) {
        attachments.add(attachment);
    }

    /**
     * Converts message to JSON payload string
     *
     * @return JSON payload
     */
    public String toJson () {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("text", header);

        if(includeDetail) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            map.put("attachments", list);

            for(SlackMessageAttachment attachment : this.attachments) {
                list.add(attachment.toMap());
            }
        }

        return JsonUtils.toJson(map);
    }

    // ---- Generated Methods ----

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isIncludeDetail() {
        return includeDetail;
    }

    public void setIncludeDetail(boolean includeDetail) {
        this.includeDetail = includeDetail;
    }

    public List<SlackMessageAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SlackMessageAttachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "SlackMessage{" +
                "rawMessage='" + rawMessage + '\'' +
                ", header='" + header + '\'' +
                ", includeDetail=" + includeDetail +
                ", attachments=" + attachments +
                '}';
    }
}
