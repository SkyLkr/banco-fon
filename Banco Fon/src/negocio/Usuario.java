package negocio;

public class Usuario {

	private String nome;
	private String cpf;
	private String Endereco;
	private String NumeroConta;
	private String Senha;
	private String Telefone;
	
	public String getEndereco() {
		return Endereco;
	}
	
	public void setEndereco(String endereco) {
		Endereco = endereco;
	}
	
	public String getNumeroConta() {
		return NumeroConta;
	}
	
	public void setNumeroConta(String numeroConta) {
		NumeroConta = numeroConta;
	}
	
	public String getSenha() {
		return Senha;
	}
	
	public void setSenha(String senha) {
		Senha = senha;
	}
	
	public String getTelefone() {
		return Telefone;
	}
	
	public void setTelefone(String telefone) {
		Telefone = telefone;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Usuario(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}
	
}
