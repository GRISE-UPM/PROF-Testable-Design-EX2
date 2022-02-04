package es.upm.grise.profundizacion.HandleDocuments;


import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;

public class TestableDesignTests {

    @BeforeAll
    static public void beforeAll() {
        System.out.println("Starting Testable Design tests");
    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        System.out.println("Start... " + testInfo.getDisplayName());
    }

    @DisplayName("Test2: La aplicación asigna el número de documento correcto.")
    @Test
    public void documentNumberId() throws NonRecoverableError {

        DocumentIdProviderDouble documentIdProviderDouble = new DocumentIdProviderDouble(1234);
        Document d = new Document(documentIdProviderDouble);
        assertEquals(1234, d.getDocumentId());
    }

    @DisplayName("Test3: Los números de documento asignados a documentos consecutivos son también números consecutivos.")
    @Test
    public void documentNumberIdConsecutive() throws NonRecoverableError {

        DocumentIdProviderDouble documentIdProviderDouble = new DocumentIdProviderDouble(1234);
        Document d = new Document(documentIdProviderDouble);
        Document e = new Document(documentIdProviderDouble);

        assertEquals(1234, d.getDocumentId());
        assertEquals(1235, e.getDocumentId());
    }

    @DisplayName("Test4: La aplicación detecta correctamente que el fichero de configuración no existe.")
    @Test
    public void noConfigFileFound() {

    }

    @DisplayName("Test5: La aplicación detecta correctamente que el driver MySQL no existe.")
    @Test
    public void noMySQLDriverFound() {

    }

    @DisplayName("Test6: La aplicación detecta correctamente que hay más de una fila en la tabla Counters.")
    @Test
    public void countersTableMoreThanOneRow() {

    }

    @DisplayName("Test7: La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.")
    @Test
    public void countersTableNotUpdated() {

    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        System.out.println("Finished... " + testInfo.getDisplayName());
    }

    @AfterAll
    static public void closeSubscriptionServiceTest(){
        System.out.println("Finished Testable Design tests");
    }


}
