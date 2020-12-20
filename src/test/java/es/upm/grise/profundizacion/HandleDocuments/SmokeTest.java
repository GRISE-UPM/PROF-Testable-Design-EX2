package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SmokeTest {
    private static final int DOCUMENT_ID = 1623;

    @Test
    public void formatTemplateCorrectly() throws Exception {
        Document d = new Document(DOCUMENT_ID);
        d.setTemplate("DECLARATION");
        d.setTitle("A");
        d.setAuthor("B");
        d.setBody("C");
        assertEquals("DOCUMENT ID: 1623\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
    }
}
