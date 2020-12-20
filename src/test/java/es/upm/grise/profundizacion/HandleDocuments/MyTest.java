package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class MyTest {
	
	@InjectMocks private DocumentIdProvider dbConnection;
	@Mock private Connection mockConnection;
	@Mock private PreparedStatement mockPStatement;
	
	@Before
	  public void setUp() {
	    MockitoAnnotations.initMocks(this);
	  }
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		//no se comprueba si el documento pone el documentId correctamente
		Document d = new Document();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		d.setTf(new TemplateFactory());
		assertEquals("DOCUMENT ID: 0\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

	}
	
	@Test
	public void checkIdDocumentCorrect() throws NonRecoverableError, SQLException {
		dbConnection.documentId=1;
		Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPStatement);
		mockPStatement.setInt(1, dbConnection.documentId);
		Mockito.when(mockPStatement.executeUpdate()).thenReturn(1);
	    int value = dbConnection.getDocumentId();
	    Assert.assertEquals(2, value);
	    Mockito.verify(mockConnection).prepareStatement(Mockito.anyString());
	    Mockito.verify(mockPStatement).executeUpdate();
	}
	
	@Test
	public void check2IdDocumentConsecutiveCorrect() throws NonRecoverableError, SQLException {
		dbConnection.documentId=1;
		Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPStatement);
		mockPStatement.setInt(1, dbConnection.documentId);
		Mockito.when(mockPStatement.executeUpdate()).thenReturn(1);
	    dbConnection.getDocumentId();
	    int value = dbConnection.getDocumentId();
	    Assert.assertEquals(3, value);

	    Mockito.verify(mockConnection, Mockito.times(2)).prepareStatement(Mockito.anyString());
	    Mockito.verify(mockPStatement, Mockito.times(2)).executeUpdate();
	}
	
	@Test
	public void noExistConfigProperties() {
		
		
		
	}
	
}
