package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document(1623);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}

	@Test
	public void generateIdCorrectly() throws NonRecoverableError {
		DocumentIdProvider docProv = DocumentIdProvider.getInstance();

		int docId = docProv.getDocumentId(docProv.getInstanceDocumentId());

		assertEquals(docId+1, docProv.getDocumentId(docProv.getInstanceDocumentId()));
	}

}
