package negocio;

import java.math.BigDecimal;

import cambio.Dinheiro;
import exceptions.SaldoInsuficienteException;
import negocio.interfaces.Sacavel;

/**
 * Conta Corrente
 * 
 */
public class ContaCorrente extends Conta implements Sacavel {
	
	/**
	 * 
	 * @param titular Titular da conta.
	 * @param numero Número da conta.
	 * @param senha Senha da conta.
	 */
	public ContaCorrente(Cliente titular, String numero, String senha) {
		super(titular, numero, senha);
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
