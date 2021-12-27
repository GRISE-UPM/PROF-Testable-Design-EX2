package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentIdProviderTest {



	@Test
	public void CorrectDocumentId() throws NonRecoverableError {
		// Este también chequea el punto --> d.	La aplicación detecta correctamente que la actualización del documentID en
		// la tabla Counters ha sido incorrectamente realizado

		DocumentIdProvider t = DocumentIdProvider.getInstance();

		int expectedId = 57899;

		assertEquals(expectedId, t.getDocumentId(expectedId-1));
	}

	@Test
	public void ConsecutiveDocumentId() throws NonRecoverableError {
		
		DocumentIdProvider t = DocumentIdProvider.getInstance();

		int OneId = t.getDocumentId(t.getActualDocumentId());

		assertEquals(OneId+1, t.getDocumentId(t.getActualDocumentId()));
	}

	@Test
	public void FileNotFound() {

		try{
			new DocumentIdProvider("PathQueNoExiste", null, null, null, null);

		}catch (NonRecoverableError e){
			assertEquals(e.getType(),"FileNotFoundException");
		}
	}


	@Test
	public void DBDriverNotFound() {

		try{
			new DocumentIdProvider("/Users/manuelgonzalezcosta/Documents/Master/profundizacion/PROF-Testable-Design/",
					"DriverQueNoExiste", null, null, null);

		}catch (NonRecoverableError e){
			assertEquals(e.getType(),"ClassNotFoundException");
		}
	}



	@Test
	public void DuplicatedCounters() {

		try{
			new DocumentIdProvider("/Users/manuelgonzalezcosta/Documents/Master/profundizacion/PROF-Testable-Design/",
					"DriverQueNoExiste", "DBWithDuplicatedCounters", "User", "Password");

		}catch (NonRecoverableError e){
			assertEquals(e.getType(),"CORRUPTED_COUNTER");
		}
	}




}
