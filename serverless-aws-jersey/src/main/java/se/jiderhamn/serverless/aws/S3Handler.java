package se.jiderhamn.serverless.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import se.jiderhamn.serverless.TransformationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author Mattias Jiderhamn
 */
public class S3Handler implements RequestHandler<S3Event, String> {
  @Override
  public String handleRequest(S3Event s3event, Context context) {
    for(S3EventNotification.S3EventNotificationRecord record : s3event.getRecords()) {

      final String inBucket = record.getS3().getBucket().getName();
      final String key;
      try {
        key = URLDecoder.decode(record.getS3().getObject().getKey(), "UTF-8");
      }
      catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }

      // Download source
      final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
      final S3Object s3Object = s3Client.getObject(inBucket, key);
      final InputStream is = s3Object.getObjectContent();

      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      TransformationService.transform(is, baos);

      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentLength(baos.size());

      // Uploading to S3 destination bucket
      final String outBucket = inBucket + ".out";
      if(! s3Client.doesBucketExist(outBucket)) {
        context.getLogger().log("Creating bucket " + outBucket);
        s3Client.createBucket(outBucket);
      }
      
      s3Client.putObject(outBucket, key, new ByteArrayInputStream(baos.toByteArray()), meta);

      context.getLogger().log("Successfully transformed " + inBucket + "/"
          + key + " to " + outBucket + "/" + key);
    }
    return "Ok";
  }
}