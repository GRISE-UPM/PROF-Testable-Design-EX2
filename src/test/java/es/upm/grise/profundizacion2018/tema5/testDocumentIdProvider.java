package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testDocumentIdProvider {
	 int DOCUMENT_ID;
	 Document d1;
	 Document d2;
	 Document d3;
	 DocumentIdProviderDouble docProvider;

	 
	 class DocumentIdProviderDouble extends DocumentIdProvider {
			
		    DocumentIdProviderDouble() throws NonRecoverableError {
			super();
			
		     }
            @Override
            void connect() throws NonRecoverableError {
			
			    set(DOCUMENT_ID);
			}
            
            @Override
            public int getDocumentId() throws NonRecoverableError {
            	 documentId++;
            	 return documentId;
            	
            }
            
        	public void set(int id) {
        		documentId=id;
        	}
        	
        	@Override
        	public  DocumentIdProvider getInstance() throws NonRecoverableError {
        		if (instance != null)

        			return instance;

        		else {

        			instance = new DocumentIdProviderDouble();
                    instance.connect();
        			return instance;

        		}	
        	}

      
	 }
	 
	 @Before
	 public void init() throws NonRecoverableError {
		    DOCUMENT_ID=1000;
		    docProvider.instance=null;
			docProvider=(DocumentIdProviderDouble) new DocumentIdProviderDouble().getInstance();
		  
		    
		    d1 = new Document();
			d1.cargar(docProvider);
			
			
			d2 = new Document();
			d2.cargar(docProvider);
	
			
		    d3 = new Document();
			d3.cargar(docProvider);
		
		 
	 }
	@Test
	public void numerosCorrectos() throws NonRecoverableError, RecoverableError {
		
		d1.setTemplate("DECLARATION");
		d1.setTitle("A");
		d1.setAuthor("B");
		d1.setBody("C");
		assertEquals("DOCUMENT ID: 1001\n\nTitle : A\nAuthor: B\n\nC", d1.getFormattedDocument());

	}
	
	@Test
	public void numerosConsecutivos() throws NonRecoverableError, RecoverableError {
		
		int id1= (int) d1.getDocumentId();
		int id2= (int) d2.getDocumentId();
		int id3= (int) d3.getDocumentId();
		
		System.out.println(id1);
		System.out.println(id2);
		System.out.println(id3);
		assertTrue(((id2-id1)==1) && ((id3-id2)==1));

	}

}

