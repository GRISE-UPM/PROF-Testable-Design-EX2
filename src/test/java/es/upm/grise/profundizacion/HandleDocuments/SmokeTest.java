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
		DocumentIdProvider documentIdProvider = new DocumentIdProvider("C:\\Users\\david\\IdeaProjects\\PROF-Testable-Design\\");
		DocumentIdProvider documentIdProvider1 = mock(DocumentIdProvider.class);
		Document d = new Document(documentIdProvider);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}

}
