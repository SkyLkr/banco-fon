package dados;

import java.io.Serializable;
import java.util.HashMap;

import negocio.Conta;
import negocio.Usuario;

/**
 * Classe que armazena os hashmaps de usuários e contas
 * 
 */
public class Dados implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1402434184282681931L;
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Conta> contas;
	
	/**
	 * Constrói o objeto com ambos os hashmaps vazios.
	 */
	public Dados() {
		usuarios = new HashMap<>();
		contas = new HashMap<>();
	}
	
	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}
	
	public HashMap<String, Conta> getContas() {
		return contas;
	}
}
