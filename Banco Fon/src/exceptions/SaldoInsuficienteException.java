package exceptions;

/**
 * 
 * @author Adriano
 *
 */
public class SaldoInsuficienteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3644785113828141507L;

	public SaldoInsuficienteException() {
		super("Erro! Saldo insuficiente.");
	}
}
