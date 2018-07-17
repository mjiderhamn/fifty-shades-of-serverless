package se.jiderhamn.serverless.azure;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

/**
 * @author Mattias Jiderhamn
 */
public class AzureFunction {

  /**
   * TODO
   * This function listens at endpoint "/api/hello". Two ways to invoke it using "curl" command in bash:
   * 1. curl -d "HTTP Body" {your host}/api/hello
   * 2. curl {your host}/api/hello?name=HTTP%20Query
   */
  @SuppressWarnings("unused")
  @FunctionName("hello")
  public HttpResponseMessage<String> hello(
      @HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
      final ExecutionContext context) {
    // TODO
    context.getLogger().info("Java HTTP trigger processed a request.");

    // Parse query parameter
    String query = request.getQueryParameters().get("name");
    String name = request.getBody().orElse(query);

    if (name == null) {
      return request.createResponse(400, "Please pass a name on the query string or in the request body");
    } else {
      return request.createResponse(200, "Hello, " + name);
    }
  }
}