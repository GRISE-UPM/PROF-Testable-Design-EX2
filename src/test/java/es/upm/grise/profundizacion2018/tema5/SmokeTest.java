package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;
import static es.upm.grise.profundizacion2018.tema5.Error.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class SmokeTest {

	/*
	 * @Test public void formatTemplateCorrectly() throws NonRecoverableError,
	 * RecoverableError {
	 * 
	 * Document d = new Document(); d.setTemplate("DECLARATION"); d.setTitle("A");
	 * d.setAuthor("B"); d.setBody("C");
	 * assertEquals("DOCUMENT ID: 1115\n\nTitle : A\nAuthor: B\n\nC",
	 * d.getFormattedDocument());
	 * 
	 * }
	 * 
	 * @Test public void testID() throws NonRecoverableError { Document d = new
	 * Document(); d.getDocumentId(); }
	 */

	DocumentIdProvider docProvid;

	@BeforeClass
	public void init() throws NonRecoverableError {
		docProvid.CONFIGFILE = "config.properties";
		docProvid.DRIVER = "com.mysql.jdbc.Driver";
		docProvid.UPDATE_QUERY = "UPDATE Counters SET documentId = ?";

		int DOCUMENT_ID;
		Document d1, d2, d3;
		DocumentIdProvider docProvider= null;
		DOCUMENT_ID = 1000;
		docProvider = (DocumentIdProvider) new DocumentIdProvider().getInstance();

		d1 = new Document();
		d1.getIdProvider(docProvider);

		d2 = new Document();
		d2.getIdProvider(docProvider);

		d3 = new Document();
		d3.getIdProvider(docProvider);
	}

	@Test
	public void testConfig() throws NonRecoverableError, IOException {

		docProvid = new DocumentIdProvider();
		docProvid.CONFIGFILE = "NoExiste";
		try {
			docProvid.connect();
		} catch (NonRecoverableError e) {
			assertEquals(NON_EXISTING_FILE.getMessage(), e.getMessage());

		}

	}

	
	@Test
	public void testNumRows() throws NonRecoverableError, IOException {

		docProvid = new DocumentIdProvider();
		docProvid.numberOfValues = 1;
		try {
			docProvid.connect();
		} catch (NonRecoverableError e) {
			assertEquals(CORRUPTED_COUNTER.getMessage(), e.getMessage());

		}

	}
	@Test
	public void testDriverMySQL() throws NonRecoverableError, IOException {

		docProvid = new DocumentIdProvider();
		docProvid.DRIVER = "DriverErroneo";
		try {
			docProvid.connect();
		} catch (NonRecoverableError e) {
			assertEquals(CANNOT_FIND_DRIVER.getMessage(), e.getMessage());

		}

	}


	@Test
	public void testUpdate() throws NonRecoverableError, IOException {

		docProvid = new DocumentIdProvider();
		docProvid.UPDATE_QUERY = "UPDATE Counters SET Id = ?";
		docProvid.connect();
		try {
			docProvid.getDocumentId();
		} catch (NonRecoverableError e) {
			assertEquals(CANNOT_UPDATE_COUNTER.getMessage(), e.getMessage());

		}

	}

	@Test
	public void testTemplate() {
		TemplateFactory tf = new TemplateFactory();
		String plantilla = tf.getTemplate("DECLARATION");
		assertEquals("DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s", plantilla);

	}

	

}
