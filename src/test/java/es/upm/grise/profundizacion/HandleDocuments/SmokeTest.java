package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.mockito.Mockito.*;


import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;
import org.mockito.Mock;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider documentIdProvider = new DocumentIdProvider("/home/dflobon/IdeaProjects/PROF-Testable-Design/");
		documentIdProvider.setDocumentId(1622);
		Document d1 = new Document(documentIdProvider);
		Document d2 = new Document(documentIdProvider);
		d1.setTemplate("DECLARATION");
		d1.setTitle("A");
		d1.setAuthor("B");
		d1.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d1.getFormattedDocument());
		d2.setTemplate("DECLARATION");
		d2.setTitle("A");
		d2.setAuthor("B");
		d2.setBody("C");
		assertEquals("DOCUMENT ID: 1624\n\nTitle : A\nAuthor: B\n\nC", d2.getFormattedDocument());

	}

}
