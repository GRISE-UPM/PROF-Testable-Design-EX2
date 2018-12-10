package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;
import java.util.Properties;
//import org.junit.Before;
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


@Test
public void plantillaCorrecta() {
		new TemplateFactory();
		String template = TemplateFactory.getTemplate("DECLARATION");
		String expected = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		assertEquals(template,expected );
	}

@Test(expected = NonRecoverableError.class)
public void driverCorrecto() throws NonRecoverableError {
	String driver = DocumentIdProvider.DRIVER;
	Properties goodProps = new Properties();
	DocumentIdProvider.getInstance(DocumentIdProvider.DRIVER).readFromDB(driver, goodProps);
	}

@Test
public void documentCorrecto() {
	new TemplateFactory();
	assertEquals(TemplateFactory.getTemplate("DECLARATION"),"DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s" );
}

@Test(expected = NonRecoverableError.class)
public void testErrorProperty() throws NonRecoverableError{
	String driver = DocumentIdProvider.DRIVER;
	DocumentIdProvider.getInstance(driver).readProp("Null");
}

@Test(expected = NonRecoverableError.class)
public void testErrorDriver() throws NonRecoverableError {
	String driver = DocumentIdProvider.DRIVER;
	Properties goodProps = new Properties();
	goodProps.setProperty("url", "hola");
	goodProps.setProperty("username", "admin");
	goodProps.setProperty("password", "admin");
	DocumentIdProvider.getInstance(driver).readFromDB("Null", goodProps);
	}

@Test
public void testErrorDocument() {
	new TemplateFactory();
	assertNotSame(TemplateFactory.getTemplate("DECLARATION"),"DOCUMENT ID: titulo : %s\nAuthor: autor" );
	}
}
