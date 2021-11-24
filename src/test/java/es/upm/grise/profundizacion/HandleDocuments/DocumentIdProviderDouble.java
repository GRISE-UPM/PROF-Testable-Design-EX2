package es.upm.grise.profundizacion.HandleDocuments;


public class DocumentIdProviderDouble extends DocumentIdProvider {

  public DocumentIdProviderDouble(int startingId) throws NonRecoverableError {
    super(startingId);
  }

  @Override
  public int getDocumentId() throws NonRecoverableError {

    if (super.documentId <= 0) throw new NonRecoverableError();

    return super.documentId++;
  }

}
