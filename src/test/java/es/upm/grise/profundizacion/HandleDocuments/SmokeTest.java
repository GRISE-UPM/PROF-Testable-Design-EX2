package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;


import org.junit.Test;

public class SmokeTest {
	
	// 4.a - La aplicación genera las plantillas correctamente
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document doc = new Document(new DocumentIdProviderDouble(1), TemplateFactory.getInstance());
		doc.setTemplate("DECLARATION");
		doc.setTitle("A");
		doc.setAuthor("B");
		doc.setBody("C");
		assertEquals("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC", doc.getFormattedDocument());

	}
	
	// 4.b - La aplicación asigna el número de documento correcto
	@Test
	public void assignsIDCorrectly() throws NonRecoverableError {
		
		Document doc = new Document(new DocumentIdProviderDouble(3), TemplateFactory.getInstance());
		
		assertEquals(3, doc.getDocumentId());
	}
	
	// 4.c - Los números de documento asignados a documentos consecutivos son también números consecutivos
	@Test
	public void assignsConsecutiveIDsCorrectly() throws NonRecoverableError{
		
		DocumentIdProviderDouble idProv = new DocumentIdProviderDouble(2);
		
		Document doc1 = new Document(idProv, TemplateFactory.getInstance());
		Document doc2 = new Document(idProv, TemplateFactory.getInstance());
		
		assertEquals(2, doc1.getDocumentId());
		assertEquals(3, doc2.getDocumentId());
	}
	
	// 5.a - La aplicación detecta correctamente que el fichero de configuración no existe
	@Test(expected = NonRecoverableError.class)
	public void errorNoConfigFile() throws NonRecoverableError {
		
		DocumentIdProviderDouble idProv = new DocumentIdProviderDouble(1);
		
		idProv.getProperties("Ruta a fichero errónea");
	}
	
	// 5.b - La aplicación detecta correctamente que el driver MySQL no existe
	@Test(expected = NonRecoverableError.class)
	public void errorNoDriver() throws NonRecoverableError {
		
		DocumentIdProviderDouble idProv = new DocumentIdProviderDouble(1);
		
		idProv.loadDBDriver("Driver erróneo");
	}
	
	// 5.c - La aplicación detecta correctamente que hay más de uan fila en la tabla Counters
	@Test (expected = NonRecoverableError.class)
	public void tablaCountersConMasDeUnaFila() throws NonRecoverableError {
		
		DocumentIdProviderDouble idProv = new DocumentIdProviderDouble(1);
		
		idProv.getLastId();
	}
	
	// 5.d - La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizada
	@Test (expected = NonRecoverableError.class)
	public void falloActualizacionTablaCounters() throws NonRecoverableError {
		
		DocumentIdProviderDouble idProv = new DocumentIdProviderDouble(-1);
		
		idProv.getDocumentId();
	}
	
}
