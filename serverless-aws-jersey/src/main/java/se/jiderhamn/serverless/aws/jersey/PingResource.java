package se.jiderhamn.serverless.aws.jersey;


import java.util.Map;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.WILDCARD)
  public Response response() {
    Map<String, String> pong = new HashMap<>();
    pong.put("pong", "Hello, World!");
    return Response.status(200).entity(pong).build();
  }

  @Path("/string")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.WILDCARD)
  public String string() {
    return "Hello, World! - string only";
  }
}