package beans;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
	
	private String nome;
	private String descricao;
	//a analisar
	private List<ContaUsuario> usuarios;
	
	public Grupo(String nome) {
		this.nome = nome;
		usuarios = new ArrayList<ContaUsuario>();
	}
	
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
	
	@Override
	public String toString() {
		String string = "";
		for(ContaUsuario usuario : getUsuarios()) {
			string += usuario.getNome() + " " + usuario.getSobrenome() + ",";
		}
		if (string.length() > 0) string = string.substring(0, string.length() - 1);
		return string;
	}
}
