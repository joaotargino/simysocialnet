package facades;

import interfaces.ProfileIF;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import beans.Group;
import beans.UserAccount;
import controller.DBController;

public class UserAccountDBFacade {
	private DBController dbController;
	private static UserAccountDBFacade instancia;
	
	protected UserAccountDBFacade() {
		this.dbController = new DBController();
	}
	
	public synchronized static UserAccountDBFacade getInstance() {
		if(instancia == null) { 
			instancia = new UserAccountDBFacade();
		}
		return instancia;
	}
	
	//UserController
	public String getName(String login) throws Exception {
		return this.dbController.getUser(login).getName();
	}
	public void setName(String login, String nome) throws Exception {
		this.dbController.getUser(login).setName(nome);
	}
	public String getSurname(String login) throws Exception {
		return this.dbController.getUser(login).getSurname();
	}
	public void setSurname(String login, String sobrenome) throws Exception {
		this.dbController.getUsers(login).setSurname(sobrenome);
	}
	public String getDayOfBirth(String login) throws Exception {
		return this.dbController.getUser(login).getDayOfBirth();
	}
	public void setDayOfBirth(String login, String dataNascimento) throws Exception {
		this.dbController.getUser(login).setDayOfBirth(dataNascimento);
	}
	public String getPassword(String login) throws Exception {
		return this.dbController.getUser(login).getPassword();
	}
	public void setPassword(String login, String senha) throws Exception {
		this.dbController.getUser(login).setPassword(senha);
	}
	public String getEmail(String login) throws Exception {
		return this.dbController.getUser(login).getEmail();
	}
	public void setEmail(String login, String email) throws Exception {
		this.dbController.getUser(login).setEmail(email);
	}
	public List<UserAccount> getFriends(String login) throws Exception {
		return this.dbController.getUser(login).getFriends();
	}

	public UserAccount getFriend(String login, String email) throws Exception {
		return this.dbController.getUser(login).getFriend(email);
	}

	public List<String> getSentFriendshipAux(String login) throws Exception {
		return this.dbController.getUser(login).getSentFriendshipAux();
	}

	public void setSentFriendshipAux(String login, List<String> sentFriendshipAux) throws Exception {
		this.dbController.getUser(login).setSentFriendshipAux(sentFriendshipAux);
	}
	/**
	 * Remove amigo da lista
	 * @param email
	 * @throws Exception
	 */
	public void removeFriend(String login, String email) throws Exception {
		this.dbController.getUser(login).removeFriend(email);
	}

	public SortedMap<String,Group> getGroups(String login) throws Exception {
		return this.dbController.getUser(login).getGroups();
	}
	public void setGroups(String login, SortedMap<String,Group> grupos) throws Exception {
		this.dbController.getUser(login).setGroups(grupos);
	}

	public String toStringFullName(String login) throws Exception {
		return this.dbController.getUser(login).toStringFullName();
	}

	public String toString(String login) throws Exception {
		return this.dbController.getUser(login).toString();
	}

	public void setPreferences(String login, List<String> preferencias) throws Exception {
		this.dbController.getUser(login).setPreferences(preferencias);
	}

	public List<String> getPreferences(String login) throws Exception {
		return this.dbController.getUser(login).getPreferences();
	}

	public boolean equals(String login, Object obj) throws Exception {
		return this.dbController.getUser(login).equals(obj);
	}

	public int compareTo(String login, UserAccount o) throws Exception {
		return this.dbController.getUser(login).compareTo(o);
	}

	public void setLogged(String login, boolean loged) throws Exception {
		this.dbController.getUser(login).setLogged(loged);
	}

	public boolean isLogged(String login) throws Exception {
		return this.dbController.getUser(login).isLogged();
	}

	public void setProfileAll(String login, ProfileIF profileAll) throws Exception {
		this.dbController.getUser(login).setProfileAll(profileAll);
	}

	public ProfileIF getProfileAll(String login) throws Exception {
		return this.dbController.getUser(login).getProfileAll();
	}

	public void setProfileJustMe(String login, ProfileIF profileJustMe) throws Exception {
		this.dbController.getUser(login).setProfileJustMe(profileJustMe);
	}

	public ProfileIF getProfileJustMe(String login) throws Exception {
		return this.dbController.getUser(login).getProfileJustMe();
	}

	public void setProfileFriends(String login, ProfileIF profileFriends) throws Exception {
		this.dbController.getUser(login).setProfileFriends(profileFriends);
	}

	public ProfileIF getProfileFriends(String login) throws Exception {
		return this.dbController.getUser(login).getProfileFriends();
	}

	public Map<String,String> getPendingFriendship(String login) throws Exception {
		return this.dbController.getUser(login).getPendingFriendship();
	}

	public Map<String,String> getSentFriendship(String login) throws Exception {
		return this.dbController.getUser(login).getSentFriendship();
	}

	/**
	 * Update o profile do usuário
	 * @param usuario
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 * @throws Exception 
	 */
	public void updateUserProfile(String login,UserAccount usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) throws Exception {
		this.dbController.getUser(login).updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
	}

	/**
	 * Seta privacidade do campo
	 * @param owner
	 * @param visibility
	 * @param field
	 * @throws Exception
	 */
	public void setFieldPrivacy(String login, String owner, String visibility, String field) throws Exception {
		this.dbController.getUser(login).setFieldPrivacy(owner, visibility, field);
	}

