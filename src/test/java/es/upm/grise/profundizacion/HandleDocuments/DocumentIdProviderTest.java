package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.jupiter.api.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;

import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.stubbing.Answer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentIdProviderTest {
    DocumentIdProvider documentIdProvider;
    Connection connection;

    @BeforeEach
    public void setUp() throws NonRecoverableError {
        this.connection = mock(Connection.class);
        this.documentIdProvider = new DocumentIdProviderDouble(this.connection);
    }


    @Test
    public void getDocumentIdText() throws NonRecoverableError, SQLException {
        int actualCount = this.documentIdProvider.documentId;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(this.connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(actualCount + 1, this.documentIdProvider.getDocumentId());
    }

    @Test
    public void getDocumentIdDatabaseExceptionText() throws SQLException {
        when(this.connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(NonRecoverableError.class, () -> this.documentIdProvider.getDocumentId());
    }


    @Test
    public void getDocumentIdMoreThanOneRawText() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(this.connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(NonRecoverableError.class, () -> this.documentIdProvider.getDocumentId());
    }

    @Test
    public void readPropertiesNullPathTest() {
        assertThrows(NonRecoverableError.class, () -> this.documentIdProvider.readProperties(null));
    }

    @Test
    public void readPropertiesMissingFileTest() {
        assertThrows(NonRecoverableError.class, () -> this.documentIdProvider.readProperties("null"));
    }

    @Test
    public void readPropertiesMissingDriver() {
        Properties properties = mock(Properties.class);
        assertThrows(NonRecoverableError.class, () -> this.documentIdProvider.createDatabaseConnection(properties));
    }


}
