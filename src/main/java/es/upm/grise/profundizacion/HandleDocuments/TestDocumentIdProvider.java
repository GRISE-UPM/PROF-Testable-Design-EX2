package es.upm.grise.profundizacion.HandleDocuments;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestDocumentIdProvider {

    @BeforeAll
    static public void beforeAll() {
        System.out.println("Executed before any test has been run");
    }

    @AfterAll
    static public void afterAll() {
        System.out.println("Executed after all tests have been run");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Executed before each test");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Executed after each test");
    }

    @Test
    public void FicherosConsecutivos() throws NonRecoverableError {
        DocumentIdProvider d = DocumentIdProvider.getInstance();

        int id = d.getDocumentId(d.getTrueId());
        assertEquals(id+1, d.getDocumentId(d.getTrueId()));
    }

    @Test
    public void FiheroNoEncontrado() {

        try {
            new DocumentIdProvider(null, "PATH", null, null, null);
        } catch (NonRecoverableError e) {
            assertEquals(e.getException(), "FileNotFound");
        }

    }
}
