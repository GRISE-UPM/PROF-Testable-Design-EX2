package es.upm.grise.profundizacion.HandleDocuments;

public class NonRecoverableError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonRecoverableError(String message) {
		super(message);
	}
}
