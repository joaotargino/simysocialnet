package main;

import java.util.Scanner;

import Util.ProfileConstants;

import controller.ProfileController;

public class UserInterface {

	private final static String FIM_DE_LINHA = System
			.getProperty("line.separator");
	private final static String menuPrincipal = "";
	private final static Scanner scan = new Scanner(System.in);
	private static SocialNet socialNet = new SocialNet();

	public static void main(String[] args) throws Exception {
		telaInicial();
	}

	public static void telaInicial() throws Exception {
		String login, senha, name = null, lastName = null, email = null, passwd = null;
		int opcao;
		final int SAIR = 0, CRIAR = 1, LOGAR = 2;
		final String OPCOES = "1. Criar nova conta" + FIM_DE_LINHA
				+ "2. Fazer Login" + FIM_DE_LINHA + "0. Sair" + FIM_DE_LINHA;
		do {
			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção desejada: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case CRIAR:
				System.out.print("Nome: ");
				name = scan.nextLine();
				System.out.print("Sobrenome: ");
				lastName = scan.nextLine();
				System.out.print("email: ");
				email = scan.nextLine();
				System.out.print("Senha: ");
				passwd = scan.nextLine();
				try {
					socialNet.createUser(name, lastName, email, passwd);
				} catch (Exception exCriar) {
					System.out.println(exCriar.getMessage());
					System.out.print("Tecle ENTER para continuar");
					scan.nextLine();
				}
				break;
			case LOGAR:
				System.out.print("login: ");
				login = scan.nextLine();
				System.out.print("senha: ");
				senha = scan.nextLine();
				try {
					socialNet.login(login, senha);
					opcao = SAIR;
					menuPrincipal(login);
				} catch (Exception exLogar) {
					System.out.println(exLogar.getMessage());
					System.out.print("Tecle ENTER para continuar");
					scan.nextLine();
				}
				break;
			case SAIR:
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != SAIR);
	}

	public static void paginaPrincipal(String login) throws Exception {

		System.out.println("usuario " + login + " estah logado!");
		socialNet.setFieldPrivacy(login, ProfileConstants.ABOUT_ME,
				ProfileConstants.ALL);
		socialNet.setFieldPrivacy(login, ProfileConstants.CONTACT_EMAIL,
				ProfileConstants.ALL);
		// socialNet.viewProfile(login, login).setAboutMe("* @ JP");
		// socialNet.viewProfile(login, login).setCountry("BR");
		// socialNet.viewProfile(login, login).setPhoto("xD");
		// socialNet.sendFriendshipRequest(login, "joao@lcc.ufcg.edu.br", "oi",
		// "conhecidos");
		// System.out.println(socialNet.viewPendingFriendship(login));
		// socialNet.acceptFriendshipRequest(login, "gafa@lsd.br",
		// "conhecidos");
		System.out.println(socialNet.listFriends(login));
		socialNet.checkProfile(login, ProfileConstants.ALL);
		System.out.println(socialNet.viewProfile(login, "joao@lcc.ufcg.edu.br")
				.toString());

		System.out.println("usuario " + login + " deslogando!");
		socialNet.logoff(login);

		telaInicial();
	}

	public static void menuPrincipal(String login) throws Exception {
		int opcao;
		final int LOGOFF = 0;
		final int MEU_PERFIL = 1;
		final int AMIGOS = 2;
		final int GRUPOS = 3;
		final int ARQUIVO = 4;
		final int PREF_USUARIOS = 5;
		final int CONFIGURACOES = 6;
		final String OPCOES = "1. Meu Perfil" + FIM_DE_LINHA + "2. Amigos"
				+ FIM_DE_LINHA + "3. Grupos" + FIM_DE_LINHA + "4. FriendList"
				+ FIM_DE_LINHA + "5. Preferências de Usuários" + FIM_DE_LINHA
				+ "6. Configurações" + FIM_DE_LINHA + "0. Logoff"
				+ FIM_DE_LINHA;

		do {
			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção desejada: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case MEU_PERFIL:
				break;
			case AMIGOS:
				menuAmigos(login);
				opcao = LOGOFF;
				break;
			case GRUPOS:
				menuGrupo(login);
				opcao = LOGOFF;
				break;
			case ARQUIVO:
				menuArquivo(login);
				opcao = LOGOFF;
				break;
			case PREF_USUARIOS:
				menuPreferenciasUsuario(login);
				opcao = LOGOFF;
				break;
			case CONFIGURACOES:
				menuConfiguracoes(login);
				opcao = LOGOFF;
				break;
			case LOGOFF:
				telaInicial();
				opcao = LOGOFF;
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != LOGOFF);
	}

