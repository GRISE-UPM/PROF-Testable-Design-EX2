/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CONNECTION_LOST;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;

/**
 *
 * @author Bartomeu Ramis
 */
public class DocumentIdProviderDouble extends DocumentIdProvider {

    protected DocumentIdProviderDouble() throws NonRecoverableError {
        super();
    }

    @Override
    protected String getPath() throws NonRecoverableError {
        return "path";
    }
    
    @Override
    protected Properties loadProperties(String path) throws NonRecoverableError {
    return null;
    }

    @Override
    protected void loadBDDriver(Properties propertiesInFile) throws NonRecoverableError {
    }

    @Override
    protected void connectDB(Properties propertiesInFile) throws NonRecoverableError {
        connection = null;
        System.out.println("Connected to DataBase!");
    }

    @Override
    protected ResultSet readTable(String query) throws NonRecoverableError {
        ResultSet rs = null;
        System.out.println("Table read!");
        return rs;
    }

    @Override
    protected void getObjectId(ResultSet resultSet) throws NonRecoverableError {
        documentId = 1622;
        System.out.println("Loaded object ID: " + documentId + "!");
    }

    @Override
    protected void closeDBConnections(ResultSet resultSet) throws NonRecoverableError {
        System.out.println("DataBase connection closed!");
    }

    @Override
    protected int updateIdCounter(String query) throws NonRecoverableError {
        System.out.println("Id counter updated!");
        return 1;
    }
    
    @Override
    public int getDocumentId() throws NonRecoverableError {

        documentId++;
        return documentId;
    }
}
