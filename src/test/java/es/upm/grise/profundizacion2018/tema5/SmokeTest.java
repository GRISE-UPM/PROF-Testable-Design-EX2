package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class SmokeTest {

	//	@Test
	//	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
	//		
	//		Document d = new Document();
	//		d.setTemplate("DECLARATION");
	//		d.setTitle("A");
	//		d.setAuthor("B");
	//		d.setBody("C");
	//		assertEquals("DOCUMENT ID: 1115\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	//
	//	}
	@Before public void initialize() {
		//		 Properties prop = new Properties();
		//			prop.setProperty("url", "");
		//			prop.setProperty("username", "");
		//			prop.setProperty("password", "");
	}
	
	// PRUEBA UNITARIA Comprobación de que se genera bien el template
	@Test
	public void standardTemplateCreation() {
		new TemplateFactory();
		String template = TemplateFactory.getTemplate("DECLARATION");
		String expected = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		assertEquals(template,expected );
	}
	// PRUEBA UNITARIA Con propiedades de configuración erroneas
	@Test(expected = NonRecoverableError.class)
	public void badProps() throws NonRecoverableError{
		String driver = DocumentIdProvider.DRIVER;
		DocumentIdProvider.getInstance(driver).readProp("badProps");
	}
	// PRUEBA UNITARIA Con driver errorneo
	@Test(expected = NonRecoverableError.class)
	public void badDriver() throws NonRecoverableError {
		String driver = DocumentIdProvider.DRIVER;
		Properties goodProps = new Properties();
		goodProps.setProperty("url", "new");
		goodProps.setProperty("username", "magasuan");
		goodProps.setProperty("password", "123");
		DocumentIdProvider.getInstance(driver).readFromDB("badDriver", goodProps);
	}

}
