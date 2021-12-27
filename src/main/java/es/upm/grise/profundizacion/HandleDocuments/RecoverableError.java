package es.upm.grise.profundizacion.HandleDocuments;

public class RecoverableError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;

	public RecoverableError(){
		super();
	}

	public RecoverableError(String type){
		super();
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

}
