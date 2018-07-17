package se.jiderhamn.serverless.aws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import se.jiderhamn.serverless.TransformationService;

/**
 * @author Mattias Jiderhamn
 */
public class AwsHandler {
  public void handler(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
     // TODO
    TransformationService.transform(inputStream, outputStream);
  }
}