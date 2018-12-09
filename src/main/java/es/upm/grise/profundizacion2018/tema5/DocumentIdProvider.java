package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DocumentIdProvider {

	private int documentId;
	private EnvironmentHandler environmentHandler;
	private ConfigurationHandler configurationHandler;
	private DatabaseProvider databaseProvider;

	public DocumentIdProvider(EnvironmentHandler environmentHandler, ConfigurationHandler configurationHandler, DatabaseProvider databaseProvider) throws NonRecoverableError {
		this.environmentHandler = environmentHandler;
		this.configurationHandler = configurationHandler;
		this.databaseProvider = databaseProvider;

		String actualPath = getPath("APP_HOME");
		Properties properties = getProperties(actualPath+"//config.properties");
		this.databaseProvider.openConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
		this.documentId = retrieveLastCounter();
	}

	private String getPath(String environmentName) throws NonRecoverableError{
		// If ENVIRON does not exist, null is returned
		String path =  environmentHandler.getVariable(environmentName);
		if (path == null) {
			throw new NonRecoverableError(UNDEFINED_ENVIRON.getMessage());
		}
		return path;
	}

	private Properties getProperties(String configFilePath) throws NonRecoverableError {
		try {
			return configurationHandler.readProperties(new FileInputStream(configFilePath));
		} catch (FileNotFoundException e) {
			throw new NonRecoverableError(NON_EXISTING_FILE.getMessage());
		} catch (IOException e) {
			throw new NonRecoverableError(CANNOT_READ_FILE.getMessage());
		}
	}

	private int retrieveLastCounter() throws NonRecoverableError {
		ResultSet resultSet;
		int numberOfValues = 0;
		int id = 0;
		String query = "SELECT documentId FROM Counters";
		resultSet = this.databaseProvider.executeQuery(query);
		try {
			while (resultSet.next()) {
				id = resultSet.getInt("documentId");
				numberOfValues++;
			}
		} catch (SQLException e) {
			throw new NonRecoverableError(INCORRECT_COUNTER.getMessage());
		}
		if(numberOfValues != 1) {
			throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());
		}
		this.databaseProvider.closeStatement();
		return id;
	}

	// Return the next valid objectID
	public int getDocumentId() throws NonRecoverableError {
		documentId++;
		// Access the COUNTERS table
		String query = "UPDATE Counters SET documentId = ?";
		int numUpdatedRows;

		// Update the documentID counter
		numUpdatedRows = this.databaseProvider.executeUpdateQuery(query, documentId);
		// Check that the update has been effectively completed
		if (numUpdatedRows != 1) {
			throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());
		}

		databaseProvider.closeUpdateStatement();

		return documentId;

	}
}
