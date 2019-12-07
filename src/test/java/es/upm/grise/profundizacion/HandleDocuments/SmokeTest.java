package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Properties;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	//Pruebas apartado 4
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");

		assertEquals("Correcto: formato", "DOCUMENT ID: 4\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	@Test
	public void numeroDocumentoCorrecto() throws NonRecoverableError, RecoverableError {
		
		Document d1 = new Document();
		int ID_DOCUMENTO = (int) d1.getDocumentId();
		int ID_CORRECTO = 1;
		
		assertEquals("Correcto: numero de documento", ID_CORRECTO, ID_DOCUMENTO);
	}
	
	@Test
	public void numerosConsecutivosADocumentosConsecutivos() throws NonRecoverableError, RecoverableError {
		
		Document d1 = new Document();
		Document d2 = new Document();
		int ID_DOCUMENTO_PRIMERO = (int) d1.getDocumentId();
		int ID_DOCUMENTO_SEGUNDO = (int) d2.getDocumentId();
		
		assertEquals("Correcto: números consecutivos a documentos consecutivos", ID_DOCUMENTO_PRIMERO, ID_DOCUMENTO_SEGUNDO-1);
	}
	
	
	//Pruebas apartado 5
	@Test(expected = NonRecoverableError.class)
	public void excepcionFicheroConfiguracionNoExiste() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProviderDouble = new DocumentIdProviderDouble();
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;
		documentIdProviderDouble.loadPropertyFile(propertiesInFile, inputFile, "daIgual");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void excepcionDriverMysqlNoExiste() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		documentIdProvider.loadDBDriver("NO_EXISTE");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void excepcionMasDeUnaFilaEnCounters() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		int numberOfValues = documentIdProvider.getLastObjectID(null, false);
	}
	
	@Test(expected = NonRecoverableError.class)
	public void excepcionIncorrectaActualizacionCounters() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		documentIdProvider.getDocumentId();
		documentIdProvider.getDocumentId();
		documentIdProvider.getDocumentId();
		documentIdProvider.getDocumentId();
		documentIdProvider.getDocumentId();	//Se simula que a la quinta va a actualizar mal

	}

}
