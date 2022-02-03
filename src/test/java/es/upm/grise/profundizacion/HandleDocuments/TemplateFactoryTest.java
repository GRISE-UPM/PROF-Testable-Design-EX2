package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TemplateFactoryTest {

	@InjectMocks
	TemplateFactory templateFactory;

	@Test
	public void getTemplateTest() throws NonRecoverableError, RecoverableError {
		assertEquals("DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s", templateFactory.getTemplate("DECLARATION"));
	}

}
