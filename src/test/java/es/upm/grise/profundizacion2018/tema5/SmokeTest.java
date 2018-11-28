package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		DocumentIdProvider prov = new DocumentIdProvider();
		int expectedDocumentId = prov.documentId + 1;
		System.out.println(expectedDocumentId);
		
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: " + expectedDocumentId + "\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	
	@Test
	public void consecutiveNumbers() throws NonRecoverableError, RecoverableError {
		
		Document doc1 = new Document();
		int idDocument1 = (int)doc1.getDocumentId();
		Document doc2 = new Document();
		int idDocument2 = (int)doc2.getDocumentId();
		
		assertEquals("Consecutive document IDs are consecutive", idDocument1 + 1, idDocument2);
		
	}
	
	@Test(expected = NonRecoverableError.class) 
	public void configurationFileDoesNotExist() throws NonRecoverableError {
		DocumentIdProvider.getInstance().getConfig("no_existente");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void inexistentConfigurationJDBC() throws NonRecoverableError {
		Properties propertiesInFile = new Properties();
		propertiesInFile.setProperty("url", "jdbc:mysql://piedrafita.ls.fi.upm.es:8000/tema5");
		propertiesInFile.setProperty("username", "tema5");
		propertiesInFile.setProperty("password", "tema5");
		DocumentIdProvider.getInstance().getConnection(propertiesInFile, "no_existente");
	}
	
	@Test
	public void moreThan1RowCountersMySQL() throws NonRecoverableError, SQLException {
		
		DocumentIdProvider prov = new DocumentIdProvider();
		prov.connection.setAutoCommit(false);
		String query = "INSERT INTO Counters (documentId) VALUES (0)";
		Statement statement = prov.connection.createStatement();
		int result = statement.executeUpdate(query);
		assertEquals("Removed 1 row", 1, result);
		
		try {
			new DocumentIdProvider();
		} catch (NonRecoverableError e) {
			assertEquals("Find error CORRUPTED_COUNTER", e.getMessage(), Error.CORRUPTED_COUNTER.getMessage());
			prov.connection.rollback();
			prov.connection.setAutoCommit(true);
			
			/*query = "DELETE FROM Counter WHERE documentId = 0";
			statement = prov.connection.createStatement();
			statement.executeQuery(query);
			assertTrue(true); // Need to reach this point*/
		}
		
	}
	
	@Test
	public void successfulMySQLupdate() throws NonRecoverableError {
		// Connection 1
		DocumentIdProvider prov1 = new DocumentIdProvider();
		int id1 = prov1.getDocumentId();
		
		// Connection 2: Different documentId than prov1
		DocumentIdProvider prov2 = new DocumentIdProvider();
		int id2 = prov2.getDocumentId();
		
		assertEquals(id1 + 1, id2);
	}

}
