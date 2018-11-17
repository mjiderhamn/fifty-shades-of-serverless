package se.jiderhamn.serverless.aws.jersey;


import se.jiderhamn.serverless.TransformationService;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

/** Jersey REST service implementation */
@Path("/transform")
public class TransformResource {

  @POST
  @Consumes(MediaType.APPLICATION_OCTET_STREAM)
  @Produces(MediaType.TEXT_XML)
  public StreamingOutput transformStream(InputStream is) {
    return outputStream -> TransformationService.transform(is, outputStream);
  }
  
}