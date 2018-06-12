package negocio;

import java.util.ArrayList;

/**
 * 
 * 
 * @author Adriano
 * 
 */
public class Cliente extends Usuario{

	ArrayList<Conta> contas;//ArrayList com todas as contasdo cliente
	
	public Cliente(String nome, String cpf) {
		super(nome, cpf);
		contas = new ArrayList<>();
	}
	
	public ArrayList<Conta> getContas() {
		return contas;
	}
}
