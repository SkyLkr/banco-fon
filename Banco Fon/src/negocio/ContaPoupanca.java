package negocio;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(Cliente titular) {
		super(titular);
		// TODO Auto-generated constructor stub
	}
	
	public void rendimento() {
		//rendimento mensal de 0,6%
		BigDecimal NewSaldo = super.getSaldo().add(super.getSaldo().multiply(new BigDecimal(0.006)));
		
	}

}
