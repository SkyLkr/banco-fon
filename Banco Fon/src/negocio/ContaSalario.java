package negocio;

import java.math.BigDecimal;

import cambio.Dinheiro;
import exceptions.SaldoInsuficienteException;

/**
 * Conta Salario
 * 
 */
public class ContaSalario extends Conta {
	
	private BigDecimal emprestimoDevido;

	/**
	 * 
	 * @param titular Titular da conta.
	 * @param numero Número da conta.
	 * @param senha Senha da conta.
	 */
	public ContaSalario(Cliente titular, String numero, String senha) {
		super(titular, numero, senha);
		emprestimoDevido = new BigDecimal(0);
	}
	
	public BigDecimal getEmprestimoDevido() {
		return emprestimoDevido;
	}
	
	public void setEmprestimoDevido(BigDecimal valor) {
		this.emprestimoDevido = valor;
	}
	
	//deposito e transferencia para conta corrente
	
	/**
	 * Transfere o valor especificado para outra conta.
	 * @param valor Valor a ser transferido
	 * @param destino Conta corrente para a qual o valor será destinado. 
	 * @throws SaldoInsuficienteException Caso o saldo da conta de origem seja menor que o valor a ser transferido.
	 */
	public void transferencia(Dinheiro valor, ContaCorrente destino) throws SaldoInsuficienteException {
		try {
			this.retirar(valor);
		} catch(SaldoInsuficienteException e) {
			throw e;
		}
		
		destino.deposito(valor);
	}
}
