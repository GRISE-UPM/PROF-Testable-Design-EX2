package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;

public class DocumentIdProviderDoubleV2 extends DocumentIdProvider {

	public DocumentIdProviderDoubleV2() throws NonRecoverableError {
		super();
	}

	@Override
	protected int getLastId() throws NonRecoverableError {
		
		//Fail
		System.out.println(CORRUPTED_COUNTER.getMessage());          	
		throw new NonRecoverableError();
	}

}
