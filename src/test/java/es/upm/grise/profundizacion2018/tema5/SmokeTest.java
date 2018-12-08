package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class SmokeTest {
	protected static int DOCUMENT_ID;
	protected static Document doc1, doc2, doc3;
	protected static DocumentIdProviderDouble documentIdProvider;

	public static class DocumentIdProviderDouble extends DocumentIdProvider {
		protected DocumentIdProviderDouble() {
			super();
		}

		private void setDocumentId(int id) {
			documentId = id;
		}

		@Override
		protected void connectDB() {
			setDocumentId(DOCUMENT_ID);
		}
		@Override
		public int getDocumentId() {
			return documentId++;
		}
		@Override
		public  DocumentIdProvider getInstance() throws NonRecoverableError {
			if (instance != null)
				return instance;
			else {
				instance = new DocumentIdProviderDouble();
				instance.connectDB();
				return instance;
			}
		}
	}

	@BeforeClass
	public static void initialize() throws NonRecoverableError {
		DOCUMENT_ID = 1115;
		documentIdProvider = (DocumentIdProviderDouble) new DocumentIdProviderDouble().getInstance();
		doc1 = new Document();
		doc1.loadDocumentIdProvider(documentIdProvider);
		doc2 = new Document();
		doc2.loadDocumentIdProvider(documentIdProvider);
		doc3 = new Document();
		doc3.loadDocumentIdProvider(documentIdProvider);
	}

	@Test
	public void formatTemplateCorrectly() throws RecoverableError {
		doc1.setTemplate("DECLARATION");
		doc1.setTitle("A");
		doc1.setAuthor("B");
		doc1.setBody("C");
		assertEquals("DOCUMENT ID: 1115\n\nTitle : A\nAuthor: B\n\nC", doc1.getFormattedDocument());

	}

	@Test
	public void generationCorrectTemplates() {
		String template = TemplateFactory.getTemplate("DECLARATION");
		assertEquals("DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s", template);
	}

	@Test
	public void correctNumberId() throws RecoverableError {
		doc1.setTemplate("DECLARATION");
		doc1.setTitle("A");
		doc1.setAuthor("B");
		doc1.setBody("C");
		assertEquals("DOCUMENT ID: 1115\n\nTitle : A\nAuthor: B\n\nC", doc1.getFormattedDocument());
	}

	@Test
	public void consecutiveNumbers() {
		int id1 = (int) doc1.getDocumentId();
		int id2 = (int) doc2.getDocumentId();
		int id3 = (int) doc3.getDocumentId();

		assertTrue(((id2 - id1) == 1) && ((id3 - id2) == 1));
	}
}
