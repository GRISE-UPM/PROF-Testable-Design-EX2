package es.upm.grise.profundizacion.HandleDocuments;

import java.sql.ResultSet;
import java.util.Properties;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;;


public class DocumentIdProviderDouble extends DocumentIdProvider {

	protected int documentId;


	public DocumentIdProviderDouble(int id) throws NonRecoverableError {
		super();
		documentId = id;
	}

	@Override
	public int getDocumentId() throws NonRecoverableError {
		if (documentId == 5) {
			throw new NonRecoverableError();
		}
		return documentId++;
	}
	
	@Override
	public void loadDbDriver (String sql) throws NonRecoverableError{
		super.loadDbDriver(sql);
	}
	
	@Override
	public void readFromCountersTable() throws NonRecoverableError{
		super.readFromCountersTable();
	}

	@Override
	public Properties loadProperties(String path) throws NonRecoverableError{
		return super.loadProperties(path);
	}
	
	@Override
	public int getLast(ResultSet resultSet, boolean checker) throws NonRecoverableError {
		if(!checker) {
			System.out.println(CORRUPTED_COUNTER.getMessage());
			throw new NonRecoverableError();
		}else {
			return 1;
		}
	}

} 