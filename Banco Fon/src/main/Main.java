package main;

import java.util.Random;
import java.util.Scanner;

import dados.AcessadorDeArquivo;
import dados.Dados;
import exceptions.OpcaoInvalidaException;
import exceptions.SenhaInvalidaException;
import exceptions.UsuarioNaoCadastradoException;
import negocio.Cliente;
import negocio.Conta;
import negocio.ContaCorrente;
import negocio.ContaPoupanca;
import negocio.ContaSalario;
import negocio.Gerente;
import negocio.Usuario;

public class Main {
	static Dados d;
	static Scanner scanner = new Scanner(System.in);
	//criar, remover, atualizar e ler
	
	public static void main(String[] args) {
		boolean temArquivo = false;
		
		d = AcessadorDeArquivo.lerArquivo("teste.mp3");
		if (d == null) {
			System.out.println("num tem");
			d = new Dados();
			temArquivo = false;
		} else {
			System.out.println("tem arquivo");
			temArquivo = true;
		}
		
		if (args.length > 0 && args[0].compareToIgnoreCase("admin") == 0) {
			loginGerente();
		} else {
			loginContaCliente();
		}
	}
	
	static void loginContaCliente() throws UsuarioNaoCadastradoException, SenhaInvalidaException {
		System.out.println("Número da conta: ");
		String numConta = scanner.nextLine();
		System.out.println("Senha: ");
		String senha = scanner.nextLine();
		
		Conta conta = d.getContas().get(numConta);
		if (conta == null) throw new UsuarioNaoCadastradoException();
		
		if (!conta.compareSenha(senha)) throw new SenhaInvalidaException();
		
	}
	
	static void loginGerente() throws UsuarioNaoCadastradoException, SenhaInvalidaException {
		System.out.print("CPF: ");
		String cpf = scanner.nextLine();
		System.out.print("Senha: ");
		String senha = scanner.nextLine();
		
		Usuario usr = d.getUsuarios().get(cpf);
		if (usr == null || !(usr instanceof Gerente)) throw new UsuarioNaoCadastradoException();
		
		Gerente ger = (Gerente) usr;
		if (!ger.compareSenha(senha)) throw new SenhaInvalidaException();
		
		menuGerente(ger);
	}
	
	static void menuGerente(Gerente g) {
		try { 
			System.out.println("[1] Cadastrar Usuário");
			System.out.println("[2] Gerenciar Usuário");
			System.out.println("[3] Remover Usuário");
			System.out.println("[4] Alterar Senha");
			System.out.println("[0] Sair");
			int opcao = Integer.parseInt(scanner.nextLine());
			if (opcao == 1) {
				while(true) {
					System.out.println("[1] Cliente");
					System.out.println("[2] Gerente");
					String op = scanner.nextLine();
					if (op.equals("1")) {
						cadastrarCliente();
						break;
					} else if (op.equals("2")) {
						cadastrarGerente();
						break;
					} else {
						System.out.println("Opção Inválida. Tente novamente.");
					}
				}
			} else if (opcao == 2) {
				String cpf;
				do {
					System.out.println("CPF do usuário: ");
					cpf = scanner.nextLine();
					
					if (d.getUsuarios().containsKey(cpf)) {
						Usuario usr = d.getUsuarios().get(cpf);
						while(true) {
							System.out.println("[1] Atualizar Dados");
							System.out.println("[2] Remover usuário");
							if (usr instanceof Cliente) System.out.println("[3] Gerenciar contas");
							String op = scanner.nextLine();
							if (op.equals("1")) {
								atualizaUsuario(usr);
								break;
							} else if (op.equals("2")) {
								d.getUsuarios().remove(cpf);
								break;
							} else if (usr instanceof Cliente && op.equals("3")) {
								Cliente cliente = (Cliente) usr;
								while (true) {
									System.out.println("[1] Criar conta");
									System.out.println("[2] Excluir conta");
									System.out.println("[3] Voltar");
									String escolha = scanner.nextLine();
									if (escolha.equals("1")) {
										cadastrarConta(cliente);
										break;
									} else if (escolha.equals("2")) {
										System.out.println("Seleciona a conta:");
										for (int i = 0; i < cliente.getContas().size(); i++) {
											System.out.println("[" + i + "] " + cliente.getContas().get(i).getNumero());
										}
										int n = Integer.parseInt(scanner.nextLine());
										
										Conta c = cliente.getContas().get(n);
										
										cliente.getContas().remove(c);
										
										d.getContas().remove(c.getNumero());
										
										if (cliente.getContas().size() < 1) d.getUsuarios().remove(cliente.getCpf());
										
										break;
									} else if (escolha.equals("3")) {
										break;
									} else {
										System.out.println("Escolha Invalida");
									}
									
								}
								break;
							} else {
								System.out.println("Opção Inválida.");
							}
						}
					} else {
						System.out.println("Esse usuário não existe. Tente novamente.");
					}
				} while (!d.getUsuarios().containsKey(cpf));
			} else if (opcao == 3) {
				System.out.print("CPF: ");
				String cpf = scanner.nextLine();
				Usuario usuario = d.getUsuarios().get(cpf);
				if (usuario instanceof Cliente) {
					Cliente cliente = (Cliente) usuario;
					
					for (Conta conta : d.getContas().values()) {
						if (conta.getTitular().equals(cliente)) d.getContas().remove(conta.getNumero());
					}
					
				}
			} else if (opcao == 4) {
				
			} else if (opcao == 0) {
				System.exit(0);
			} else {
				throw new OpcaoInvalidaException();
			}
		} catch(NumberFormatException | OpcaoInvalidaException e) {
			menuGerente(g);
		}
	}
	
