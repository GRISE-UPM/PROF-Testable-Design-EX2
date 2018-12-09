package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class SmokeTest {

	@Before public void initialize() {
		//		 Properties prop = new Properties();
		//			prop.setProperty("url", "");
		//			prop.setProperty("username", "");
		//			prop.setProperty("password", "");
	}


	/* 4. Realizar las siguientes pruebas:
			a. La aplicación genera las plantillas correctamente.
			b. La aplicación asigna el número de documento correcto.
			c. Los números de documento asignados a documentos consecutivos son también números consecutivos.
		5. Realizar adicionalmente las siguientes pruebas:
			a. La aplicación detecta correctamente que el fichero de configuración no existe.
			b. La aplicación detecta correctamente que el driver MySQL no existe.
			c. La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
			d. La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
	 */
	
	// 4ab La aplicación genera las plantillas correctamente y asigna el numero correcto.
	@Test
	public void templateCorrecto() {
		new TemplateFactory();
		String template = TemplateFactory.getTemplate("DECLARATION");
		String expected = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		assertEquals(template,expected );
	}
	// 4c La aplicación asigna el número de documento correcto.
	@Test
	public void consecutivos() throws NonRecoverableError, RecoverableError {
		//No conseguido.
	}

	// Ejercicio 5
	// La aplicación detecta correctamente que el fichero de configuración no existe.
	@Test(expected = NonRecoverableError.class)
	public void badProps() throws NonRecoverableError{
		String driver = DocumentIdProvider.DRIVER;
		DocumentIdProvider.getInstance(driver).readProp("badProps");
	}
	// La aplicación detecta correctamente que el driver MySQL no existe.
	@Test(expected = NonRecoverableError.class)
	public void badDriver() throws NonRecoverableError {
		String driver = DocumentIdProvider.DRIVER;
		Properties goodProps = new Properties();
		goodProps.setProperty("url", "new");
		goodProps.setProperty("username", "magasuan");
		goodProps.setProperty("password", "123");
		DocumentIdProvider.getInstance(driver).readFromDB("badDriver", goodProps);
	}
	//UPdate 


}
