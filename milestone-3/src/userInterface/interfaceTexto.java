package userInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import usuario.Usuario;

public class interfaceTexto {
	
	private final static Scanner scan = new Scanner(System.in);
	private static FacadeUser facadeTexto;
	
	
	static String fimDeLinha = System.getProperty("line.separator");

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		int entrada = 3;
		String nome, sobrenome, email, senha, login, password = null;
		facadeTexto = new FacadeUser();
		
		while(entrada != 2){
			mostraTelaInicial();
			System.out.print("Digite a opcao desejada: ");
			entrada = scan.nextInt();
			switch (entrada) {
			case 0:
				System.out.print("Nome: ");
                nome = scan.next();
                System.out.print("Sobrenome: ");
                sobrenome = scan.next();
                System.out.print("Email: ");
                email = scan.next();
                System.out.print("Senha: ");
                senha = scan.next();
                try {
                        facadeTexto.createUser(nome, sobrenome, email, senha);
                } catch (Exception e) {
                        System.out.println(e.getMessage() + fimDeLinha + "Voce esta sendo redirecionado ao menu inical..." + fimDeLinha);
                        
                }
                break;
				
			case 1:
				System.out.print("login: ");
                login = scan.next();
                System.out.print("senha: ");
                password = scan.next();
                try {
                	facadeTexto.login(login, password);
                	menuDeLogin(login);
                	int opcaoDeLogin = 1;
                	while(opcaoDeLogin != 0){
                		switch (opcaoDeLogin) {
						case 0:
							
							break;

						default:
							break;
						}
                	}
                	
                	
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(e.getMessage() + fimDeLinha + "Voce esta sendo redirecionado ao menu inical..." + fimDeLinha);
                }
				break;
				
			case 2:
				System.out.println("Voce esta saindo do MySocialNet...");
				System.exit(0);				
				break;
				
			default:
				System.out.println("Entrada invalida, por favor tente novamente ou saia do programa!" + fimDeLinha);
				break;
			}
			
		}
	}

	/**
	 * Indica as opcoes 
	 */
	private static void mostraTelaInicial() {
		System.out.println(fimDeLinha + ".:: MySocialNet ::." + fimDeLinha);
		System.out.println("0. Criar conta" + fimDeLinha + "1. Logar" + fimDeLinha + "2. Sair" + fimDeLinha);
	}
	
	private static void menuDeLogin(String login){
		System.out.println(fimDeLinha + ".:: MySocialNet ::." + fimDeLinha);
		System.out.println("0. Visualisar Meu Perfil" + fimDeLinha + "1. Visualizar Amigos" + fimDeLinha + "2. Visualizar Grupos" + fimDeLinha + "3. Visualizar Arquivos" + fimDeLinha
			          + "4. Visualizar Configurações" + fimDeLinha + "5. Fazer Logoff" + fimDeLinha);
		System.out.print("Digite a opção desejada: ");
		int opcao = scan.nextInt();
		while(opcao != 5){
			switch(opcao){
				case 0:
					visualizarPerfil(login);
					opcao = 0;
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				default:
					System.out.println("Escolha uma opção válida.");
					break;
					
			}	
		}
		
		
	}
	
	private static void visualizarPerfil(String login){
		System.out.println(fimDeLinha + ".:: MySocialNet ::." + fimDeLinha);
		System.out.println("0. Visualisar Menu Principal" + fimDeLinha + "1. Visualizar Perfil" + fimDeLinha + 
				"2. Alterar dados do Perfil" + fimDeLinha + "3. Setar nova Privacidade" + fimDeLinha
			    + "4. Minhas Preferencias" + fimDeLinha);
		System.out.print("Digite a opção desejada: ");
		
		int opcao = scan.nextInt();
		while(opcao != 0){
			switch(opcao){
				case 0:
					menuDeLogin(login);
					opcao = 0;
					break;
				case 1:
					 try {
						 System.out.println(facadeTexto.getUsuario(login).viewPerfil());
                     } catch (Exception e) {System.out.println(e.getMessage());}
                 break;
					
				case 2:
					menuAlterarDadosDoPerfil(login);
					break;

				case 3:
					menuSetaPrivacidade(login);
					break;
				case 4:
					menuSetaMinhasPreferencias(login);
					break;
				default:
					System.out.println("Escolha uma opção válida.");
					break;
					
			}	
		}

	}
	
	private static void menuAlterarDadosDoPerfil(String login){
		System.out.println(fimDeLinha + ".:: MySocialNet ::." + fimDeLinha);
		System.out.println("0. Voltar a Visualizar Perfil" + fimDeLinha + "Quero alterar Dados: " + fimDeLinha
				+ "1. Sobre Mim" + fimDeLinha + 
				"2. Minha Idade" + fimDeLinha + "3. Minha Foto" + fimDeLinha 
				+ "4. Meu país" + fimDeLinha + "5. Minha Cidade" + fimDeLinha 
				+ "6. Gênero" + fimDeLinha + "7. Meu Email para Contato" + fimDeLinha);
		System.out.print("Digite a opção desejada: ");
		
		int opcao = scan.nextInt();
		while(opcao != 0){
			switch(opcao){
				case 0:
					visualizarPerfil(login);
					opcao = 0;
					break;
				case 1:
					System.out.print("Sobre mim: "); 
					String aboutMe = scan.nextLine();
					try{
						Usuario me = facadeTexto.getUsuario(login);
						facadeTexto.updateUserProfile(login, aboutMe, me.getPerfil().getAge().getCampo(), 
								me.getPerfil().getPhoto().getCampo(), me.getPerfil().getCountry().getCampo(), 
								me.getPerfil().getCity().getCampo(), me.getPerfil().getGender().getCampo(),
								me.getPerfil().getContactEmail().getCampo());
					}catch(Exception e){ 
						System.out.println(e.getMessage());
					}
                 break;
					
				case 2:
					System.out.print("Minha idade: "); 
					String idade = scan.nextLine();
					try{
						Usuario me = facadeTexto.getUsuario(login);
						facadeTexto.updateUserProfile(login, me.getPerfil().getAboutMe().getCampo(), idade, 
								me.getPerfil().getPhoto().getCampo(), me.getPerfil().getCountry().getCampo(), 
								me.getPerfil().getCity().getCampo(), me.getPerfil().getGender().getCampo(),
								me.getPerfil().getContactEmail().getCampo());
					}catch(Exception e){ 
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					System.out.print("Minha Foto: "); 
					String foto = scan.nextLine();
					try{
						Usuario me = facadeTexto.getUsuario(login);
						facadeTexto.updateUserProfile(login, me.getPerfil().getAboutMe().getCampo(), me.getPerfil().getAge().getCampo(), 
								foto, me.getPerfil().getCountry().getCampo(), 
								me.getPerfil().getCity().getCampo(), me.getPerfil().getGender().getCampo(),
								me.getPerfil().getContactEmail().getCampo());
					}catch(Exception e){ 
						System.out.println(e.getMessage());
					}
					break;
				case 4:
					System.out.print("Meu País: "); 
					String pais = scan.nextLine();
					try{
						Usuario me = facadeTexto.getUsuario(login);
						facadeTexto.updateUserProfile(login, me.getPerfil().getAboutMe().getCampo(), me.getPerfil().getAge().getCampo(), 
								me.getPerfil().getPhoto().getCampo(), pais,	me.getPerfil().getCity().getCampo(), me.getPerfil().getGender().getCampo(),
								me.getPerfil().getContactEmail().getCampo());
					}catch(Exception e){ 
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					System.out.print("Meu Cidade: "); 
					String cidade = scan.nextLine();
					try{
						Usuario me = facadeTexto.getUsuario(login);
						facadeTexto.updateUserProfile(login, me.getPerfil().getAboutMe().getCampo(), me.getPerfil().getAge().getCampo(), 
								me.getPerfil().getPhoto().getCampo(), me.getPerfil().getCountry().getCampo(), cidade, me.getPerfil().getGender().getCampo(),
								me.getPerfil().getContactEmail().getCampo());
					}catch(Exception e){ 
						System.out.println(e.getMessage());
					}
					break;
				case 6:
					System.out.print("Genero: "); 
					String genero = scan.nextLine();
					try{
						Usuario me = facadeTexto.getUsuario(login);
						facadeTexto.updateUserProfile(login, me.getPerfil().getAboutMe().getCampo(), me.getPerfil().getAge().getCampo(), 
								me.getPerfil().getPhoto().getCampo(), me.getPerfil().getCountry().getCampo(), me.getPerfil().getCity().getCampo(), genero,
								me.getPerfil().getContactEmail().getCampo());
					}catch(Exception e){ 
						System.out.println(e.getMessage());
					}
					break;
				case 7:
					System.out.print("Email de contato: "); 
					String emailContato = scan.nextLine();
					try{
						Usuario me = facadeTexto.getUsuario(login);
						facadeTexto.updateUserProfile(login, me.getPerfil().getAboutMe().getCampo(), me.getPerfil().getAge().getCampo(), 
								me.getPerfil().getPhoto().getCampo(), me.getPerfil().getCountry().getCampo(), me.getPerfil().getCity().getCampo(), me.getPerfil().getGender().getCampo(),
								emailContato);
					}catch(Exception e){ 
						System.out.println(e.getMessage());
					}
					break;
				default:
					System.out.println("Escolha uma opção válida.");
					break;
					
			}	
		}
	} // alteraDadosDoPerfil
	
	private static void menuSetaPrivacidade(String login){
		
	}
	
	private static void menuSetaMinhasPreferencias(String login) {
		
	}
	
}
