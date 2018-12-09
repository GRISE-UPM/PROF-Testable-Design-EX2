package es.upm.grise.profundizacion2018.tema5;

public class NonRecoverableError extends Exception {

	public NonRecoverableError() {
		super();
	}
	
	public NonRecoverableError(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;

}
