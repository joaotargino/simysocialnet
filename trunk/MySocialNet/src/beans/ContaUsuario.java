package beans;

import java.util.ArrayList;
import java.util.List;

import Util.Util;

/**
 * @author Rafael Aquino
 *
 */
public class ContaUsuario {
	private String nome;
	private String sobrenome;
	private String dataNascimento;
	private String senha;
	private String email;
	private List<Grupo> grupos;

	public ContaUsuario(String nome, String sobrenome, String dataNascimento, String senha, String email) throws Exception {
		if(Util.verificaString(nome)) {
			this.nome = nome;
		}
		if(Util.verificaString(sobrenome)) {
			this.sobrenome = sobrenome;
		}
		if(Util.verificaData(dataNascimento)) {
			this.dataNascimento = dataNascimento;
		}
		this.senha = senha;
		if(Util.verificaEmail(email)) {
			this.email = email;
		}
		//TODO criar as exceções
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) throws Exception {
		if (Util.verificaString(nome)) {
			this.nome = nome;
		}
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) throws Exception {
		if (Util.verificaString(sobrenome)) {
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
	public void setSenha(String senha) {
		this.senha = senha;
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
		List<ContaUsuario> amigos = new ArrayList<ContaUsuario>();
		for (Grupo grupo : grupos) {
			amigos.addAll(grupo.getUsuarios());
		}
		return amigos;
	}
	
	public ContaUsuario getAmigo(String email) {
		//TODO
		return null;
	}
	
	public void removerAmigo(String email) {
		//TODO
	}
	
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	@Override
	public String toString() {
		return nome + " " + sobrenome;
	}


}
