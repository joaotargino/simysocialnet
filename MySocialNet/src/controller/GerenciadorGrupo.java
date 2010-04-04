package controller;

import java.util.List;

import beans.ContaUsuario;
import beans.Grupo;

public class GerenciadorGrupo {
	
	private GerenciadorUsuario gerenciadorUsuario;
	
	public void init() {
		gerenciadorUsuario = new GerenciadorUsuario();
	}

	public void adicionar(String login, String grupo, String loginToAdd) throws Exception {
		
		ContaUsuario usuario = gerenciadorUsuario.getUsuario(login);
		ContaUsuario usuarioToAdd = gerenciadorUsuario.getUsuario(loginToAdd);
		for(int i = 0; i < usuario.getGrupos().size(); i++) {
			if(usuario.getGrupos().get(i).getNome().equals(grupo) && 
					(!usuario.getGrupos().get(i).getUsuarios().contains(usuarioToAdd))) {
				usuario.getGrupos().get(i).getUsuarios().add(usuarioToAdd);
			}
		}
		gerenciadorUsuario.update(usuario);
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
