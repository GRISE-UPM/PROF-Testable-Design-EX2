package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError, SQLException {
		
		Document d = new Document(new DocumentIdProviderMock(1623));
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	
	@Test
	public void theNumberShouldBeCorrect() throws NonRecoverableError, RecoverableError, SQLException {
		
		Document d = new Document(new DocumentIdProviderMock(999));
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 999\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	
	@Test
	public void theNumberShouldBeConsecutive() throws NonRecoverableError, RecoverableError, SQLException {
		DocumentIdProviderMock provider = new DocumentIdProviderMock(999);
		Document d = new Document(provider);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 999\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
		Document d2 = new Document(provider);
		d2.setTemplate("DECLARATION");
		d2.setTitle("A");
		d2.setAuthor("B");
		d2.setBody("C");
		assertEquals("DOCUMENT ID: 1000\n\nTitle : A\nAuthor: B\n\nC", d2.getFormattedDocument());
	}
	
	@Test
	public void shouldFailIfFileDoesntExists() throws NonRecoverableError, SQLException {

		DocumentIdProvider prov = new DocumentIdProviderMock(0);
		boolean except = false;
		try {
			prov.loadProperties("doesntExists");
		}catch (NonRecoverableError | NullPointerException e){
			except = true;
		}
		assertTrue(except);

	}
	
	@Test
	public void shouldFailIfBadDriver() throws NonRecoverableError, SQLException {

		DocumentIdProvider prov = new DocumentIdProviderMock(0);
		boolean except = false;
		try {
			prov.loadDatabaseDriver("doesntExists");
		}catch (NonRecoverableError e){
			except = true;
		}
		assertTrue(except);

	}
	
	@Test
	public void shouldFailIfCannotUpdateTheCounter() throws NonRecoverableError, SQLException {

		DocumentIdProvider prov = new DocumentIdProviderMock(9999999);
		boolean except = false;
		try {
			prov.getDocumentId();
		}catch (NonRecoverableError e){
			except = true;
		}
		assertTrue(except);

	}
	
}
