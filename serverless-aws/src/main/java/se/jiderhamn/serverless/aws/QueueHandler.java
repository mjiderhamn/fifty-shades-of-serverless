package se.jiderhamn.serverless.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import se.jiderhamn.serverless.TransformationService;

/**
 * @author Mattias Jiderhamn
 */
public class QueueHandler implements RequestHandler<SQSEvent, Void> {
  
  static final String OUTPUT_QUEUE = "https://sqs.eu-central-1.amazonaws.com/047533455857/50shades-result";

  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    context.getLogger().log("About to process " + event.getRecords().size() + " record(s)");
    final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient(); // NOTE! Can take longer than default timeout!
    for(SQSEvent.SQSMessage msg : event.getRecords()) {
      final String output = TransformationService.transform(msg.getBody());

      // Send back result
      // context.getLogger().log("Sending " + output.length() + " bytes to " + OUTPUT_QUEUE);
      sqs.sendMessage(OUTPUT_QUEUE, output);
    }
    return null;
  }
}