package main;

import interfaces.ProfileIF;

import java.util.List;

import beans.Group;
import beans.UserAccount;
import facades.ControllersFacade;

public class SocialNet {

	private static SocialNet social;
	private ControllersFacade controllersFacade;

	public synchronized static SocialNet getInstance() {
		if (social == null) {
			social = new SocialNet();
		}
		return social;
	}
	
	
	protected SocialNet() { 
		controllersFacade = ControllersFacade.getInstance();
	}

	/**
	 * 
	 * Loga o usuario ao sistema
	 * @throws Exception
	 * @throws Exception
	 */
	public void login(String login, String senha) throws Exception {
	this.controllersFacade.login(login, senha);;
	}

	/**
	 * Desloga o usuario do sistema
	 * 
	 * @throws Exception
	 */
	public void logoff(String email) throws Exception {
	this.controllersFacade.logoff(email);;
	}

	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 * @throws Exception
	 */
	public boolean isLoged(String login) throws Exception {
		return this.controllersFacade.isLoged(login);
	}

	/**
	 * Cria um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Nome do usu�rio deve ser informado"
	 * "Sobrenome do usu�rio deve ser informado"
	 * "E-mail do usu�rio deve ser informado" "Senha deve ser informada"
	 * "E-mail inv�lido" "A senha deve ter pelo menos 6 d�gitos"
	 * "Login indispon�vel"
	 * 
	 * @param name
	 *            - o nome do usuario
	 * @param lastName
	 *            - o sobrenome do usuario
	 * @param email
	 *            - o email do usuario
	 * @param passwd
	 *            - a senha do usuario
	 * @throws Exception
	 */
	public void createUser(String name, String lastName, String email,
			String passwd) throws Exception {		this.controllersFacade.createUser(name, lastName, email, passwd);;
	}

	/**
	 * Recupera um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente" "Usu�rio n�o logado"
	 * 
	 * @param login
	 *            - o email do usuario
	 * @return String contendo nome e sobrenome do usuario
	 * @throws Exception
	 */
	public UserAccount getUser(String login) throws Exception {
		return this.controllersFacade.getUser(login);
	}

	/**
	 * Atualiza as informa��es do usuario,pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente" "Usu�rio n�o logado"
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
	public void updateUserProfile(String login, String aboutMe, String age,
			String photo, String country, String city, String gender,
			String contactEmail) throws Exception {
				this.controllersFacade.updateUserProfile(login, aboutMe, age, photo, country, city, gender, contactEmail);;
	}

	/**
	 * Altera a privacidade de algum campo
	 * 
	 * @param login
	 * @param field
	 * @param type
	 * @throws Exception
	 */
	public void setFieldPrivacy(String login, String field, String type)
			throws Exception {		this.controllersFacade.setFieldPrivacy(login, field, type);;
	}

	/**
	 * Diz o que est� disponivel no perfil dependendo da visibilidade, pode
	 * ocorrer os seguintes erros:
	 * 
	 * "Perfil inexistente"
	 * 
	 * @param login
	 * @param visibility
	 * @return Sting no formato campo(1)=valor(1),...,campo(n)=valor(n)]
	 *         exemplo: "photo=photo.png,aboutMe=,gender=male"
	 * @throws Exception
	 */
	public ProfileIF checkProfile(String login, String visibility) throws Exception {
		return this.controllersFacade.checkProfile(login, visibility);
	}

	public ProfileIF viewProfile(String viewer, String profileOwner) throws Exception {
		return this.controllersFacade.viewProfile(viewer, profileOwner);
	}

	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void addUserPreference(String login, String preference)throws Exception {		this.controllersFacade.addUserPreferences(login, preference);;
	}

	/**
	 * Lista as preferencias do usuario
	 * 
	 * @param login
	 * @return String com as preferencias separada so por virgula (nao sei se
	 *         isso eh uma re- presentacao de lista, mas como la nos testes tava
	 *         como se fosse string...
	 * @throws Exception
	 */
	public List<String> listUserPreferences(String login) throws Exception {
		return this.controllersFacade.getUser(login).getPreferences();
	}

	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void removeUserPreference(String login, String preference) throws Exception {		this.controllersFacade.removeUserPreference(login, preference);;
	}

