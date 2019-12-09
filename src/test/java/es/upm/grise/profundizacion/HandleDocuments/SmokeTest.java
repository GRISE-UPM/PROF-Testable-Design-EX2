package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	private static DocumentDouble document1;
	private static DocumentDouble document2;
	private static DocumentIdProviderDouble2 provider;
	private final int DOCID0 = 0, DOCID1 = 1;
	
	private final static String TEMPLATE = "DECLARATION";
	private final static String TITLE = "TEST";
	private final static String AUTHOR ="ANTONIO";
	private final static String BODY = "BODYTEST";
	
	private static DocumentDouble generateDocument() {
		DocumentDouble document = new DocumentDouble();
	
		//Establece las opciones del documento
		document.setTemplate(TEMPLATE);
		document.setTitle(TITLE);
		document.setAuthor(AUTHOR);
		document.setBody(BODY);
		
		return document;

	}
	
	@BeforeClass
    public static void init() {
		document1 = generateDocument();
		document2 = generateDocument();
		provider = new DocumentIdProviderDouble2();
	}
      
	@Test
	//Prueba si la plantilla se genera correctamente
	public void generaPlantillaCorrectamente() throws NonRecoverableError, RecoverableError {
		final String expectedOutcome = "DOCUMENT ID: "+DOCID0+"\n\nTitle : "+TITLE+"\nAuthor: "+AUTHOR+"\n\n"+BODY;
		assertEquals(expectedOutcome, document1.getFormattedDocument());
	}
	
	@Test
	//Prueba si genera correctamente el numero de ID
	public void generaIdDocumentoCorrecto() {
		assertEquals(DOCID0,document1.getDocumentId());
	}
	
	@Test
	//Prueba si genera ids de Documentos correlativos
	public void generaIdDocumentosCorrelativos() {
		assertEquals(DOCID0+DOCID1,(int)document1.getDocumentId()+(int)document2.getDocumentId());
	}
	
	@Test (expected=NonRecoverableError.class)
	//Prueba que no existe el fichero de configuracion
	public void noExisteFicheroCFG() throws NonRecoverableError {
		provider.GetPropertyFile("/error/");
	}
	
	@Test (expected=NonRecoverableError.class)
	//Prueba que no existe el fichero de configuracion
	public void noDetectaDriverSQL() throws NonRecoverableError {
		provider.LoadBBDD_Driver("driver_inexistente");
	}
	
	@Test 
	//Prueba que existe mas de 1 fila de Counters
	public void detectaMasDeUnaFila() throws NonRecoverableError {
		assertTrue(provider.GetLastObjectID()>0);
	}
	
	@Test (expected=NonRecoverableError.class)
	//Prueba que existe mas de 1 fila de Counters
	public void actualizacionErroneaCounters() throws NonRecoverableError {
		provider.getDocumentId();
	}
	
}
