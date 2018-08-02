package se.jiderhamn.serverless.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.junit.Test;

/**
 * @author Mattias Jiderhamn
 */
public class SendSQSMessageIT {
  
  private static final String INPUT_QUEUE = "https://sqs.eu-central-1.amazonaws.com/047533455857/50shades-input";

  @Test
  public void sendSQSMessage() {
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    sqs.sendMessage(INPUT_QUEUE, "<?xml version=\"1.0\"?>\n" +  // TODO Read file instead
        "<order>\n" +
        "  <head>\n" +
        "    <customer>\n" +
        "      <id>123</id>\n" +
        "    </customer>\n" +
        "  </head>\n" +
        "  <lines>\n" +
        "    <line>\n" +
        "      <article>\n" +
        "        <id>FOO</id>\n" +
        "      </article>\n" +
        "      <quantity>3</quantity>\n" +
        "    </line>\n" +
        "    <line>\n" +
        "      <article>\n" +
        "        <id>BAR</id>\n" +
        "      </article>\n" +
        "      <quantity>1</quantity>\n" +
        "    </line>\n" +
        "  </lines>\n" +
        "</order>");
  }
  
  @Test
  public void receiveMessage() {
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    final ReceiveMessageResult messageResult = sqs.receiveMessage(QueueHandler.OUTPUT_QUEUE);
    if(messageResult.getMessages().isEmpty())
      System.err.println("No messages");
    for(Message message : messageResult.getMessages()) {
      System.out.println("Result: " + message.getBody());
    }

  }
}