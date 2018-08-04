package se.jiderhamn.serverless.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import se.jiderhamn.serverless.TransformationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Mattias Jiderhamn
 */
public class AwsHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {
    context.getLogger().log("Input: " + request.getBody());
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    TransformationService.transform(new ByteArrayInputStream(request.getBody().getBytes()), baos);
    return new ApiGatewayResponse(new String(baos.toByteArray()), null, 200, false);
  }
}