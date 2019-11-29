package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDouble2 extends DocumentIdProvider{
	
	public final static int MORE_THAN_ONE = 10;
	
	public DocumentIdProviderDouble2() {}
	
	public int GetLastObjectID() throws NonRecoverableError {
		return MORE_THAN_ONE;
	}
	
	@Override
	public int DocumentIDUpdate() throws NonRecoverableError {
		return MORE_THAN_ONE;
	}
}
