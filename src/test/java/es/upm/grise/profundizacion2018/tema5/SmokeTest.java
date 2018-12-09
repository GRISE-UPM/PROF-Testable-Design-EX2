package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class SmokeTest {

 
	@Test(expected = NonRecoverableError.class)
	public void DriverOK() throws NonRecoverableError {
		DocumentIdProvider.getInstance(DocumentIdProvider.DRIVER).loadFromDB("driverValido",  new Properties());

	}
	
	@Test(expected = NonRecoverableError.class)
	public void DriverNoOK() throws NonRecoverableError {
		Properties Properties = new Properties();
		Properties.setProperty("url", "");
		Properties.setProperty("username", "admin");
		Properties.setProperty("password", "admin");
		DocumentIdProvider.getInstance(DocumentIdProvider.DRIVER).loadFromDB("driverNoValido", Properties);

	}

	@Test(expected = NonRecoverableError.class)
	public void PropsErroneas() throws NonRecoverableError{
		DocumentIdProvider.getInstance(DocumentIdProvider.DRIVER).loadProp("PropiedadesErroneas");
	}

	@Test
	public void DocCorrecto() {
		new TemplateFactory();
		assertEquals(TemplateFactory.getTemplate("DECLARATION"),"DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s" );
	}
	
	@Test
	public void DocErroneo() {
		new TemplateFactory();
		assertNotSame(TemplateFactory.getTemplate("DECLARATION"),"DOCUMENT ID: titulo : %s\nAuthor: autor" );
	}

}
