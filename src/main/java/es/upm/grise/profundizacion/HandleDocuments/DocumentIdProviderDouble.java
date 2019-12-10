package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import java.sql.ResultSet;
import java.util.Properties;

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
	public void loadDbDriver(String sql) throws NonRecoverableError {
		super.loadDbDriver(sql);
	}

	@Override
	public void readFromCountersTable() throws NonRecoverableError {
		super.readFromCountersTable();
	}

	@Override
	public Properties getProperties(String path) throws NonRecoverableError {
		return super.getProperties(path);
	}

	@Override
	public int last(ResultSet resultSet, boolean find) throws NonRecoverableError {
		if (find)
			return 1;
		else {
			System.out.println(CORRUPTED_COUNTER.getMessage());
			throw new NonRecoverableError();
		}
	}

}
