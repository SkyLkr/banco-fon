package exceptions;

public class UsuarioNaoCadastradoException extends Exception {
	public UsuarioNaoCadastradoException() {
		super("Usuário não cadastrado no sistema");
	}
}
