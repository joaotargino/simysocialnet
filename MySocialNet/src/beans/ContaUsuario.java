package beans;

import interfaces.Preferenciavel;

import java.util.ArrayList;
import java.util.List;

import Util.Util;

/**
 * @author Rafael Aquino
 *
 */
public class ContaUsuario implements Comparable<ContaUsuario>{
	
	private String nome;
	private Profile<Preferenciavel> profile;
	private String sobrenome;
	private String dataNascimento;
	private String senha;
	private String email;
	private List<Grupo> grupos;
	private List<ContaUsuario> amigos;
	private List<String> preferencias;
	private boolean loged;
	
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
	}

	public ContaUsuario() {
		preferencias = new ArrayList<String>();
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

	public void setProfile(Profile<Preferenciavel> profile) {
		this.profile = profile;
	}

	public Profile<Preferenciavel> getProfile() {
		return profile;
	}
	
	public void setFieldPrivacy(String field, String visibility) {
		profile.setFieldPrivacy
		(field, visibility);
	}

	public String checkProfile(String visibility) {
		return profile.checkProfile(visibility);
	}
	public static void main(String[] args) {
//		ContaUsuario usuario = new ContaUsuario("Telles","Nobrega","")
	}
}
