package controller;

import java.util.List;

import beans.ContaUsuario;
import beans.Grupo;

public class GerenciadorGrupo {
	
	private static GerenciadorGrupo instance;
	
	public static synchronized GerenciadorGrupo getInstance() {
		if (instance == null) {
			instance = new GerenciadorGrupo();
		}
		return instance;
	}

	public void adicionar(String login, String grupo) {
		// TODO Auto-generated method stub

	}

	public void remover(String login, String group) {
		// TODO Auto-generated method stub

	}
	
	public Grupo getGrupo(ContaUsuario usuario, String group) throws Exception {
		for(Grupo grupo : usuario.getGrupos()) {
			if (grupo.getNome().equals(group)) return grupo;
		}
		throw new Exception("Grupo " + group + " não existe");
	}

	public ContaUsuario getMembro(ContaUsuario user,String friend, String group) throws Exception {
		List<ContaUsuario> listaUsuarios = getGrupo(user,group).getUsuarios();
		for (ContaUsuario usuario : listaUsuarios) {
			if(usuario.getEmail().equals(friend)) {
				return usuario;
			}
		}
		return null;
	}
}
