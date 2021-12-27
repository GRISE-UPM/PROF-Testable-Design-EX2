package es.upm.grise.profundizacion.HandleDocuments;

public class NonRecoverableError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;

	public NonRecoverableError(){
		super();
	}

	public NonRecoverableError(String type){
		super();
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

}
