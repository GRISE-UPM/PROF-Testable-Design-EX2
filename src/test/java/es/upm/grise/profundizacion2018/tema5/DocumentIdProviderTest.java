package es.upm.grise.profundizacion2018.tema5;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentIdProviderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void check_enviroment_does_not_exist_exception() throws NonRecoverableError, IOException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(null);

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.UNDEFINED_ENVIRON.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_config_does_not_exists_exception() throws NonRecoverableError, IOException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn("C:");

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);


        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.NON_EXISTING_FILE.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_config_cannot_be_readed() throws NonRecoverableError, IOException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenThrow(IOException.class);


        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CANNOT_READ_FILE.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_cannot_instatiate_driver_exception() throws IOException, NonRecoverableError {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        doThrow(new NonRecoverableError(Error.CANNOT_INSTANTIATE_DRIVER.getMessage())).when(mockDatabaseProvider).openConnection(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CANNOT_INSTANTIATE_DRIVER.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_cannot_find_driver_exception() throws IOException, NonRecoverableError {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        doThrow(new NonRecoverableError(Error.CANNOT_FIND_DRIVER.getMessage())).when(mockDatabaseProvider).openConnection(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CANNOT_FIND_DRIVER.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_cannot_connect_database_exception() throws IOException, NonRecoverableError {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        doThrow(new NonRecoverableError(Error.CANNOT_CONNECT_DATABASE.getMessage())).when(mockDatabaseProvider).openConnection(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CANNOT_CONNECT_DATABASE.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_cannot_run_query_exception() throws IOException, NonRecoverableError {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        doThrow(new NonRecoverableError(Error.CANNOT_RUN_QUERY.getMessage())).when(mockDatabaseProvider).executeQuery(Matchers.anyString());

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CANNOT_RUN_QUERY.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_incorrect_counter_exception() throws IOException, NonRecoverableError, SQLException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        ResultSet mockResult = mock(ResultSet.class);
        when(mockResult.next()).thenThrow(new SQLException());

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.INCORRECT_COUNTER.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_corrupted_counter_exception() throws IOException, NonRecoverableError, SQLException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        ResultSet mockResult = mock(ResultSet.class);
        when(mockResult.next()).thenReturn(false);

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);

        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CORRUPTED_COUNTER.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_connection_lost_exception() throws IOException, NonRecoverableError, SQLException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        ResultSet mockResult = mock(ResultSet.class);
        when(mockResult.next()).thenAnswer(new Answer<Boolean>() {

            boolean firstTime = true;
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                    if(firstTime) {
                        firstTime = false;
                        return true;
                    }
                return firstTime;
            }
        });
        when(mockResult.getInt(Matchers.anyString())).thenReturn(10);

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);
        doThrow(new NonRecoverableError(Error.CONNECTION_LOST.getMessage())).when(mockDatabaseProvider).closeStatement();
        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CONNECTION_LOST.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
    }

    @Test
    public void check_corrupted_counter_in_update_exception() throws IOException, NonRecoverableError, SQLException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        ResultSet mockResult = mock(ResultSet.class);
        when(mockResult.next()).thenAnswer(new Answer<Boolean>() {

            boolean firstTime = true;
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                if(firstTime) {
                    firstTime = false;
                    return true;
                }
                return firstTime;
            }
        });
        when(mockResult.getInt(Matchers.anyString())).thenReturn(10);

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);
        when(mockDatabaseProvider.executeUpdateQuery(Matchers.anyString(), Matchers.anyInt())).thenReturn(0);
        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ASSERT
        thrown.expect(NonRecoverableError.class);
        thrown.expectMessage(Error.CORRUPTED_COUNTER.getMessage());

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
        documentIdProvider.getDocumentId();
    }

    @Test
    public void check_get_document_id_is_next() throws IOException, NonRecoverableError, SQLException {
        //ARRANGE
        EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
        when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

        ResultSet mockResult = mock(ResultSet.class);
        when(mockResult.next()).thenAnswer(new Answer<Boolean>() {

            boolean firstTime = true;
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                if(firstTime) {
                    firstTime = false;
                    return true;
                }
                return firstTime;
            }
        });
        when(mockResult.getInt(Matchers.anyString())).thenReturn(10);

        DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
        when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);
        when(mockDatabaseProvider.executeUpdateQuery(Matchers.anyString(), Matchers.anyInt())).thenReturn(1);
        ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

        Properties mockProperties = new Properties();
        mockProperties.put("url", "url");
        mockProperties.put("username", "username");
        mockProperties.put("password", "password");
        when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

        //ACT
        DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
        int documentId = documentIdProvider.getDocumentId();

        //ASSERT
        assertEquals(documentId, 11);
    }


}
