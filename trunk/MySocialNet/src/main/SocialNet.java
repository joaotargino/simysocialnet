package main;

import java.util.List;

import Util.ProfileConstants;
import beans.ContaUsuario;
import beans.Profile;
import controller.GerenciadorGrupo;
import controller.GerenciadorUsuario;

public class SocialNet {
	
	private static SocialNet social;
	private ContaUsuario usuario;
	private GerenciadorGrupo gerenciadorGrupo;
	
	public synchronized static SocialNet getInstance() {
		if(social == null) {
			social = new SocialNet();
		}
		return social;
	}
	
	/**
	 * Loga o usuario ao sistema
	 * @throws Exception 
	 * @throws Exception 
	 */
	public void login(String login, String senha) throws Exception {
		try {
			usuario = GerenciadorUsuario.getInstance().getUsuario(login);
			if ( usuario == null) throw new Exception("Login inválido ou senha incorreta");
			else if (!senha.equals(usuario.getSenha())) throw new Exception("Login inválido ou senha incorreta");
		} catch (Exception e) {
			throw new Exception("Login inválido ou senha incorreta");
		}
		if (usuario.isLoged()) throw new Exception("Usuário já logado");
		usuario.setLoged(true);
	}
	
	/**
	 * Desloga o usuario do sistema
	 */
	public void logoff(String email) {
		usuario.setLoged(false);
		usuario = null;
	}
	
	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 */
	public boolean estaLogado() {
		return (usuario != null);
	}
	
	/**
	 * Cria um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Nome do usuário deve ser informado"
	 * "Sobrenome do usuário deve ser informado"
	 * "E-mail do usuário deve ser informado"
	 * "Senha deve ser informada"
	 * "E-mail inválido"
	 * "A senha deve ter pelo menos 6 dígitos"
	 * "Login indisponível"
	 * 
	 * @param name - o nome do usuario
	 * @param lastName - o sobrenome do usuario
	 * @param email - o email do usuario
	 * @param passwd - a senha do usuario
	 * @throws Exception 
	 */
	public void createUser(String name, String lastName, String email, String passwd) throws Exception {
		ContaUsuario contaUsuario;
		try {
			contaUsuario = new ContaUsuario(name, lastName, passwd, email);
		}
		catch (Exception e) {
			if (e.getMessage().equals("String invalida")) throw new Exception("Login indisponível");
			else throw new Exception(e.getMessage());
		}
		GerenciadorUsuario.getInstance().adicionar(contaUsuario);
	}
	
