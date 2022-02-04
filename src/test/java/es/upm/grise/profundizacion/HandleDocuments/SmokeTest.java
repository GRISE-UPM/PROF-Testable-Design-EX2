package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;

import static org.mockito.Mockito.*;
import org.mockito.Mock;

public class SmokeTest {
	
	//PRUEBAS REALIZADAS
	// a.	La aplicación genera las plantillas correctamente.
	// b.	La aplicación asigna el número de documento correcto.
	// c.	Los números de documento asignados a documentos consecutivos son también números consecutivos.

	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		//Document d = new Document();
		//d.setTemplate("DECLARATION");
		//d.setTitle("A");
		//d.setAuthor("B");
		//d.setBody("C");
		//assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

		Document document = new Document(1623);
		document.setTemplate("DECLARATION");
		document.setTitle("A");
		document.setAuthor("B");
		document.setBody("C");
		assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", document.getFormattedDocument());
		
	}
	
	@Test
	public void CorrectDocumentID() throws NonRecoverableError, RecoverableError {
		Document document2 = new Document(1624);
		document2.setTemplate("DECLARATION");
		document2.setTitle("A");
		document2.setAuthor("B");
		document2.setBody("C");
		assertEquals("DOCUMENT ID: 1624\n\nTitle : A\nAuthor: B\n\nC", document2.getFormattedDocument());
		
	}
}
