package main;

import interfaces.ProfileIF;

import java.util.List;
import java.util.Set;

import beans.Group;
import beans.UserAccount;
import controller.DBController;
import controller.GroupController;
import controller.UserController;

public class SocialNet {

	private static SocialNet social;
	private GroupController groupController = new GroupController();;
	private UserController userController = new UserController();;
	private DBController dbController = new DBController();;

	public synchronized static SocialNet getInstance() {
		if (social == null) {
			social = new SocialNet();
		}
		return social;
	}

	/**
	 * 
	 * Loga o usuario ao sistema
	 * @throws Exception
	 * @throws Exception
	 */
	public void login(String login, String senha) throws Exception {
		userController.login(login, senha);
	}

	/**
	 * Desloga o usuario do sistema
	 * 
	 * @throws Exception
	 */
	public void logoff(String email) throws Exception {
		userController.logoff(email);
	}

	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 * @throws Exception
	 */
	public boolean isLoged(String login) throws Exception {
		return userController.isLoged(login);
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
			String passwd) throws Exception {
		userController.createUser(name, lastName, email, passwd);
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
		return dbController.getUser(login);
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
		
		userController.updateUserProfile(login, aboutMe, age, photo, country, city, gender, contactEmail);
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
			throws Exception {
		
		userController.setFieldPrivacy(login, field, type);
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
		
		return userController.checkProfile(login, visibility);
	}

	public ProfileIF viewProfile(String viewer, String profileOwner) throws Exception {
		
		return userController.viewProfile(viewer, profileOwner);
	}

	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void addUserPreference(String login, String preference)
			throws Exception {

		userController.addUserPreferences(login, preference);
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
		return this.dbController.getUsers(login).getPreferences();
	}

	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void removeUserPreference(String login, String preference) throws Exception {
		
		userController.removeUserPreference(login, preference);
	}

	/**
	 * Deleta a conta do usuario
	 * 
	 * @param login
	 * @throws Exception
	 */
	public void deleteUser(String login) throws Exception {
		userController.deleteUser(login);
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
		return groupController.listGroupMembers(email, group);
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
		return groupController.getMembro(login, friend, group);
	}

	/**
	 * @param email
	 * @param group
	 * @param user
	 * @throws Exception
	 */
	public void addGroupMember(String email, String group, String user)
			throws Exception {
		groupController.addToGroup(email, group, user);
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
			throws Exception {
		groupController.removeFromGroup(email, group, user);
	}

	/**
	 * Listar os amigos
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public List<UserAccount> listFriends(String email) throws Exception {
		return userController.listFriends(email);
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
		return this.dbController.findNewFriend(login, friend);
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
			String message, String group) throws Exception {
		userController.sendFriendshipRequest(login, user, message, group);
	}

	/**
	 * Ver os convites de amizade. que est�o pendentes, para o usuario
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public List<String> viewPendingFriendship(String login) throws Exception {
		return userController.viewPendingFriendship(login);
	}

	/**
	 * Ver os convites de amizade enviados pelo usuario que est�o pendentes
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public List<String> viewSentFriendship(String login) throws Exception {
		return userController.viewSentFriendship(login);
	}

	/**
	 * Rejeita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 */
	public void declineFriendshipRequest(String login, String contact)
			throws Exception {
		userController.declineFriendshipRequest(login, contact);
	}

	/**
	 * Aceita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 * @param group
	 */
	public void acceptFriendshipRequest(String login, String contact,
			String group) throws Exception {
		userController.acceptFriendshipRequest(login, contact, group);
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
		return userController.getFriend(email, friend);
	}

	/**
	 * Remove um amigo da lista de amigos
	 * 
	 * @param login
	 * @param friend
	 * @throws Exception
	 */
	public void removeFriend(String login, String friend) throws Exception {
		UserAccount usuario = this.dbController.getUsers(login);
		UserAccount amigo;
		try {
			amigo = this.dbController.getUsers(friend);
		} catch (Exception e) {
			throw new Exception("Amigo não existente no sistema");
		}
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		amigo = userController.getFriend(login, friend);
		if (amigo == null)
			throw new Exception("Amigo não encontrado em nenhum grupo");
		for (Group grupo : usuario.getGroups()) {
			if (grupo.getUsers().contains(amigo)) {
				grupo.getUsers().remove(amigo);
				this.dbController.update(usuario);
				break;
			}
		}
		for (Group grupo : amigo.getGroups()) {
			if (grupo.getUsers().contains(usuario)) {
				grupo.getUsers().remove(usuario);
				this.dbController.update(amigo);
				break;
			}
		}
	}

	/**
	 * Recomenda amigos a partir da lista de amigos por grau de similaridade ou
	 * se ele estiver nos grupos melhores amigos ou familia
	 * 
	 * @param login
	 * @return
	 */
	public Set<UserAccount> getRecommendFriends(String login) throws Exception {
		return this.userController.getRecommendedFriends(login);
	}

	/**
	 * Gera um arquivo a partir da lista de amigos com os campos passados por
	 * parametro
	 * 
	 * @param login
	 * @param fileName
	 * @param exportFields
	 */
	public void exportFriendList(String login, String fileName,
			String exportFields) {

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
		userController.clean();
	}
	
	public static void main(String[] args) throws Exception {
		SocialNet net = new SocialNet();
		net.clean();
		
		net.createUser("Telles", "Nobrega", "telles@telles.com","123456");
		net.createUser("Japa","Hideo", "japa@japa.com", "123456");
		net.createUser("Rafael","Aquino","rafael@rafael.com","123456");
		net.login("telles@telles.com", "123456");
		net.sendFriendshipRequest("telles@telles.com", "rafael@rafael.com", "messagem", "familia");
		net.sendFriendshipRequest("telles@telles.com", "japa@japa.com", "messagem", "familia");
		net.logoff("telles@telles.com");
		net.login("rafael@rafael.com", "123456");
		net.acceptFriendshipRequest("rafael@rafael.com", "telles@telles.com", "familia");
		net.sendFriendshipRequest("rafael@rafael.com", "japa@japa.com", "messagem", "familia");
		net.logoff("rafael@rafael.com");
		net.login("japa@japa.com", "123456");
		net.acceptFriendshipRequest("japa@japa.com", "telles@telles.com", "familia");
		net.acceptFriendshipRequest("japa@japa.com", "rafael@rafael.com", "familia");
		net.logoff("japa@japa.com");
		net.login("telles@telles.com", "123456");
		System.out.println(net.listFriends("telles@telles.com"));
		
	}
}
