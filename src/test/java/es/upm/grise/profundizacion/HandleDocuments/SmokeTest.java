package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;
import java.io.InputStream;
import java.util.Properties;




public class SmokeTest {
	
	//a). La aplicación genera las plantillas correctamente.
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {

		Document d = new Document();
		d.setTemplate("DECLARATION");
 		d.setTitle("A");
 		d.setAuthor("B");
 		d.setBody("C");
 		assertEquals( "DOCUMENT ID: 3\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	//g). La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
	@Test(expected = NonRecoverableError.class)
	public void actualizationInCountersIncorrect() throws NonRecoverableError {

		DocumentIdProviderDouble test_document = new DocumentIdProviderDouble();
		test_document.getDocumentId();
		test_document.getDocumentId();
		test_document.getDocumentId();
		test_document.getDocumentId();
		test_document.getDocumentId();
		test_document.getDocumentId();	
		test_document.getDocumentId();
	}
	
	//e). La aplicación detecta correctamente que el driver MySQL no existe.
	@Test(expected = NonRecoverableError.class)
	public void nonExistingDriver() throws NonRecoverableError {

		DocumentIdProviderDouble documentInstances = new DocumentIdProviderDouble();
		documentInstances.loadDriver("No existe");
	}
	
	//b). La aplicación asigna el número de documento correcto.
	@Test
	public void documentNumberCorrect() throws NonRecoverableError, RecoverableError {

		int correct_id = 4;
 		Document prueba1 = new Document();
 		int document_id = prueba1.getDocumentId();
 		assertEquals( correct_id, document_id);
	}
	
	//f). La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
	@Test(expected = NonRecoverableError.class)
	public void rowsInCounterTable() throws NonRecoverableError {

		DocumentIdProviderDouble test_document = new DocumentIdProviderDouble();
		int result = test_document.getlastObjectId();
	}
			
	
	//c). Los números de documento asignados a documentos consecutivos son también números consecutivos.
	@Test
	public void consecutiveDocumentIds() throws NonRecoverableError {

		Document first = new Document();
	 	int first_id = first.getDocumentId();
	 	Document second = new Document();
	 	int second_id = second.getDocumentId();
	 	int correct_id = second_id-1;
	 	assertEquals( first_id, correct_id);
	}
		
	//d). La aplicación detecta correctamente que el fichero de configuración no existe.
	@Test(expected = NonRecoverableError.class)
	public void nonExistingFile() throws NonRecoverableError {

		DocumentIdProviderDouble test_document = new DocumentIdProviderDouble();
	 	InputStream inputFile = null;
	 	Properties propertiesInFile = new Properties();
	 	test_document.getProperties(propertiesInFile, inputFile);
	}

}
