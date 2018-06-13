package negocio;

public class Gerente extends Usuario{
	
	private String senha;
	
	public Gerente(String nome, String cpf, String endereco, String telefone, String senha) {
		super(nome, cpf, endereco, telefone);
		this.senha = senha;
	}
	//cadastrar usuario(gerente ou cliente), gerenciar_usuario  e remover_usuario

	public boolean compareSenha(String outraSenha) {
		return senha.equals(outraSenha);
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
