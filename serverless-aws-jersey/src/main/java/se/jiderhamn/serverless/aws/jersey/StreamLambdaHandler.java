package se.jiderhamn.serverless.aws.jersey;

import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Bootstrapping AWS request handler that bridges between AWS Lambda and Jersey
 */
public class StreamLambdaHandler implements RequestStreamHandler {
  private static final ResourceConfig jerseyApplication = new ResourceConfig()
      .packages("se.jiderhamn.serverless.aws.jersey")
      .register(JacksonFeature.class);
  private static final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler
      = JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
      throws IOException {
    handler.proxyStream(inputStream, outputStream, context);

    // just in case it wasn't closed by the mapper
    outputStream.close();
  }
}