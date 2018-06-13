package negocio;

import java.math.BigDecimal;
import java.util.ArrayList;

import exceptions.DesempregadoException;
import exceptions.SaldoInsuficienteException;

public class ContaCorrente extends Conta {

	public ContaCorrente(Cliente titular, String numero, String senha) {
		super(titular, numero, senha);
	}
	
	public void transferencia(BigDecimal valor, Conta destino) throws SaldoInsuficienteException {
		try {
			this.retirar(valor);
		} catch (SaldoInsuficienteException e) {
			throw e;
		}
		destino.deposito(valor);
	}
	
	public void realizarEmprestimo(BigDecimal valor)  throws DesempregadoException {
		ArrayList<Conta> contasDoCliente = this.getTitular().getContas();
		ContaSalario cs = null;
		for (Conta conta : contasDoCliente) {
			if (conta instanceof ContaSalario) {
				cs = (ContaSalario) conta;
				break;
			}
		}
		if (cs != null) {
			cs.setEmprestimoDevido(valor);
		} else {
			throw new DesempregadoException("Esta conta não possui conta salário associada.");
		}
	}
}
