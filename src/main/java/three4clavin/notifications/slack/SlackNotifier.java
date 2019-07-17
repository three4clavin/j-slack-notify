package three4clavin.notifications.slack;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 * Command line interface leveraging SlackClient
 */
public class SlackNotifier {
    private static final Logger LOG = LoggerFactory.getLogger(SlackNotifier.class);

    private Options cmdLineOptions;
    private SlackClient slackClient;
    private String[] args;

    public SlackNotifier (String[] args) {
        buildCommandLineOptions();

        // For now, assume we get the auth token from an env var
        String slackAuth = System.getenv("SLACK_AUTH");
        if((slackAuth == null) || "".equals(slackAuth)) {
            usage("Slack auth token must be provided with the SLACK_AUTH environment variable");
        }
        slackClient = new SlackClient(slackAuth);

        this.args = args;
    }

    public static void main(String[] args) throws SlackNotificationException {
        SlackNotifier sn = new SlackNotifier(args);
        sn.run();
    }

    public void run() throws SlackNotificationException {
        SlackMessage message = parseCommandLine(args);
        slackClient.sendMessage(message);
    }

    private SlackMessage parseCommandLine (String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( cmdLineOptions, args);
        }
        catch(ParseException pe) {
            usage("Parse Exception: "+pe.getMessage());
        }

        if(cmd.hasOption("help")) {
            usage("");
        }

        String rawMessage = cmd.hasOption("raw") ? cmd.getOptionValue("raw") : null;

        SlackMessage message = new SlackMessage(
                rawMessage,
                cmd.getOptionValue("header"),
                true);
        SlackMessageAttachment attachment = new SlackMessageAttachment();
        attachment.setText(cmd.getOptionValue("text"));
        attachment.setFallback(cmd.getOptionValue("fallback"));
        attachment.setColor(cmd.getOptionValue("color"));
        if(cmd.hasOption("image")) {
            // "http://pa1.narvii.com/6390/77d3260160598eef96c66b7955528c4c49a61621_00.gif"
            attachment.setImageURL(cmd.getOptionValue("image"));
        }

        Properties longProperties = cmd.getOptionProperties("L");
        for(String propertyName : longProperties.stringPropertyNames()) {
            attachment.addField(propertyName, longProperties.getProperty(propertyName), false);
        }
        Properties shortProperties = cmd.getOptionProperties("S");
        for(String propertyName : shortProperties.stringPropertyNames()) {
            attachment.addField(propertyName, shortProperties.getProperty(propertyName), true);
        }

        message.addAttachment(attachment);

        return message;
    }

    private void buildCommandLineOptions () {
        cmdLineOptions = new Options();
        Option help = Option.builder("h")
                .longOpt("help")
                .argName("help" )
                .desc("print this help message and exit" )
                .build();
        cmdLineOptions.addOption(help);
        Option raw = Option.builder("r")
                .longOpt("raw")
                .argName("rawMessage" )
                .hasArg()
                .desc("raw message text to display" )
                .build();
        cmdLineOptions.addOption(raw);
        Option header = Option.builder("m")
                .longOpt("header")
                .argName("headerMessage" )
                .hasArg()
                .desc("header message to display" )
                .required()
                .build();
        cmdLineOptions.addOption(header);
        Option text = Option.builder("t")
                .longOpt("text")
                .argName("text" )
                .hasArg()
                .desc("basic text message to send" )
                .required()
                .build();
        cmdLineOptions.addOption(text);
        Option fallback = Option.builder("f")
                .longOpt("fallback")
                .argName("fallbackText" )
                .hasArg()
                .desc("text to display if full message can't be displayed" )
                .required()
                .build();
        cmdLineOptions.addOption(fallback);
        Option color = Option.builder("c")
                .longOpt("color")
                .argName("color" )
                .hasArg()
                .desc("color for message" )
                .required()
                .build();
        cmdLineOptions.addOption(color);
        Option imgUrl = Option.builder("i")
                .longOpt("image")
                .argName("imageURL" )
                .hasArg()
                .desc("image URL to display" )
                .build();
        cmdLineOptions.addOption(imgUrl);
        Option longField = Option.builder()
                .longOpt("L")
                .argName("name=value")
                .hasArgs()
                .valueSeparator()
                .numberOfArgs(2)
                .desc("specify a custom long attachment field")
                .build();
        cmdLineOptions.addOption(longField);
        Option shortField = Option.builder()
                .longOpt("S")
                .argName("name=value")
                .hasArgs()
                .valueSeparator()
                .numberOfArgs(2)
                .desc("specify a custom short attachment field")
                .build();
        cmdLineOptions.addOption(shortField);
    }

    private void usage(String message) {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        formatter.printUsage(pw,80,"SlackNotifier", cmdLineOptions);
        pw.flush();

        LOG.error("\n"+sw.toString());
        LOG.error(message);
        System.exit(1);
    }
}
