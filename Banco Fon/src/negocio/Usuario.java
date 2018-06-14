package negocio;

import java.io.Serializable;

/**
 * Usuário do Sistema
 * 
 */
public abstract class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6301136832903229587L;
	private String nome;
	private String cpf;
	private String endereco;
	private String telefone;
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
	
	/**
	 * 
	 * @param nome Nome do usuário
	 * @param cpf CPF do usuário
	 * @param endereco Endereço do usuário
	 * @param telefone Telefone do usuário
	 */
	public Usuario(String nome, String cpf, String endereco, String telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.endereco = endereco;
	}
	
}
