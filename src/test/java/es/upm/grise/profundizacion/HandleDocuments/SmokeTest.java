package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SmokeTest {

	private static final String TEMPLATE_FORMAT = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";

	private static final String TEMPLATE = "DECLARATION";
	private static final String TITLE = "TITLE";
	private static final String AUTHOR = "AUTHOR";
	private static final String BODY = "BODY";

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {

		int initialId = 1;
		Document doc = createDocument(createDocumentProviderDouble(initialId));
		doc.setTemplate(TEMPLATE);
		doc.setTitle(TITLE);
		doc.setAuthor(AUTHOR);
		doc.setBody(BODY);

		assertEquals(getFormattedTemplate(initialId, TITLE, AUTHOR, BODY), doc.getFormattedDocument());
	}

	@Test
	public void shouldAssignCorrectId() throws NonRecoverableError {

		int initialId = 1;
		Document doc = createDocument(createDocumentProviderDouble(initialId));
		assertEquals(initialId, doc.getDocumentId());
	}

	@Test
	public void shouldAssignConsecutiveIds() throws NonRecoverableError {

		int initialId = 1;
		DocumentIdProvider idProvider = createDocumentProviderDouble(initialId);

		Document doc1 = createDocument(idProvider);
		Document doc2 = createDocument(idProvider);

		assertEquals(initialId, doc1.getDocumentId());
		assertEquals(initialId+1, doc2.getDocumentId());
	}

	@Test
	public void shouldDetectConfigFileNotExists() throws NonRecoverableError {

		int initialId = 1;
		DocumentIdProvider idProvider = createDocumentProviderDouble(initialId);

		exceptionRule.expect(NonRecoverableError.class);
		exceptionRule.expectMessage(Error.NON_EXISTING_FILE.getMessage());

		idProvider.loadProperties("/nonExistingPath/");
	}

	@Test
	public void shouldDetectDBDriverNotExists() throws NonRecoverableError {

		int initialId = 1;
		DocumentIdProvider idProvider = createDocumentProviderDouble(initialId);

		exceptionRule.expect(NonRecoverableError.class);
		exceptionRule.expectMessage(Error.CANNOT_FIND_DRIVER.getMessage());

		idProvider.loadDatabaseDriver("com.nonExistingDriver.jdbc");
	}

	@Test
	public void shouldDetectCorruptedCounter() throws NonRecoverableError {

		int initialId = 1;
		DocumentIdProvider idProvider = createDocumentProviderDouble(initialId);

		exceptionRule.expect(NonRecoverableError.class);
		exceptionRule.expectMessage(Error.CORRUPTED_COUNTER.getMessage());

		idProvider.getLastId(new ResultSetDouble());
	}

	@Test
	public void shouldDetectFailCounterUpdate() throws NonRecoverableError {

		int initialId = -1;
		DocumentIdProvider idProvider = createDocumentProviderDouble(initialId);

		exceptionRule.expect(NonRecoverableError.class);
		exceptionRule.expectMessage(Error.CANNOT_UPDATE_COUNTER.getMessage());

		idProvider.getDocumentId();
	}

	private Document createDocument(DocumentIdProvider idProvider) throws NonRecoverableError {
		return new Document(idProvider, TemplateFactory.getInstance()); 
	}

	private DocumentIdProvider createDocumentProviderDouble(int documentId) throws NonRecoverableError {
		DocumentIdProviderDouble docProvider = new DocumentIdProviderDouble(documentId);
		return docProvider;
	}

	private String getFormattedTemplate(int documentId, String title, String author, String body) {
		return String.format(TEMPLATE_FORMAT, documentId, title, author, body);
	}
}
