package es.upm.grise.profundizacion2018.tema5;

public class NonRecoverableError extends Exception {

	/**
	 * 
	 */
	public NonRecoverableError() {
		//VACIO
	}
	public NonRecoverableError(String error) {
		super(error);
	}
	private static final long serialVersionUID = 1L;

}
