package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito; 
import org.powermock.core.classloader.annotations.PrepareForTest; 
import org.powermock.modules.junit4.PowerMockRunner; 

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "es.upm.grise.profundizacion2018.tema5.*")
public class SmokeTest {
	
	@Before public void initialize() {
		// Initial test fails because the APP_HOME variable is not defined. We will define it
		// https://stackoverflow.com/questions/318239/how-do-i-set-environment-variables-from-java
		final String currentDir = System.getProperty("user.dir") + "/";
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.getenv("APP_HOME")).thenReturn(currentDir); 
	}
	
	// TESTS
	
	// 4a - La aplicación genera las plantillas correctamente
	// 4b - La aplicación asigna el número de documento correcto
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		// We need to mock System to bypass the APP_HOME environment variable.
	
		Document d = new Document();		
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		d.setDocumentId(1115);
		
		assertEquals("DOCUMENT ID: 1115\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	
	// 4c - Los números de documento asignados a documentos consecutivos son también numeros consecutivos.
	
	@Test
	public void checkIfConsecutiveDocumentsGetConsecutiveNumbers() throws NonRecoverableError {
		Document docA = new Document("Declaration", "A", "B", "C");
		Document docB = new Document("Declaration", "A", "B", "C");
		assertEquals(docA.getDocumentId()+1,docB.getDocumentId());
	}
	
	
	// 5a La aplicación detecta correctamente que el fichero de configuración no existe
	
	@Test(expected = NonRecoverableError.class)
	public void checkIfConfigurationFileExists() throws NonRecoverableError {
		 // Easiest way without 
        DocumentIdProvider doc = new DocumentIdProvider();
        doc.connectToBBDD(null);
        
	}
	
	// 5b La aplicación detecta correctamente que el driver MySQL no existe.
	
	@Test(expected = NonRecoverableError.class)
	public void checkIfMySQLDriverExists() throws NonRecoverableError {
		 // Easiest way without 
        DocumentIdProvider doc = new DocumentIdProvider();
        doc.loadDDBBDriver("none");
        
	}
	
	// 5c La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
	
	@Test(expected = NonRecoverableError.class)
	public void checkForMoreThanOnereturn() throws NonRecoverableError {
		 // Easiest way without 
        DocumentIdProvider doc = new DocumentIdProvider();
        doc.checkNumberOfvalues(2);
        
	}
	
	// 5d La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
	
	@Test(expected = NonRecoverableError.class)
	public void checkCorrectUpdates() throws NonRecoverableError {
		 // Easiest way without 
        DocumentIdProvider doc = new DocumentIdProvider();
        doc.checkUpdateCompleted(2);
        
	}
}
