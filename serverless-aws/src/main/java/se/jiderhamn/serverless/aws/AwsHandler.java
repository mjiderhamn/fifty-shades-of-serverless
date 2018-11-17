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
    final String output = TransformationService.transform(request.getBody());
    return ApiGatewayResponse.builder()
        .setStatusCode(200)
        .setRawBody(output)
        .build();
  }
}