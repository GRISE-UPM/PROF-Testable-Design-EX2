package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	private static final int NEXT_ID = 5;
	Document d;
	Document d2;
	DocumentIdProvider provider;
	
	@Before
	public void setUp() throws NonRecoverableError {
		// Initialize with provider double
		provider = new DocumentIdProviderDouble(NEXT_ID);
		
		d = new Document(provider.getDocumentId());
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		
		d2 = new Document(provider.getDocumentId());
		d2.setTemplate("DECLARATION");
		d2.setTitle("A1");
		d2.setAuthor("B1");
		d2.setBody("C1");
	}
	
	//4a
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		assertEquals("DOCUMENT ID: "+NEXT_ID+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	//4b
	@Test
	public void assignDocumentIdCorrectly() throws NonRecoverableError {
		// expected NEXT_ID
		assertEquals(NEXT_ID,d.getDocumentId());
	}
	
	//4c
	@Test
	public void assignDocumentIdConsecutively() throws NonRecoverableError {
		// expected NEXT_ID
		assertEquals(NEXT_ID,d.getDocumentId()); // doc1 has NEXT_ID
		assertEquals(NEXT_ID+1,d2.getDocumentId()); // doc2 has NECT_ID + 1
	}
	
	//5a
	@Test (expected = NonRecoverableError.class)
	public void loadPropertiesConfigNotExists() throws NonRecoverableError {
		provider.loadProperties("errorPath");
	}
	
	//5b
	@Test (expected = NonRecoverableError.class)
	public void loadDriverNotExists() throws NonRecoverableError {
		provider.loadDBDriver("com.mynonexistingdriver.mysql");
	}
	
	//5c
	@Test (expected = NonRecoverableError.class)
	public void moreThanOneRowOnCountersTable() throws NonRecoverableError {
		//Gets the more than one column error when trying to getLastId
		provider = new DocumentIdProviderDoubleV2();
	}
	
	//5d
	@Test (expected = NonRecoverableError.class)
	public void incorrectUpdateOfDocumentIdOnConuntersTable() throws NonRecoverableError {
		provider = new DocumentIdProviderDouble(-1);
		d = new Document(provider.getDocumentId());
	}

}
