package cambio;

import java.io.Serializable;
import java.math.BigDecimal;

public class Dinheiro implements Serializable {
	
	public static Moedas MOEDA = Moedas.REAL;
	
	private BigDecimal valor;
	private Moedas moeda;
	
	public Dinheiro() {
		valor = new BigDecimal(0);
		moeda = Moedas.REAL;
	}
	
	public Dinheiro(BigDecimal valor) {
		this.valor = valor;
		this.moeda = Moedas.REAL;
	}
	
	public Dinheiro(String valor) {
		this.valor = new BigDecimal(valor);
		this.moeda = Moedas.REAL;
	}
	
	public Dinheiro(double valor) {
		this.valor = new BigDecimal(valor);
		this.moeda = Moedas.REAL;
	}
	
	public Dinheiro(Moedas moeda) {
		valor = new BigDecimal(0);
		this.moeda = moeda;
	}
	
	public Dinheiro(BigDecimal valor, Moedas moeda) {
		this.valor = valor;
		this.moeda = moeda;
	}
	
	public Dinheiro(String valor, Moedas moeda) {
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
	}
	
	public Dinheiro(double valor, Moedas moeda) {
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
	}
	
	public void setMoeda(Moedas moeda) {
		if (this.moeda.preco.compareTo(moeda.preco) == -1)
			valor = valor.multiply(moeda.preco);
		else if (this.moeda.preco.compareTo(moeda.preco) == 1)
			valor = valor.divide(moeda.preco);
		this.moeda = moeda;
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
		Moedas temp = moeda;
		if (moeda != MOEDA) {
			setMoeda(MOEDA);
		}
		String r = moeda.simbolo + valor.toString();
		setMoeda(temp);
		
		return r;
	}
}
