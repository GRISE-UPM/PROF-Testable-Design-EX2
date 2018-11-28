package es.upm.grise.profundizacion2018.tema5;

public class NonRecoverableError extends Exception {

	public NonRecoverableError() {
		super();
	}

	public NonRecoverableError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NonRecoverableError(String message, Throwable cause) {
		super(message, cause);
	}

	public NonRecoverableError(String message) {
		super(message);
	}

	public NonRecoverableError(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
