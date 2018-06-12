package main;

import java.util.Scanner;

import exceptions.OpcaoInvalidaException;

public class Main {
	static Scanner leia = new Scanner(System.in);
	//criar, remover, atualizar e ler
	
	public static void main(String[] args) {
		
	}
	
	public static void menu() throws OpcaoInvalidaException {
		System.out.println("Acessar como:\n1-Gerente\n2-cliente");
		
		String entrada = leia.nextLine();
		int num;
		try {
			num = Integer.parseInt(entrada);
		} catch (NumberFormatException e) {
			throw new OpcaoInvalidaException();
		}
		if (num == 1) {
			dope
		} else if (num == 2) {
			
		} else {
			throw new OpcaoInvalidaException();
		}
		
		/*switch(num) {
			case 1:
				break;
			case 2:
				break;
			
		}*/
	}
}
