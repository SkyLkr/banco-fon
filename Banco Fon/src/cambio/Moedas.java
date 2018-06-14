package cambio;

import java.math.BigDecimal;

public enum Moedas {
	REAL ("R$", 1),
	DOLAR ("US$", 3.7);
	
	public String simbolo;
	public BigDecimal preco;
	Moedas (String simbolo, double preco) {
		this.simbolo = simbolo;
		this.preco = new BigDecimal(preco);
	}
}
