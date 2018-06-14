package exceptions;

/**
 * Lançada quando um cliente tenta fazer um emprestimo do mesmo tipo que já possui.
 * 
 */
public class JaPossuiEmprestimoException extends Exception {
	public JaPossuiEmprestimoException() {
		super("Cliente já possui emprestimo em aberto");
	}
}
