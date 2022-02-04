package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		DocumentIdProvider idpro = new DocumentIdProviderFake();
		TemplateFactory templatefactory = new TemplateFactoryImp();
		Document d = new Document(idpro, templatefactory);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}

}
