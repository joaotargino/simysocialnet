package controller;

import java.util.List;

import beans.ContaUsuario;

public class GerenciadorGrupo {

	public void adicionar(String login, String grupo) {
		// TODO Auto-generated method stub

	}

	public void remover(String login, String group) {
		// TODO Auto-generated method stub

	}
	
	public List<ContaUsuario> getMembros(String group) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContaUsuario getMembro(String friend, String group) {
		List<ContaUsuario> listaUsuarios = getMembros(group);
		for (ContaUsuario usuario : listaUsuarios) {
			if(usuario.getEmail().equals(friend)) {
				return usuario;
			}
		}
		return null;
	}

}
