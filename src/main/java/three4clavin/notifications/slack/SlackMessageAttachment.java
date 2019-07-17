package three4clavin.notifications.slack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an attachment to a Slack message.  Attachments usually have
 * a basic text message, and can optionally include images, key-value pair
 * extended fields, and other attributes.
 */
public class SlackMessageAttachment {
    private String text;
    private String imageURL;
    private String color;
    private String fallback;
    private List<Map<String, Object>> fields;

    /**
     * Creates an empty attachment
     */
    public SlackMessageAttachment() {
        text     = null;
        imageURL = null;
        color    = null;
        fallback = null;
        fields   = new ArrayList<Map<String, Object>>();
    }

    /**
     * Creates a basic attachment with no extended fields
     *
     * @param text Text value to display
     * @param imageURL URL for image to display
     * @param color Color to use
     * @param fallback Fallback message
     */
    public SlackMessageAttachment(String text, String imageURL, String color, String fallback) {
        this.text     = text;
        this.imageURL = imageURL;
        this.color    = color;
        this.fallback = fallback;
        fields = new ArrayList<Map<String, Object>>();
    }

    /**
     * Adds a key-value pair attribute to display
     *
     * @param name Key of attribute
     * @param value Value of attribute
     * @param isShort If false, attribute will take up an entire row in the
     *                attachment display.  If true, can be displayed in
     *                columns with other attributes
     */
    public void addField (String name, String value, boolean isShort) {
        Map<String, Object> field = new HashMap<String, Object>();
        field.put("title", name);
        field.put("value", value);
        field.put("short", isShort);
        fields.add(field);
    }

    /**
     * Converts the attachment to a HashMap - normally a prelude to converting
     * to a JSON payload.  Format:
     *   {
     *     "text": String,
     *     "image_url": String,
     *     "color": String,
     *     "fallback": String,
     *     "fields": {
     *       "key": String,
     *       "key": String,
     *       ...
     *     }
     *   }
     *
     * @return Map representing attachment
     */
    public Map<String, Object> toMap () {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fields", fields);
        map.put("text", text);
        map.put("fallback", fallback);
        map.put("image_url", imageURL);
        map.put("color", color);
        return map;
    }

    // ---- Generated Code ----

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    public List<Map<String, Object>> getFields() {
        return fields;
    }

    public void setFields(List<Map<String, Object>> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "SlackMessageAttachment{" +
                "text='" + text + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", color='" + color + '\'' +
                ", fallback='" + fallback + '\'' +
                ", fields=" + fields +
                '}';
    }
}
