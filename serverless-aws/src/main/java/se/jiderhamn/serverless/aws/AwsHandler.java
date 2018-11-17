package se.jiderhamn.serverless.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import se.jiderhamn.serverless.TransformationService;

/**
 * @author Mattias Jiderhamn
 */
@SuppressWarnings("unused")
public class AwsHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {
    context.getLogger().log("Input: " + request.getBody());
    final byte[] output = TransformationService.transform(request.getBody().getBytes());
    return new ApiGatewayResponse.Builder()
        .setStatusCode(200)
        .setRawBody(new String(output))
        .build();
  }
}