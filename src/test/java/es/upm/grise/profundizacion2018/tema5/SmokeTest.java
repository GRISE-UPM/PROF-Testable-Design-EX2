package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {		
		Document d = new Document();
		int documentId = d.getDocumentId();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: " + documentId + "\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	@Test 
	public void consecutiveCallsShouldReturnConsecutiveNumbers() throws NonRecoverableError, RecoverableError {		
		Document firstDocument = new Document();
		Document secondDocument = new Document();
		int firstDocumentId = firstDocument.getDocumentId();
		int secondDocumentId = secondDocument.getDocumentId();
		assertEquals(firstDocumentId + 1, secondDocumentId);
	}
}
