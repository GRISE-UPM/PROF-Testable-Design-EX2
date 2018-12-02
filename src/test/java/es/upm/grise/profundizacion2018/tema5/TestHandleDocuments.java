package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion2018.tema5.Error.CORRUPTED_COUNTER;
import static es.upm.grise.profundizacion2018.tema5.Error.NON_EXISTING_FILE;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestHandleDocuments {
	DocumentIdProvider docProvid;

    @Before 
    public void init() {
    	docProvid.CONFIGFILE="config.properties";
    	docProvid.DRIVER="com.mysql.jdbc.Driver";
    	docProvid.UPDATE_QUERY="UPDATE Counters SET documentId = ?";
    }

	@Test 
	public void testConfiguracionNoExiste() throws NonRecoverableError, IOException {
		
		docProvid= new DocumentIdProvider();
		docProvid.CONFIGFILE="NoExiste";
		try{
			docProvid.connect();
		}
		catch(NonRecoverableError e) {
			assertEquals(NON_EXISTING_FILE.getMessage(), e.getMessage());
			
		}
		
		
	}

	@Test 
	public void driverMySQLSNoExiste() throws NonRecoverableError, IOException {
		
		docProvid= new DocumentIdProvider();
		docProvid.DRIVER="DriverErroneo";
		try{
			docProvid.connect();
		}
		catch(NonRecoverableError e) {
			assertEquals(CANNOT_FIND_DRIVER.getMessage(), e.getMessage());
			
		}
		
		
	}
	@Test 
	public void detectaFilasCounter() throws NonRecoverableError, IOException {
		
		docProvid= new DocumentIdProvider();
		docProvid.numberOfValues=1;
		try{
			docProvid.connect();
		}
		catch(NonRecoverableError e) {
			assertEquals(CORRUPTED_COUNTER.getMessage(), e.getMessage());
			
		}
		
		
	}
	
	@Test 
	public void updateIncorrecto() throws NonRecoverableError, IOException {
		
		docProvid= new DocumentIdProvider();
		docProvid.UPDATE_QUERY="UPDATE Counters SET Id = ?";
		docProvid.connect();
		try{
			docProvid.getDocumentId();
		}
		catch(NonRecoverableError e) {
			assertEquals(CANNOT_UPDATE_COUNTER.getMessage(), e.getMessage());
			
		}
		
		
	}
	
	 
}
