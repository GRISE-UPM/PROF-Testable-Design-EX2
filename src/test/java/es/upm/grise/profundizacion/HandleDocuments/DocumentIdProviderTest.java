package es.upm.grise.profundizacion.HandleDocuments;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentIdProviderTest extends TestCase {

    @Mock
    private MySQLHelper mySQLHelper;
    private DocumentIdProvider documentIdProvider;

    @Before
    public void setup() {
        documentIdProvider = new DocumentIdProvider(mySQLHelper);
    }

    @Test
    public void testGetDocumentId() throws Exception {
        when(mySQLHelper.getLastDocumentId()).thenReturn(0);

        assertEquals(1, documentIdProvider.getDocumentId());

        verify(mySQLHelper).updateLastDocumentId(1);
    }
}