	/**
	 * Deleta a conta do usuario
	 * 
	 * @param login
	 * @throws Exception
	 */
	public void deleteUser(String login) throws Exception {		this.controllersFacade.deleteUser(login);
	}

	/**
	 * Lista os membros de um grupo
	 * 
	 * @param email
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Group listGroupMembers(String email, String group) throws Exception {
		return this.controllersFacade.listGroupMembers(email, group);
	}

	/**
	 * Procura um mebro de um grupo
	 * 
	 * @param login
	 * @param friend
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public UserAccount findGroupMember(String login, String friend,
			String group) throws Exception {
		return this.controllersFacade.getMembro(login, friend, group);
	}

	/**
	 * @param email
	 * @param group
	 * @param user
	 * @throws Exception
	 */
	public void addGroupMember(String email, String group, String user) throws Exception {		this.controllersFacade.addToGroup(email, group, user);
	}

	/**
	 * Remove um membro do grupo
	 * 
	 * @param email
	 * @param group
	 * @param user
	 * @throws Exception
	 */
	public void removeGroupMember(String email, String group, String user)
			throws Exception {		this.controllersFacade.removeFromGroup(email, group, user);
	}

	/**
	 * Listar os amigos
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public List<UserAccount> listFriends(String email) throws Exception {
		return this.controllersFacade.listFriends(email);
	}

	/**
	 * procura um novo amigo
	 * 
	 * @param login
	 * @param friend
	 * @return
	 * @throws Exception
	 */
	public UserAccount findNewFriend(String login, String friend)
			throws Exception {
		return this.controllersFacade.findNewFriend(login, friend);
	}

	/**
	 * Envia um convite de amizade para outro usuario
	 * 
	 * @param login
	 * @param user
	 * @param message
	 * @param group
	 */
	public void sendFriendshipRequest(String login, String user,
			String message, String group) throws Exception {		this.controllersFacade.sendFriendshipRequest(login, user, message, group);
	}

	/**
	 * Ver os convites de amizade. que est�o pendentes, para o usuario
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public List<String> viewPendingFriendship(String login) throws Exception {
		return this.controllersFacade.viewPendingFriendship(login);
	}

	/**
	 * Ver os convites de amizade enviados pelo usuario que est�o pendentes
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public List<String> viewSentFriendship(String login) throws Exception {
		return this.controllersFacade.viewSentFriendship(login);
	}

	/**
	 * Rejeita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 */
	public void declineFriendshipRequest(String login, String contact)
			throws Exception {		this.controllersFacade.declineFriendshipRequest(login, contact);
	}

	/**
	 * Aceita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 * @param group
	 */
	public void acceptFriendshipRequest(String login, String contact, String group) throws Exception {		this.controllersFacade.acceptFriendshipRequest(login, contact, group);
	}

	/**
	 * Pega um amigo da lista de amigos
	 * 
	 * @param email
	 * @param friend
	 * @return
	 * @throws Exception
	 */
	public UserAccount getFriend(String email, String friend) throws Exception {
		return this.controllersFacade.getFriend(email, friend);
	}

	/**
	 * Remove um amigo da lista de amigos
	 * 
	 * @param login
	 * @param friend
	 * @throws Exception
	 */
	public void removeFriend(String login, String friend) throws Exception {		this.controllersFacade.removeFriend(login, friend);
	}

	/**
	 * Recomenda amigos a partir da lista de amigos por grau de similaridade ou
	 * se ele estiver nos grupos melhores amigos ou familia
	 * 
	 * @param login
	 * @return
	 */
	public List<UserAccount> getRecommendFriends(String login) throws Exception {
		return this.controllersFacade.getRecommendedFriends(login);
	}

	/**
	 * Gera um arquivo a partir da lista de amigos com os campos passados por
	 * parametro
	 * 
	 * @param login
	 * @param fileName
	 * @param exportFields
	 */
	public void exportFriendList(String login, String fileName, String exportFields) throws Exception {		this.controllersFacade.exportFriendList(login, fileName, exportFields);
	}

	/**
	 * Recupera uma lista de amigos a partir de um arquivo
	 * 
	 * @param login
	 * @param file
	 * @throws Exception 
	 */
	public String restoreFriendList(String login, String file) throws Exception {
		return this.controllersFacade.restoreFriendList(login,file);
	}

	/**
	 * Limpar o banco de dados
	 */
	public void clean() {		this.controllersFacade.clean();
	}
	
}