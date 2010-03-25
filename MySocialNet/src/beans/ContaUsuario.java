package beans;

import java.util.List;

public class ContaUsuario {
	public String nome;
	public String sobrenome;
	public String dataNascimento;
	public String senha;
	public String email;
	public List<ContaUsuario> amigos;
	public List<Grupo> grupos;
	
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
		return amigos;
	}
	public void setAmigos(List<ContaUsuario> amigos) {
		this.amigos = amigos;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
}
