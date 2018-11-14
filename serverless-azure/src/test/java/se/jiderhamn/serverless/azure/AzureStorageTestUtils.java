package se.jiderhamn.serverless.azure;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

class AzureStorageTestUtils {
  static String getConnectionString() throws URISyntaxException, IOException {
    File projectDir = new File(AzureStorageQueueIT.class.getClassLoader().getResource("./").toURI()).getParentFile().getParentFile();
    final File propsFile = new File(projectDir, "azure.properties");
    if(! propsFile.exists()) {
      throw new RuntimeException(propsFile.getAbsolutePath() + " does not exist - please create");
    }

    Properties props = new Properties();
    props.load(new FileInputStream(propsFile));
    String storageConnectionString = props.getProperty("StorageConnectionString");
    Assert.assertNotNull("Please provide StorageConnectionString in " + propsFile, storageConnectionString);
    return storageConnectionString;
  }

  static byte[] getInputBytes() throws IOException {
    return IOUtils.toByteArray(AzureStorageTestUtils.class.getResourceAsStream("input.xml"));
  }

  static File getInputFile() throws URISyntaxException {
    return new File(AzureStorageTestUtils.class.getResource("input.xml").toURI());
  }
}
