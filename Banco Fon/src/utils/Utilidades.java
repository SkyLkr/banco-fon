package utils;

import java.io.IOException;

/**
 * Classe abstrata que contém funçoes úteis para o sistema.
 */
public abstract class Utilidades {
	
	/**
	 * Método estático para limpar a tela do console
	 */
	public static void limparTela() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else  {
				Runtime.getRuntime().exec("clear");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
