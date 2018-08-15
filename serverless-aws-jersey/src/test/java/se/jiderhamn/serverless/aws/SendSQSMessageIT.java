package se.jiderhamn.serverless.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Mattias Jiderhamn
 */
public class SendSQSMessageIT {
  
  private static final String INPUT_QUEUE = "https://sqs.eu-central-1.amazonaws.com/047533455857/50shades-input";

  @Test
  public void sendSQSMessage() throws IOException {
    final String input = IOUtils.toString(getClass().getResourceAsStream("input.xml"));
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    sqs.sendMessage(INPUT_QUEUE, input);
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