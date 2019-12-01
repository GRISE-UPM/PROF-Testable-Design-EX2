package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {

	/** Una carpeta temporal. */
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	/** La fábrica de plantillas. */
	private TemplateFactory templateFactory = new TemplateFactory();
	
	/** El identificador aleatorio. */
	private int id;

	/** El título aleatorio. */
	private String title;

	/** El autor aleatorio. */
	private String author;

	/** El cuerpo aleatorio. */
	private String body;

	@Before
	public void setUp() {
		title  = randomString(6);
		author = randomString(6);
		body   = randomString(6);
		id     = new Random().nextInt();
	}

	// Comprueba que se asigna el identificador correcto
	@Test
	public void correctDocumentId() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override        String             _getAppPath()                                                   { return null; }
			@Override        Properties         _readCredentials(boolean force)                                 { return null; }
			@Override        void               _loadDrivers()                                                  {              }
			@Override        Connection         _createConnection(String url, String username, String password) { return null; }
			@Override public DocumentIdProvider connect()                                                       { return null; }
			@Override        ResultSet          _queryCounter(Connection connection)                            { return null; }
			@Override        int                _readId()                                                       { return 0;    }
			@Override        int                _updateId(Connection connection, int id)                        { return 1;    }
			@Override public int                getDocumentId()                                                 { return id;   }
		};
		Document d = new Document(templateFactory, provider);
		setupDocument(d);
		assertEquals(id, d.getDocumentId());
	}

	// Comprueba que los identificadores crecen monotónicamente
	@Test
	public void consecutiveDocumentId() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override        String             _getAppPath()                                                   { return null; }
			@Override        Properties         _readCredentials(boolean force)                                 { return null; }
			@Override        void               _loadDrivers()                                                  {              }
			@Override        Connection         _createConnection(String url, String username, String password) { return null; }
			@Override public DocumentIdProvider connect()                                                       { return null; }
			@Override        ResultSet          _queryCounter(Connection connection)                            { return null; }
			@Override        int                _readId()                                                       { return id;   }
			@Override        int                _updateId(Connection connection, int id)                        { return 1;    }
		};
		for (int i = 1; i <= 10; i++) {
			Document d = new Document(templateFactory, provider);
			setupDocument(d);
			assertEquals(id + i, d.getDocumentId());
		}
	}

	// Comprueba que el formato de la plantilla es correcto
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override        String             _getAppPath()                                                   { return null; }
			@Override        Properties         _readCredentials(boolean force)                                 { return null; }
			@Override        void               _loadDrivers()                                                  {              }
			@Override        Connection         _createConnection(String url, String username, String password) { return null; }
			@Override public DocumentIdProvider connect()                                                       { return null; }
			@Override        ResultSet          _queryCounter(Connection connection)                            { return null; }
			@Override        int                _readId()                                                       { return id;   }
			@Override        int                _updateId(Connection connection, int id)                        { return 1;    }
		};
		Document d = new Document(templateFactory, provider);
		setupDocument(d);
		String expected = "DOCUMENT ID: " + (id + 1);
		expected += "\n\nTitle : " + title;
		expected += "\nAuthor: " + author;
		expected += "\n\n" + body;
		assertEquals(expected, d.getFormattedDocument());
	}

	// Comprueba que el archivo de configuración no existe
	@Test(expected = NonRecoverableError.class)
	public void configDoesNotExist() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return folder.getRoot().getAbsolutePath(); }
			@Override void       _loadDrivers()                                                  {                                            }
			@Override Connection _createConnection(String url, String username, String password) { return null;                               }
			@Override ResultSet  _queryCounter(Connection connection)                            { return null;                               }
			@Override int        _updateId(Connection connection, int id)                        { return 1;                                  }
		};
		new Document(templateFactory, provider);
	}

	// Comprueba que el archivo de configuración sí existe
	@Test
	public void configDoesExist() throws NonRecoverableError, IOException {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return folder.getRoot().getAbsolutePath(); }
			@Override void       _loadDrivers()                                                  {                                            }
			@Override Connection _createConnection(String url, String username, String password) { return null;                               }
			@Override ResultSet  _queryCounter(Connection connection)                            { return null;                               }
			@Override int        _updateId(Connection connection, int id)                        { return 1;                                  }
		};
		folder.newFile("config.properties");
		new Document(templateFactory, provider);
	}

	// Comprueba que el driver MySQL no existe
	@Test(expected = NonRecoverableError.class)
	public void driverDoesExist1() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return null;                        }
			@Override Properties _readCredentials(boolean force)                                 { return null;                        }
			@Override void       _loadDrivers() throws InstantiationException                    { throw new InstantiationException(); }
			@Override Connection _createConnection(String url, String username, String password) { return null;                        }
			@Override ResultSet  _queryCounter(Connection connection)                            { return null;                        }
			@Override int        _updateId(Connection connection, int id)                        { return 1;                           }
		};
		new Document(templateFactory, provider);
	}

	// Comprueba que el driver MySQL no existe
	@Test(expected = NonRecoverableError.class)
	public void driverDoesExist2() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return null;                        }
			@Override Properties _readCredentials(boolean force)                                 { return null;                        }
			@Override void       _loadDrivers() throws IllegalAccessException                    { throw new IllegalAccessException(); }
			@Override Connection _createConnection(String url, String username, String password) { return null;                        }
			@Override ResultSet  _queryCounter(Connection connection)                            { return null;                        }
			@Override int        _updateId(Connection connection, int id)                        { return 1;                           }
		};
		new Document(templateFactory, provider);
	}

	// Comprueba que el driver MySQL no existe
	@Test(expected = NonRecoverableError.class)
	public void driverDoesExist3() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return null;                        }
			@Override Properties _readCredentials(boolean force)                                 { return null;                        }
			@Override void       _loadDrivers() throws ClassNotFoundException                    { throw new ClassNotFoundException(); }
			@Override Connection _createConnection(String url, String username, String password) { return null;                        }
			@Override ResultSet  _queryCounter(Connection connection)                            { return null;                        }
			@Override int        _updateId(Connection connection, int id)                        { return 1;                           }
		};
		new Document(templateFactory, provider);
	}

	// Comprueba si hay más de una fila
	@Test(expected = NonRecoverableError.class)
	public void moreThanOneCounter() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return null;             }
			@Override Properties _readCredentials(boolean force)                                 { return new Properties(); }
			@Override Connection _createConnection(String url, String username, String password) { return null;             }
			@Override int        _updateId(Connection connection, int id)                        { return 1;                }
			@Override ResultSet  _queryCounter(Connection connection) {
				return new DummyResultSet() {
					private int n = 10;
					@Override public boolean next() throws SQLException { return n-- == 0; }					
				};
			}
		};
		new Document(templateFactory, provider);
	}

	// Comprueba si falla la actualización
	@Test(expected = NonRecoverableError.class)
	public void failUpdate1() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return null;             }
			@Override Properties _readCredentials(boolean force)                                 { return new Properties(); }
			@Override Connection _createConnection(String url, String username, String password) { return null;             }
			@Override int        _readId()                                                       { return id;               }
			@Override int        _updateId(Connection connection, int id)                        { return 0;                }
		};
		new Document(templateFactory, provider);
	}

	// Comprueba si falla la actualización
	@Test(expected = NonRecoverableError.class)
	public void failUpdate2() throws NonRecoverableError {
		DocumentIdProvider provider = new DocumentIdProvider() {
			@Override String     _getAppPath()                                                   { return null;              }
			@Override Properties _readCredentials(boolean force)                                 { return new Properties();  }
			@Override Connection _createConnection(String url, String username, String password) { return null;              }
			@Override int        _readId()                                                       { return id;                }
			@Override int        _updateId(Connection connection, int id) throws SQLException    { throw new SQLException(); }
		};
		new Document(templateFactory, provider);
	}

	/**
	 * Crea una cadena de texto aleatoria con el número de bytes especificados.
	 * 
	 * @param length The string length.
	 * @return A random string.
	 */
	private static String randomString(int length) {
		byte[] array = new byte[length];
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}

	/**
	 * Configura un documento.
	 * 
	 * @param document El documento.
	 */
	private void setupDocument(Document document) {
		document.setTemplate("DECLARATION");
		document.setTitle(title);
		document.setAuthor(author);
		document.setBody(body);
	}

}
