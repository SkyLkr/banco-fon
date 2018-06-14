package exceptions;

/**
 * Lançada quando uma credencial de senha é digitada incorretamente.
 * 
 */
public class SenhaInvalidaException extends Exception {
	public SenhaInvalidaException() {
		super("Senha Inválida");
	}
}
