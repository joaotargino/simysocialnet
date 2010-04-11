package main;

import java.util.Scanner;

import Util.ProfileConstants;

//TODO mudar o nome das excecoes (tipo e.getMessage() , e1... e2...)
//TODO alguem tem que rever uns parametros de classe q tao despadronizados =x depois lembro quais!
//TODO botar uma linha pra pedir ENTER e um sc.nextLine() depois das excecoes? 
//TODO porra, esqueci de visitar o perfil qdo procura a pessoa ¬¬

public class UserInterface {

	private final static String FIM_DE_LINHA = System
			.getProperty("line.separator");
	private final static String menuPrincipal = "";
	private final static Scanner scan = new Scanner(System.in);
	private static SocialNet socialNet = new SocialNet();

	public static void main(String[] args) throws Exception {
		telaInicial();
	}

	private static int printOpcoes(final String OPCOES) {
		int opcao;
		System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
		System.out.print(OPCOES + FIM_DE_LINHA
				+ "Informe a opção desejada: ");
		opcao = scan.nextInt();
		scan.nextLine();
		return opcao;
	}

	public static void telaInicial() throws Exception {
		String login, senha, name = null, lastName = null, email = null, passwd = null;
		int opcao;
		final int SAIR = 0, CRIAR = 1, LOGAR = 2;
		final String OPCOES = "1. Criar nova conta" + FIM_DE_LINHA
				+ "2. Fazer Login" + FIM_DE_LINHA + "0. Sair" + FIM_DE_LINHA;
		do {
			opcao = printOpcoes(OPCOES);

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
		socialNet.viewProfile(login, login).setAboutMe("* @ JP");
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
				+ FIM_DE_LINHA + "3. Grupos" + FIM_DE_LINHA + "4. Arquivo"
				+ FIM_DE_LINHA + "5. Preferências de Usuários" + FIM_DE_LINHA
				+ "6. Configurações" + FIM_DE_LINHA + "0. Logoff"
				+ FIM_DE_LINHA;

		do {
			opcao = printOpcoes(OPCOES);

			switch (opcao) {
			case MEU_PERFIL:
				meuPerfil(login);
				opcao = LOGOFF;
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

	public static void meuPerfil(String login) throws Exception {
		int opcao;
		String friend, contact, group, message;
		final int MENU_PRINCIPAL = 0;
		final int VISUALIZAR = 1;
		final int ALTERAR = 2;
		final int SETAR_PRIVACIDADE = 3;
		final int PROCURAR = 4;
		final int VISUALIZAR_CONVITES_PENDENTES = 5;
		final int VISUALIZAR_MEUS_PEDIDOS_AMIZADE = 6;
		final int ACEITAR = 7;
		final int RECUSAR = 8;
		final int RECOMENDACOES = 9;
		final String OPCOES = "1. Visualizar meu perfil" + FIM_DE_LINHA + "2. Alterar dados do meu perfil"
				+ FIM_DE_LINHA + "3. Setar Privacidade" + FIM_DE_LINHA + "4. Procurar"
				+ FIM_DE_LINHA + "5. Visualizar Convites Pendentes"
				+ FIM_DE_LINHA + "6. Visualizar Meus Pedidos Pendentes"
				+ FIM_DE_LINHA + "7. Aceitar Convite" + FIM_DE_LINHA
				+ "8. Recusar Convite" + FIM_DE_LINHA
				+ "9. Visualizar Recomendações" + FIM_DE_LINHA
				+ "0. Menu Principal" + FIM_DE_LINHA;
		do {

			opcao = printOpcoes(OPCOES);

			switch (opcao) {
			case VISUALIZAR:
				try {
					System.out.println(socialNet.viewProfile(login, login)
							.toString());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case ALTERAR:
				alterarPerfil(login);
				opcao = MENU_PRINCIPAL;
				break;
			case SETAR_PRIVACIDADE:
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

	public static void alterarPerfil(String login) throws Exception {
		int opcao;
		final int VOLTAR = 0;
		final int ABOUT_ME = 1;
		final int AGE = 2;
		final int CITY = 3;
		final int COUNTRY = 4;
		final int CONTACT_MAIL = 5;
		final int GENDER = 6;
		final int PHOTO = 7;
		final String OPCOES = "1. About Me" + FIM_DE_LINHA + "2. Idade"
				+ FIM_DE_LINHA + "3. Cidade" + FIM_DE_LINHA + "4. País"
				+ FIM_DE_LINHA + "5. E-mail de contato" + FIM_DE_LINHA
				+ "6. Sexo" + FIM_DE_LINHA + "7. Foto" + FIM_DE_LINHA

				+ "0. Voltar" + FIM_DE_LINHA;
		do {

			System.out.println(FIM_DE_LINHA + "MySocialNet" + FIM_DE_LINHA);
			System.out.print(OPCOES + FIM_DE_LINHA
					+ "Informe a opção que deseja alterar: ");
			opcao = scan.nextInt();
			scan.nextLine();

			switch (opcao) {
			case ABOUT_ME:
				System.out.print("About Me: ");
				String aboutMe = scan.nextLine();
				try {
					socialNet.viewProfile(login, login).setAboutMe(aboutMe);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case AGE:
				break;
			case CITY:
				break;
			case CONTACT_MAIL:
				break;
			case COUNTRY:
				break;
			case GENDER:
				break;
			case PHOTO:
				break;
			case VOLTAR:
				meuPerfil(login);
				opcao = VOLTAR;
				break;
			default:
				System.out.println("Opção Inválida");
				break;
			}
		} while (opcao != VOLTAR);
	}

	// TODO colocar um view perfil pra amigo ae...
	public static void menuAmigos(String login) throws Exception {
		int opcao;
		String friend, contact, group, message;
		final int MENU_PRINCIPAL = 0;
		final int ADICIONAR = 1;
		final int REMOVER = 2;
		final int LISTAR = 3;
		final int PROCURAR = 4;
		final int VISITAR = 5;
		final int VISUALIZAR_CONVITES_PENDENTES = 6;
		final int VISUALIZAR_MEUS_PEDIDOS_AMIZADE = 7;
		final int ACEITAR = 8;
		final int RECUSAR = 9;
		final int RECOMENDACOES = 10;
		final String OPCOES = "1. Adicionar" + FIM_DE_LINHA + "2. Remover"
				+ FIM_DE_LINHA + "3. Listar" + FIM_DE_LINHA + "4. Procurar"
				+ FIM_DE_LINHA + "5. Visitar perfil"
				+ FIM_DE_LINHA + "6. Visualizar Convites Pendentes"
				+ FIM_DE_LINHA + "7. Visualizar Meus Pedidos Pendentes"
				+ FIM_DE_LINHA + "8. Aceitar Convite" + FIM_DE_LINHA
				+ "9. Recusar Convite" + FIM_DE_LINHA
				+ "10. Visualizar Recomendações" + FIM_DE_LINHA
				+ "0. Menu Principal" + FIM_DE_LINHA;

		do {
			opcao = printOpcoes(OPCOES);

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
						.print("Informe o login ou nome completo do usuario a ser procurado: ");
				friend = scan.nextLine();
				try {
					System.out.println(socialNet.findNewFriend(login, friend)
							.toString());
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
				break;
			case VISITAR:
				System.out
						.print("Informe o login do usuario: ");
				friend = scan.nextLine();
				try {
					System.out.println(socialNet.viewProfile(login, friend)
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
				try {
					socialNet.declineFriendshipRequest(login, contact);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
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

	// no idea de como funfa
	public static void menuArquivo(String login) throws Exception {
		int opcao;
		final int MENU_PRINCIPAL = 0, EXPORTAR = 1, IMPORTAR = 2;
		final String OPCOES = "1. Exportar Lista De Amigos" + FIM_DE_LINHA
				+ "2. Importar Lista De Amigs" + FIM_DE_LINHA
				+ "0. Menu Principal" + FIM_DE_LINHA;
		do {
			opcao = printOpcoes(OPCOES);

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
			opcao = printOpcoes(OPCOES);

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

	// nao sei ainda como vai ficar esse field e type. olho amanha cedo (serao
	// dadas as opcoes ao user, ne?)
	public static void menuConfiguracoes(String login) throws Exception {
		int opcao;
		String field, type;
		final int MENU_PRINCIPAL = 0, SETAR_PRIVACIDADE = 1, DELETAR = 2;
		final String OPCOES = "1. Setar Privacidade" + FIM_DE_LINHA
				+ "2. Deletar Minha Conta" + FIM_DE_LINHA + "0. Menu Principal"
				+ FIM_DE_LINHA;
		do {
			opcao = printOpcoes(OPCOES);

			switch (opcao) {
			case SETAR_PRIVACIDADE:
				System.out
						.print("Informe o campo para alterar a privacidade: ");
				field = scan.nextLine();
				System.out.print("Informe o tipo de privacidade: ");
				type = scan.nextLine();
				try {
					socialNet.setFieldPrivacy(login, field, type);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
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

	// PRECISO REVER ISSO!!!!!!!!!!!!!!!!!!!!! vlw.
	public static void menuPreferenciasUsuario(String login) throws Exception {
		int opcao;
		String preference;
		final int MENU_PRINCIPAL = 0, ADICIONAR = 1, REMOVER = 2, VISUALIZAR = 3;
		final String OPCOES = "1. Adicionar" + FIM_DE_LINHA + "2. Remover"
				+ FIM_DE_LINHA + "3. Visualizar" + FIM_DE_LINHA
				+ "0. Menu Principal" + FIM_DE_LINHA;
		do {
			opcao = printOpcoes(OPCOES);

			switch (opcao) {
			case ADICIONAR:
				System.out.println("preferencia: ");
				preference = scan.nextLine();
				try {
					socialNet.addUserPreference(login, preference);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case REMOVER:
				System.out.println("preferencia: ");
				preference = scan.nextLine();
				try {
					socialNet.removeUserPreference(login, preference);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case VISUALIZAR:
				try {
					System.out.println(socialNet.listUserPreferences(login));
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

}
