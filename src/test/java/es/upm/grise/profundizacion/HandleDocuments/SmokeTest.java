package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SmokeTest {

	@InjectMocks
	Document d;

	@Mock
	DocumentIdProvider provider;

	/**
	 * La aplicación genera las plantillas correctamente
	 */
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {

		d = new Document(provider);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");

		assertEquals("DOCUMENT ID: 0\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}

	/**
	 * La aplicación asigna el número de documento correcto.
	 */
	@Test
	public void getCorrectDocumentId() throws NonRecoverableError, RecoverableError {

		when(provider.getDocumentId()).thenReturn(1);
		d = new Document(provider);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");

		assertEquals("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}

}