	/**
	 * Recupera um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usuário não logado"
	 * 
	 * @param login - o email do usuario
	 * @return String contendo nome e sobrenome do usuario
	 * @throws Exception 
	 */
	public ContaUsuario getUser (String login) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado"); 
		return usuario;
	}
	
	/**
	 * Atualiza as informações do usuario,pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usuário não logado"
	 * 
	 * @param login
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 * @throws Exception 
	 */
	public void updateUserProfile(String login, String aboutMe, int age, String photo, String country, String city, String gender, String contactEmail) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		GerenciadorUsuario.getInstance().updateUserProfile(usuario);
	}
	
	/**
	 * Altera a privacidade de algum campo
	 * 
	 * @param login
	 * @param field
	 * @param type
	 * @throws Exception 
	 */
	public void setFieldPrivacy(String login, String field, String type) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		GerenciadorUsuario.getInstance().updateUserProfile(usuario);
	}
	
	/**
	 * Diz o que está disponivel no perfil dependendo da visibilidade, pode ocorrer os seguintes erros:
	 * 
	 * "Perfil inexistente"
	 * 
	 * @param login
	 * @param visibility
	 * @return Sting no formato campo(1)=valor(1),...,campo(n)=valor(n)]
	 * 			exemplo: "photo=photo.png,aboutMe=,gender=male"
	 * @throws Exception 
	 */
	public String viewProfile( String login, String visibility) throws Exception {
		
		ContaUsuario user;
		try {
			 user = GerenciadorUsuario.getInstance().getUsuario(login);
		} catch (Exception e) {
			throw new Exception("Perfil inexistente");
		}
		
		if (!(estaLogado()) && visibility.equals(ProfileConstants.JUST_ME)) throw new Exception("Usuário não logado");
		Profile profile = user.getProfile();
		return null;
	}
	
	
	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void addUserPreference(String login, String preference) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(login);
		if(!user.isLoged()) {
			throw new Exception("Usuário não logado");
		}
		GerenciadorUsuario.getInstance().addUserPreferences(user, preference);
	}
	
	/**
	 * Lista as preferencias do usuario
	 * 
	 * @param login
	 * @return String com as preferencias separada so por virgula (nao sei se isso eh uma re-
	 * presentacao de lista, mas como la nos testes tava como se fosse string...
	 */
	public List<String> listUserPreferences(String login) {
		return usuario.getPreferencias();
	}
	
	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void removeUserPreference (String login, String preference) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		user.getPreferencias().remove(preference);
	}
	
	/**
	 * Deleta a conta do usuario
	 * @param login
	 * @throws Exception 
	 */
	public void deleteUser(String login) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		GerenciadorUsuario.getInstance().remover(login);
	}
	
	/**
	 * Lista os membros de um grupo
	 * 
	 * @param email
	 * @param group
	 * @return
	 */
	public List<ContaUsuario> listGroupMembers(String email, String group) {
		return gerenciadorGrupo.getMembros(group);
	}
	
	/**
	 * Procura um mebro de um grupo
	 * 
	 * @param login
	 * @param friend
	 * @param group
	 * @return
	 */
	public ContaUsuario findGroupMember(String login,String friend, String group) {
		return gerenciadorGrupo.getMembro(friend, group);
	}
	
	/**
	 * @param email
	 * @param group
	 * @param user
	 */
	public void addGroupMember(String email, String group, String user) {
		gerenciadorGrupo.adicionar(user, group);
	}
	
	/**
	 * Remove um membro do grupo
	 * 
	 * @param email
	 * @param group
	 * @param user
	 */
	public void removeGroupMember(String email, String group, String user) {
		gerenciadorGrupo.remover(user, group);
	}
	
	/**
	 * Listar os amigos
	 * 
	 * @param email
	 * @return
	 * @throws Exception 
	 */
	public List<ContaUsuario> listFriends(String email) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(email);
		return user.getAmigos();
	}
	
	/**
	 * procura um novo amigo
	 * 
	 * @param login
	 * @param friend
	 * @return
	 * @throws Exception 
	 */
	public ContaUsuario findNewFriend(String login, String friend) throws Exception {
		return GerenciadorUsuario.getInstance().getUsuario(friend);
	}
	
	/**
	 * Envia um convite de amizade para outro usuario
	 * 
	 * @param login
	 * @param user
	 * @param message
	 * @param group
	 */
	public void sendFriendshipRequest(String login, String user, String message, String group) {
		
	}
	
	/**
	 * Ver os convites de amizade. que estão pendentes, para o usuario
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> viewPendingFriendship(String login) {
		return null;
	}
	
	/**
	 * Ver os convites de amizade enviados 
	 * pelo usuario que estão pendentes
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> viewSentFriendship (String login) {
		return null;
	}
	
	/**
	 * Rejeita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 */
	public void declineFriendshipRequest (String login, String contact){
		
	}
	
	/**
	 * Aceita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 * @param group
	 */
	public void acceptFriendshipRequest (String login, String contact, String group){
		
	}
	
	/**
	 * Pega um amigo da lista de amigos
	 * 
	 * @param email
	 * @param friend
	 * @return
	 */
	public ContaUsuario getFriend(String email, String friend) {
		return null;
	}
	
	/**
	 * Remove um amigo da lista de amigos
	 * 
	 * @param login
	 * @param friend
	 */
	public void removeFriend(String login, String friend) {
		
	}
	
	/**
	 * Recomenda amigos a partir da lista de amigos 
	 * por grau de similaridade ou se ele estiver nos 
	 * grupos melhores amigos ou familia
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> getRecommendFriends(String login) {
		return null;
	}
	
	/**
	 * Gera um arquivo a partir da lista de amigos com os campos passados por parametro 
	 * 
	 * @param login
	 * @param fileName
	 * @param exportFields
	 */
	public void exportFriendList(String login, String fileName, String exportFields) {
		
	}
	
	/**
	 * Recupera uma lista de amigos a partir de um arquivo
	 * 
	 * @param login
	 * @param file
	 */
	public void restoreFriendList(String login, String file) {
		
	}
	
	/**
	 * Limpar o banco de dados
	 */
	public void clean() {
		
	}
}
