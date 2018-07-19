package se.jiderhamn.serverless.azure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.*;
import se.jiderhamn.serverless.TransformationService;

/**
 * @author Mattias Jiderhamn
 */
public class AzureFunction {
  
  public static final String INPUT_QUEUE = "50shades-in";
  
  public static final String RESULT_QUEUE = "50shades-result";

  /** Ping method to allow verifying Function App is up and running */
  @FunctionName("ping")
  public String ping(
      @HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
      @BindingName("name") String name,
      final ExecutionContext context) {
    context.getLogger().info("Java HTTP trigger processed a request.");

    return "Pong" + ((name == null) ? "" : " - Name: " + name);
  }

  /** Transform XML document from one format to another */
  @FunctionName("transform")
  public byte[] transform(
      @HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) byte[] input,
      final ExecutionContext context) {
    context.getLogger().info("Transforming HTTP input");
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    TransformationService.transform(new ByteArrayInputStream(input), baos);
    return baos.toByteArray();
  }

  /** Transform XML document from one format to another */
  @FunctionName("transformStorageQueue")
  @QueueOutput(name = "queueResult", queueName = RESULT_QUEUE, connection = "AzureWebJobsStorage")
  public byte[] transformQueue(
      @QueueTrigger(name = "input", dataType = "binary", queueName = AzureFunction.INPUT_QUEUE, connection = "AzureWebJobsStorage") byte[] input,
      final ExecutionContext context) {
    context.getLogger().info("Transforming storage queue input");
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    TransformationService.transform(new ByteArrayInputStream(input), baos);
    return baos.toByteArray();
  }
}