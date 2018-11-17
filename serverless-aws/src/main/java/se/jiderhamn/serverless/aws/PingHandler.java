package se.jiderhamn.serverless.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Mattias Jiderhamn
 */
@SuppressWarnings("unused")
public class PingHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {
  
  private static final Logger logger = LogManager.getLogger(PingHandler.class);

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {
    context.getLogger().log("Handling input: " + request);
    return new ApiGatewayResponse.Builder()
        .setStatusCode(200)
        .setRawBody("Input: " + request.getBody())
        .build();
  }
}