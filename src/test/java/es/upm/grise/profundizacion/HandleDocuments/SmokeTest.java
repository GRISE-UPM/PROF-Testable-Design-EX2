package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	private DocumentIdProvider doc;
	private Document doc2;
	
	@Before
	public void setUp () throws NonRecoverableError{
		doc = new DocumentIdProviderDouble(1);
		doc2 = new Document(doc);
		
	}
	// Apartado 4
	
	// a)	La aplicación genera las plantillas correctamente.
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {	
		doc2.setTemplate("DECLARATION");
		doc2.setTitle("A");
		doc2.setAuthor("B");
		doc2.setBody("C");
		assertEquals("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC", doc2.getFormattedDocument());
	}
	
	// b)	La aplicación asigna el número de documento correcto.
	@Test
	public void numberOfDocumentRight() throws NonRecoverableError {
		int id_doc = (int) doc2.getDocumentId();
		assertEquals(1, id_doc);
	}
	
	// c)	Los números de documento asignados a documentos consecutivos son también números consecutivos.
	@Test
	public void consecutiveId() throws NonRecoverableError {
		Document doc3 = new Document(new DocumentIdProviderDouble(2));
		assertEquals(1, (int) doc2.getDocumentId());
		assertEquals(2, (int) doc3.getDocumentId());
	}

	// Apartado 5
	
	// a)	La aplicación detecta correctamente que el fichero de configuración no existe.
	@Test (expected = NonRecoverableError.class)
	public void nonExistentFile() throws NonRecoverableError {
		new DocumentIdProviderDouble(1).loadProperties("Path imaginario 123");
	}
	
	// b)	La aplicación detecta correctamente que el driver MySQL no existe.
	@Test (expected = NonRecoverableError.class)
	public void NonExistentMysqlDriver() throws NonRecoverableError {
		doc.loadDbDriver("Driver inexistente");
	}
	
	// c) La aplicación detecta correctamente que hay más de una fila en la tabla Counters
	@Test (expected = NonRecoverableError.class)
	public void moreThanOneFile () throws NonRecoverableError {
		doc.getLast(null, false);
	}
	
	
	// d)	La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente
	@Test (expected = NonRecoverableError.class)
	public void failUpdatingCountersTable() throws NonRecoverableError {
		for(int i = 0; i < 5; i++) {  // <- Simulamos que a la 5º de fallo
			doc.getDocumentId();
			
		}
		
	
	}
}