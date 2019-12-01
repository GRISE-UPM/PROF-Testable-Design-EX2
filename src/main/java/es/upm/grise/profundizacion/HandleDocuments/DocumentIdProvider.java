package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Proveedor de identificadores de documentos.
 */
public class DocumentIdProvider {

	/** Patrón de acceso "singleton". */
	private static DocumentIdProvider instance;

	/**
	 * Devuelve la instancia única de esta clase.
	 * 
	 * @return El proveedor de documentos.
	 * @throws NonRecoverableError Si el constructor falla.
	 */
	public static DocumentIdProvider getInstance() throws NonRecoverableError {
		return instance == null
			? (instance = new DocumentIdProvider())
			: instance;
	}

	/** Crea un proveedor de documentos. */
	DocumentIdProvider() throws NonRecoverableError {}

	/** Variable de entorno. */
	private static String ENVIRON = "APP_HOME";

	/**
	 * Devuelve la ruta base de la aplicación.
	 * 
	 * @return La ruta base.
	 * @throws NonRecoverableError Si la variable de entorno {@code APP_HOME} no existe.
	 */
	String _getAppPath() throws NonRecoverableError {
		String path = System.getenv(ENVIRON);
		if (path == null) {
			System.err.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();
		}
		return path;
	}

	/** La URL de la base de datos. */
	private String url;

	/** El usuario de la base de datos. */
	private String username;

	/** La contraseña de la base de datos. */
	private String password;

	/**
	 * Lee el archivo de configuración y devuelve las credenciales de acceso a la base de datos.
	 * 
	 * @param force Fuerza la actualización de las credenciales.
	 * @return Las credenciales de acceso.
	 * @throws NonRecoverableError Si la variable de entorno {@code APP_HOME} no está definida.
	 * @throws NonRecoverableError Si el archivo {@code config.properties} no existe.
	 * @throws NonRecoverableError Si el archivo {@code config.properties} no puede ser leído.
	 */
	Properties _readCredentials(boolean force) throws NonRecoverableError {
		Properties properties = new Properties();
		if (url == null || username == null || password == null || force) {
			String path = _getAppPath();
			try (InputStream inputFile = new FileInputStream(Paths.get(path, "config.properties").toString())) {
				properties.load(inputFile);
			} catch (FileNotFoundException e) {
				System.err.println(NON_EXISTING_FILE.getMessage());          	
				throw new NonRecoverableError();
			} catch (IOException e) {
				System.err.println(CANNOT_READ_FILE.getMessage());          	
				throw new NonRecoverableError();
			}
		}
		return properties;
	}

	/**
	 * Carga los drivers para acceder a la base de datos.
	 * 
	 * @throws InstantiationException Si no se puede instanciar el driver de la base de datos.
	 * @throws IllegalAccessException Si no se puede acceder el driver de la base de datos.
	 * @throws ClassNotFoundException Si no se puede encontrar el driver de la base de datos.
	 */
	void _loadDrivers() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}

	/** Conexión a la base de datos (open during program execution). */
	private Connection connection = null;
	
	/**
	 * Crea la conexión a la base de datos.
	 * 
	 * @param url      La URL de acceso.
	 * @param username El usuario.
	 * @param password La contraseña.
	 * @return La conexión.
	 * @throws SQLException Si la conexión no se puede establecer.
	 */
	Connection _createConnection(String url, String username, String password) throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	/**
	 * Connects to the database.
	 * 
	 * @return The document id provider.
	 * @throws NonRecoverableError Si no se puede conectar a la base de datos.
	 */
	public DocumentIdProvider connect() throws NonRecoverableError {
		if (connection == null) {
			try {
				_loadDrivers();
			} catch (InstantiationException e) {
				System.err.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
				throw new NonRecoverableError();
			} catch (IllegalAccessException e) {
				System.err.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
				throw new NonRecoverableError();
			} catch (ClassNotFoundException e) {
				System.err.println(CANNOT_FIND_DRIVER.getMessage());          	
				throw new NonRecoverableError();
			}
			try {
				Properties props = _readCredentials(false);
				if (url == null)      url = props.getProperty("url");
				if (username == null) username = props.getProperty("username");
				if (password == null) password = props.getProperty("password");
				connection = _createConnection(url, username, password);
			} catch (SQLException e) {
				System.err.println(CANNOT_CONNECT_DATABASE.getMessage());          	
				throw new NonRecoverableError();
			}			
		}
		return this;
	}
	
	/** El identificador del nuevo documento. */
	int documentId = -1;
	
	/**
	 * Devuelve el conjunto de filas de la tabla counters.
	 * 
	 * @param connection La conexión.
	 * @return El conjunto de filas.
	 * @throws SQLException Si no se puede ejecutar la sentencia SQL.
	 */
	ResultSet _queryCounter(Connection connection) throws SQLException {
		String query = "SELECT documentId FROM Counters";
		return connection.createStatement().executeQuery(query);
	}
	
	/**
	 * Lee el contador de la base de datos.
	 * 
	 * @return El proveedor de documentos.
	 * @throws NonRecoverableError Si no se puede ejecutar la consulta SQL.
	 * @throws NonRecoverableError Si el contador es incorrecto.
	 * @throws NonRecoverableError Si el contador está corrupto.
	 * @throws NonRecoverableError Si la conexión a la base de datos se cierra.
	 */
	int _readId() throws NonRecoverableError {
		connect();

		// Lectura de la tabla "Counters"
		ResultSet resultSet = null;
		try {
			resultSet = _queryCounter(connection);
		} catch (SQLException e) {
			System.err.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();
		}

		int doc = 0;
		if (resultSet == null) return doc;
		
		// Obtención del último identificador
		int numberOfValues = 0;
		try {
			while (resultSet.next()) {
				doc = resultSet.getInt("documentId");
				numberOfValues += 1;
			}
		} catch (SQLException e) {
			System.err.println(INCORRECT_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}

		// Solo un contador puede ser extraído
		if (numberOfValues != 1) {
			System.err.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}

		// Cierre de todas las conexiones a la base de datos
		try {
			resultSet.close();
		} catch (SQLException e) {
			System.err.println(CONNECTION_LOST.getMessage());          	
			throw new NonRecoverableError();
		}
		return doc;
	}

	/**
	 * Actualiza el contador en la base de datos.
	 * 
	 * @param id El nuevo identificador.
	 * @return El número de filas actualizadas.
	 * @throws SQLException Si el contador no se actualiza correctamente.
	 */
	int _updateId(Connection connection, int id) throws SQLException {
		String query = "UPDATE Counters SET documentId = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, documentId);
		return preparedStatement.executeUpdate();
	}
	
	/**
	 * Devuelve el siguiente identificador de documento.
	 * 
	 * @return El identificador.
	 * @throws NonRecoverableError Si el contador no se actualiza correctamente.
	 */
	public int getDocumentId() throws NonRecoverableError {
		if (documentId == -1) documentId = _readId();
		documentId += 1;
		try {
			int numUpdatedRows = _updateId(connection, documentId);
			if (numUpdatedRows != 1) {
				System.err.println(CORRUPTED_COUNTER.getMessage());          	
				throw new NonRecoverableError();
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
			System.err.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		return documentId;
	}

}
