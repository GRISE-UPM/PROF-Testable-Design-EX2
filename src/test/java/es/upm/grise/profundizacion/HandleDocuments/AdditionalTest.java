package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.Test;

public class AdditionalTest {

	@Test(expected = NonRecoverableError.class)
	public void noExistConfigProperties() throws IOException, NonRecoverableError {
		InputStream st = Mockito.mock(InputStream.class); 
		Properties prop = Mockito.mock(Properties.class);
		prop.load(st);
		
		DocumentIdProvider di = new DocumentIdProvider();
		di.checkConfigFile(st, "abdc", prop);
		
	    Mockito.verify(prop, Mockito.times(1)).load(st);

		
	}
}
