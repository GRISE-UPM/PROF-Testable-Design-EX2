package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_CONNECT_DATABASE;
import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_INSTANTIATE_DRIVER;
import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_READ_FILE;
import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_RUN_QUERY;
import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion2018.tema5.Error.CONNECTION_LOST;
import static es.upm.grise.profundizacion2018.tema5.Error.CORRUPTED_COUNTER;
import static es.upm.grise.profundizacion2018.tema5.Error.INCORRECT_COUNTER;
import static es.upm.grise.profundizacion2018.tema5.Error.NON_EXISTING_FILE;
import static es.upm.grise.profundizacion2018.tema5.Error.UNDEFINED_ENVIRON;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class SmokeTest {
	Document doc1;
	Document doc2;
	DocumentIdProviderTest documentIdProviderTest;
	public String url = "jdbc:mysql://piedrafita.ls.fi.upm.es:8000/tema5";
	public String 	username = "tema5";
	public String password = "tema5";
	@Before
	public void toTest() throws NonRecoverableError, RecoverableError {
		doc1 = new Document();
		doc2 = new Document();
		documentIdProviderTest = new DocumentIdProviderTest();

	}
	
	
	public static class DocumentIdProviderTest extends DocumentIdProvider{
		/*
		public String url = "jdbc:mysql://piedrafita.ls.fi.upm.es:8000/tema5";
		public String 	username = "tema5";
		public String password = "tema5";
		
		
		// Create the connection to the database
		private DocumentIdProviderTest() throws NonRecoverableError {
			
			// Load DB driver
			try {

					Class.forName("com.mysql.jdbc.Driver").newInstance();

			} catch (InstantiationException e) {

					System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
					throw new NonRecoverableError();

			} catch (IllegalAccessException e) {

					System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
					throw new NonRecoverableError();

			} catch (ClassNotFoundException e) {

					System.out.println(CANNOT_FIND_DRIVER.getMessage());          	
					throw new NonRecoverableError();

			}

				// Create DB connection
			try {

					connection = DriverManager.getConnection(url, username, password);

			} catch (SQLException e) {

					System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
					throw new NonRecoverableError();

			}

				// Read from the COUNTERS table
				String query = "SELECT documentId FROM Counters";
				Statement statement = null;
				ResultSet resultSet = null;

				try {

					statement = connection.createStatement();
					resultSet = statement.executeQuery(query);

				} catch (SQLException e) {

					System.out.println(CANNOT_RUN_QUERY.getMessage());          	
					throw new NonRecoverableError();

				}

				// Get the last objectID
				int numberOfValues = 0;
				try {

					while (resultSet.next()) {

						documentId = resultSet.getInt("documentId");
						numberOfValues++;

					}

				} catch (SQLException e) {

					System.out.println(INCORRECT_COUNTER.getMessage());          	
					throw new NonRecoverableError();

				}

				// Only one objectID can be retrieved
				if(numberOfValues != 1) {

					System.out.println(CORRUPTED_COUNTER.getMessage());          	
					throw new NonRecoverableError();

				}

				// Close all DB connections
				try {

					resultSet.close();
					statement.close();

				} catch (SQLException e) {

					System.out.println(CONNECTION_LOST.getMessage());          	
					throw new NonRecoverableError();

				}
		}	
			// Return the next valid objectID
			public  int getDocumentIdTest() throws NonRecoverableError {

					documentId++;

					// Access the COUNTERS table
					String query = "UPDATE Counters SET documentId = ?";
					int numUpdatedRows;

					// Update the documentID counter
					try {

						PreparedStatement preparedStatement = connection.prepareStatement(query);
						preparedStatement.setInt(1, documentId);
						numUpdatedRows = preparedStatement.executeUpdate();

					} catch (SQLException e) {

						System.out.println(e.toString());
						System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
						throw new NonRecoverableError();

					}

					// Check that the update has been effectively completed
					if (numUpdatedRows != 1) {

						System.out.println(CORRUPTED_COUNTER.getMessage());          	
						throw new NonRecoverableError();

					}

					return documentId;

			}
			*/
		
	}
	
	/**
	 * a. La aplicación genera las plantillas correctamente 
	 * b. La aplicación asigna el número de documento correcto.
	 * @throws NonRecoverableError
	 * @throws RecoverableError
	 */
	@Test
	public void test1() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: "+d.documentId+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	


	/**
	 * c. Los números de documento asignados a documentos consecutivos son también
	 *  números consecutivos.
	 * @throws NonRecoverableError
	 * @throws RecoverableError
	 */
	@Test
	public void test3() throws NonRecoverableError, RecoverableError {
		/*DocumentIdProviderTest documentIdProviderTest = new DocumentIdProviderTest();
		int prevId = documentIdProviderTest.getDocumentIdTest();		
		Document document = new Document(documentIdProviderTest.getDocumentIdTest());		
		assertEquals(document.getDocumentId(),prevId+1);*/
		
		assertEquals(doc1.getDocumentId() + 1, doc2.getDocumentId());
	}
	
	
	/**
	 * a. La aplicación detecta correctamente que el fichero de configuración no existe.
	 * @throws NonRecoverableError
	 * @throws RecoverableError
	 */
	@Test(expected = NonRecoverableError.class)
	public void test4() throws NonRecoverableError, RecoverableError {
		
		Properties propertiesInFile = new Properties();
		documentIdProviderTest.checkIfConfigFileExists(propertiesInFile, null, "falsePath");
	}
	
	/**
	 * b. La aplicación detecta correctamente que el driver MySQL no existe.
	 * @throws NonRecoverableError
	 * @throws RecoverableError
	 */
	@Test
	public void test5() throws NonRecoverableError, RecoverableError {
		documentIdProviderTest.loadBdDriver("nonExisting.Driver");
	}
	
	/**
	 * c. La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
	 * @throws NonRecoverableError
	 * @throws RecoverableError
	 * @throws SQLException 
	 */
	@Test
	public void test6() throws NonRecoverableError, RecoverableError, SQLException {
		/*
		documentIdProviderTest.checkIfConfigFileExists(new Properties(),null,System.getenv(documentIdProviderTest.ENVIRON));
		documentIdProviderTest.loadBdDriver("com.mysql.jdbc.Driver");
		documentIdProviderTest.connection = documentIdProviderTest.createDbConnection(url, username, password);
		String query = "SELECT documentId FROM Counters";
		Statement stat =  documentIdProviderTest.connection.createStatement();
		ResultSet result = stat.executeQuery(query);
*/
	}
	/**
	 * La aplicación detecta correctamente que la actualización del documentID en la tabla Counters 
	 * ha sido incorrectamente realizado.
	 * 	 * @throws NonRecoverableError
	 * @throws RecoverableError
	 * @throws SQLException 
	 */
	@Test
	public void test7() throws NonRecoverableError, RecoverableError, SQLException {
		Document doc = new Document();
		assertEquals(documentIdProviderTest.getDocumentId(), doc.getDocumentId());
	}
	
}
