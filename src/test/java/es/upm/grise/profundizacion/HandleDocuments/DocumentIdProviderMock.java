package es.upm.grise.profundizacion.HandleDocuments;

import java.sql.SQLException;
import java.util.Properties;

public class DocumentIdProviderMock extends DocumentIdProvider{

	private int id;

	public DocumentIdProviderMock(int firstId) throws NonRecoverableError, SQLException {
		super();
		id = firstId;
	}


	@Override
	public int getDocumentId() throws NonRecoverableError {
		int res = id;
		id++;
		if(id > 999999) {
			throw new NonRecoverableError();
		} else
			return res;
	}

	@Override
	public Properties loadProperties(String path) throws NonRecoverableError {
		return super.loadProperties(path);
	}

	@Override
	public void loadDatabaseDriver(String driverClass) throws NonRecoverableError {
		super.loadDatabaseDriver(driverClass);
	}
	
		
}

