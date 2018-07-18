package se.jiderhamn.serverless;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;

/**
 * Service class containing the actual work to be performed by the serverless functions
 * @author Mattias Jiderhamn
 */
public class TransformationService {
  
  private static Transformer transformer;
  
  private static Transformer getTransformer() {
    if(transformer == null) { // Not yet initialized
      TransformerFactory factory = TransformerFactory.newInstance();
      final String xslPath = "transformation.xsl";
      final InputStream is = TransformationService.class.getClassLoader().getResourceAsStream(xslPath);
      final URL xslt = TransformationService.class.getClassLoader().getResource(xslPath);
      if(is == null || xslt == null)
        throw new RuntimeException("Cannot find XSLT " + xslt);

      try {
        transformer = factory.newTransformer(new StreamSource(is, xslt.toExternalForm()));
        transformer.setOutputProperty(OutputKeys.METHOD, "xml"); 
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");
        // transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_LINE_SEPARATOR, "\n");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      }
      catch (TransformerConfigurationException e) {
        throw new RuntimeException(e);
      }
    }
    return transformer;
  }
  
  /** Transform XML document from one format to another */
  public static void transform(InputStream input, OutputStream output) {
    try {
      getTransformer().transform(new StreamSource(input), new StreamResult(output));
    }
    catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }
  
}