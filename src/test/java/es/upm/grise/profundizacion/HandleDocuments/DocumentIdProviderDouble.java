package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_UPDATE_COUNTER;

import java.sql.ResultSet;
import java.util.Properties;

public class DocumentIdProviderDouble extends DocumentIdProvider {

	private int documentId;

	public DocumentIdProviderDouble(int initialId) throws NonRecoverableError {
		super();
		documentId = initialId;
	}

	/**
	 * En el caso de que el initialId sea negativo se lanzará la excepción para simular actualizacion fallida
	 */
	@Override
	public int getDocumentId() throws NonRecoverableError {

		if (documentId < 0) {
			throw new NonRecoverableError(CANNOT_UPDATE_COUNTER.getMessage());
		}

		return documentId++;
	}

	@Override
	public Properties loadProperties(String path) throws NonRecoverableError {
		return super.loadProperties(path);
	}

	@Override
	public void loadDatabaseDriver(String driverClass) throws NonRecoverableError {
		super.loadDatabaseDriver(driverClass);
	}

	@Override
	public int getLastId(ResultSet resultSet) throws NonRecoverableError {
		return super.getLastId(resultSet);
	}
}
