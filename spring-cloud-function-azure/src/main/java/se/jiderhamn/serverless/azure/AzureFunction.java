package se.jiderhamn.serverless.azure;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import se.jiderhamn.serverless.TransformationService;

import java.util.Optional;

/**
 * @author Mattias Jiderhamn
 */
public class AzureFunction {
  
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
  @FunctionName("transform1")
  public byte[] transform(
      @HttpTrigger(name = "req", methods = {"post"}, authLevel = AuthorizationLevel.ANONYMOUS) byte[] input,
      final ExecutionContext context) {
    context.getLogger().info("Transforming HTTP input");
    return TransformationService.transform(input);
  }
  
}