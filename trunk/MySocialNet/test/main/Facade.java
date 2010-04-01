package main;

import java.util.List;

import beans.ContaUsuario;

/**
 * @author tellesmvn, rafael aquino
 *
 */
public class Facade {
	
	private SocialNet social = SocialNet.getInstance();
	
	/**
	 * Loga o usuario ao sistema
	 */
	public void login(){
		social.login();
	}
	
	/**
	 * Desloga o usuario do sistema
	 */
	public void logout() {
		social.logout();
		
	}
	
	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 */
	public boolean estaLogado() {
		return social.estaLogado();
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
	 */
	public void createUser(String name, String lastName, String email, String passwd) {
		social.createUser(name, lastName, email, passwd);
	}
	
	/**
	 * Recupera um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usuário não logado"
	 * 
	 * @param login - o email do usuario
	 * @return String contendo nome e sobrenome do usuario
	 */
	public ContaUsuario getUser (String login) {
		return social.getUser(login);
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
	 */
	public void updateUserProfile(String login, String aboutMe, int age, String photo, String country, String city, String gender, String contactEmail) {
		social.updateUserProfile(login, aboutMe, age, photo, country, city, gender, contactEmail);
	}
	
	/**
	 * Altera a privacidade de algum campo
	 * 
	 * @param login
	 * @param field
	 * @param type
	 */
	public void setFieldPrivacy(String login, String field, String type) {
		social.setFieldPrivacy(login, field, type);
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
	 */
	public String viewProfile( String login, String visibility) {
		return social.viewProfile(login, visibility);
	}
	
	
	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 */
	public void addUserPreference(String login, String preference) {
		social.addUserPreference(login, preference);
	}
	
	/**
	 * Lista as preferencias do usuario
	 * 
	 * @param login
	 * @return String com as preferencias separada so por virgula (nao sei se isso eh uma re-
	 * presentacao de lista, mas como la nos testes tava como se fosse string...
	 */
	public List<String> listUserPreferences(String login) {
		return social.listUserPreferences(login);
	}
	
	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 */
	public void removeUserPreference (String login, String preference) {
		social.removeUserPreference(login, preference);
	}
	
	/**
	 * Deleta a conta do usuario
	 * @param login
	 */
	public void deleteUser(String login) {
		social.deleteUser(login);
	}
	
	/**
	 * Lista os membros de um grupo
	 * 
	 * @param email
	 * @param group
	 * @return
	 */
	public List<ContaUsuario> listGroupMembers(String email, String group) {
		return social.listGroupMembers(email, group);
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
		return social.findGroupMember(login, friend, group);
	}
	
	/**
	 * @param email
	 * @param group
	 * @param user
	 */
	public void addGroupMember(String email, String group, String user) {
		social.addGroupMember(email, group, user);
	}
	
	/**
	 * Remove um membro do grupo
	 * 
	 * @param email
	 * @param group
	 * @param user
	 */
	public void removeGroupMember(String email, String group, String user) {
		social.removeGroupMember(email, group, user);
	}
	
	/**
	 * Listar os amigos
	 * 
	 * @param email
	 * @return
	 */
	public List<ContaUsuario> listFriends(String email) {
		return social.listFriends(email);
	}
	
	/**
	 * procura um novo amigo
	 * 
	 * @param login
	 * @param friend
	 * @return
	 */
	public ContaUsuario findNewFriend(String login, String friend) {
		return social.findNewFriend(login, friend);
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
		social.sendFriendshipRequest(login, user, message, group);
	}
	
	/**
	 * Ver os convites de amizade. que estão pendentes, para o usuario
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> viewPendingFriendship(String login) {
		return social.viewPendingFriendship(login);
	}
	
	/**
	 * Ver os convites de amizade enviados 
	 * pelo usuario que estão pendentes
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> viewSentFriendship (String login) {
		return social.viewSentFriendship(login);
	}
	
	/**
	 * Rejeita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 */
	public void declineFriendshipRequest (String login, String contact){
		social.declineFriendshipRequest(login, contact);
	}
	
	/**
	 * Aceita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 * @param group
	 */
	public void acceptFriendshipRequest (String login, String contact, String group){
		social.acceptFriendshipRequest(login, contact, group);
	}
	
	/**
	 * Pega um amigo da lista de amigos
	 * 
	 * @param email
	 * @param friend
	 * @return
	 */
	public ContaUsuario getFriend(String email, String friend) {
		return social.getFriend(email, friend);
	}
	
	/**
	 * Remove um amigo da lista de amigos
	 * 
	 * @param login
	 * @param friend
	 */
	public void removeFriend(String login, String friend) {
		social.removeFriend(login, friend);
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
		return social.getRecommendFriends(login);
	}
	
	/**
	 * Gera um arquivo a partir da lista de amigos com os campos passados por parametro 
	 * 
	 * @param login
	 * @param fileName
	 * @param exportFields
	 */
	public void exportFriendList(String login, String fileName, String exportFields) {
		social.exportFriendList(login, fileName, exportFields);
	}
	
	/**
	 * Recupera uma lista de amigos a partir de um arquivo
	 * 
	 * @param login
	 * @param file
	 */
	public void restoreFriendList(String login, String file) {
		social.restoreFriendList(login, file);
	}
}