	static void atualizaUsuario(Usuario usuario) {
		System.out.println("Campos em branco para deixar inalterado.");
		System.out.println("Nome (" + usuario.getNome() +"): ");
		String nome = scanner.nextLine();
		System.out.println("Endereço (" + usuario.getEndereco() + "): ");
		String end = scanner.nextLine();
		System.out.println("Telefone (" + usuario.getTelefone() + "): ");
		String tele = scanner.nextLine();
		
		if (!nome.isEmpty()) usuario.setNome(nome);
		if (!end.isEmpty()) usuario.setEndereco(end);
		if (!tele.isEmpty()) usuario.setTelefone(tele);
	}
	
	static void cadastrarGerente() {
		System.out.println("Nome: ");
		String nome = scanner.nextLine();
		System.out.println("CPF: ");
		String cpf = scanner.nextLine();
		System.out.println("Endereço: ");
		String end = scanner.nextLine();
		System.out.println("Telefone: ");
		String telefone = scanner.nextLine();
		System.out.println("Senha: ");
		String senha = scanner.nextLine();
		
		Gerente novo = new Gerente(nome, cpf, end, telefone, senha);
		
		d.getUsuarios().put(cpf, novo);
	}
	
	static void cadastrarCliente() {
		System.out.println("Nome: ");
		String nome = scanner.nextLine();
		System.out.println("CPF: ");
		String cpf = scanner.nextLine();
		System.out.println("Endereço: ");
		String end = scanner.nextLine();
		System.out.println("Telefone: ");
		String telefone = scanner.nextLine();
		
		Cliente novo = new Cliente(nome, cpf, end, telefone);
		
		cadastrarConta(novo);
		
		d.getUsuarios().put(cpf, novo);
	}
	
	static String gerarNumeroConta() {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < 6; i++) {
			int numero = r.nextInt(10);
			sb.append(numero);
		}
		return sb.toString();
	}
	
	static void cadastrarConta(Cliente cliente) {
		
		System.out.print("Escolha uma senha para a conta: ");
		String senha = scanner.nextLine();
		Conta nova;
		String numero;
		
		do {
			numero = gerarNumeroConta();
		} while(d.getContas().containsKey(numero));
		
		while(true) {
			System.out.println("[1] Conta Corrente");
			System.out.println("[2] Conta Poupança");
			System.out.println("[3] Conta Salário");
			String opcao = scanner.nextLine();
			
			
			if (opcao.equals("1")) {
				nova = new ContaCorrente(cliente, numero, senha);
				break;
			} else if (opcao.equals("2")) {
				nova = new ContaPoupanca(cliente, numero, senha);
				break;
			} else if (opcao.equals("3")) {
				nova = new ContaSalario(cliente, numero, senha);
				break;
			} else {
				System.out.println("Opção Inválida");
			}
		}
		
		d.getContas().put(numero, nova);
	}
}
