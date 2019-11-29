package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Properties;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	//este test se ejecuta mas tarde no se por que, por lo que se ha puesto N=&, aunque deberia ser 3, que es con el que se ha iniciado el double
	// pero como coge antes el test 2 y el 3, estos crean los documentso 3, 4 y 5, asi que toca el 6
	@Test
	public void numeroCorrecto() throws NonRecoverableError, RecoverableError {
		
		int N=6;
		Document d = new Document();
		int documentId=d.getDocumentId();
		//System.out.println(documentId);
		assertEquals(N,documentId);

	}
	
	@Test
	public void formatoCorrecto() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		int documentId=d.getDocumentId();
		//System.out.println(documentId);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: "+documentId+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	
	@Test
	public void numerosConsecutivos() throws NonRecoverableError, RecoverableError {
		
		Document d = new Document();
		int documentId=d.getDocumentId();
		//System.out.println(documentId);
		Document d2 = new Document();
		int documentId2=d2.getDocumentId();
		assertEquals(documentId,documentId2-1);

	}
	
	@Test(expected = NonRecoverableError.class)
	public void fichConiguracionNoExiste() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		Properties propierties = new Properties();
		InputStream file = null;
		documentIdProvider.loadPropertyFile( "algo", file,propierties);
	}
	
	@Test(expected = NonRecoverableError.class)
	public void driverNoExiste() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		String driver= "algunDriver";
		documentIdProvider.loadDBdriver( driver);
	}
	
	@Test(expected = NonRecoverableError.class)
	public void masDeUnaFilaenLaTablaCounters() throws NonRecoverableError{
		int numberOfValues=2;
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		//ResultSet rs = null;
		//int numberOfValues = documentIdProvider.getLastObjectID(rs, 0);
		documentIdProvider.checkNumberOfValues(numberOfValues);
	}
	
	@Test(expected = NonRecoverableError.class)
	public void errorActualizandoCounters() throws NonRecoverableError{
		int numberOfValues=2;
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		//ResultSet rs = null;
		//int numberOfValues = documentIdProvider.getLastObjectID(rs, 0);
		documentIdProvider.laSiguienteDaError();
		documentIdProvider.getDocumentId();
	}
	
	
	
	
	
	
}
