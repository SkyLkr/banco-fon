package negocio;

import java.math.BigDecimal;

import cambio.Dinheiro;
import exceptions.SaldoInsuficienteException;
import negocio.interfaces.Sacavel;

/**
 * Conta Poupança
 * 
 */
public class ContaPoupanca extends Conta implements Sacavel {
	
	/**
	 * 
	 * @param titular Titular da conta.
	 * @param numero Número da conta.
	 * @param senha Senha da conta.
	 */
	public ContaPoupanca(Cliente titular, String numero, String senha) {
		super(titular, numero, senha);
	}
	
	/**
	 * Calcula o rendimento mensal da conta.
	 * @return Valor do rendimento mensal da conta.
	 */
	public Dinheiro calculaRendimento() {
		//rendimento mensal de 0,6%
		return new Dinheiro(super.getSaldo().getValor().add(super.getSaldo().getValor().multiply(new BigDecimal(0.006))));
	}

	public void transferencia(Dinheiro valor, Conta destino) throws SaldoInsuficienteException {
		try {
			this.retirar(valor);
		} catch (SaldoInsuficienteException e) {
			throw e;
		}
		destino.deposito(valor);
	}

	@Override
	public void saque(Dinheiro valor) throws SaldoInsuficienteException {
		retirar(valor);
	}
	
}
