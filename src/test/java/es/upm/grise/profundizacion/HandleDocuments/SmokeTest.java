package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;
import static org.junit.Assert.*;


import org.junit.Rule;
import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.Properties;

public class SmokeTest {
	//comprueba tanto que hace bien el template como que toma el id correcto del documento;
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document(0);
		d.documentId = 1623;
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}

	//Al construir documentos se llama a la funcion getDocument del DocumentIdProvider, asi que porbamos esa
	@Test
	public void consecutiveIDsForConsecutiveDocuments() throws NonRecoverableError, RecoverableError {

		DocumentIdProviderDouble did = new DocumentIdProviderDouble(0);
		int id1= did.getDocumentId();
		int id2= did.getDocumentId();
		assertEquals(1,id2-id1);
	}
	
	//Assertions.assertThrows(Exception.class, () -> {
	//			f.convert(str); });

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@Test
	public void nonExistingFile() throws NonRecoverableError {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		expectedEx.expect(NonRecoverableError.class);
		//expectedEx.expectMessage(NON_EXISTING_FILE.getMessage());
		DocumentIdProvider did = new DocumentIdProvider();
		String path = "PATH_NO_EXISTE";
		Properties propertiesInFile = new Properties();
		did.propertyFile(path,  propertiesInFile);
		assertEquals(NON_EXISTING_FILE.getMessage(), outContent.toString());
	}


	@Test
	public void nonExistingDriver() throws NonRecoverableError {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		expectedEx.expect(NonRecoverableError.class);
		//expectedEx.expectMessage(NON_EXISTING_FILE.getMessage());
		DocumentIdProvider did = new DocumentIdProvider();
		did.loadDriver("driver_inventado");
		assertEquals(CANNOT_FIND_DRIVER.getMessage(), outContent.toString());
	}

	@Test
	public void masDeUnaFilaCounter() throws NonRecoverableError {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ResultSetDouble rs = new ResultSetDouble(2);
		DocumentIdProvider did = new DocumentIdProvider();
		expectedEx.expect(NonRecoverableError.class);
		did.objectID(rs);
		assertEquals(CORRUPTED_COUNTER.getMessage(), outContent.toString());

	}

	@Test
	public void incorrectUpdate() throws NonRecoverableError {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		DocumentIdProvider did = new DocumentIdProvider();
		expectedEx.expect(NonRecoverableError.class);
		//Si no se realiza la actualizacion el numero de filas actualizadas permanece en 0
		did.checkUpdate(0);
		assertEquals(CORRUPTED_COUNTER.getMessage(), outContent.toString());

	}


	

}
