package facades;

import interfaces.ProfileIF;

import java.util.List;

import beans.Group;
import beans.UserAccount;
import controller.GroupController;
import controller.UserController;

public class ControllersFacade {
	
	private GroupController groupController;
	private UserController userController;
	private static ControllersFacade instancia;
	
	protected ControllersFacade() {
		this.groupController = new GroupController();
		this.userController = new UserController();
	}
	public synchronized static ControllersFacade getInstance() {
		if(instancia == null) { 
			instancia = new ControllersFacade();
		}
		return instancia;
	}
	
	//GroupController
	public void addToGroup(String login, String grupo, String loginToAdd) throws Exception {
		this.groupController.addToGroup(login, grupo, loginToAdd);
	} 

	/**
	 * Remove usuário do grupo
	 * @param login
	 * @param grupo
	 * @param loginToRemove
	 * @throws Exception
	 */
	public void removeFromGroup(String login, String grupo, String loginToRemove) throws Exception {
		this.groupController.removeFromGroup(login, grupo, loginToRemove);
	}
	
	public Group getGroup(UserAccount usuario, String group) throws Exception {
		return this.groupController.getGroup(usuario, group);
	}

	public UserAccount getMembro(String login, String friend, String group) throws Exception {
		return this.groupController.getMembro(login, friend, group);
	}

	/**
	 * Lista todos os membros do grupo
	 * @param email
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Group listGroupMembers(String email, String group) throws Exception {
		return this.groupController.listGroupMembers(email, group);
	}
	
	//UserController
	public List<UserAccount> getUsuariosLogados() {
		return this.userController.getUsuariosLogados();
	}

	public boolean isLoged(String login) throws Exception {
		return this.userController.isLoged(login);
	}

	/**
	 * Faz o login no sistema
	 * @param login
	 * @param senha
	 * @throws Exception
	 */
	public void login(String login, String senha) throws Exception {
		this.userController.login(login, senha);
	}

	/**
	 * Desloga do sistema
	 * @param login
	 * @throws Exception
	 */
	public void logoff(String login) throws Exception {
		this.userController.logoff(login);
	}

	/**
	 * Adiciona as preferências do usuário
	 * @param login
	 * @param preferencia
	 * @throws Exception
	 */
	public void addUserPreferences(String login,String preferencia) throws Exception {
		this.userController.addUserPreferences(login, preferencia);
	}

	/**
	 * Envia um convite para um amigo
	 * @param login
	 * @param user
	 * @param message
	 * @param group
	 * @throws Exception
	 */
	public void sendFriendshipRequest(String login, String user, String message, String group) throws Exception {
		this.userController.sendFriendshipRequest(login, user, message, group);
	}

	/**
	 * Aceita convite do usuário
	 * @param login
	 * @param contact
	 * @param group
	 * @throws Exception
	 */
	public void acceptFriendshipRequest(String login, String contact,String group) throws Exception {
		this.userController.acceptFriendshipRequest(login, contact, group);
	}

	/**
	 * Limpa o DB
	 */
	public void clean() {
		this.userController.clean();
	}

	/**
	 * Lista os amigos
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public List<UserAccount> listFriends(String email) throws Exception {
		return this.userController.listFriends(email);
	}

	/**
	 * Ver lista de convites recebidos
	 * @param login
	 * @return a lista de convites recebidos
	 * @throws Exception
	 */
	public List<String> viewPendingFriendship(String login) throws Exception{
		return this.userController.viewPendingFriendship(login);
	}

	/**
	 * Ver lista de convites enviados
	 * @param login
	 * @return a lista de convites enviados
	 * @throws Exception
	 */
	public List<String> viewSentFriendship(String login)throws Exception {
		return this.userController.viewSentFriendship(login);
	}

	/**
	 * Rejeita um convite enviado
	 * @param login
	 * @param contact
	 * @throws Exception
	 */
	public void declineFriendshipRequest(String login, String contact) throws Exception{
		this.userController.declineFriendshipRequest(login, contact);
	}

	public UserAccount getFriend(String email, String friend) throws Exception {
		return this.userController.getFriend(email, friend);
	}

	public List<UserAccount> getRecommendedFriends(String login) throws Exception{
		return this.userController.getRecommendedFriends(login);
	}

	/**
	 * Cria um novo usuário
	 * @param name
	 * @param lastName
	 * @param email
	 * @param passwd
	 * @throws Exception
	 */
	public void createUser(String name, String lastName, String email,
			String passwd) throws Exception {
		this.userController.createUser(name, lastName, email, passwd);
	}

	/**
	 * Atualiza profile do usuário
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
	public void updateUserProfile(String login, String aboutMe, String age,
			String photo, String country, String city, String gender,
			String contactEmail) throws Exception {

		this.userController.updateUserProfile(login, aboutMe, age, photo, country, city, gender, contactEmail);
	}

	/**
	 * Seta a privacidade de um campo
	 * @param login
	 * @param field
	 * @param type
	 * @throws Exception
	 */
	public void setFieldPrivacy(String login, String field, String type) throws Exception {
		this.userController.setFieldPrivacy(login, field, type);
	}

	/**
	 * Checa um profile
	 * @param login
	 * @param visibility a visibilidade que se deseja checar
	 * @return o profile
	 * @throws Exception
	 */
	public ProfileIF checkProfile(String login, String visibility) throws Exception {
		return this.userController.checkProfile(login, visibility);
	}

	/**
	 * Mostra o profile
	 * @param viewer
	 * @param profileOwner
	 * @return
	 * @throws Exception
	 */
	public ProfileIF viewProfile(String viewer, String profileOwner) throws Exception {
		return this.userController.viewProfile(viewer, profileOwner);
	}

	/**
	 * Remove preferência de um usuário
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void removeUserPreference(String login, String preference) throws Exception {
		this.userController.removeUserPreference(login, preference);
	}

	/**
	 * Remove um usuário
	 * @param login
	 * @throws Exception
	 */
	public void deleteUser(String login) throws Exception {
		this.userController.deleteUser(login);
	}


	/**
	 * Exporta lista de amigos
	 * @param usuario
	 * @param login
	 * @param fileName
	 * @param exportFields
	 * @throws Exception
	 */
	public void exportFriendList(String login, String fileName, String exportFields) throws Exception {
		this.userController.exportFriendList(login, fileName, exportFields);
	}

	/**
	 * Recupera lista de amigos de um arquivo
	 * @param usuario
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String restoreFriendList(String login, String fileName) throws Exception{
		return this.userController.restoreFriendList(login, fileName);
	}

	/**
	 * Recupera um único amigo
	 * @param usuario
	 * @param friendInformation
	 * @return
	 */
	public String restoreSingleFriend(UserAccount usuario,String friendInformation) {
		return this.userController.restoreSingleFriend(usuario, friendInformation);
	}

	/**
	 * Remove amigo
	 * @param login
	 * @param friend
	 * @throws Exception
	 */
	public void removeFriend(String login, String friend) throws Exception {
		this.userController.removeFriend(login, friend);
	}

	public UserAccount getUser(String login) throws Exception{
		return this.userController.getUser(login);
	}

	public UserAccount getUsers(String login) throws Exception{
		return this.userController.getUsers(login);
	}

	public UserAccount findNewFriend(String login, String friend) throws Exception{
		return this.userController.findNewFriend(login, friend);
	}

}
