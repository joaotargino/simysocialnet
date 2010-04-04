package beans;

import interfaces.ProfileIF;

import java.util.ArrayList;
import java.util.List;

import sun.text.normalizer.CharTrie.FriendAgent;

import controller.GerenciadorProfile;
import controller.GerenciadorUsuario;

import Util.Util;

/**
 * @author Rafael Aquino
 *
 */
public class ContaUsuario implements Comparable<ContaUsuario>{
	
	private String nome;
	private ProfileIF profileAll;
	private ProfileIF profileJustMe;
	private ProfileIF profileFriends;
	private String sobrenome;
	private String dataNascimento;
	private String senha;
	private String email;
	private List<Grupo> grupos;
	private List<ContaUsuario> amigos;
	private List<String> preferencias;
	private boolean loged;
	private GerenciadorProfile gerenciaProfile;
	private GerenciadorUsuario gerenciaUsuario;
	private List<String> pendingFriendship;
	private List<String> sentFriendship;
	
	
	public ContaUsuario(String nome, String sobrenome, String dataNascimento, String senha, String email) throws Exception {
		if(Util.verificaString(nome,"Nome")) {
			this.nome = nome;
		}
		if(Util.verificaString(sobrenome, "Sobrenome")) {
			this.sobrenome = sobrenome;
		}
		if(Util.verificaData(dataNascimento)) {
			this.dataNascimento = dataNascimento;
		}
		if(Util.verificaSenha(senha)) {
			this.senha = senha;
		}
		if(Util.verificaEmail(email)) {
			this.email = email;
		}
		preferencias = new ArrayList<String>();
		profileAll = new ProfileAll();
		profileJustMe = new ProfileJustMe();
		profileFriends = new ProfileFriends();
		gerenciaProfile = new GerenciadorProfile();
		amigos = new ArrayList<ContaUsuario>();
		createGroups();
	}
	
	public ContaUsuario(String nome, String sobrenome, String senha, String email) throws Exception {
		if(Util.verificaString(nome,"Nome")) {
			this.nome = nome;
		}
		if(Util.verificaString(sobrenome,"Sobrenome")) {
			this.sobrenome = sobrenome;
		}
		if(Util.verificaSenha(senha)) {
			this.senha = senha;
		}
		if(Util.verificaEmail(email)) {
			this.email = email;
		}
		preferencias = new ArrayList<String>();
		amigos = new ArrayList<ContaUsuario>();
		profileAll = new ProfileAll();
		profileJustMe = new ProfileJustMe();
		profileFriends = new ProfileFriends();
		gerenciaProfile = new GerenciadorProfile();
		createGroups();
	}

	public ContaUsuario() {
		preferencias = new ArrayList<String>();
		profileAll = new ProfileAll();
		profileJustMe = new ProfileJustMe();
		profileFriends = new ProfileFriends();
		gerenciaProfile = new GerenciadorProfile();
		amigos = new ArrayList<ContaUsuario>();
		createGroups();
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) throws Exception {
		if (Util.verificaString(nome, "Nome")) {
			this.nome = nome;
		}
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) throws Exception {
		if (Util.verificaString(sobrenome, "Sobrenome")) {
			this.sobrenome = sobrenome;
		}
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) throws Exception {
		if(Util.verificaData(dataNascimento)) {
			this.dataNascimento = dataNascimento;
		}
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) throws Exception {
		if(Util.verificaSenha(senha)) { 
			this.senha = senha;
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
	public List<ContaUsuario> getAmigos() {
		if(amigos == null) {
			return populaListaAmigos();
		}
		return this.amigos;
	}
	
	public ContaUsuario getAmigo(String email) throws Exception {
		if(amigos == null) {
			populaListaAmigos();
		}
		for (ContaUsuario usuario : this.amigos) {
			if(usuario.getEmail().equals(email)) {
				return usuario;
			}
		}
		throw new Exception("Amigo n�o encontrado");
	}
	
	public void removerAmigo(String email) throws Exception {
		for (Grupo grupo : this.grupos) {
			for (ContaUsuario usuario : grupo.getUsuarios()) {
				if(usuario.getEmail().equals(email)){
					grupo.getUsuarios().remove(usuario);
					populaListaAmigos();
				}
			}
		}
		throw new Exception("Amigo n�o encontrado");
		
	}
	
	public void adiconarAmigo(ContaUsuario amigo, Grupo grupo) {
		
	}
	
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	@Override
	public String toString() {
		return "Nome=" + nome + ",Sobrenome=" + sobrenome;
	}
	
	private List<ContaUsuario> populaListaAmigos() {
		for (Grupo grupo : grupos) {
			this.amigos.addAll(grupo.getUsuarios());
		}
		return this.amigos;
	}

	public void setPreferencias(List<String> preferencias) {
		this.preferencias = preferencias;
	}

	public List<String> getPreferencias() {
		return preferencias;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContaUsuario) {
			ContaUsuario user = (ContaUsuario) obj;
			return user.getEmail().equals(getEmail());
		}
		return false;
	}
	
	@Override
	public int compareTo(ContaUsuario o) {
		if (o.getEmail().equals(getEmail())) return 0;
		return -1;
	}

	public void setLoged(boolean loged) {
		this.loged = loged;
	}

	public boolean isLoged() {
		return loged;
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
	
	public void updateUserProfile(ContaUsuario usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		gerenciaProfile.updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
	}
	
	public void setFieldPrivacy(String owner, String visibility, String field) throws Exception {
		gerenciaProfile.setPrivacy(owner, visibility, field);
	}
	
	public ProfileIF getProfile(ContaUsuario user, String visibility) {
		return gerenciaProfile.getProfile(user, visibility);
	}
	
	public void sendFriendshipRequest(String user, String message, String group) throws Exception{
		if(!(sentFriendship.contains(user))) {
			sentFriendship.add(user);
			gerenciaUsuario.sendFriendshipRequest(this.nome,this.sobrenome,this.email, user, message, group);
		}
		throw new Exception("Você já enviou um convite para esse usuário");
	}
	
	public void addFriendshipRequest(String name, String sobrenome, String email, String message) {
		pendingFriendship.add(name + " " + sobrenome + "<" + email + "> - mensagem: " + message);
	}
	
	private void createGroups() {
		grupos = new ArrayList<Grupo>();
		Grupo conhecidos = new Grupo("conhecidos");
		Grupo escola = new Grupo("escola");
		Grupo familia = new Grupo("familia");
		Grupo melhoresAmigos = new Grupo ("melhores amigos");
		Grupo trabalho = new Grupo("trabalho");
		grupos.add(conhecidos);
		grupos.add(escola);
		grupos.add(familia);
		grupos.add(melhoresAmigos);
		grupos.add(trabalho);
	}
	
}
