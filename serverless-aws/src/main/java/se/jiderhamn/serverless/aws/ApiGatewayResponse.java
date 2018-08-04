package se.jiderhamn.serverless.aws;

import java.util.Collections;
import java.util.Map;

/**
 * TODO Replace with serverless.com template https://github.com/serverless/serverless/blob/master/lib/plugins/create/templates/aws-java-maven/src/main/java/com/serverless/ApiGatewayResponse.java
 * API gateway compatible function response
 * @author Mattias Jiderhamn
 */
public class ApiGatewayResponse {
  private final String body;
  	private final Map<String, String> headers;
  	private final int statusCode;
  	private final boolean base64Encoded;
  
  	public ApiGatewayResponse(String body, Map<String, String> headers,
                              int statusCode, boolean base64Encoded) {
  		this.statusCode = statusCode;
  		this.body = body;
  		this.headers = (headers == null) ? null : Collections.unmodifiableMap(headers);
  		this.base64Encoded = base64Encoded;
  	}
  
  	public String getBody() {
  		return body;
  	}
  
  	public Map<String, String> getHeaders() {
  		return headers;
  	}
  
  	public int getStatusCode() {
  		return statusCode;
  	}

  	// TODO Use Jackson annotation to get "isBase64Encoded"
  	public boolean isIsBase64Encoded() {
  		return base64Encoded;
  	}
  	
}