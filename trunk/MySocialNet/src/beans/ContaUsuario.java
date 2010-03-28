package beans;

import java.util.ArrayList;
import java.util.List;

public class ContaUsuario {
	public String nome;
	public String sobrenome;
	public String dataNascimento;
	public String senha;
	public String email;
//	public List<ContaUsuario> amigos;
	public List<Grupo> grupos;
	
	public ContaUsuario(String nome, String sobrenome, String dataNascimento, String senha, String email) throws Exception {
		if(Util.verificaString(nome)) {
			this.nome = nome;
		}
		if(Util.verificaString(sobrenome)) {
			this.sobrenome = sobrenome;
		}
		this.dataNascimento = dataNascimento;
		this.senha = senha;
		//TODO criar a comparação se o email eh valido 
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<ContaUsuario> getAmigos() {
		List<ContaUsuario> amigos = new ArrayList<ContaUsuario>();
		for (Grupo grupo : grupos) {
			amigos.addAll(grupo.getUsuarios());
		}
		return amigos;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	
}
