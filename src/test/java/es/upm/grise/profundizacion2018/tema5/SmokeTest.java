package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmokeTest {


	class DocumentIdProviderDouble extends DocumentIdProvider {
		public DocumentIdProviderDouble(boolean incorrectNumberOfvalues, boolean moreThanOneUpdatedRows) {
			this.incorrectNumberOfvalues = incorrectNumberOfvalues;
			this.moreThanOneUpdatedRows = moreThanOneUpdatedRows;
		}
	}

	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider dip = DocumentIdProvider.getInstance();
		dip.setEnvironment(new Environment(""));
		dip.setConfigFile("config.properties");
		dip.loadIdProvider();

		Document d = new Document(dip);
		int id = d.getDocumentId();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: "+id+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}


	@Test
	public void appAssignsConsecutiveDocNumberToConsecutiveDocs() throws NonRecoverableError, RecoverableError{
		DocumentIdProvider dip = DocumentIdProvider.getInstance();
		dip.setEnvironment(new Environment(""));
		dip.setConfigFile("config.properties");
		dip.setDbDriver("com.mysql.jdbc.Driver");
		dip.loadIdProvider();


		Document d1 = new Document(dip);
		int id1 = d1.getDocumentId();
		Document d2 = new Document(dip);
		int id2 = d2.getDocumentId();

		assertEquals(id2-id1, 1);
	}

	@Test(expected = NonRecoverableError.class)
	public void appDetectsNotExistingConfigFile() throws NonRecoverableError {
		DocumentIdProvider dip = DocumentIdProvider.getInstance();
		dip.setEnvironment(new Environment(""));
		dip.setConfigFile("confioperties");
		dip.setDbDriver("com.mysql.jdbc.Driver");
		dip.loadIdProvider();
	}

	@Test(expected = NonRecoverableError.class)
	public void appDetectsMYSQLDriverDoesNotExist() throws NonRecoverableError {
		DocumentIdProvider dip = DocumentIdProvider.getInstance();
		dip.setEnvironment(new Environment(""));
		dip.setConfigFile("config.properties");
		dip.setDbDriver("com.mysql.jdb");
		dip.loadIdProvider();
	}

	@Test(expected = NonRecoverableError.class)
	public void appDetectsMoreThanOneRowInCountersTable() throws NonRecoverableError{
		DocumentIdProviderDouble dipd = new DocumentIdProviderDouble(true, false);
		dipd.setEnvironment(new Environment(""));
		dipd.setConfigFile("config.properties");
		dipd.setDbDriver("com.mysql.jdbc.Driver");
		dipd.loadIdProvider();

	}

	@Test(expected = NonRecoverableError.class)
	public void appDetectsDocumentIdUpdateInCountersTableIsIncorrect() throws NonRecoverableError{
		DocumentIdProviderDouble dipd = new DocumentIdProviderDouble(false, true);
		dipd.setEnvironment(new Environment(""));
		dipd.setConfigFile("config.properties");
		dipd.setDbDriver("com.mysql.jdbc.Driver");
		dipd.loadIdProvider();

		Document d = new Document(dipd);
		dipd.getDocumentId();
	}

}
