package beans;

import facades.DBFacade;
import facades.ProfileFacade;
import interfaces.ProfileIF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import Util.ProfileConstants;
import Util.Util;
import controller.GroupController;

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
	private GroupController groupController;
	private DBFacade dbFacade;
	private List<Invitation> pendingFriendship;
	private List<Invitation> sentFriendship;
	private ProfileFacade profileFacade;
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
		dbFacade = DBFacade.getInstance();
		logged = false;
		groupController = new GroupController();
		pendingFriendship = new ArrayList<Invitation>();
		sentFriendship = new ArrayList<Invitation>();
		preferences = new ArrayList<String>();
		profileAll = createProfiles(ProfileConstants.ALL);
		profileJustMe = createProfiles(ProfileConstants.JUST_ME);
		profileFriends = createProfiles(ProfileConstants.FRIENDS);
		profileFacade = ProfileFacade.getInstance();
		friends = new ArrayList<UserAccount>();
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
		dbFacade = DBFacade.getInstance();
		logged = false;
		groupController = new GroupController();
		pendingFriendship = new ArrayList<Invitation>();
		sentFriendship = new ArrayList<Invitation>();
		preferences = new ArrayList<String>();
		friends = new ArrayList<UserAccount>();
		profileAll = createProfiles(ProfileConstants.ALL);
		profileJustMe = createProfiles(ProfileConstants.JUST_ME);
		profileFriends = createProfiles(ProfileConstants.FRIENDS);
		profileFacade = ProfileFacade.getInstance();
		createGroups();
	}
	
	/**
	 * Construtor de UserAccount
	 */
	public UserAccount() {
		dbFacade = DBFacade.getInstance();
		groupController = new GroupController();
		pendingFriendship = new ArrayList<Invitation>();
		sentFriendship = new ArrayList<Invitation>();
		preferences = new ArrayList<String>();
		friends = new ArrayList<UserAccount>();
		profileAll = createProfiles(ProfileConstants.ALL);
		profileJustMe = createProfiles(ProfileConstants.JUST_ME);
		profileFriends = createProfiles(ProfileConstants.FRIENDS);
		profileFacade = ProfileFacade.getInstance();
		logged = false;
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

	public void setPreferences(List<String> preferences) {
		this.preferences = preferences;
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

	public List<Invitation> getPendingFriendship() {
		return pendingFriendship;
	}

	public List<Invitation> getSentFriendship() {
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
		profileFacade.updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
	}

	/**
	 * Seta privacidade do campo
	 * @param owner
	 * @param visibility
	 * @param field
	 * @throws Exception
	 */
	public void setFieldPrivacy(String owner, String visibility, String field) throws Exception {
		profileFacade.setPrivacy(owner, visibility, field);
	}

	public ProfileIF getProfile(UserAccount user, String visibility) {
		return profileFacade.getProfile(user, visibility);
	}

	public void setSentFriendship(List<Invitation> sentFriendship) {
		this.sentFriendship = sentFriendship;
	}

	public void setPendingFriendship(List<Invitation> pendingFriendship) {
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
	
	private ProfileIF createProfiles(String type) {
		ProfileIF profile = null; 
		if (type.equals(ProfileConstants.ALL)) {
			profile = new ProfileAll();
			profile.init();
		}
		else if (type.equals(ProfileConstants.FRIENDS)) {
			profile = new ProfileFriends();
			profile.init();
		}
		else if (type.equals(ProfileConstants.JUST_ME)) {
			profile = new ProfileJustMe();
			profile.init();
		}
		return profile;
	}

	/**
	 * Aceita um convite de outro usuário
	 * @param contact
	 * @param group
	 * @param contato
	 * @throws Exception
	 */
	public Invitation acceptFriendshipRequest(String contact, String group,UserAccount contato)throws Exception {
//		for (String string : this.getPendingFriendship().keySet()) {
//			if(string.equals(contact)) {
//				this.addToGroup(contato, group);
//				break;
//			}
//		}
		Invitation invite = null;
		Iterator<Invitation> it = this.getPendingFriendship().iterator();
		while(it.hasNext()){
			Invitation invitation = it.next();
			if(invitation.getSenderLogin().equals(contact)) {
				invite = invitation;  
				this.addToGroup(contato, invitation.getSenderGroup());
				it.remove();
				break;
			}
		}
		this.updateBD();
		this.updateFriends();
		return invite;
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
	public void sendFriendshipRequest(Invitation invitation) throws Exception {
		populateFriendsList();
		UserAccount account = this.dbFacade.getUsers(invitation.getReceiverLogin());
		if(friends.contains(account)) throw new Exception ("Usuários " + this.getName() + " " + this.getSurname() + " e " + account.getName() + " " + account.getSurname() + " já são amigos");
		if(this.getSentFriendship().contains(invitation)) throw new Exception("Você já enviou um convite para o usuário " + account.toStringFullName());
		this.getSentFriendship().add(invitation);
		this.updateBD();
		this.updateFriends();
	}

	/**
	 * Recebe o convite do usuário
	 * @param logado
	 * @param login
	 * @param message
	 */
	public void receiveFriendshipRequest(Invitation invitation) {
		pendingFriendship.add(invitation);
		this.updateBD();
		this.updateFriends();
	}

	/**
	 * @return a lista de usuários para qual foi enviado um convite
	 */
	public List<String> viewSentFriendship() {
		List<String> resposta = new ArrayList<String>();
		if(sentFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
//		for (String key : sentFriendshipAux) {
//			resposta.add(key);
//		}
		Iterator<Invitation> it = this.getSentFriendship().iterator();
		while(it.hasNext()){
			Invitation invitation = it.next();
			resposta.add(invitation.getReceiverLogin());
		}
		return resposta;
	}

	/**
	 * @return a lista com convites recebidos
	 * @throws Exception 
	 */
	public List<String> viewPendingFriendship() throws Exception {
		List<String> resposta = new ArrayList<String>();
		if(pendingFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
//		for (String value : pendingFriendship.values()) {
//			resposta.add(value);
//		}
		Iterator<Invitation> it = this.getPendingFriendship().iterator();
		while(it.hasNext()){
			Invitation invitation = it.next();
			resposta.add(invitation.getMessage());
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
			this.updateFriends();
			dbFacade.update();
		}

	}

	/**
	 * Remove convite da lista de convites enviados 
	 * @param login
	 * @param user
	 * @throws Exception
	 */
	public void removeSentFriendshipRequest(Invitation invitation,String login, UserAccount user) throws Exception{
		this.addToGroup(user, invitation.getSenderGroup());
		this.getSentFriendship().remove(invitation);
		this.updateBD();
		this.updateFriends();
	}

	/**
	 * Rejeita um convite recebido
	 * @param login
	 * @param map
	 */
	public void declinePendingFriendshipRequest(String login, List<Invitation> invites) {
//		for (String key : map.keySet()) {
//			if(key.equals(login)) {
//				chave = key;
//			}
//		}
		
		Iterator<Invitation> it = this.getPendingFriendship().iterator();
		while(it.hasNext()){
			Invitation invitation = it.next();
			if(invitation.getSenderLogin().equals(login)) {
				it.remove();
				break;
			}
		}
		this.updateBD();
	}
	
	/**
	 * Rejeita um convite recebido
	 * @param login
	 * @param map
	 */
	public void declineSentFriendshipRequest(String login, List<Invitation> invites) {
//		for (String key : map.keySet()) {
//			if(key.equals(login)) {
//				chave = key;
//			}
//		}
		
		Iterator<Invitation> it = this.getSentFriendship().iterator();
		while(it.hasNext()){
			Invitation invitation = it.next();
			if(invitation.getReceiverLogin().equals(login)) {
				it.remove();
				break;
			}
		}
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
		this.dbFacade.update(this);
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
		for (UserAccount user : this.getFriends()) {
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
			double similarity = ((double) commumPreferences.size()/(double) allPreferences.size());
			if (similarity >= this.SIMILARITY_LEVEL) {
				similarityFriends.add(user);
			}
		}
		return similarityFriends;
	}

	/**
	 * Atualiza os amigos
	 */
	public void updateFriends() {
		for (UserAccount user : this.getFriends()) {
			user.updateBD();
		}
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
