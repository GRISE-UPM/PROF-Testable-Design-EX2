package es.upm.grise.profundizacion.HandleDocuments;

import java.sql.SQLException;

/**
 * Hereda de la clase abstracta para evitar tener que implementar todos los metodos de ResultSet
 * 
 * @author jonatan
 *
 */
public class ResultSetDouble extends AbstractResultSetDouble {

	private int counter;

	public ResultSetDouble() {
		counter = 0;
	}

	/**
	 * Simula que haya 2 filas
	 */
	@Override
	public boolean next() throws SQLException {
		counter++;
		return counter < 3;
	}
}
