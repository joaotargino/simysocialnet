package beans;

import interfaces.ProfileIF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import Util.Util;
import controller.DBController;
import controller.GroupController;
import controller.ProfileController;

/**
 * @author Rafael Aquino, Telles Nóbrega, Thiago Ortega
 *
 */
public class UserAccount implements Comparable<UserAccount>{

	private String name;
	private ProfileIF profileAll;
	private ProfileIF profileJustMe;
	private ProfileIF profileFriends;
	private String surname;
	private String dayOfBirth;
	private String password;
	private String email;
	private SortedMap<String,Group> groups;
	private List<UserAccount> friends;
	private List<String> preferences;
	private boolean logged;
	private ProfileController profileController;
	private GroupController groupController;
	private DBController DBController;
	private Map<String,String> pendingFriendship;
	private Map<String,String> sentFriendship;
	private List<String> sentFriendshipAux;
	private final double SIMILARITY_LEVEL = 0.35;


	/**
	 * Construtor de UserAccount
	 * @param nome
	 * @param sobrenome
	 * @param dataNascimento
	 * @param senha
	 * @param email
	 * @throws Exception
	 */
	public UserAccount(String nome, String sobrenome, String dataNascimento, String senha, String email) throws Exception {
		if(Util.verifyString(nome,"Nome")) {
			this.name = nome;
		}
		if(Util.verifyString(sobrenome, "Sobrenome")) {
			this.surname = sobrenome;
		}
		if(Util.verifyDate(dataNascimento)) {
			this.dayOfBirth = dataNascimento;
		}
		if(Util.verifyPassword(senha)) {
			this.password = senha;
		}
		if(Util.verifyEmail(email)) {
			this.email = email;
		}
		logged = false;
		groupController = new GroupController();
		pendingFriendship = new HashMap<String,String>();
		sentFriendship = new HashMap<String,String>();
		preferences = new ArrayList<String>();
		profileAll = new ProfileAll();
		profileJustMe = new ProfileJustMe();
		profileFriends = new ProfileFriends();
		profileAll.init();
		profileJustMe.init();
		profileFriends.init();
		DBController = new DBController();
		profileController = new ProfileController();
		friends = new ArrayList<UserAccount>();
		sentFriendshipAux = new ArrayList<String>();
		createGroups();
	}

	/**
	 * Construtor de UserAccount
	 * @param nome
	 * @param sobrenome
	 * @param senha
	 * @param email
	 * @throws Exception
	 */
	public UserAccount(String nome, String sobrenome, String senha, String email) throws Exception {
		if(Util.verifyString(nome,"Nome")) {
			this.name = nome;
		}
		if(Util.verifyString(sobrenome,"Sobrenome")) {
			this.surname = sobrenome;
		}
		if(Util.verifyPassword(senha)) {
			this.password = senha;
		}
		if(Util.verifyEmail(email)) {
			this.email = email;
		}
		logged = false;
		groupController = new GroupController();
		pendingFriendship = new HashMap<String,String>();
		sentFriendship = new HashMap<String,String>();
		preferences = new ArrayList<String>();
		friends = new ArrayList<UserAccount>();
		profileAll = new ProfileAll();
		profileJustMe = new ProfileJustMe();
		profileFriends = new ProfileFriends();
		DBController = new DBController();
		profileController = new ProfileController();
		profileAll.init();
		profileJustMe.init();
		profileFriends.init();
		sentFriendshipAux = new ArrayList<String>();
		createGroups();
	}

	/**
	 * Construtor de UserAccount
	 */
	public UserAccount() {
		groupController = new GroupController();
		pendingFriendship = new HashMap<String,String>();
		sentFriendship = new HashMap<String,String>();
		preferences = new ArrayList<String>();
		profileAll = new ProfileAll();
		profileJustMe = new ProfileJustMe();
		profileFriends = new ProfileFriends();
		profileController = new ProfileController();
		DBController = new DBController();
		friends = new ArrayList<UserAccount>();
		profileAll.init();
		profileJustMe.init();
		profileFriends.init();
		logged = false;
		sentFriendshipAux = new ArrayList<String>();
		createGroups();
	}

