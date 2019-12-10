package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {

	// Test 4.a: Genera plantillas correctamente
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("Format template correctly", "DOCUMENT ID: 4\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}

	// Test 4.b: Asigna numero documentos correctos
	@Test
	public void numberDocumentCorrectly() throws NonRecoverableError, RecoverableError {

		Document d = new Document();
		int id_document = (int) d.getDocumentId();
		int solution = 1;

		assertEquals( "Number from documents correctly", id_document, solution );
	}

	// Test 4.c: Numeros documento asignados son numeros consecutivos
	@Test
	public void numberDocumentsAreConsecutives() throws NonRecoverableError, RecoverableError {

		Document d1 = new Document();
		Document d2 = new Document();
		int id_document1 = (int) d1.getDocumentId();
		int id_document2 = (int) d1.getDocumentId();
		int difference = id_document2-id_document1;
		int solution = 1;

		assertEquals( "Number from documents correctly", difference, solution );
	}

	// Test 5.a: Deteccion fichero configuracion no existe
	@Test(expected = NonRecoverableError.class)
	public void fileConfigurationNotExisting() throws NonRecoverableError {

	}

	// Test 5.b: Deteccion driver MySQL no existe
	@Test(expected = NonRecoverableError.class)
	public void driverMySQLNotExisting() throws NonRecoverableError {

	}


	// Test 5.c: Deteccion mas de una fila en tabla Counters
	@Test(expected = NonRecoverableError.class)
	public void moreThanOneRowInCountersTable() throws NonRecoverableError {

	}

	// Test 5.d: Deteccion actualizacion incorrecta del documentID en tabla Counters
	@Test(expected = NonRecoverableError.class)
	public void incorrectUpdateInCountersTable() throws NonRecoverableError {

	}

}
