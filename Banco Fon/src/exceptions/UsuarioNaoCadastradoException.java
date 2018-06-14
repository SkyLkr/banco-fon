package exceptions;

/**
 * Lançada quando tenta-se acessar uma conta de um usuário que não foi cadastrado no sistema.
 * 
 */
public class UsuarioNaoCadastradoException extends Exception {
	public UsuarioNaoCadastradoException() {
		super("Usuário não cadastrado no sistema");
	}
}
