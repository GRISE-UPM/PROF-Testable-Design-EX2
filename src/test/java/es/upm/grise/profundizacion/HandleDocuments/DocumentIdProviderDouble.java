package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;

import java.sql.Connection;
import java.util.Properties;

class DocumentIdProviderDouble extends DocumentIdProvider{

		private int dummyID;

		DocumentIdProviderDouble(int dummyID) throws NonRecoverableError {
			super();
			this.dummyID = dummyID;
		}


		@Override
		public String envVar() throws NonRecoverableError {
			return "";
		}

		@Override
		public Properties propLoader(String path) throws NonRecoverableError {
			return super.propLoader(path);
		}
		
		@Override
		public Connection connCreator(String url, String username, String password) throws NonRecoverableError {
			return super.connCreator(url, username, password);
		}
		
		@Override
		public int getID() throws NonRecoverableError {
			return super.getID();
		}

		@Override
		public int getDocumentId() throws NonRecoverableError {
			if (dummyID < 0) { // Simular fallo de actualizacion
				System.out.println(CORRUPTED_COUNTER.getMessage());    
				throw new NonRecoverableError();
			}
			return dummyID++;
		}
	}