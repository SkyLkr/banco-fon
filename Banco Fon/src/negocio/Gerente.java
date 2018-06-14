package negocio;

/**
 * Gerente do Banco
 * 
 */
public class Gerente extends Usuario{
	
	private String senha;
	
	/**
	 * 
	 * @param nome Nome completo do gerente
	 * @param cpf CPF do gerente
	 * @param endereco Endereço do gerente
	 * @param telefone Telefone do gerente
	 * @param senha Senha do gerente
	 */
	public Gerente(String nome, String cpf, String endereco, String telefone, String senha) {
		super(nome, cpf, endereco, telefone);
		this.senha = senha;
	}
	
	/**
	 * Compara a senha do gerente com outra
	 * @param outraSenha Senha a ser comparada com a do gerente
	 * @return true caso as senhas sejam iguais ou false caso sejam diferentes
	 */
	public boolean compareSenha(String outraSenha) {
		return senha.equals(outraSenha);
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
