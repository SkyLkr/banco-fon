package negocio;

import java.math.BigDecimal;

import exceptions.SaldoInsuficienteException;

public class ContaCorrente extends Conta {

	public ContaCorrente(Cliente titular) {
		super(titular);
	}
	
	public void transferir(BigDecimal valor, Conta destino) throws SaldoInsuficienteException {
		try {
			this.retirar(valor);
		} catch (SaldoInsuficienteException e) {
			throw e;
		}
		destino.deposito(valor);
	}
	
	
}
