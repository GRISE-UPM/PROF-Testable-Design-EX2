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

    protected DocumentIdProviderDouble(){
        super();
    }
    
    public void setStartingId(int id){
        this.documentId = id;
    }

    @Override
    public int getDocumentId() throws NonRecoverableError {
        documentId++;
        return documentId;
    }
}
