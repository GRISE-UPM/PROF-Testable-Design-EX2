package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		int ID=(int) d.getDocumentId();
		assertEquals("DOCUMENT ID: "+ID+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}

}