	public ProfileIF getProfile(String login, UserAccount user, String visibility) throws Exception {
		return this.dbController.getUser(login).getProfile(user, visibility);
	}

	public void setSentFriendship(String login,Map<String,String> sentFriendship) throws Exception {
		this.dbController.getUser(login).setSentFriendship(sentFriendship);
	}

	public void setPendingFriendship(String login, Map<String,String> pendingFriendship) throws Exception {
		this.dbController.getUser(login).setPendingFriendship(pendingFriendship);
	}
	/**
	 * Aceita um convite de outro usuário
	 * @param contact
	 * @param group
	 * @param contato
	 * @throws Exception
	 */
	public void acceptFriendshipRequest(String login, String contact, String group,UserAccount contato)throws Exception {
		this.dbController.getUser(login).acceptFriendshipRequest(contact, group, contato);
	}

	public Group getGrupo(String login, UserAccount usuario, String group) throws Exception {
		return this.dbController.getUser(login).getGrupo(usuario, group);
	}

	/**
	 * Envia um convite para usuário
	 * @param login
	 * @param group
	 * @throws Exception
	 */
	public void sendFriendshipRequest(String login, String email, String group) throws Exception {
		this.dbController.getUser(login).sendFriendshipRequest(email, group);
	}

	/**
	 * Recebe o convite do usuário
	 * @param logado
	 * @param login
	 * @param message
	 */
	public void receiveFriendshipRequest(String login, UserAccount logado, String email, String message) throws Exception {
		this.dbController.getUser(login).receiveFriendshipRequest(logado, email, message);
	}

	/**
	 * @return a lista de usuários para qual foi enviado um convite
	 */
	public List<String> viewSentFriendship(String login) throws Exception {
		return this.dbController.getUser(login).viewSentFriendship();
	}

	/**
	 * @return a lista com convites recebidos
	 */
	public List<String> viewPendingFriendship(String login) throws Exception {
		return this.dbController.getUser(login).viewPendingFriendship();
	}

	/**
	 * Adiciona preferência do usuário
	 * @param preferencia
	 */
	public void addUserPreferences(String login, String preferencia) throws Exception {
		this.dbController.getUser(login).addUserPreferences(preferencia);
	}

	/**
	 * Remove convite da lista de convites enviados 
	 * @param login
	 * @param user
	 * @throws Exception
	 */
	public void removeSentFriendshipRequest(String login, String email, UserAccount user) throws Exception{
		this.dbController.getUser(login).removeSentFriendshipRequest(email, user);
	}

	/**
	 * Rejeita um convite recebido
	 * @param login
	 * @param map
	 */
	public void declineFriendshipRequest(String login, String email, Map<String,String> map) throws Exception {
		this.dbController.getUser(login).declineFriendshipRequest(email, map);
	}

	public UserAccount getFriend(String login, UserAccount friend, String fullName) throws Exception {
		return this.dbController.getUser(login).getFriend(friend, fullName);
	}
	/**
	 * @return a lista de de amigos recomendados
	 * @throws Exception
	 */
	public List<UserAccount> getRecommendedFriends(String login) throws Exception{
		return this.dbController.getUser(login).getRecommendedFriends();
	}

	/**
	 * Atualiza os amigos
	 */
	public void updateFriends(String login) throws Exception {
		this.dbController.getUser(login).updateFriends();
	}
	

	/**
	 * Adiciona um amigo ao DB
	 * @param contaUsuario
	 * @throws Exception
	 */
	public void addToDB(UserAccount contaUsuario) throws Exception {
		this.dbController.addToDB(contaUsuario);
	}

	/**
	 * Remove amigo do DB
	 * @param login
	 * @throws Exception
	 */
	public void removeFromDB(String login) throws Exception {
		this.dbController.removeFromDB(login);
	}	

	/**
	 * Retorna um usuário do bd
	 * @param login o login do usuário
	 * @return o usuário
	 * @throws Exception
	 */
	public UserAccount getUsers(String login) throws Exception {
		return this.dbController.getUsers(login);
	}

	/**
	 * @return todos os usuários 
	 */
	public List<UserAccount> getAllUsers() {
		return this.dbController.getAllUsers();
	}

	/**
	 * Procura um usuário no DB
	 * @param login o login do usuário
	 * @param friend o login do amigo
	 * @return o amigo
	 * @throws Exception 
	 */
	public UserAccount findNewFriend(String login, String friend) throws Exception {
		return this.dbController.findNewFriend(login, friend);
	}

	/**
	 * Atualiza o DB
	 * @param user
	 */
	public void update(UserAccount user) {
		this.dbController.update(user);
	}

	/**
	 * Atualiza o DB
	 * @param user
	 */
	public void update() {
		this.dbController.update();
	}


	/**
	 * Retorna usuário
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public UserAccount getUser(String login) throws Exception {
		return this.dbController.getUser(login);
	}

	/**
	 * Procura um usuário pelo nome completo dele
	 * @param completeName
	 * @return o usário, se encontrar
	 * @throws Exception
	 */
	public UserAccount getUserFromCompleteName(String completeName) throws Exception {
		return this.dbController.getUserFromCompleteName(completeName);
	}


}
