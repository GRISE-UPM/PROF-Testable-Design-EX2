package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Properties;

public class SmokeTest {

	class DocumentIdProviderDouble extends DocumentIdProvider{
		DocumentIdProviderDouble(Properties customProperties) throws NonRecoverableError {
			initClass(customProperties);
		}
	}

	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		Document d = new Document();
		int documentId = d.getDocumentId();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: "+documentId+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}

	@Test
	public void shouldReturnConsecutiveNumbers() throws NonRecoverableError {
		Document d = new Document();
		int documentId1 = d.getDocumentId();
		Document d2 = new Document();
		int documentId2 = d.getDocumentId();
		assertEquals(documentId2,documentId1+1);
	}


}
