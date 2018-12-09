package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		Document d = new Document();
		int documentId = (int) d.getDocumentId();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: " + documentId + "\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	@Test
	public void consecutiveDocumentNumber() throws NonRecoverableError, RecoverableError {
		Document d = new Document();
		int documentId = (int) d.getDocumentId();
		Document d2 = new Document();
		int documentId2 = (int) d2.getDocumentId();
		assertEquals(documentId, (documentId2 - 1));
	}
	
	@Test (expected = NonRecoverableError.class)
	public void configFileNotExist() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider.getInstance().CreateConexion(null);
	}
	
	@Test (expected = NonRecoverableError.class)
	public void configFileNotExist2() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider.getInstance().CreateConexion("otro");
	}
	
	@Test (expected = NonRecoverableError.class)
	public void MySQLDriverNotExist() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider.getInstance().LoadDBDriver("otro");
	}

	@Test (expected = NonRecoverableError.class)
	public void DetectMoreThanOneRowANDDetectIncorrectUpdateId() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider.getInstance().CheckUpdateRules(2);
	}
}
