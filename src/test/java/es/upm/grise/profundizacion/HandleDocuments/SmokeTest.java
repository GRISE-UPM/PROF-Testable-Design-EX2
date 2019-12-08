package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;
import java.io.InputStream;
import java.util.Properties;
import org.junit.Test;
import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {

	//La aplicación genera las plantillas correctamente.
 	@Test
 	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {

		Document d = new Document();
		d.setTemplate("DECLARATION");
 		d.setTitle("A");
 		d.setAuthor("B");
 		d.setBody("C");
 		assertEquals( "DOCUMENT ID: 4\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
 	}
 	
 	//La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
 	@Test(expected = NonRecoverableError.class)
 	public void falloEnCountersCuandoActualiza() throws NonRecoverableError{
 		DocumentIdProviderDouble prueba = new DocumentIdProviderDouble();
 		prueba.getDocumentId();
 		prueba.getDocumentId();
 		prueba.getDocumentId();
 		prueba.getDocumentId();
 		prueba.getDocumentId();
 		prueba.getDocumentId();	
 		prueba.getDocumentId();

 	}
 	//La aplicación detecta correctamente que el driver MySQL no existe.
 	@Test(expected = NonRecoverableError.class)
 	public void noExisteDriver() throws NonRecoverableError{
 		DocumentIdProviderDouble prueba = new DocumentIdProviderDouble();
 		prueba.loadDriver();
 	}
 	
 	//La aplicación asigna el número de documento correcto.
 	@Test
 	public void documentoConNumeroValido() throws NonRecoverableError, RecoverableError {
 		int id_solucion = 1;
 		Document prueba1 = new Document();
 		int id_docuemnto = prueba1.getDocumentId();
 		assertEquals( id_solucion, id_docuemnto);
 	}
 	
    //La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
  	@Test(expected = NonRecoverableError.class)
  	public void tablaCountersVariasFilas() throws NonRecoverableError{
  		DocumentIdProviderDouble prueba = new DocumentIdProviderDouble();
  		int resultado = prueba.getlastObjectId();
  				
  	}
 	//Los números de documento asignados a documentos consecutivos son también números consecutivos.
 	@Test
 	public void documentosConsecutivosAsignacion() throws NonRecoverableError{

 		Document primero = new Document();
 		int id_primero = primero.getDocumentId();
 		Document segundo = new Document();
 		int id_segundo = segundo.getDocumentId();
 		int comprobar = id_segundo-1;
 		assertEquals( id_primero, comprobar);
 	}
 	
 	//La aplicación detecta correctamente que el fichero de configuración no existe.
 	@Test(expected = NonRecoverableError.class)
 	public void noExisteFicheroConf() throws NonRecoverableError{
 		DocumentIdProviderDouble prueba = new DocumentIdProviderDouble();
 		InputStream inputFile = null;
 		Properties propertiesInFile = new Properties();
 		prueba.getProperties(propertiesInFile, inputFile);
 	}




}