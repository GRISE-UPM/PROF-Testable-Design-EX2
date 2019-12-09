package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	@Test
	public void templateFromatOk() throws NonRecoverableError, RecoverableError {

		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("Vientos de inverno");
		d.setAuthor("George R. R. Martin");
		d.setBody("Terminalo ya hombre");
		assertEquals("DOCUMENT ID: 2\n\nTitle : Vientos de inverno\nAuthor: George R. R. Martin\n\nTerminalo ya hombre", d.getFormattedDocument());	
	}
	
	@Test
 	public void numeroOk() throws NonRecoverableError, RecoverableError {

 		Document d = new Document();
 		int ID_DOCUMENTO = d.getDocumentId();
 		int ID_CORRECTO = 1;

 		assertEquals("Correcto: numero de documento", ID_CORRECTO, ID_DOCUMENTO);
 	}
	
	@Test
	public void numerosAsignadosOk() throws NonRecoverableError, RecoverableError {

		Document d1 = new Document();
		Document d2 = new Document();
		int ID_DOCUMENTO_PRIMERO = d1.getDocumentId();
		int ID_DOCUMENTO_SEGUNDO = d2.getDocumentId();
		assertEquals(ID_DOCUMENTO_PRIMERO, ID_DOCUMENTO_SEGUNDO-1);
	}
	
	@Test(expected = NonRecoverableError.class)
	public void testNoExisteDriver() throws NonRecoverableError{
		DocumentIDInstances documentInstances = new DocumentIDInstances();
		documentInstances.loadDrivers("No existe");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void testNoFicheroConfiguracion() throws NonRecoverableError{
		DocumentIDInstances documentInstances = new DocumentIDInstances();
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;
		documentInstances.getProperties(propertiesInFile,inputFile,"hola");
	}

	@Test(expected = NonRecoverableError.class)
	public void testMasDeUnaFila() throws NonRecoverableError{
		DocumentIDInstances documentInstances = new DocumentIDInstances();
		int numberOfValues = documentInstances.getID(false);
	}

	@Test(expected = NonRecoverableError.class)
	public void testNoActualizacion() throws NonRecoverableError{
		DocumentIDInstances documentInstances = new DocumentIDInstances();
		documentInstances.getDocumentId();
		documentInstances.getDocumentId();
		documentInstances.getDocumentId();
		documentInstances.getDocumentId();
		documentInstances.getDocumentId();	
	}

}
