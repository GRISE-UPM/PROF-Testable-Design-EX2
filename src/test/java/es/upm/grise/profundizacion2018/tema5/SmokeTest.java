package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Properties;

public class SmokeTest {

	class DocumentIdProviderDouble extends DocumentIdProvider{

		private String path;
		private String jdbc_driver;
		private Properties properties;

		DocumentIdProviderDouble(Properties properties,
								 String path, String jdbc_driver) throws NonRecoverableError {
			this.properties = properties;
			this.path = path;
			this.jdbc_driver = jdbc_driver;
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
		Properties getProperties() throws NonRecoverableError {
			if(this.properties == null)return super.getProperties();
			return this.properties;
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
				new DocumentIdProviderDouble(null, "fake/path", null);
		Document d = new Document(documentIdProviderDouble);
	}

	@Test(expected = NonRecoverableError.class)
	public void shouldThrowExceptionWhenMySQLDriverNotExits() throws NonRecoverableError {
		DocumentIdProviderDouble documentIdProviderDouble =
				new DocumentIdProviderDouble(new Properties(), null, "fake.jdbc.driver");
		Document d = new Document(documentIdProviderDouble);

	}



}
