package negocio;

import java.io.Serializable;
import java.math.BigDecimal;

import cambio.Dinheiro;
import exceptions.SaldoInsuficienteException;

/**
 * Representa uma conta bancária.
 *
 */
public abstract class Conta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6064535102735711429L;
	private Cliente titular;
	private String numero;
	private String senha;
	private Dinheiro saldo;
	
	public Cliente getTitular() {
		return titular;
	}
	public Dinheiro getSaldo() {
		return saldo;
	}
	
	/**
	 * 
	 * @param titular Titular da conta.
	 * @param numero Número da conta.
	 * @param senha Senha da conta.
	 */
	public Conta(Cliente titular, String numero, String senha) {
		this.titular = titular;
		this.saldo = new Dinheiro(Dinheiro.MOEDA);
		this.titular.getContas().add(this);//Adicionar a conta que esta sendo criada ao arraylist de contas do titular
		this.numero = numero;
		this.senha = senha;
	}
	
	/**
	 * Compara a senha da conta com outra
	 * @param outraSenha Senha para comparar com a senha da conta.
	 * @return true caso as senhas sejam iguais ou false caso sejam diferentes.
	 */
	public boolean compareSenha(String outraSenha) {
		return senha.equals(outraSenha);
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNumero() {
		return numero;
	}
	
	/**
	 * Retira um determinado valor da conta.
	 * @param valor Valor a ser retirado.
	 * @throws SaldoInsuficienteException Caso o saldo da conta seja inferior ao que deseja retirar.
	 */
	protected void retirar(Dinheiro valor) throws SaldoInsuficienteException {
		if (this.getSaldo().compareTo(valor) >= 0) {
			this.saldo = this.saldo.sub(valor);
		} else {
			throw new SaldoInsuficienteException();
		}
	}
	
	/**
	 * Deposita um valor na conta.
	 * @param valor Valor a ser adicionado à conta.
	 */
	public void deposito(Dinheiro valor) {
		this.saldo = this.saldo.add(valor);
	}
	

}