	public static void menuAmigos(String login) throws Exception {
		int opcao;
		String friend, contact, group, message;
		final int MENU_PRINCIPAL = 0;
		final int ADICIONAR = 1;
		final int REMOVER = 2;
		final int LISTAR = 3;
		final int PROCURAR = 4;
		final int VISUALIZAR_CONVITES_PENDENTES = 5;
		final int VISUALIZAR_MEUS_PEDIDOS_AMIZADE = 6;
		final int ACEITAR = 7;
		final int RECUSAR = 8;
		final int RECOMENDACOES = 9;
		final String OPCOES = "1. Adicionar" + FIM_DE_LINHA + "2. Remover"
				+ FIM_DE_LINHA + "3. Listar" + FIM_DE_LINHA + "4. Procurar"
				+ FIM_DE_LINHA + "5. Visualizar Convites Pendentes"
				+ FIM_DE_LINHA + "6. Visualizar Meus Pedidos Pendentes"
				+ FIM_DE_LINHA + "7. Aceitar Convite" + FIM_DE_LINHA
				+ "8. Recusar Convite" + FIM_DE_LINHA
				+ "9. Visualizar Recomendações" + FIM_DE_LINHA
				+ "0. Menu Principal" + FIM_DE_LINHA;

		do {
			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção desejada: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case ADICIONAR:
				System.out.print("Informe o login do amigo a ser adicionado: ");
				friend = scan.nextLine();
				System.out
						.print("Informe a mensagem a ser enviada no convite: ");
				message = scan.nextLine();
				System.out
						.print("Informe o grupo onde o usuário será adicionado: ");
				group = scan.nextLine();
				try {
					socialNet.sendFriendshipRequest(login, friend, message,
							group);
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
				break;
			case REMOVER:
				System.out.print("Informe o login do amigo a ser removido: ");
				friend = scan.nextLine();
				try {
					socialNet.removeFriend(login, friend);
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
				break;
			case LISTAR:
				try {
					System.out.println(socialNet.listFriends(login));
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
				break;
			case PROCURAR:
				System.out
						.print("Informe o login do usuario a ser procurado: ");
				friend = scan.nextLine();
				try {
					System.out.println(socialNet.findNewFriend(login, friend)
							.toString());
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
				break;
			case VISUALIZAR_CONVITES_PENDENTES:
				try {
					System.out.println(socialNet.viewPendingFriendship(login));
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
				break;
			case VISUALIZAR_MEUS_PEDIDOS_AMIZADE:
				try {
					System.out.println(socialNet.viewSentFriendship(login));
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
				break;
			case ACEITAR:
				System.out.print("Informe o login do amigo a ser adicionado: ");
				contact = scan.nextLine();
				System.out.print("Informe o grupo do amigo a ser adicionado: ");
				group = scan.nextLine();

				try {
					socialNet.acceptFriendshipRequest(login, contact, group);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case RECUSAR:
				System.out.print("Informe o login do usuario a ser recusado: ");
				contact = scan.nextLine();
				socialNet.declineFriendshipRequest(login, contact);
				break;
			case RECOMENDACOES:
				try {
					System.out.println(socialNet.getRecommendFriends(login));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case MENU_PRINCIPAL:
				menuPrincipal(login);
				opcao = MENU_PRINCIPAL;
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != MENU_PRINCIPAL);

	}

	public static void menuArquivo(String login) throws Exception {
		int opcao;
		final int MENU_PRINCIPAL = 0, EXPORTAR = 1, IMPORTAR = 2;
		final String OPCOES = "1. Exportar Lista De Amigos" + FIM_DE_LINHA
				+ "2. Importar Lista De Amigs" + FIM_DE_LINHA
				+ "0. Menu Principal" + FIM_DE_LINHA;
		// import export
		do {
			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção desejada: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case EXPORTAR:
				// socialNet.exportFriendList(login, fileName, exportFields);
				// alguem que sabe como isso funciona...
				break;
			case IMPORTAR:
				// socialNet.restoreFriendList(login, file);
				// idem
				break;
			case MENU_PRINCIPAL:
				menuPrincipal(login);
				opcao = MENU_PRINCIPAL;
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != MENU_PRINCIPAL);
	}

	public static void menuGrupo(String login) throws Exception {
		int opcao;
		String friend, group;
		final int MENU_PRINCIPAL = 0, ADICIONAR = 1, REMOVER = 2, LISTAR = 3, PROCURAR = 4;
		final String OPCOES = "1. Adicionar Amigo a grupo" + FIM_DE_LINHA
				+ "2. Remover amigo do grupo" + FIM_DE_LINHA
				+ "3. Listar Grupo" + FIM_DE_LINHA + "4. Procurar Grupo"
				+ FIM_DE_LINHA + "0. Menu Principal" + FIM_DE_LINHA;
		do {
			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção desejada: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case ADICIONAR:
				System.out
						.print("Informe o login do amigo a ser adicionado no grupo: ");
				friend = scan.nextLine();
				System.out
						.print("Informe o grupo onde o amigo deve ser adicionado: ");
				group = scan.nextLine();
				try {
					socialNet.addGroupMember(login, group, friend);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case REMOVER:
				System.out
						.print("Informe o login do amigo a ser removido do grupo: ");
				friend = scan.nextLine();
				System.out
						.print("Informe o grupo onde o amigo deve ser removido: ");
				group = scan.nextLine();
				try {
					socialNet.removeGroupMember(login, group, friend);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case LISTAR:
				System.out.print("Informe o grupo que deseja listar: ");
				group = scan.nextLine();
				try {
					socialNet.listGroupMembers(login, group);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case PROCURAR:
				System.out.print("Informe o login do amigo: ");
				friend = scan.nextLine();
				System.out.print("Informe o grupo: ");
				group = scan.nextLine();
				try {
					socialNet.findGroupMember(login, friend, group);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case MENU_PRINCIPAL:
				menuPrincipal(login);
				opcao = MENU_PRINCIPAL;
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != MENU_PRINCIPAL);
	}

	public static void menuConfiguracoes(String login) throws Exception {
		int opcao;
		final int MENU_PRINCIPAL = 0, SETAR_PRIVACIDADE = 1, DELETAR = 2;
		final String OPCOES = "1. Setar Privacidade" + FIM_DE_LINHA
				+ "2. Deletar Minha Conta" + FIM_DE_LINHA + "0. Menu Principal"
				+ FIM_DE_LINHA;
		do {
			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção desejada: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case SETAR_PRIVACIDADE:
				break;
			case DELETAR:
				System.out.print("Deseja deletar a sua conta? (Y/n): ");
				String resposta = scan.nextLine();
				if (resposta.equalsIgnoreCase("Y"))
					try {
						socialNet.deleteUser(login);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				break;
			case MENU_PRINCIPAL:
				menuPrincipal(login);
				opcao = MENU_PRINCIPAL;
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != MENU_PRINCIPAL);
	}

	public static void menuPreferenciasUsuario(String login) throws Exception {
		int opcao;
		final int MENU_PRINCIPAL = 0, ADICIONAR = 1, REMOVER = 2, VISUALIZAR = 3;
		final String OPCOES = "1. Adicionar" + FIM_DE_LINHA + "2. Remover"
				+ FIM_DE_LINHA + "3. Visualizar" + FIM_DE_LINHA
				+ "0. Menu Principal" + FIM_DE_LINHA;
		do {
			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção desejada: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case ADICIONAR:
				break;
			case REMOVER:
				break;
			case VISUALIZAR:
				break;
			case MENU_PRINCIPAL:
				menuPrincipal(login);
				opcao = MENU_PRINCIPAL;
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != MENU_PRINCIPAL);
	}

}
