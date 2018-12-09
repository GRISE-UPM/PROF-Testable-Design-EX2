package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class SmokeTest {
	
	class DocumentIdProviderDouble extends DocumentIdProvider{
		public DocumentIdProviderDouble() throws NonRecoverableError{			
		}
	}
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {		
		Document d = new Document();
		int documentId = d.getDocumentId();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: " + documentId + "\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	@Test 
	public void consecutiveCallsShouldReturnConsecutiveNumbers() throws NonRecoverableError, RecoverableError {		
		Document firstDocument = new Document();
		Document secondDocument = new Document();
		int firstDocumentId = firstDocument.getDocumentId();
		int secondDocumentId = secondDocument.getDocumentId();
		assertEquals(firstDocumentId + 1, secondDocumentId);
	}
	
	@Test(expected = NonRecoverableError.class)
	public void ifConfigFileDoesntExitsShouldThrowException() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;
		documentIdProvider.checkIfConfigFileExists(propertiesInFile, inputFile, "falsePath");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void ShouldThrowExceptionIfDriverDoesntExists() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		documentIdProvider.loadBdDriver("nonExisting.Driver");
	}
	
}
