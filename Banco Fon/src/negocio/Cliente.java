package negocio;

import java.math.BigDecimal;
import java.util.ArrayList;

import cambio.Dinheiro;
import exceptions.DesempregadoException;
import exceptions.JaPossuiEmprestimoException;

/**
 * Representa um cliente do banco
 * 
 */
public class Cliente extends Usuario{

	private ArrayList<Conta> contas;//ArrayList com todas as contas do cliente
	private Dinheiro emprestimoDevido;
	
	/**
	 * 
	 * @param nome Nome completo do Cliente
	 * @param cpf CPF do cliente
	 * @param endereco Endereço do cliente
	 * @param telefone Número de telefone do cliente
	 */
	public Cliente(String nome, String cpf, String endereco, String telefone) {
		super(nome, cpf, endereco, telefone);
		contas = new ArrayList<>();
		emprestimoDevido = new Dinheiro();
	}
	
	public ArrayList<Conta> getContas() {
		return contas;
	}
	
	/**
	 * Realiza um empréstimo não consignado para o cliente.
	 * @param valor Valor do emprestimo.
	 * @throws JaPossuiEmprestimoException Caso o cliente já possua um emprestimo não consignado em aberto.
	 */
	public void realizarEmprestimoNaoConsignado(Dinheiro valor) throws JaPossuiEmprestimoException {
		if (emprestimoDevido.compareTo(new Dinheiro()) == 0)
			emprestimoDevido = valor;
		else
			throw new JaPossuiEmprestimoException();
	}
	
	/**
	 * Realiza um empréstimo consignado para o cliente.
	 * @param valor Valor do empréstimo.
	 * @throws DesempregadoException Caso o cliente não possua conta salário associada.
	 * @throws JaPossuiEmprestimoException Caso o cliente já possua um emprestimo consignado em aberto.
	 */
	public void realizarEmprestimoConsignado(Dinheiro valor)  throws DesempregadoException, JaPossuiEmprestimoException {
		ContaSalario cs = null;
		for (Conta conta : contas) {
			if (conta instanceof ContaSalario) {
				cs = (ContaSalario) conta;
				break;
			}
		}
		if (cs != null) {
			if (cs.getEmprestimoDevido().compareTo(new Dinheiro()) == 0)
				cs.setEmprestimoDevido(valor);
			else
				throw new JaPossuiEmprestimoException();
		} else {
			throw new DesempregadoException("Esta conta não possui conta salário associada.");
		}
	}
}
