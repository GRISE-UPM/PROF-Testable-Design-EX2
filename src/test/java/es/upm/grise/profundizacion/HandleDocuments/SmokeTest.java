package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;




public class SmokeTest {


	@DisplayName("Test1: La aplicación genera las plantillas correctamente.")
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {

		DocumentIdProviderDouble documentIdProviderDouble = new DocumentIdProviderDouble(1623);
		Document d = new Document(documentIdProviderDouble);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}

}
