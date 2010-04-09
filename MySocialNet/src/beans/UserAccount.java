package beans;

import interfaces.ProfileIF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import Util.Util;
import controller.DBController;
import controller.GroupController;
import controller.ProfileController;

/**
 * @author Rafael Aquino
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
	private List<Group> groups;
	private List<UserAccount> friends;
	private List<String> preferences;
	private boolean logged;
	private ProfileController profileController;
	private GroupController groupController;
	private DBController DBController;
	private Map<String,String> pendingFriendship;
	private Map<String,String> sentFriendship;
	
	
	public UserAccount(String nome, String sobrenome, String dataNascimento, String senha, String email) throws Exception {
		if(Util.verificaString(nome,"Nome")) {
			this.name = nome;
		}
		if(Util.verificaString(sobrenome, "Sobrenome")) {
			this.surname = sobrenome;
		}
		if(Util.verificaData(dataNascimento)) {
			this.dayOfBirth = dataNascimento;
		}
		if(Util.verificaSenha(senha)) {
			this.password = senha;
		}
		if(Util.verificaEmail(email)) {
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
		createGroups();
	}
	
	public UserAccount(String nome, String sobrenome, String senha, String email) throws Exception {
		if(Util.verificaString(nome,"Nome")) {
			this.name = nome;
		}
		if(Util.verificaString(sobrenome,"Sobrenome")) {
			this.surname = sobrenome;
		}
		if(Util.verificaSenha(senha)) {
			this.password = senha;
		}
		if(Util.verificaEmail(email)) {
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
		createGroups();
	}

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
		createGroups();
	}

	public String getName() {
		return name;
	}
	public void setName(String nome) throws Exception {
		if (Util.verificaString(nome, "Nome")) {
			this.name = nome;
		}
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String sobrenome) throws Exception {
		if (Util.verificaString(sobrenome, "Sobrenome")) {
			this.surname = sobrenome;
		}
	}
	public String getDayOfBirth() {
		return dayOfBirth;
	}
	public void setDayOfBirth(String dataNascimento) throws Exception {
		if(Util.verificaData(dataNascimento)) {
			this.dayOfBirth = dataNascimento;
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String senha) throws Exception {
		if(Util.verificaSenha(senha)) { 
			this.password = senha;
		}
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws Exception {
		if (Util.verificaEmail(email)) {
			this.email = email;
		}
	}
	public List<UserAccount> getFriends() {
		if(friends.isEmpty()) {
			return populateFriendsList();
		}
		return this.friends;
	}
	
	public UserAccount getFriend(String email) throws Exception {
		if(friends.isEmpty()) {
			populateFriendsList();
		}
		for (UserAccount usuario : this.friends) {
			if(usuario.getEmail().equals(email)) {
				return usuario;
			}
		}
		throw new Exception("Amigo não encontrado");
	}
	
	public void removeFriend(String email) throws Exception {
		for (Group grupo : this.groups) {
			for (UserAccount usuario : grupo.getUsers()) {
				if(usuario.getEmail().equals(email)){
					grupo.getUsers().remove(usuario);
					populateFriendsList();
				}
			}
		}
		throw new Exception("Amigo não encontrado");
		
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> grupos) {
		this.groups = grupos;
	}
	
	public String toStringFullName() {
		return name + " " + surname;
	}
	@Override
	public String toString() {
		return "Nome=" + name + ",Sobrenome=" + surname;
	}
	
	private List<UserAccount> populateFriendsList() {
		for (Group grupo : groups) {
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
	
	public void updateUserProfile(UserAccount usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		profileController.updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
	}
	
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
	
	private void createGroups() {
		groups = new ArrayList<Group>();
		Group conhecidos = new Group("conhecidos");
		Group escola = new Group("escola");
		Group familia = new Group("familia");
		Group melhoresAmigos = new Group ("melhores amigos");
		Group trabalho = new Group("trabalho");
		groups.add(conhecidos);
		groups.add(escola);
		groups.add(familia);
		groups.add(melhoresAmigos);
		groups.add(trabalho);
	}

	public void acceptFriendshipRequest(String contact, String group,UserAccount contato)throws Exception {
		for (String string : this.getPendingFriendship().keySet()) {
			if(string.equals(contact)) {
				addAtGroup(contato, group);
			}
		}
		this.getPendingFriendship().remove(contact);
		this.updateBD();
	}

	public Group getGrupo(UserAccount usuario, String group) throws Exception {
		return groupController.getGroup(usuario, group);
	}
	
	public void sendFriendshipRequest(String login, String group) throws Exception {
		populateFriendsList();
		UserAccount account = this.DBController.getUsers(login);
		if(friends.contains(account)) throw new Exception ("Ele já é seu amigo");
		if (sentFriendship.keySet().contains(login)) throw new Exception("Você já enviou um convite para esse usuário");
		sentFriendship.put(login,group);
		this.updateBD();
	}
	
	public void receiveFriendshipRequest(UserAccount logado, String login, String message) {
		pendingFriendship.put(login, logado.getName() + " " + logado.getSurname() + " <" + login + "> - mensagem: " + message);
		this.updateBD();
	}
	
	public List<String> viewSentFriendship() {
		List<String> resposta = new ArrayList<String>();
		if(sentFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");;
		for (String key : sentFriendship.keySet()) {
			resposta.add(key);
		}
		return resposta;
		
	}
	
	public List<String> viewPendingFriendship() {
		List<String> resposta = new ArrayList<String>();
		if(pendingFriendship.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
		for (String value : pendingFriendship.values()) {
			resposta.add(value);
		}
		return resposta;
	}

	public void addUserPreferences(String preferencia) {
		if (!this.getPreferences().contains(preferencia)) {
			this.getPreferences().add(preferencia);
			this.updateBD();
		}
		
	}

	public void removeSentFriendshipRequest(String login, UserAccount user) throws Exception{
		this.addAtGroup(user, this.getSentFriendship().get(login));
		this.getSentFriendship().remove(login);
		this.updateBD();
	}
	
	public void declineFriendshipRequest(String login, Map<String,String> map) {
		String chave = "";
		for (String key : map.keySet()) {
			if(key.equals(login)) {
				chave = key;
			}
		}
		map.remove(chave);
		this.updateBD();
	}
	
	public UserAccount getFriend(UserAccount friend, String fullName) {
		for(UserAccount user : this.getFriends()) {
			String nomeCompleto = user.getName() + " " + user.getSurname();
			if (user.equals(friend) || nomeCompleto.equals(fullName)) return user;
		}
		return null;
	}
	
	private void addAtGroup(UserAccount user, String group) throws Exception {
		Group grupo = this.getGrupo(this, group);
		grupo.getUsers().add(user);
		Collections.sort(grupo.getUsers());
		this.updateBD();
		
	}
	
	
	private void updateBD() {
		this.DBController.update(this);
	}

	public Set<UserAccount> getRecommendedFriends() throws Exception{
		this.populateFriendsList();
		SortedSet<UserAccount> output = new TreeSet<UserAccount>();
		Set<UserAccount> tmp = new TreeSet<UserAccount>();
		List<UserAccount> familiaList = getFriendsFromGroup("familia");
		List<UserAccount> melhoresAmigosList = getFriendsFromGroup("melhores amigos");
		
//		for (UserAccount userAccount : putFriendsIntoList(familiaList)) {
//			tmp.add(userAccount);
//		}
		tmp.addAll(putFriendsIntoList(familiaList));
		tmp.addAll(putFriendsIntoList(melhoresAmigosList));
		
		for (UserAccount userAccount : tmp) {
			if(!(friends.contains(userAccount)|| userAccount.equals(this))) {
				output.add(userAccount);
			}
		}
		return output;
	}
	
	private List<UserAccount> putFriendsIntoList(List<UserAccount>list) throws Exception{
		List<UserAccount> tmp = new ArrayList<UserAccount>();
		for (UserAccount userAccount : list) {
			List<UserAccount> listaFamilia = new ArrayList<UserAccount>();
			listaFamilia = userAccount.getFriendsFromGroup("familia");
			List<UserAccount> listaMelhoresAmigos = new ArrayList<UserAccount>();
			listaMelhoresAmigos = userAccount.getFriendsFromGroup("melhores amigos");
			for (UserAccount user : listaFamilia) {
				tmp.add(user);
			}
			for (UserAccount user : listaMelhoresAmigos) {
				tmp.add(user);
			}
		}
		return tmp;
	}
	
	private List<UserAccount> getFriendsFromGroup(String group) throws Exception {
		return this.getGrupo(this, group).getUsers();
		
	}
	
	public static void main(String[] args) {
	}
}
