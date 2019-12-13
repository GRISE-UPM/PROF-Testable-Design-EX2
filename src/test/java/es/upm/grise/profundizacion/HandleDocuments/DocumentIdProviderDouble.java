package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDouble extends DocumentIdProvider {
	
	@Override
	void createDBConnection() {
		// Se eliminan las dependencias de la BD
	}
	
	@Override
	void readCountersTable() {
		// Se eliminan las dependencias de la BD
	}
	
	@Override
	int getLastObjectID() {
		// Se eliminan las dependencias de la BD
		return 5;
	}
	
	@Override
	void closeDBConnection() {
		// Se eliminan las dependencias de la BD
	}
	
	@Override
	int updateDocumentIDCounter() throws NonRecoverableError {
		// Se eliminan las dependencias de la BD
		return 0;
	}

}
