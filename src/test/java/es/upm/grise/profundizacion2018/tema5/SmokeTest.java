package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmokeTest {


	class CopyCatDocumentIdProvider extends DocumentIdProvider{

		private String path;
		private String driver;
		private boolean moreThanOneDocument;
		private boolean moreThanOneDocumentUpdated;

		CopyCatDocumentIdProvider(String path, boolean moreThanOneDocument, String driver, boolean moreThanOneDocumentUpdated) throws NonRecoverableError {
			this.path = path;
			this.driver = driver;
			this.moreThanOneDocument = moreThanOneDocument;
			this.moreThanOneDocumentUpdated = moreThanOneDocumentUpdated;
			logicBase();
		}

		@Override
		String getPath(){
			if (path != null) return path;
			return super.getPath();
		}

		@Override
		String getDriver(){
			if (driver != null) return driver;
			return super.getDriver();
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
	public void test3NumerosConsecutivos() throws NonRecoverableError {
		Document d = new Document();
		int documentId1 = d.getDocumentId();
		Document d2 = new Document();
		int documentId2 = d2.getDocumentId();
		assertEquals(documentId2,documentId1+1);
	}






	@Test(expected = NonRecoverableError.class)
	public void testFicheroDeConfiguracionNoExiste() throws NonRecoverableError {

		CopyCatDocumentIdProvider cd = new CopyCatDocumentIdProvider("fake", false,null,false);
		Document d = new Document(cd);

		d.getDocumentId();





	}



	@Test(expected = NonRecoverableError.class)
	public void testDriverNoExiste() throws NonRecoverableError {
		CopyCatDocumentIdProvider cd = new CopyCatDocumentIdProvider(null,false, "fake",false);
		Document d = new Document(cd);

		d.getDocumentId();
	}


	@Test(expected = NonRecoverableError.class)
	public void  masDeUnaFilaCounters() throws NonRecoverableError {
		CopyCatDocumentIdProvider documentIdProviderDouble =
				new CopyCatDocumentIdProvider( null,true, null,false);
		Document d = new Document(documentIdProviderDouble);
	}


	@Test(expected = NonRecoverableError.class)
	public void  actDocCorrecta() throws NonRecoverableError {
		CopyCatDocumentIdProvider documentIdProviderDouble =
				new CopyCatDocumentIdProvider( null,true, null,true);
		Document d = new Document(documentIdProviderDouble);
	}


}
