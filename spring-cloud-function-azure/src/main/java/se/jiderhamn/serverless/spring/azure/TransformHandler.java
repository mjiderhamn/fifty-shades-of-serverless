package se.jiderhamn.serverless.spring.azure;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.AzureSpringBootRequestHandler;

/**
 * @author Mattias Jiderhamn
 */
public class TransformHandler extends AzureSpringBootRequestHandler<byte[], byte[]> {

	@FunctionName("transform")
	public byte[] transform(@HttpTrigger(name="req", methods={HttpMethod.GET, HttpMethod.POST}, authLevel=AuthorizationLevel.ANONYMOUS) byte[] input, ExecutionContext context) {
		return handleRequest(input, context);
	}

}