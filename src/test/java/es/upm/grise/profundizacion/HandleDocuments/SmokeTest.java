package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	int CORRECT_ID=3;
	InputStream INPUT = null;
	Properties PROPERTIES = new Properties();
	
	
	//Este test comprueba el correcto funcionamiento de la clase
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		
		assertEquals("DOCUMENT ID: 2\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	
	//Este test comprueba que se devuelva el id correcto
	@Test
 	public void correctNumberID() throws NonRecoverableError, RecoverableError {

 		Document d = new Document();

 		assertEquals(CORRECT_ID,d.getDocumentId());
 	}
	
	//Este test comprueba que dos documentos consecutivos tienen id consecutivo
	@Test
	public void consecutiveNumbers() throws NonRecoverableError, RecoverableError {

		Document d1 = new Document();
		Document d2 = new Document();
		
		assertEquals(d1.getDocumentId(), d2.getDocumentId()-1); 
	}
	
	//Este test comprueba que al llegar a un limite se lanza la excepcion de contador
	@Test(expected = NonRecoverableError.class)
 	public void counterActualizationFailure() throws NonRecoverableError{
		DocumentProviderTest testCounter = new DocumentProviderTest();
		testCounter.getDocumentId();
		testCounter.getDocumentId();
		testCounter.getDocumentId();
		testCounter.getDocumentId();
		testCounter.getDocumentId();

 	}
	
	//Este test comprueba que no existe el config file
	@Test(expected = NonRecoverableError.class)
 	public void nonExistingConfigFile() throws NonRecoverableError{
		DocumentProviderTest testConf = new DocumentProviderTest();
		testConf.loadPropertiesInFile(PROPERTIES, INPUT, "test");

 	}
	
	//Este test comprueba que no existe el driver
	@Test(expected = NonRecoverableError.class)
 	public void badDriver() throws NonRecoverableError{
		DocumentProviderTest testConf = new DocumentProviderTest();
		testConf.loadDBDriver("test");

 	}
	
	//Este test comprueba que no haya más de una fila
	@Test(expected = NonRecoverableError.class)
 	public void counterMoreThan1RowFailure() throws NonRecoverableError{
		DocumentProviderTest testConf = new DocumentProviderTest();
		testConf.getlastObjectId(false);

 	}

}
