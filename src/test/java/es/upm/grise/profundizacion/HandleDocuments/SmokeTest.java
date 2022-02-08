package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

import static org.junit.Assert.*;
import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {

	DocumentIdProviderInt dp;
	Document d, d2;

	// Tests apartado 4

	// La aplicación genera las plantillas correctamente.
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {

		d = new Document(1623);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}

	//La aplicación asigna el número de documento correcto.
	@Test
	public void asignDocumentId() throws NonRecoverableError, RecoverableError {
		int id = 10;
		dp = new DocumentIdProviderInt(id);
		d = new Document(dp);
		assertEquals(id+1, d.getDocumentId());
	}

	//Los números de documento asignados a documentos consecutivos son también números consecutivos.
	@Test
	public void asignNumConsecutives() throws NonRecoverableError, RecoverableError {
		int id = 20;
		dp = new DocumentIdProviderInt(id);
		d = new Document(dp);
		d2 = new Document(dp);
		int id1 = (int) d.getDocumentId();
		int id2 = (int) d2.getDocumentId();
		assertEquals(id1+1, id2);
	}


	// Tests apartado 5

	// La aplicación detecta correctamente que el fichero de configuración no existe.
	@Test
	public void fileConfigEmpty() throws NonRecoverableError, RecoverableError {

		String driverDB = "com.mysql.cj.jdbc.Driver";
		try{
			dp = new DocumentIdProviderInt("/no-existe/",driverDB);
		} catch (NonRecoverableError e) {
			assertEquals(NON_EXISTING_FILE.getMessage(), e.getType());
		}
	}

	// La aplicación detecta correctamente que el driver MySQL no existe.
	@Test
	public void driverDBnotFound() throws NonRecoverableError {

		String path = System.getProperty("user.dir") + "/";
		try{
			dp = new DocumentIdProviderInt(path,"com.mysql.no-existe");
		} catch (NonRecoverableError e) {
			assertEquals(CANNOT_FIND_DRIVER.getMessage(), e.getType());
		}
	}

	// La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
	@Test
	public void numUpdatedRowsNE1() throws NonRecoverableError {

		try{
			dp = new DocumentIdProviderInt(-1);
			dp.getDocumentId();
		} catch (NonRecoverableError e) {
			assertEquals(INCORRECT_COUNTER.getMessage(), e.getType());
		}
	}

	// La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
	@Test
	public void errorUpdatedDocumentID() throws NonRecoverableError {

		try{
			dp = new DocumentIdProviderInt(0);
			dp.getDocumentId();
		} catch (NonRecoverableError e) {
			assertEquals(CORRUPTED_COUNTER.getMessage(), e.getType());
		}
	}

}
