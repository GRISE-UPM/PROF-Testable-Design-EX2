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
	// Se comprueba que se genera con un driver erroneo pero propiedades correctas
	@Test(expected = NonRecoverableError.class)
	public void ConfigDriverBad() throws NonRecoverableError {
		Properties Properties = new Properties();
		Properties.setProperty("url", "");
		Properties.setProperty("username", "");
		Properties.setProperty("password", "");
		DocumentIdProvider.getInstance(DocumentIdProvider.DRIVER).loadFromDB("notvaliddriver", Properties);
		
	}
	// Se comprueba que se genara con propiedades erroneas
	@Test(expected = NonRecoverableError.class)
	public void ConfigPropsBad() throws NonRecoverableError{
		DocumentIdProvider.getInstance(DocumentIdProvider.DRIVER).loadProp("badProps");
	}
	// Una vez que hemos comprobado lo que no se comprueba correctamente, comprobamos que se genera bien 
	@Test
	public void standardTemplateCreation() {
		String template =  new TemplateFactory().getTemplate("DECLARATION");
		String expected = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		assertEquals(template,expected );
	}



}
