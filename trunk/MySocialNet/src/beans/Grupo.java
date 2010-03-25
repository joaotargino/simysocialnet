package beans;

import java.util.List;

public class Grupo {
	private String nome;
	private String descricao;
	//a analisar
	private List<ContaUsuario> usuarios;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<ContaUsuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<ContaUsuario> usuarios) {
		this.usuarios = usuarios;
	}
}
