package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentFactoryTest {
    @Mock
    private DocumentIdProvider documentIdProvider;

    private DocumentFactory documentFactory;

    @Before
    public void setup() {
        documentFactory = new DocumentFactory(documentIdProvider);
    }

    // 3.c Los números de documento asignados a documentos consecutivos son también números consecutivos
    @Test
    public void testCreateDocument() throws Exception {
        when(documentIdProvider.getDocumentId()).thenReturn(0).thenReturn(1);

        assertEquals(0, documentFactory.createDocument().getDocumentId());
        assertEquals(1, documentFactory.createDocument().getDocumentId());
    }
}