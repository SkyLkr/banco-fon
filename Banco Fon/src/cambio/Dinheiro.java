package cambio;

import java.io.Serializable;
import java.math.BigDecimal;

public class Dinheiro implements Serializable {
	
	public static Moedas MOEDA = Moedas.REAL;
	
	private BigDecimal valor;
	private Moedas moeda;
	
	public Dinheiro() {
		valor = new BigDecimal(0);
		moeda = MOEDA;
	}
	
	public Dinheiro(BigDecimal valor) {
		this.valor = valor.abs();
		this.moeda = MOEDA;
	}
	
	public Dinheiro(String valor) {
		this.valor = (new BigDecimal(valor)).abs();
		this.moeda = MOEDA;
	}
	
	public Dinheiro(double valor) {
		this.valor = (new BigDecimal(valor)).abs();
		this.moeda = MOEDA;
	}
	
	public Dinheiro(Moedas moeda) {
		valor = new BigDecimal(0);
		this.moeda = moeda;
	}
	
	public Dinheiro(BigDecimal valor, Moedas moeda) {
		this.valor = valor.abs();
		this.moeda = moeda;
	}
	
	public Dinheiro(String valor, Moedas moeda) {
		this.valor = (new BigDecimal(valor)).abs();
		this.moeda = moeda;
	}
	
	public Dinheiro(double valor, Moedas moeda) {
		this.valor = (new BigDecimal(valor)).abs();
		this.moeda = moeda;
	}
	
	public void setMoeda(Moedas novaMoeda) {
		if (this.moeda.preco.compareTo(novaMoeda.preco) == -1)
			valor = valor.divide(novaMoeda.preco, 2, BigDecimal.ROUND_HALF_DOWN);
		else
			valor = valor.multiply(this.moeda.preco);
		this.moeda = novaMoeda;
	}
	
	public Moedas getMoeda() {
		return moeda;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public int compareTo(Dinheiro other) {
		Moedas temp = other.getMoeda();
		other.setMoeda(this.moeda);
		
		int res = this.valor.compareTo(other.getValor());
		other.setMoeda(temp);
		
		return res;
	}
	
	public Dinheiro add(Dinheiro other) {
		Moedas temp = other.getMoeda();
		System.err.println("Somando " + this.getMoeda() + " com " + other.getMoeda());
		other.setMoeda(this.getMoeda());
		Dinheiro novo = new Dinheiro(this.getValor().add(other.getValor()), this.getMoeda());
		
		other.setMoeda(temp);
		
		return novo;
	}
	
	public Dinheiro sub(Dinheiro other) {
		Moedas temp = other.getMoeda();
		other.setMoeda(this.getMoeda());
		Dinheiro novo = new Dinheiro(this.getValor().subtract(other.getValor()), this.getMoeda());
		
		other.setMoeda(temp);
		
		return novo;
	}
	
	@Override
	public String toString() {
		/*Moedas temp = this.moeda;
		if (this.moeda != MOEDA) {
			setMoeda(MOEDA);
		}*/
		String r = this.moeda.simbolo + String.format("%.2f", valor.doubleValue());
		//setMoeda(temp);
		
		return r;
	}
}
