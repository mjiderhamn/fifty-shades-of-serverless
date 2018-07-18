package se.jiderhamn.serverless;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class TransformationServiceTest {
  
  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  @org.junit.Test
  public void transform() {
    final InputStream is = getClass().getResourceAsStream("./input.xml");
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    TransformationService.transform(is, baos);
    assertEquals("<Order customerId=\"123\">" + LINE_SEPARATOR +
        "  <OrderLine ArticleNo=\"FOO\" Quantity=\"3\"/>" + LINE_SEPARATOR +
        "  <OrderLine ArticleNo=\"BAR\" Quantity=\"1\"/>" + LINE_SEPARATOR +
        "</Order>" + LINE_SEPARATOR, baos.toString());
  }
}