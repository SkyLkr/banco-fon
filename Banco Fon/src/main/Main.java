package main;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Scanner;

import cambio.Dinheiro;
import cambio.Moedas;
import dados.AcessadorDeArquivo;
import dados.Dados;
import exceptions.DesempregadoException;
import exceptions.JaPossuiEmprestimoException;
import exceptions.OpcaoInvalidaException;
import exceptions.SaldoInsuficienteException;
import exceptions.SenhaInvalidaException;
import exceptions.UsuarioNaoCadastradoException;
import negocio.Cliente;
import negocio.Conta;
import negocio.ContaCorrente;
import negocio.ContaPoupanca;
import negocio.ContaSalario;
import negocio.Gerente;
import negocio.Usuario;
import negocio.interfaces.Sacavel;
import utils.Utilidades;

/**
 * Classe principal do programa. Ela gerencia toda a interação do programa com o usuário.
 * 
 */
public class Main {
	static Dados d;
	static Scanner scanner = new Scanner(System.in);
	public static final String CAMINHO_DO_ARQUIVO = "teste.dat"; 
	//criar, remover, atualizar e ler
	
	public static void main(String[] args) {
		boolean temArquivo = false;
		
		d = AcessadorDeArquivo.lerArquivo(CAMINHO_DO_ARQUIVO);
		
		if (d == null) {
			d = new Dados();
			temArquivo = false;
		} else {
			temArquivo = true;
		}
		
		do {
			System.out.println("Selecionar Moeda: ");
			System.out.println("[1] Real");
			System.out.println("[2] Dólar");
			String o = scanner.nextLine();
			if (o.equals("1")) {
				Dinheiro.MOEDA = Moedas.REAL;
				break;
			} else if (o.equals("2")) {
				Dinheiro.MOEDA = Moedas.DOLAR;
				break;
			} else {
				System.out.println("Opção Inválida");
			}
		} while (true);
		
		if (args.length > 0 && args[0].compareToIgnoreCase("admin") == 0) {
			try {
				if (!temArquivo) {
					System.out.println("CONFIGURAÇÃO INICIAL DO SISTEMA");
					System.out.println("Cadastrar um gerente: ");
					cadastrarGerente();
					AcessadorDeArquivo.gravarArquivo(d, CAMINHO_DO_ARQUIVO);
					Utilidades.limparTela();
				}
				loginGerente();
			} catch (UsuarioNaoCadastradoException | SenhaInvalidaException e) {
				System.out.println(e.toString());
			}
		} else {
			try {
				if (temArquivo)
					loginContaCliente();
				else
					System.out.println("O sistema não foi configurado para uso dos clientes");
			} catch (UsuarioNaoCadastradoException | SenhaInvalidaException e) {
				System.out.println(e.toString());
			}
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
	
		menuConta(conta);
	}
	
	static void menuConta(Conta conta) {
		while(true) {
			System.out.println("[1] Ver saldo");
			System.out.println("[2] Depositar");
			System.out.println("[3] Sacar");
			System.out.println("[4] Transferir");
			System.out.println("[5] Emprestimo consignado");
			System.out.println("[6] Emprestimo não-consignado");
			System.out.println("[0] Sair");
			
			String opcao = scanner.nextLine();
			
			if (opcao.equals("1")) {
				System.out.println("Saldo na conta: " + conta.getSaldo().toString());
				if (conta instanceof ContaPoupanca) {
					ContaPoupanca poupanca = (ContaPoupanca) conta;
					System.out.println("Saldo após rendimento mensal: " + poupanca.calculaRendimento());
				}
				scanner.nextLine();
				Utilidades.limparTela();
			} else if (opcao.equals("2")) {
				boolean errou = false; 
				Dinheiro valor = new Dinheiro(Dinheiro.MOEDA);
				do {
					System.out.println("Valor do depósito: ");
					String entrada = scanner.nextLine();
					try {
						valor = new Dinheiro(entrada, Dinheiro.MOEDA);
						errou = false;
					} catch (NumberFormatException e) {
						System.out.println("Valor inválido.");
						errou = true;
					}
				} while (errou);
				
				conta.deposito(valor);
				
				System.out.println("Você depositou " + valor + " com sucesso.");
				
			} else if (opcao.equals("3")) {
				if (conta instanceof Sacavel) {
				
					boolean errou = false; 
					Dinheiro valor = new Dinheiro(Dinheiro.MOEDA);
					do {
						System.out.println("Valor: ");
						String entrada = scanner.nextLine();
						try {
							valor = new Dinheiro(entrada, Dinheiro.MOEDA);
							errou = false;
						} catch (NumberFormatException e) {
							System.out.println("Valor inválido.");
							errou = true;
						}
					} while (errou);
					
					Sacavel cs = (Sacavel) conta;
					try {
						cs.saque(valor);
						System.out.println("Você sacou " + valor + " com sucesso.");
					} catch (SaldoInsuficienteException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Conta Salário não permite realização de saque");
				}
				
			} else if (opcao.equals("4")) {
				System.out.println("Número da conta destino: ");
				String num = scanner.nextLine();
				if (!d.getContas().containsKey(num)) {
					System.out.println("Essa conta não existe no sistema");
					continue;
				}
				
				Conta destino = d.getContas().get(num);
				
				boolean errou = false; 
				Dinheiro valor = new Dinheiro(Dinheiro.MOEDA);
				do {
					System.out.println("Valor: ");
					String entrada = scanner.nextLine();
					try {
						valor = new Dinheiro(entrada, Dinheiro.MOEDA);
						errou = false;
					} catch (NumberFormatException e) {
						System.out.println("Valor inválido.");
						errou = true;
					}
				} while (errou);
				
				System.err.println("Transferindo " + valor.getMoeda() + " para " + destino.getSaldo().getMoeda());
				
				if (conta instanceof ContaSalario) {
					if (destino instanceof ContaCorrente) {
						ContaSalario cs = (ContaSalario) conta;
						try {
							cs.transferencia(valor, (ContaCorrente) destino);
						} catch (SaldoInsuficienteException e) {
							System.out.println(e.getMessage());
						}
					}
				} else {
					Sacavel cs = (Sacavel) conta;
					try {
						cs.transferencia(valor, destino);
					} catch (SaldoInsuficienteException e) {
						System.out.println(e.getMessage());
					}
				}
				
			} else if (opcao.equals("5")) {
				boolean errou = false; 
				BigDecimal valor = new BigDecimal(0);
				do {
					System.out.println("Valor: ");
					String entrada = scanner.nextLine();
					try {
						valor = new BigDecimal(entrada);
						errou = false;
					} catch (NumberFormatException e) {
						System.out.println("Valor inválido.");
						errou = true;
					}
				} while (errou);
				
				try {
					conta.getTitular().realizarEmprestimoConsignado(valor);
				} catch (DesempregadoException | JaPossuiEmprestimoException e) {
					System.out.println(e.getMessage());
				}
				
			} else if (opcao.equals("6")) {
				boolean errou = false; 
				BigDecimal valor = new BigDecimal(0);
				do {
					System.out.println("Valor do depósito: ");
					String entrada = scanner.nextLine();
					try {
						valor = new BigDecimal(entrada);
						errou = false;
					} catch (NumberFormatException e) {
						System.out.println("Valor inválido.");
						errou = true;
					}
				} while (errou);
				
				try {
					conta.getTitular().realizarEmprestimoNaoConsignado(valor);
				} catch (JaPossuiEmprestimoException e) {
					System.out.println(e.getMessage());
				}
				
			} else if (opcao.equals("0")) {
				AcessadorDeArquivo.gravarArquivo(d, CAMINHO_DO_ARQUIVO);
				break;
			} else {
				System.out.println("Opção inválida. Tente novamente.");
			}
		}
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
	
	static void menuGerente(Gerente gerente) {
		while(true) {
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
						System.out.println("[0] Voltar");
						String op = scanner.nextLine();
						if (op.equals("1")) {
							cadastrarCliente();
							break;
						} else if (op.equals("2")) {
							cadastrarGerente();
							break;
						} else if (op.equals("0")) {
							Utilidades.limparTela();
							break;
						}else {
							System.out.println("Opção Inválida. Tente novamente.");
						}
					}
				} else if (opcao == 2) {
					String cpf;
					do {
						System.out.println("CPF do usuário (0 para voltar): ");
						cpf = scanner.nextLine();
						
						if (cpf.equals("0")) break;
						
						if (d.getUsuarios().containsKey(cpf)) {
							Usuario usr = d.getUsuarios().get(cpf);
							while(true) {
								System.out.println("[1] Atualizar Dados");
								System.out.println("[2] Remover usuário");
								System.out.println("[0] Voltar");
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
										System.out.println("[0] Voltar");
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
										} else if (escolha.equals("0")) {
											break;
										} else {
											System.out.println("Escolha Invalida");
										}
										
									}
									break;
								} else if (op.equals("0")) {
									Utilidades.limparTela();
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
					String cpf;
					while(true) {
						System.out.print("CPF (0 para cancelar): ");
						cpf = scanner.nextLine();
						if (d.getUsuarios().containsKey(cpf)) {
							break;
						} else if (!cpf.equals("0")) {
							System.out.println("Esse usuário não existe. Tente novamente.");
						} else {
							break;
						}
					}
					
					if (!cpf.equals("0")) {
						Usuario usuario = d.getUsuarios().get(cpf);
						if (usuario instanceof Cliente) {
							Cliente cliente = (Cliente) usuario;
							
							for (Conta conta : cliente.getContas()) {
								if (conta.getTitular().equals(cliente)) d.getContas().remove(conta.getNumero());
							}
							
						}
					}
				} else if (opcao == 4) {
					System.out.print("Senha atual: ");
					String senha = scanner.nextLine();
					System.out.println("Nova senha: ");
					String novaSenha = scanner.nextLine();
					if (gerente.compareSenha(senha)) {
						gerente.setSenha(novaSenha);
					} else {
						System.out.println("Senha atual incorreta!");
						scanner.nextLine();
					}
				} else if (opcao == 0) {
					AcessadorDeArquivo.gravarArquivo(d, CAMINHO_DO_ARQUIVO);
					System.exit(0);
				} else {
					throw new OpcaoInvalidaException();
				}
			} catch(NumberFormatException | OpcaoInvalidaException e) {
				System.out.println(e.toString());
			}
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
		
		System.out.println("Conta cadastrada com sucesso.");
		System.out.println("Número da conta: " + numero);
		scanner.nextLine();
	}
}
