package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mysql.jdbc.AssertionFailedException;

import static org.mockito.Mockito.*;

import java.util.Properties;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	/**
	 * a.	La aplicación genera las plantillas correctamente.
	 * @throws NonRecoverableError
	 * @throws RecoverableError
	 */
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
//		Document d = new Document();
//		d.setTemplate("DECLARATION");
//		d.setTitle("A");
//		d.setAuthor("B");
//		d.setBody("C");
		assertEquals("DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s", TemplateFactory.getTemplate("DECLARATION"));
		
	}
	
	@Test
	public void asignacionIdCorrectaDoc() throws NonRecoverableError {
		int value = 1;
		DocumentIdProvider doc = mock(DocumentIdProvider.class);
		when(doc.getDocumentId()).thenReturn(value);

		Document docTest = new Document(doc);
		
		docTest.setId();
				
		assertEquals(value, docTest.getDocumentId());
	}
	
//	@Test
//	public void asignacionIdConsecutivasDoc() throws NonRecoverableError {
//		int value = 1, value1 = 2;
//		DocumentIdProvider doc = mock(DocumentIdProvider.class);
//		when(doc.getDocumentId()).thenReturn(value);
//		
//		DocumentIdProvider doc1 = mock(DocumentIdProvider.class);
//		when(doc.getDocumentId()).thenReturn(value1);
//				
//		Document docTest = new Document(doc);
//		Document docTest1 = new Document(doc1);
//		
//		docTest.setId();
//		docTest1.setId();	
//		
//		assertTrue((int)docTest.getDocumentId() <(int) docTest1.getDocumentId());
//	}
	
	
	@Test
	public void deteccionFicheroConfig() throws NonRecoverableError {
		DocumentIdProvider doc = new DocumentIdProvider();

		String path = System.getenv("APP_HOME");
		
		Properties prop = new Properties();
		

		prop.setProperty("password", "tema5");
		prop.setProperty("url", "jdbc:mysql://piedrafita.ls.fi.upm.es:8000/tema5");
		prop.setProperty("username", "tema5");

		assertEquals(prop, doc.loadPropertiesFile(path));
	}
	
	@Test(expected = NonRecoverableError.class)
	public void testErrorDriver() throws NonRecoverableError {
		DocumentIdProvider doc = new DocumentIdProvider();
		doc.getDriverManager();

	}
}
