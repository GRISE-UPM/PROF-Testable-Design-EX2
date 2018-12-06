package es.upm.grise.profundizacion2018.tema5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TemplateFactoryTest {

	@Test
	public void standardTemplateCreation() {
		String template = new TemplateFactory().getTemplate("DECLARATION");
		assertEquals(template, "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s");
	}
}
