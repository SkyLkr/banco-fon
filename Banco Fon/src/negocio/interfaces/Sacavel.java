package negocio.interfaces;

import cambio.Dinheiro;
import exceptions.SaldoInsuficienteException;
import negocio.Conta;

/**
 * 
 * @author Adriano
 *
 */
public interface Sacavel {
	
	/**
	 * Saca da conta o valor especificado.
	 * @param valor Valor do saque.
	 * @throws SaldoInsuficienteException Caso o saldo da conta seja menor que o valor do saque.
	 */
	public void saque(Dinheiro valor) throws SaldoInsuficienteException;
	
	/**
	 * Transfere o valor especificado para outra conta.
	 * @param valor Valor a ser transferido.
	 * @param destino Conta para o qual o valor será destinado.
	 * @throws SaldoInsuficienteException Caso o saldo da conta de origem seja menor que o valor da transfêrencia.
	 */
	public void transferencia(Dinheiro valor, Conta destino) throws SaldoInsuficienteException;
}
