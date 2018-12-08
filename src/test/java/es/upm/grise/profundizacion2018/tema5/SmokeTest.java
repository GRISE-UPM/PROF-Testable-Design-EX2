package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;
import org.junit.Test;


public class SmokeTest {

	class DocumentIdProviderDouble extends DocumentIdProvider{

		private String path;
		private String jdbc_driver;
		private boolean moreThanOneDocument;
		private boolean moreThanOneDocumentUpdated;

		DocumentIdProviderDouble(String path,
								 String jdbc_driver,
								 boolean moreThanOneDocument,
								 boolean moreThanOneDocumentUpdated) throws NonRecoverableError {
			this.path = path;
			this.jdbc_driver = jdbc_driver;
			this.moreThanOneDocument = moreThanOneDocument;
			this.moreThanOneDocumentUpdated = moreThanOneDocumentUpdated;
			initClass();
		}

		@Override
		String getPath() throws NonRecoverableError {
			if(this.path == null) return super.getPath();
			return this.path;
		}

		@Override
		String getJdbc_driver(){
			if(this.jdbc_driver == null)return super.getJdbc_driver();
			return this.jdbc_driver;
		}

		@Override
		public int getNumberOfValues() {
			if(this.moreThanOneDocument)return 2;
			return 1;
		}

		@Override
		public int getNumUpdatedRows() {
			if(this.moreThanOneDocumentUpdated)return 2;
			return 1;
		}

	}

	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		Document d = new Document();
		int documentId = d.getDocumentId();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: "+documentId+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}

	@Test
	public void shouldReturnConsecutiveNumbers() throws NonRecoverableError {
		Document d = new Document();
		int documentId1 = d.getDocumentId();
		Document d2 = new Document();
		int documentId2 = d2.getDocumentId();
		assertEquals(documentId2,documentId1+1);
	}

	@Test(expected = NonRecoverableError.class)
	public void shouldThrowExceptionWhenPathNotExits() throws NonRecoverableError {
		DocumentIdProviderDouble documentIdProviderDouble =
				new DocumentIdProviderDouble( "fake/path", null,false,false);
		Document d = new Document(documentIdProviderDouble);
	}

	@Test(expected = NonRecoverableError.class)
	public void shouldThrowExceptionWhenMySQLDriverNotExits() throws NonRecoverableError {
		DocumentIdProviderDouble documentIdProviderDouble =
				new DocumentIdProviderDouble(null, "fake.jdbc.driver",false,false);
		Document d = new Document(documentIdProviderDouble);

	}

	@Test(expected = NonRecoverableError.class)
	public void shouldThrowExceptionWhenThereIsMoreThanOneResult() throws NonRecoverableError {
		DocumentIdProviderDouble documentIdProviderDouble =
				new DocumentIdProviderDouble( null, null,true,false);
		Document d = new Document(documentIdProviderDouble);
	}

	@Test(expected = NonRecoverableError.class)
	public void shouldThrowExceptionWhenThereIsMoreThanOneUpdated() throws NonRecoverableError {
		DocumentIdProviderDouble documentIdProviderDouble =
				new DocumentIdProviderDouble( null, null,false,true);
		Document d = new Document(documentIdProviderDouble);
	}



}
