package negocio;

import java.math.BigDecimal;

import exceptions.SaldoInsuficienteException;

/**
 * 
 * @author Adriano
 *
 */
public abstract class Conta {
	
	private Cliente titular;
	private BigDecimal saldo;
	
	public Cliente getTitular() {
		return titular;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	
	public Conta(Cliente titular) {
		this.titular = titular;
		this.saldo = new BigDecimal(0);
		this.titular.getContas().add(this);//Adicionar a conta que esta sendo criada ao arraylist de contas do titular
	}
	
	protected void retirar(BigDecimal valor) throws SaldoInsuficienteException {
		if (this.getSaldo().compareTo(valor) >= 0) {
			this.saldo = this.saldo.subtract(valor);
		} else {
			throw new SaldoInsuficienteException();
		}
	}
	
	public void deposito(BigDecimal valor) {
		this.saldo = this.saldo.add(valor);
	}
	
	//Empretimo consignado somente se possuir conta salario
}
