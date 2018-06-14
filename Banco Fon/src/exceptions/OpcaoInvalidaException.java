package exceptions;


/**
 * Lançada quando uma opção inválida é selecionada em um menu.
 * 
 */
public class OpcaoInvalidaException extends Exception {
	public OpcaoInvalidaException() {
		super("Opção Inválida!");
	}
}
