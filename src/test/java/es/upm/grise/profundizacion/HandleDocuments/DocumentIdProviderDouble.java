package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

import java.util.Properties;


public class DocumentIdProviderDouble extends DocumentIdProvider {
	
	protected int documentId;
	

	public DocumentIdProviderDouble(int id) throws NonRecoverableError {
		super();
		documentId = id;
	}
	
	@Override
	public int getDocumentId() throws NonRecoverableError {
		if (documentId == 4) {
			throw new NonRecoverableError();
		}
		return documentId++;
	}
	
	@Override
	public Properties getProperties(String path) throws NonRecoverableError{
		return super.getProperties(path);
	}
	
	@Override
	public void loadDBDriver (String driver) throws NonRecoverableError{
		super.loadDBDriver(driver);
	}
	
	@Override
	public void readCounters() throws NonRecoverableError{
		super.readCounters();
	}
}