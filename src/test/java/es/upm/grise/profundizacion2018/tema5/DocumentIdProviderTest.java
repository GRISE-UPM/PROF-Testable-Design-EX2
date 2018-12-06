package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Properties;

public class DocumentIdProviderTest {
	
	/*@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1115\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}*/

	@Test(expected = NonRecoverableError.class)
	public void notExistingConfigFile() throws NonRecoverableError{
		DocumentIdProvider.getInstance(DocumentIdProvider.MYSQL_DRIVER).loadPropertiesFromFile("thisisnotavalidpath/so/.it/will/fail");
	}

	@Test(expected = NonRecoverableError.class)
	public void notExistingDbDriver() throws NonRecoverableError {
		Properties prop = new Properties();
		prop.setProperty("url", "");
		prop.setProperty("username", "");
		prop.setProperty("password", "");
		DocumentIdProvider.getInstance(DocumentIdProvider.MYSQL_DRIVER).loadDbConnectionFromProperties(prop, "notvaliddriver");
	}

}
