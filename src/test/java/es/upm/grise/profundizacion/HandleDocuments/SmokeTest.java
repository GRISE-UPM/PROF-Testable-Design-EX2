package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class SmokeTest {
	
	private static final int INITIAL_ID = 2407;
	private static final String DECLARATION_TEMPLATE = "DECLARATION";
	private static final String BASE_TITLE = "A";
	private static final String BASE_AUTHOR = "B";
	private static final String BASE_BODY = "C";

	DocumentIdProvider dIdProvider;

	@Before
	public void setup() throws NonRecoverableError {
		dIdProvider = new DocumentIdProviderDouble(INITIAL_ID);
	}

	private Document createDocument(int id) throws NonRecoverableError {
		Document d = new Document(id);
		d.setTemplate(DECLARATION_TEMPLATE);
		d.setTitle(BASE_TITLE + id);
		d.setAuthor(BASE_AUTHOR + id);
		d.setBody(BASE_BODY + id);
		return d;
	}

	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = createDocument(dIdProvider.getDocumentId());

		assertEquals("DOCUMENT ID: " + INITIAL_ID + "\n\nTitle : "+ BASE_TITLE + INITIAL_ID +
			"\nAuthor: "+ BASE_AUTHOR + INITIAL_ID +"\n\n"+ BASE_BODY + INITIAL_ID, d.getFormattedDocument());
	}

	@Test
	public void correctDocumentIdAssigned() throws NonRecoverableError, RecoverableError {

		int randomId = new Random().nextInt();

		Document d = createDocument(randomId);
		assertTrue(d.getFormattedDocument().contains("DOCUMENT ID: " + randomId));
	}

	@Test
	public void consecutiveDocumentsHaveConsecutiveIds() throws NonRecoverableError {
		
		int firstId = dIdProvider.getDocumentId();
		int nextId = dIdProvider.getDocumentId();

		assertTrue(firstId + 1 == nextId);

	}

}
