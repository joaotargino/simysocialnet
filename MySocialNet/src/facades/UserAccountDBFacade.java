//package facades;
//
//import interfaces.ProfileIF;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.SortedMap;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
//import controller.DBController;
//import Util.Util;
//import beans.Group;
//import beans.UserAccount;
//
//public class UserAccountDBFacade {
//	private UserAccount userAccount;
//	private DBController dbController;
//	private Group group;
//	private static UserAccountDBFacade instancia;
//	
//	protected UserAccountDBFacade() {
//		this.userAccount = new UserAccount();
//		this.dbController = new DBController();
//	}
//	
//	public synchronized static UserAccountDBFacade getInstance() {
//		if(instancia == null) { 
//			instancia = new UserAccountDBFacade();
//		}
//		return instancia;
//	}
//	
//	//UserController
//	public String getName() {
//		return this.userAccount.getName();
//	}
//	public void setName(String nome) throws Exception {
//		this.userAccount.setName(nome);
//	}
//	public String getSurname() {
//		return this.userAccount.getSurname();
//	}
//	public void setSurname(String sobrenome) throws Exception {
//		this.userAccount.setSurname(sobrenome);
//	}
//	public String getDayOfBirth() {
//		return this.userAccount.getDayOfBirth();
//	}
//	public void setDayOfBirth(String dataNascimento) throws Exception {
//		this.userAccount.setDayOfBirth(dataNascimento);
//	}
//	public String getPassword() {
//		return this.userAccount.getPassword();
//	}
//	public void setPassword(String senha) throws Exception {
//		this.userAccount.setPassword(senha);
//	}
//	public String getEmail() {
//		return this.userAccount.getEmail();
//	}
//	public void setEmail(String email) throws Exception {
//		this.userAccount.setEmail(email);
//	}
//	public List<UserAccount> getFriends() {
//		return this.userAccount.getFriends();
//	}
//
//	public UserAccount getFriend(String email) throws Exception {
//		return this.userAccount.getFriend(email);
//	}
//
//	public List<String> getSentFriendshipAux() {
//		return this.userAccount.getSentFriendshipAux();
//	}
//
//	public void setSentFriendshipAux(List<String> sentFriendshipAux) {
//		this.userAccount.setSentFriendshipAux(sentFriendshipAux);
//	}
//	/**
//	 * Remove amigo da lista
//	 * @param email
//	 * @throws Exception
//	 */
//	public void removeFriend(String email) throws Exception {
//		this.userAccount.removeFriend(email);
//	}
//
//	public SortedMap<String,Group> getGroups() {
//		return this.userAccount.getGroups();
//	}
//	public void setGroups(SortedMap<String,Group> grupos) {
//		this.userAccount.setGroups(grupos);
//	}
//
//	public String toStringFullName() {
//		return this.userAccount.toStringFullName();
//	}
//
//	@Override
//	public String toString() {
//		return this.userAccount.toString();
//	}
//
//	public void setPreferences(List<String> preferencias) {
//		this.userAccount.setPreferences(preferencias);
//	}
//
//	public List<String> getPreferences() {
//		return this.userAccount.getPreferences();
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		return this.userAccount.equals(obj);
//	}
//
//	public int compareTo(UserAccount o) {
//		return this.userAccount.compareTo(o);
//	}
//
//	public void setLogged(boolean loged) {
//		this.userAccount.setLogged(loged);
//	}
//
//	public boolean isLogged() {
//		return this.userAccount.isLogged();
//	}
//
//	public void setProfileAll(ProfileIF profileAll) {
//		this.profileAll = profileAll;
//	}
//
//	public ProfileIF getProfileAll() {
//		return profileAll;
//	}
//
//	public void setProfileJustMe(ProfileIF profileJustMe) {
//		this.profileJustMe = profileJustMe;
//	}
//
//	public ProfileIF getProfileJustMe() {
//		return profileJustMe;
//	}
//
//	public void setProfileFriends(ProfileIF profileFriends) {
//		this.profileFriends = profileFriends;
//	}
//
//	public ProfileIF getProfileFriends() {
//		return profileFriends;
//	}
//
//	public Map<String,String> getPendingFriendship() {
//		return pendingFriendship;
//	}
//
//	public Map<String,String> getSentFriendship() {
//		return sentFriendship;
//	}
//
//	/**
//	 * Update o profile do usuário
//	 * @param usuario
//	 * @param aboutMe
//	 * @param age
//	 * @param photo
//	 * @param country
//	 * @param city
//	 * @param gender
//	 * @param contactEmail
//	 */
//	public void updateUserProfile(UserAccount usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
//		profileController.updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
//	}
//
//	/**
//	 * Seta privacidade do campo
//	 * @param owner
//	 * @param visibility
//	 * @param field
//	 * @throws Exception
//	 */
//	public void setFieldPrivacy(String owner, String visibility, String field) throws Exception {
//		profileController.setPrivacy(owner, visibility, field);
//	}
//
//	public ProfileIF getProfile(UserAccount user, String visibility) {
//		return profileController.getProfile(user, visibility);
//	}
//
//	public void setSentFriendship(Map<String,String> sentFriendship) {
//		this.sentFriendship = sentFriendship;
//	}
//
//	public void setPendingFriendship(Map<String,String> pendingFriendship) {
//		this.pendingFriendship = pendingFriendship;
//	}
//	/**
//	 * Aceita um convite de outro usuário
//	 * @param contact
//	 * @param group
//	 * @param contato
//	 * @throws Exception
//	 */
//	public void acceptFriendshipRequest(String contact, String group,UserAccount contato)throws Exception {
//		for (String string : this.getPendingFriendship().keySet()) {
//			if(string.equals(contact)) {
//				this.addToGroup(contato, group);
//				break;
//			}
//		}
//		this.getPendingFriendship().remove(contact);
//		this.updateBD();
//		this.updateFriends();
//	}
//
//	public Group getGrupo(UserAccount usuario, String group) throws Exception {
//		return groupController.getGroup(usuario, group);
//	}
//
//	/**
//	 * Envia um convite para usuário
//	 * @param login
//	 * @param group
//	 * @throws Exception
//	 */
//	public void sendFriendshipRequest(String login, String group) throws Exception {
//		populateFriendsList();
//		UserAccount account = this.DBController.getUsers(login);
//		if(friends.contains(account)) throw new Exception ("Usuários " + this.getName() + " " + this.getSurname() + " e " + account.getName() + " " + account.getSurname() + " já são amigos");
//		if (sentFriendship.keySet().contains(login)) throw new Exception("Você já enviou um convite para o usuário " + account.getName() + " " + account.getSurname());
//		sentFriendship.put(login,group);
//		this.getSentFriendshipAux().add(login);
//		this.updateBD();
//		this.updateFriends();
//	}
//
//	/**
//	 * Recebe o convite do usuário
//	 * @param logado
//	 * @param login
//	 * @param message
//	 */
//	public void receiveFriendshipRequest(UserAccount logado, String login, String message) {
//		pendingFriendship.put(login, logado.getName() + " " + logado.getSurname() + " <" + login + "> - mensagem: " + message);
//		this.updateBD();
//		this.updateFriends();
//	}
//
//	/**
//	 * @return a lista de usuários para qual foi enviado um convite
//	 */
//	public List<String> viewSentFriendship() {
//		List<String> resposta = new ArrayList<String>();
//		if(sentFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
//		for (String key : sentFriendshipAux) {
//			resposta.add(key);
//		}
//		return resposta;
//	}
//
//	/**
//	 * @return a lista com convites recebidos
//	 */
//	public List<String> viewPendingFriendship() {
//		List<String> resposta = new ArrayList<String>();
//		if(pendingFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
//		for (String value : pendingFriendship.values()) {
//			resposta.add(value);
//		}
//		return resposta;
//	}
//
//	/**
//	 * Adiciona preferência do usuário
//	 * @param preferencia
//	 */
//	public void addUserPreferences(String preferencia) {
//		if (!this.getPreferences().contains(preferencia)) {
//			this.getPreferences().add(preferencia);
//			this.updateBD();
//			this.updateFriends();
//			DBController.update();
//		}
//
//	}
//
//	/**
//	 * Remove convite da lista de convites enviados 
//	 * @param login
//	 * @param user
//	 * @throws Exception
//	 */
//	public void removeSentFriendshipRequest(String login, UserAccount user) throws Exception{
//		this.addToGroup(user, this.getSentFriendship().get(login));
//		this.getSentFriendship().remove(login);
//		this.getSentFriendshipAux().remove(login);
//		this.updateBD();
//		this.updateFriends();
//	}
//
//	/**
//	 * Rejeita um convite recebido
//	 * @param login
//	 * @param map
//	 */
//	public void declineFriendshipRequest(String login, Map<String,String> map) {
//		String chave = "";
//		for (String key : map.keySet()) {
//			if(key.equals(login)) {
//				chave = key;
//			}
//		}
//		map.remove(chave);
//		if(map.equals(this.getSentFriendship())) this.getSentFriendshipAux().remove(chave);
//		this.updateBD();
//	}
//
//	public UserAccount getFriend(UserAccount friend, String fullName) {
//		for(UserAccount user : this.getFriends()) {
//			String nomeCompleto = user.getName() + " " + user.getSurname();
//			if (user.equals(friend) || nomeCompleto.equals(fullName)) return user;
//		}
//		return null;
//	}
//	/**
//	 * @return a lista de de amigos recomendados
//	 * @throws Exception
//	 */
//	public List<UserAccount> getRecommendedFriends() throws Exception{
//		this.setFriends(new ArrayList<UserAccount>());
//		List<UserAccount> output = new ArrayList<UserAccount>();
//		List<UserAccount> friends = this.getGrupo(this, "melhores amigos").getUsers();
//		List<UserAccount> family = this.getGrupo(this, "familia").getUsers();
//		List<UserAccount> recomendedFriends = new ArrayList<UserAccount>();
//
//		output.addAll(putFriendsIntoList(family));
//		output.addAll(putFriendsIntoList(friends));
//		for (UserAccount user : this.getFriends()) {
//			output.addAll(user.getSimilarityFriends(this));
//		}
//
//		for (int i = 0; i < output.size(); i ++) {
//			if ((!recomendedFriends.contains(output.get(i))) && !(output.get(i).getEmail().equals(getEmail()) || getFriends().contains(output.get(i)))) {
//				recomendedFriends.add(output.get(i));
//			}
//		}
//
//		return recomendedFriends;
//	}
//
//	/**
//	 * Atualiza os amigos
//	 */
//	public void updateFriends() {
//		for (UserAccount user : this.getFriends()) {
//			user.updateBD();
//		}
//	}
//
//}
