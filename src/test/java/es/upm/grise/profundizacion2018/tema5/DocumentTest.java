package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

public class DocumentTest {
	
	private static Document d1, d2, d3;
	private static DocumentIdProvider provider;
	
	public static class DocumentIdProviderDoubleDocumentTest extends DocumentIdProvider {
		
		@Override
		public DocumentIdProvider getInstance() throws NonRecoverableError {
			if (instance != null)
				return instance;
			else {
				instance = new DocumentIdProviderDoubleDocumentTest();
				return instance;
			}	
		}
		
		protected DocumentIdProviderDoubleDocumentTest() throws NonRecoverableError {
			super();
		}
		
		protected void connectToDB() throws NonRecoverableError {
			documentId = 1115;
		}
		
		@Override
		protected int updateCounter() throws SQLException {
			return 1;
		}
		
		@Override
		public int getDocumentId() throws NonRecoverableError {
			return documentId++;
		}
		
	}
	
	@BeforeClass
	public static void initialize() throws NonRecoverableError {
		provider = new DocumentIdProviderDoubleDocumentTest().getInstance();
		d1 = new Document();
		d1.loadProvider(provider);
		d2 = new Document();
		d2.loadProvider(provider);
		d3 = new Document();
		d3.loadProvider(provider);
	}
	
	@Test
	// La aplicación genera las plantillas correctamente
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		assertEquals("DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s", TemplateFactory.getTemplate("DECLARATION"));
	}
	
	@Test
	// La aplicación asigna el número de documento correcto.
	public void documentIDCorrect() throws NonRecoverableError, RecoverableError {
		d2.setTemplate("DECLARATION");
		d2.setTitle("A");
		d2.setAuthor("B");
		d2.setBody("C");
		assertEquals("DOCUMENT ID: 1116\n\nTitle : A\nAuthor: B\n\nC", d2.getFormattedDocument());
	}
	
	@Test
	// Los números de documento asignados a documentos consecutivos son también números consecutivos.
	public void documentIDConsecutive() throws NonRecoverableError, RecoverableError {
		assertEquals((int)d2.getDocumentId()-(int)d1.getDocumentId(), 1);
		assertEquals((int)d3.getDocumentId()-(int)d2.getDocumentId(), 1);
	}
}
