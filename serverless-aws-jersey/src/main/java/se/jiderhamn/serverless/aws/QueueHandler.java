package se.jiderhamn.serverless.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import se.jiderhamn.serverless.TransformationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Mattias Jiderhamn
 */
public class QueueHandler implements RequestHandler<SQSEvent, Void> {
  
  static final String OUTPUT_QUEUE = "https://sqs.eu-central-1.amazonaws.com/047533455857/50shades-result";

  @Override
  public Void handleRequest(SQSEvent event, Context context)
  {
    context.getLogger().log("About to process " + event.getRecords().size() + " record(s)");
    final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient(); // NOTE! Can take longer than default timeout!
    for(SQSEvent.SQSMessage msg : event.getRecords()) {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      TransformationService.transform(new ByteArrayInputStream(msg.getBody().getBytes()), baos);
      
      // Send back result
      // context.getLogger().log("Sending " + baos.toByteArray().length + " bytes to " + OUTPUT_QUEUE);
      sqs.sendMessage(OUTPUT_QUEUE, new String(baos.toByteArray()));
    }
    return null;
  }
}