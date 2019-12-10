package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document(new DocumentIdProviderDouble(1), TemplateFactory.getInstance());
		
		d.setTemplate("DECLARATION");
		d.setTitle("Titulo");
		d.setAuthor("Autor");
		d.setBody("Cuerpo");
		assertEquals("DOCUMENT ID: 1\n\nTitle : Titulo\nAuthor: Autor\n\nCuerpo", d.getFormattedDocument());

	}
	
	@Test
	public void assignsIDCorrectly() throws NonRecoverableError {
		Document d = new Document(new DocumentIdProviderDouble(10), TemplateFactory.getInstance());
		assertEquals(10, d.getDocumentId());
	}
	
	@Test
	public void AssignsConsecutiveIDCorrectly() throws NonRecoverableError{
		DocumentIdProviderDouble idprov = new DocumentIdProviderDouble(1);
		
		Document d1 = new Document(idprov, TemplateFactory.getInstance());
		Document d2 = new Document(idprov, TemplateFactory.getInstance());
		
		assertEquals(1, d1.getDocumentId());
		assertEquals(2, d2.getDocumentId());
	}

	@Test(expected = NonRecoverableError.class)
	public void errorNoConfig() throws NonRecoverableError {
		DocumentIdProviderDouble idprov = new DocumentIdProviderDouble(1);
		
		idprov.propLoader("Esto falla");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void errorNoDriver() throws NonRecoverableError {
		DocumentIdProviderDouble idprov = new DocumentIdProviderDouble(1);
		
		idprov.driverLoader("Esto falla");
	}
	
	@Ignore
	@Test(expected = NonRecoverableError.class)
	public void errorMasDeUnaFila() {
		
	}
	
	@Test(expected = NonRecoverableError.class)
	public void errorActualizarIncorrecto() throws NonRecoverableError {
		DocumentIdProviderDouble idprov = new DocumentIdProviderDouble(-1);
		
		idprov.getDocumentId();
	}
}













