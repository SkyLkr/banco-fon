package negocio;

import java.math.BigDecimal;

import exceptions.SaldoInsuficienteException;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(Cliente titular) {
		super(titular);
		// TODO Auto-generated constructor stub
	}
	
	public BigDecimal calculaRendimento() {
		//rendimento mensal de 0,6%
		return super.getSaldo().add(super.getSaldo().multiply(new BigDecimal(0.006)));
	}

	public void transferencia(BigDecimal valor, Conta destino) throws SaldoInsuficienteException {
		try {
			this.retirar(valor);
		} catch (SaldoInsuficienteException e) {
			throw e;
		}
		destino.deposito(valor);
	}
	
}
