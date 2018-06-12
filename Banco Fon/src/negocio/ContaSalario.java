package negocio;

import java.math.BigDecimal;

import exceptions.SaldoInsuficienteException;

public class ContaSalario extends Conta {
	
	private BigDecimal emprestimoDevido;

	public ContaSalario(Cliente titular) {
		super(titular);
		emprestimoDevido = new BigDecimal(0);
	}
	
	public BigDecimal getEmprestimoDevido() {
		return emprestimoDevido;
	}
	
	public void setEmprestimoDevido(BigDecimal valor) {
		this.emprestimoDevido = valor;
	}
	
	//deposito e transferencia para conta corrente
	
	public void transferencia(BigDecimal valor, ContaCorrente destino) throws SaldoInsuficienteException {
		try {
			this.retirar(valor);
		} catch(SaldoInsuficienteException e) {
			throw e;
		}
		
		destino.deposito(valor);
	}
}
