package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmokeTest {
	
	@Test
	public void plantillaCorrecta() throws NonRecoverableError, RecoverableError {
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		int ID=(int) d.getDocumentId();
		assertEquals("DOCUMENT ID: "+ID+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	@Test
	public void numeroDocumentoCorrecto() throws NonRecoverableError, RecoverableError{
		int id_esperado = new DocumentIdProvider().getInstance().getDocumentId()+1;
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: "+id_esperado+"\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
		
	}
	
	@Test
	public void numeroDocumentoConsecutivoCorrecto() throws NonRecoverableError{
		Document d1 = new Document();
		Document d2 = new Document();
		int res = (int)d2.getDocumentId()-(int)d1.getDocumentId();
		assertEquals(1,res);
	}
	
	@Test
	public void ficheroNoExisteCorrecto() throws NonRecoverableError{
		DocumentIdProvider d = new DocumentIdProvider().getInstance();
		try{
			d.checkfile("no_existe");
		}catch(NonRecoverableError e){
			assertEquals(Error.NON_EXISTING_FILE.getMessage(),e.getMessage());
		}
	}
	
	@Test
	public void driverNoExisteCorrecto() throws NonRecoverableError{
		DocumentIdProvider d = new DocumentIdProvider().getInstance();
		try{
			d.checkDriver("no_existe");
		}catch(NonRecoverableError e){
			assertEquals(Error.CANNOT_FIND_DRIVER.getMessage(),e.getMessage());
		}
	}
	
	
	
	

}
