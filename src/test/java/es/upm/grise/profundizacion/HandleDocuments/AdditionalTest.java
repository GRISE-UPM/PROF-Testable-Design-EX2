package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

public class AdditionalTest extends SmokeTest {

  @Test(expected = NonRecoverableError.class)
  public void noConfigFileDetected() throws NonRecoverableError {
    dIdProvider.loadProperties("PropertiesFile", new Properties());
  }

  @Test(expected = NonRecoverableError.class)
  public void noMySqlDriverDetected() throws NonRecoverableError {
    dIdProvider.loadDbDriver("MySqlDriver");
  }

  @Test(expected = NonRecoverableError.class)
  public void tableCountersHasSeveralRowsDetected() throws NonRecoverableError {
    dIdProvider.verifyNumberOfValues(2);
  }

  @Test(expected = NonRecoverableError.class)
  public void incorrectDocumentIdUpdateInTableCountersDetected() throws NonRecoverableError {
    DocumentIdProvider dIdProvider2 = new DocumentIdProviderDouble(-10);
    assertNotNull(dIdProvider2);
    new Document(dIdProvider2.getDocumentId());
  }

}