	public String getName() {
		return name;
	}
	public void setName(String nome) throws Exception {
		if (Util.verifyString(nome, "Nome")) {
			this.name = nome;
		}
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String sobrenome) throws Exception {
		if (Util.verifyString(sobrenome, "Sobrenome")) {
			this.surname = sobrenome;
		}
	}
	public String getDayOfBirth() {
		return dayOfBirth;
	}
	public void setDayOfBirth(String dataNascimento) throws Exception {
		if(Util.verifyDate(dataNascimento)) {
			this.dayOfBirth = dataNascimento;
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String senha) throws Exception {
		if(Util.verifyPassword(senha)) { 
			this.password = senha;
		}

	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws Exception {
		if (Util.verifyEmail(email)) {
			this.email = email;
		}
	}
	public List<UserAccount> getFriends() {
		friends = new ArrayList<UserAccount>();
		return populateFriendsList();
	}

	public UserAccount getFriend(String email) throws Exception {
		for (UserAccount usuario : getFriends()) {
			if(usuario.getEmail().equals(email)) {
				return usuario;
			}
		}
		throw new Exception("Amigo não encontrado");
	}
	
	public List<String> getSentFriendshipAux() {
		return sentFriendshipAux;
	}

	public void setSentFriendshipAux(List<String> sentFriendshipAux) {
		this.sentFriendshipAux = sentFriendshipAux;
	}

	/**
	 * Remove amigo da lista
	 * @param email
	 * @throws Exception
	 */
	public void removeFriend(String email) throws Exception {
		for (Group grupo : this.groups.values()) {
			for (UserAccount usuario : grupo.getUsers()) {
				if(usuario.getEmail().equals(email)){
					grupo.getUsers().remove(usuario);
					populateFriendsList();
					this.updateBD();
				}
			}
		}
		throw new Exception("Amigo não encontrado");

	}

	public SortedMap<String,Group> getGroups() {
		return groups;
	}
	public void setGroups(SortedMap<String,Group> grupos) {
		this.groups = grupos;
	}

	public String toStringFullName() {
		return name + " " + surname;
	}
	@Override
	public String toString() {
		return "Nome=" + name + ",Sobrenome=" + surname;
	}

	/**
	 * Popula lista de amigos
	 * @return a lista populada e ordenada
	 */
	private List<UserAccount> populateFriendsList() {
		for (Group grupo : groups.values()) {
			this.friends.addAll(grupo.getUsers());
		}
		Collections.sort(this.friends);
		return this.friends;
	}

	public void setPreferences(List<String> preferencias) {
		this.preferences = preferencias;
	}

	public List<String> getPreferences() {
		return preferences;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserAccount) {
			UserAccount user = (UserAccount) obj;
			return user.getEmail().equals(getEmail());
		}
		return false;
	}

	@Override
	public int compareTo(UserAccount o) {
		String string = getName() + " " + getSurname();
		return string.compareTo(o.getName() + " " + o.getSurname());
	}

	public void setLogged(boolean loged) {
		this.logged = loged;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setProfileAll(ProfileIF profileAll) {
		this.profileAll = profileAll;
	}

	public ProfileIF getProfileAll() {
		return profileAll;
	}

	public void setProfileJustMe(ProfileIF profileJustMe) {
		this.profileJustMe = profileJustMe;
	}

	public ProfileIF getProfileJustMe() {
		return profileJustMe;
	}

	public void setProfileFriends(ProfileIF profileFriends) {
		this.profileFriends = profileFriends;
	}

	public ProfileIF getProfileFriends() {
		return profileFriends;
	}

	public Map<String,String> getPendingFriendship() {
		return pendingFriendship;
	}

	public Map<String,String> getSentFriendship() {
		return sentFriendship;
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
	 */
	public void updateUserProfile(UserAccount usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		profileController.updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
	}

	/**
	 * Seta privacidade do campo
	 * @param owner
	 * @param visibility
	 * @param field
	 * @throws Exception
	 */
	public void setFieldPrivacy(String owner, String visibility, String field) throws Exception {
		profileController.setPrivacy(owner, visibility, field);
	}

	public ProfileIF getProfile(UserAccount user, String visibility) {
		return profileController.getProfile(user, visibility);
	}

	public void setSentFriendship(Map<String,String> sentFriendship) {
		this.sentFriendship = sentFriendship;
	}

	public void setPendingFriendship(Map<String,String> pendingFriendship) {
		this.pendingFriendship = pendingFriendship;
	}

	/**
	 * Cria os grupos do usuário
	 */
	private void createGroups() {
		groups = new TreeMap<String,Group>();
		Group conhecidos = new Group("conhecidos");
		Group escola = new Group("escola");
		Group familia = new Group("familia");
		Group melhoresAmigos = new Group ("melhores amigos");
		Group trabalho = new Group("trabalho");
		groups.put("conhecidos", conhecidos);
		groups.put("escola", escola);
		groups.put("familia", familia);
		groups.put("melhores amigos", melhoresAmigos);
		groups.put("trabalho", trabalho);
	}

	/**
	 * Aceita um convite de outro usuário
	 * @param contact
	 * @param group
	 * @param contato
	 * @throws Exception
	 */
	public void acceptFriendshipRequest(String contact, String group,UserAccount contato)throws Exception {
		for (String string : this.getPendingFriendship().keySet()) {
			if(string.equals(contact)) {
				this.addToGroup(contato, group);
				break;
			}
		}
		this.getPendingFriendship().remove(contact);
		this.updateBD();
	}

	public Group getGrupo(UserAccount usuario, String group) throws Exception {
		return groupController.getGroup(usuario, group);
	}

	/**
	 * Envia um convite para usuário
	 * @param login
	 * @param group
	 * @throws Exception
	 */
	public void sendFriendshipRequest(String login, String group) throws Exception {
		populateFriendsList();
		UserAccount account = this.DBController.getUsers(login);
		if(friends.contains(account)) throw new Exception ("Usuários " + this.getName() + " " + this.getSurname() + " e " + account.getName() + " " + account.getSurname() + " já são amigos");
		if (sentFriendship.keySet().contains(login)) throw new Exception("Você já enviou um convite para o usuário " + account.getName() + " " + account.getSurname());
		sentFriendship.put(login,group);
		this.getSentFriendshipAux().add(login);
		this.updateBD();
	}

	/**
	 * Recebe o convite do usuário
	 * @param logado
	 * @param login
	 * @param message
	 */
	public void receiveFriendshipRequest(UserAccount logado, String login, String message) {
		pendingFriendship.put(login, logado.getName() + " " + logado.getSurname() + " <" + login + "> - mensagem: " + message);
		this.updateBD();
	}

	/**
	 * @return a lista de usuários para qual foi enviado um convite
	 */
	public List<String> viewSentFriendship() {
		List<String> resposta = new ArrayList<String>();
		if(sentFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
		for (String key : sentFriendshipAux) {
			resposta.add(key);
		}
		return resposta;
	}

	/**
	 * @return a lista com convites recebidos
	 */
	public List<String> viewPendingFriendship() {
		List<String> resposta = new ArrayList<String>();
		if(pendingFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
		for (String value : pendingFriendship.values()) {
			resposta.add(value);
		}
		return resposta;
	}

	/**
	 * Adiciona preferência do usuário
	 * @param preferencia
	 */
	public void addUserPreferences(String preferencia) {
		if (!this.getPreferences().contains(preferencia)) {
			this.getPreferences().add(preferencia);
			this.updateBD();
		}

	}

	/**
	 * Remove convite da lista de convites enviados 
	 * @param login
	 * @param user
	 * @throws Exception
	 */
	public void removeSentFriendshipRequest(String login, UserAccount user) throws Exception{
		this.addToGroup(user, this.getSentFriendship().get(login));
		this.getSentFriendship().remove(login);
		this.getSentFriendshipAux().remove(login);
		this.updateBD();
	}

	/**
	 * Rejeita um convite recebido
	 * @param login
	 * @param map
	 */
	public void declineFriendshipRequest(String login, Map<String,String> map) {
		String chave = "";
		for (String key : map.keySet()) {
			if(key.equals(login)) {
				chave = key;
			}
		}
		map.remove(chave);
		if(map.equals(this.getSentFriendship())) this.getSentFriendshipAux().remove(chave);
		this.updateBD();
	}

	public UserAccount getFriend(UserAccount friend, String fullName) {
		for(UserAccount user : this.getFriends()) {
			String nomeCompleto = user.getName() + " " + user.getSurname();
			if (user.equals(friend) || nomeCompleto.equals(fullName)) return user;
		}
		return null;
	}

	/**
	 * Adiciona usuário a um grupos 
	 * @param user
	 * @param group
	 * @throws Exception
	 */
	private void addToGroup(UserAccount user, String group) throws Exception {
		Group grupo = this.getGroups().get(group);
		List<UserAccount> users = grupo.getUsers();
		users.add(user);
		grupo.setUsers(users);
		Collections.sort(grupo.getUsers());
		this.getGroups().put(grupo.getName(), grupo);
		this.updateBD();

	}


	/**
	 * Atualiza o banco de dados
	 */
	private void updateBD() {
		this.DBController.update();
	}

	public void setFriends(List<UserAccount> friends) {
		this.friends = friends;
	}

	/**
	 * @return a lista de de amigos recomendados
	 * @throws Exception
	 */
	public List<UserAccount> getRecommendedFriends() throws Exception{
		this.setFriends(new ArrayList<UserAccount>());
		List<UserAccount> output = new ArrayList<UserAccount>();
		List<UserAccount> friends = this.getGrupo(this, "melhores amigos").getUsers();
		List<UserAccount> family = this.getGrupo(this, "familia").getUsers();
		List<UserAccount> recomendedFriends = new ArrayList<UserAccount>();

		output.addAll(putFriendsIntoList(family));
		output.addAll(putFriendsIntoList(friends));
		for (UserAccount user : DBController.getAllUsers()) {
			output.addAll(user.getSimilarityFriends(this));
		}
		
		for (int i = 0; i < output.size(); i ++) {
			if ((!recomendedFriends.contains(output.get(i))) && !(output.get(i).getEmail().equals(getEmail()) || getFriends().contains(output.get(i)))) {
				recomendedFriends.add(output.get(i));
			}
		}

		return recomendedFriends;
	}

	/**
	 * Pega o  nível de similaridade entre possíveis amigos 
	 * @param userAccount
	 * @return
	 */
	private List<UserAccount> getSimilarityFriends(UserAccount userAccount) {
		List<UserAccount> similarityFriends = new ArrayList<UserAccount>();
		for (UserAccount user : this.getFriends()) {
			Set<String> commumPreferences = new TreeSet<String>();
			Set<String> allPreferences = new TreeSet<String>();
			commumPreferences.addAll(user.getPreferences());
			allPreferences.addAll(user.getPreferences());
			commumPreferences.retainAll(userAccount.getPreferences());
			allPreferences.retainAll(userAccount.getPreferences());
			if (((double) commumPreferences.size()/(double) allPreferences.size()) >= this.SIMILARITY_LEVEL) {
				similarityFriends.add(user);
			}
		}
		return similarityFriends;
	}

	/**
	 * Colocas amigos em uma lista
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<UserAccount> putFriendsIntoList(List<UserAccount>list) throws Exception{
		List<UserAccount> output = new ArrayList<UserAccount>();
		for (UserAccount user : list) {
			user.setFriends(new ArrayList<UserAccount>());
			output.addAll(user.getGrupo(user, "familia").getUsers());
			output.addAll(user.getGrupo(user, "melhores amigos").getUsers());
		}
		return output;
	}

}
