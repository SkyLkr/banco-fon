package exceptions;

/**
 * Lançada quando tenta-se realizar uma operação de saque/transferencia de um valor mais alto
 * que o saldo da conta.
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
