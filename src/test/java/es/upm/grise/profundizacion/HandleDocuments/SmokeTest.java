package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	// Pruebas apartado 4
	
	// a.	La aplicación genera las plantillas correctamente.
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		int id = 1;
		DocumentIdProviderDouble dP = new DocumentIdProviderDouble(id);
		Document d = new Document(dP);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	// b.	La aplicación asigna el número de documento correcto.
	@Test
	public void numeroDocumentoCorrecto() throws NonRecoverableError {
		int id = 1;
		DocumentIdProviderDouble dP = new DocumentIdProviderDouble(id);
		Document d = new Document(dP);
		int id_doc = (int) d.getDocumentId();
		int id_exp = 1;
		assertEquals(id_exp, id_doc);
	}
	
	// c.	Los números de documento asignados a documentos consecutivos son también números consecutivos.
	@Test
	public void numeroDocumentoConsecutivo() throws NonRecoverableError {
		int id = 1;
		DocumentIdProviderDouble dP = new DocumentIdProviderDouble(id);
		Document d1 = new Document(dP);
		int id2 = 2;
		DocumentIdProviderDouble dP2 = new DocumentIdProviderDouble(id2);
		Document d2 = new Document(dP2);
		int id_doc1 = (int) d1.getDocumentId();
		int id_doc2 = (int) d2.getDocumentId();
		int id_exp = 1;
		assertEquals(id_exp, id_doc1);
		assertEquals(id_exp+1, id_doc2);
	}

	// Pruebas apartado 5
	
	// a.	La aplicación detecta correctamente que el fichero de configuración no existe.
	@Test (expected = NonRecoverableError.class)
	public void ficheroConfNoExiste() throws NonRecoverableError {
		int id = 1;
		DocumentIdProvider dP = new DocumentIdProviderDouble(id);
		dP.getProperties("Path no existe");
	}
	
	// b.	La aplicación detecta correctamente que el driver MySQL no existe.
	@Test (expected = NonRecoverableError.class)
	public void driveMySQLNoExiste() throws NonRecoverableError {
		int id = 1;
		DocumentIdProvider dP = new DocumentIdProviderDouble(id);
		dP.loadDBDriver("Driver no existe");
	}
	
	// c.	La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
	@Test (expected = NonRecoverableError.class)
	public void tablaCountersConMasDeUnaFila() throws NonRecoverableError {
		int id = 1;
		DocumentIdProvider dP = new DocumentIdProviderDouble(id);
		ResultSet rS = new crearResultSet();
		dP.getLastId(rS);
	}
	
	// d.	La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
	@Test (expected = NonRecoverableError.class)
	public void falloActualizaciontablaCounters() throws NonRecoverableError {
		int id = 1;
		DocumentIdProvider dP = new DocumentIdProviderDouble(id);
		dP.getDocumentId();
		dP.getDocumentId();
		dP.getDocumentId();
		// Se ha forzado para que a la cuarta salte el fallo 
		dP.getDocumentId();
	}
}