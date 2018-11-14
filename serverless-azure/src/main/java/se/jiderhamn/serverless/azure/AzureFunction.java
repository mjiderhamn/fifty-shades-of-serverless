package se.jiderhamn.serverless.azure;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.*;
import se.jiderhamn.serverless.TransformationService;

import java.util.Optional;

import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.HttpMethod.POST;

/**
 * {@code Byte[]} is used instead of {@code byte[]} as a workaround for https://github.com/Azure/azure-functions-java-worker/issues/239
 * @author Mattias Jiderhamn
 */
public class AzureFunction {
  
  public static final String INPUT_QUEUE = "50shades-in";
  
  public static final String RESULT_QUEUE = "50shades-result";

  public static final String AZURE_WEB_JOBS_STORAGE = "AzureWebJobsStorage";

  public static final String BLOB_INPUT_PATH = "input";

  /** Ping method to allow verifying Function App is up and running */
  @FunctionName("ping")
  public String ping(
      @HttpTrigger(name = "req", methods = {GET, POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
      @BindingName("name") String name,
      final ExecutionContext context) {
    context.getLogger().info("Java HTTP trigger processed a request.");

    return "Pong" + ((name == null) ? "" : " - Name: " + name);
  }

  /** Transform XML document from one format to another */
  @FunctionName("transform")
  public byte[] transform(
      @HttpTrigger(name = "req", methods = {POST}, authLevel = AuthorizationLevel.ANONYMOUS, dataType = "binary") Byte[] request,
      final ExecutionContext context) {
    context.getLogger().info("Transforming HTTP input");
    return TransformationService.transform(request);
  }

  /** Transform XML document from one format to another */
  @FunctionName("transformStorageQueue")
  @QueueOutput(name = "queueResult", queueName = RESULT_QUEUE, connection = AZURE_WEB_JOBS_STORAGE)
  public byte[] transformQueue(
      @QueueTrigger(name = "input", dataType = "binary", queueName = AzureFunction.INPUT_QUEUE, connection = AZURE_WEB_JOBS_STORAGE) Byte[] input,
      final ExecutionContext context) {
    context.getLogger().info("Transforming storage queue input");
    return TransformationService.transform(input);
  }

  /** See https://docs.microsoft.com/en-us/azure/azure-functions/functions-bindings-storage-blob#trigger---blob-name-patterns */
  @FunctionName("transformBlob")
  @BlobOutput(name = "blobResult", path = "result/{name}", connection = AZURE_WEB_JOBS_STORAGE)
  public byte[] transformBlob(
      @BlobTrigger(name = "content", dataType = "binary", path = BLOB_INPUT_PATH + "/{name}", connection = AZURE_WEB_JOBS_STORAGE) Byte[] content,
      // @BindingName("name") String name,
      final ExecutionContext context) {
    context.getLogger().info("Transforming blob input");
    return TransformationService.transform(content);
  }
}