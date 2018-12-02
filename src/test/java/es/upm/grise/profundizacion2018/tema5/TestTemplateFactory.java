package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTemplateFactory {

	@Test
	public void generacionPlantillas() {
		TemplateFactory tf= new TemplateFactory();
		String plantilla=tf.getTemplate("DECLARATION");
		assertEquals("DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s",plantilla);
		
	}

}
