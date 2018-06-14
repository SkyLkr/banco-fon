package exceptions;

/**
 *	Lançada quando um usuário que não possui ContaSalario tenta executar uma ação que requer
 *	uma conta desse tipo.
 */
public class DesempregadoException extends Exception {
	public DesempregadoException(String msg) {
		super(msg);
	}
}
