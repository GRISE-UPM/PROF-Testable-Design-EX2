package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		d.setDocumentId(1);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	@Test
	public void asignaNumeroDocumentoCorrecto() {
		
		Document d = new Document();
		d.setDocumentId(1);
		assertEquals(1, d.getDocumentId());
	}
	
	@Test
	public void asignaNumerosDocumentosConsecutivosCorrectos() {
		
		Document d = new Document();
		d.setDocumentId(0);
		assertEquals(0, d.getDocumentId());
		Document d2 = new Document();
		d2.setDocumentId(1);
		assertEquals(1, d2.getDocumentId());
	}
	
	@Test (expected=NonRecoverableError.class)
	public void detectaFicheroConfiguracionNoExiste() throws NonRecoverableError {
		
		DocumentIdProviderDouble provider = new DocumentIdProviderDouble();
		provider.loadPropertyFile("/noExiste");
	}
	
	@Test (expected=NonRecoverableError.class)
	public void detectaDriverNoExiste() throws NonRecoverableError {
		
		DocumentIdProviderDouble provider = new DocumentIdProviderDouble();
		provider.loadDBDriver("noExiste");
	}
	
	@Test
	public void detectaMasUnaFilaTabla() throws NonRecoverableError {
		
		DocumentIdProviderDouble provider = new DocumentIdProviderDouble();
		assertTrue(provider.getLastObjectID() > 0);
	}
	
	@Test (expected=NonRecoverableError.class)
	public void detectaActualizacionIncorrecta() throws NonRecoverableError {
		
		DocumentIdProviderDouble provider = new DocumentIdProviderDouble();
		provider.getDocumentId();
	}
}
