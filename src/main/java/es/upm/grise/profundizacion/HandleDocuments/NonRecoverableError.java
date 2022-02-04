package es.upm.grise.profundizacion.HandleDocuments;

public class NonRecoverableError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String exception;

	public NonRecoverableError(String exception) {
		this.exception = exception;
    }

	public String getException() {
		return this.exception;
	}
}
