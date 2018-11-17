package se.jiderhamn.serverless.azure;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mattias Jiderhamn
 */
public class AzureStorageQueueIT {

  private CloudQueueClient queueClient;
  private CloudBlobClient blobClient;

  @Before
  public void init() throws URISyntaxException, IOException, InvalidKeyException {
    String storageConnectionString = AzureStorageTestUtils.getConnectionString();

    final CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(storageConnectionString);
    queueClient = cloudStorageAccount.createCloudQueueClient();
    blobClient = cloudStorageAccount.createCloudBlobClient();
  }

  @Test
  public void enqueueMessage() throws URISyntaxException, StorageException, IOException {
    final CloudQueue queue = queueClient.getQueueReference(AzureFunction.INPUT_QUEUE);
    queue.createIfNotExists();
    queue.addMessage(new CloudQueueMessage(FileUtils.readFileToByteArray(AzureStorageTestUtils.getInputFile())));
  }

  @Test
  public void addBlob() throws URISyntaxException, StorageException, IOException {

    final CloudBlobContainer container = blobClient.getContainerReference(AzureFunction.BLOB_INPUT_PATH);
    container.createIfNotExists();
    container.getBlockBlobReference(System.currentTimeMillis() + ".xml")
        .uploadFromFile(AzureStorageTestUtils.getInputFile().getAbsolutePath());
  }
}