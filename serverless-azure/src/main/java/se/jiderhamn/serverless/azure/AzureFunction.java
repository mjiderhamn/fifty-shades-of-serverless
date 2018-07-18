package se.jiderhamn.serverless.azure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import se.jiderhamn.serverless.TransformationService;

/**
 * @author Mattias Jiderhamn
 */
public class AzureFunction {

  /** Ping method to allow verifying Function App is up and running */
  @SuppressWarnings("unused")
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
    context.getLogger().info("Transforming input");
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    TransformationService.transform(new ByteArrayInputStream(input), baos);
    return baos.toByteArray();
  }
}