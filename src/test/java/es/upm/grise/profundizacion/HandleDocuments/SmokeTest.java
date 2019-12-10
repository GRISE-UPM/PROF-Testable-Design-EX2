package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {

	private DocumentIdProvider documentIdProvider;
	private Document document;

	public void iniciar() throws NonRecoverableError {
		documentIdProvider = new DocumentIdProviderDouble(1);
		document = new Document(documentIdProvider);
	}

	// Ejercicio 4a
	@Test
	public void testPlantillasCorrectas() throws NonRecoverableError, RecoverableError {
		iniciar();
		document.setTemplate("DECLARATION");
		document.setTitle("A");
		document.setAuthor("B");
		document.setBody("C");
		assertEquals("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC", document.getFormattedDocument());
	}

	// Ejercicio 4b
	@Test
	public void testNumeroDocumentoCorrecto() throws NonRecoverableError {
		iniciar();
		int id = (int) document.getDocumentId();
		assertEquals(1, id);
	}

	// Ejercicio 4c
	@Test
	public void testNumeroDocumentoConsecutivoCorrecto() throws NonRecoverableError {
		iniciar();
		Document documentSig = new Document(new DocumentIdProviderDouble(2));
		assertEquals(1, (int) document.getDocumentId());
		assertEquals(2, (int) documentSig.getDocumentId());
	}

	// Ejercicio 5a
	@Test(expected = NonRecoverableError.class)
	public void testNoFicheroConfiguracion() throws NonRecoverableError {
		iniciar();
		new DocumentIdProviderDouble(1).getProperties("No existe");

	}

	// Ejercicio 5b
	@Test(expected = NonRecoverableError.class)
	public void testNoDriveMySQL() throws NonRecoverableError {
		iniciar();
		documentIdProvider.loadDbDriver("No existe");
	}

	// Ejercicio 5c
	@Test(expected = NonRecoverableError.class)
	public void testMasFilas() throws NonRecoverableError {
		iniciar();
		documentIdProvider.last(null, false);
	}

	// Ejercicio 5d
	@Test(expected = NonRecoverableError.class)
	public void testNoActualizacion() throws NonRecoverableError {
		iniciar();
		for(int i = 0; i < 100; i++) {
			documentIdProvider.getDocumentId();
		}
	}
	
}