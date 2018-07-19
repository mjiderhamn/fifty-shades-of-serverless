package se.jiderhamn.serverless.azure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Properties;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mattias Jiderhamn
 */
public class AzureStorageQueueIT {

  private CloudQueueClient queueClient;

  @Before
  public void init() throws URISyntaxException, IOException, InvalidKeyException {
    final File propsFile = new File(new File(
        AzureStorageQueueIT.class.getClassLoader().getResource("./").toURI()).getParentFile().getParentFile(), 
        "azure.properties");
    if(! propsFile.exists()) {
      throw new RuntimeException(propsFile.getAbsolutePath() + " does not exist - please create");
    }

    Properties props = new Properties();
    props.load(new FileInputStream(propsFile));
    String storageConnectionString = props.getProperty("StorageConnectionString");
    Assert.assertNotNull("Please provide StorageConnectionString in " + propsFile, storageConnectionString);

    final CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(storageConnectionString);
    queueClient = cloudStorageAccount.createCloudQueueClient();
  }
  
  @Test
  public void enqueue() throws URISyntaxException, StorageException, IOException {
    final CloudQueue queue = queueClient.getQueueReference(AzureFunction.INPUT_QUEUE);
    queue.createIfNotExists();
    queue.addMessage(new CloudQueueMessage(IOUtils.toByteArray(getClass().getResourceAsStream("input.xml"))));
  }
}