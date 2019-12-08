package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

public class SmokeTest {
	
	
	
	
	
	@Test
	public void formatoPlantillaCorrecto() throws NonRecoverableError, RecoverableError {
		
		Document documento = new Document();
		documento.setTemplate("DECLARATION");
		documento.setTitle("A");
		documento.setAuthor("B");
		documento.setBody("C");
		assertEquals("DOCUMENT ID: "+documento.getDocumentId()+"\n\nTitle : A\nAuthor: B\n\nC", documento.getFormattedDocument());

	}
	
	
	/*Este es el test mas molesto, puesto que se desea comprobar el correcto funcionamiento de los ID procedentes de la Base
	  de datos, es necesario actualizar la variable "resultado" en cada ejecución del test (sumar 4 al valor anterior).
	  Mi consejo:
	  Realizar este test de manera local y desactivarlo (@Ignore) cuando se envie al Jenkins, ya que predecir el valor que
	  debe tener la variable en el test de Jenkins puede ser complicado.
	 */
	@Test
	public void numeroCorrecto() throws NonRecoverableError, RecoverableError {
		int resultado=280;
		Document documento = new Document();
		System.out.println(documento.getDocumentId());
		assertEquals(resultado,documento.getDocumentId());

	}
	
	
	
	@Test
	public void numerosConsecutivos() throws NonRecoverableError, RecoverableError {
		
		Document documento1 = new Document();
		int Id1=documento1.getDocumentId();
		Document documento2 = new Document();
		int Id2=documento2.getDocumentId();
		assertEquals(Id1+1,Id2);

	}
	
	@Test(expected = NonRecoverableError.class)
	public void ficheroDeConfiguracionNoExiste() throws NonRecoverableError{
		DocumentIdProviderDouble documento = new DocumentIdProviderDouble();
		
		InputStream file = null;
		
		documento.ficheroProperties(file, "PathFalso");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void driverNoExiste() throws NonRecoverableError{
		DocumentIdProviderDouble documento = new DocumentIdProviderDouble();
		documento.cargarDriver();
	}
	
	
	@Test(expected = NonRecoverableError.class)
	public void masDeUnaFilaEnLaTablaCounters() throws NonRecoverableError{
		int valor=1;
		DocumentIdProviderDouble documento = new DocumentIdProviderDouble();
		documento.checkearID(valor);
	}
	

}
