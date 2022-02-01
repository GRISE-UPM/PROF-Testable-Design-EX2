package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmokeTest {

    DocumentIdProviderDouble dp;

    public SmokeTest() throws NonRecoverableError {
        dp = new DocumentIdProviderDouble();
        dp.setStartingId(1622);
    }

    @Test
    public void idAssignationCorrect() throws NonRecoverableError, RecoverableError {
        int id = 100;
        dp.setStartingId(id);
        Document d = new Document(dp.getDocumentId());
        assertEquals("The assignation of the docID is correct.", (int) d.getDocumentId(), id+1);
    }

    @Test
    public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {

        Document d = new Document(dp.getDocumentId());
        d.setTemplate("DECLARATION");
        d.setTitle("A");
        d.setAuthor("B");
        d.setBody("C");
        assertEquals("The format of the document is correct", "DOCUMENT ID: " + d.getDocumentId() + "\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());

    }

    @Test
    public void consecutiveDocumentIDTest() throws NonRecoverableError, RecoverableError {

        Document d = new Document(dp.getDocumentId());
        Document d2 = new Document(dp.getDocumentId());

        assertEquals("Consecutive Id assigantion is correct", (int) d.getDocumentId() + 1, d2.getDocumentId());
    }

    @Test
    public void noConfigurationFileTest() throws NonRecoverableError {
        assertThrows("No configuration file has been found", NonRecoverableError.class, () -> dp.loadProperties("example/path/file"));
    }
    
     @Test
    public void noDriverToLoadTest() throws NonRecoverableError {
        assertThrows("No properties file to load driver from found", NonRecoverableError.class, () -> dp.loadProperties("fake/path"));
    }
    
    @Test
    public void ResultSetErrorTest() throws NonRecoverableError, SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        when(rs.getInt("documentId")).thenReturn(1);
        assertThrows("Error when reading ResultSet", NonRecoverableError.class, () -> dp.getObjectId(rs));
    }
}
