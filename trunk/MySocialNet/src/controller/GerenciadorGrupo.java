package controller;

import java.util.Collections;
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
		ContaUsuario usuarioToAdd;
		
		try {
			usuarioToAdd = gerenciadorUsuario.getUsuario(loginToAdd);
		} catch (Exception e) {
			throw new Exception("Usuário a ser adicionado inexistente no sistema");
		}
		
		Grupo group = getGrupo(usuario, grupo);

		if (! usuario.isLoged()) throw new Exception("Usuário não logado");
		if (group.getUsuarios().contains(usuarioToAdd)) throw new Exception("Contato já existente no grupo " + grupo);
		
		removerDeOutroGrupo(usuario, usuarioToAdd);
		
		group.getUsuarios().add(usuarioToAdd);
		Collections.sort(group.getUsuarios());
		gerenciadorUsuario.update(usuario);
	} 
	
	private void removerDeOutroGrupo(ContaUsuario usuario, ContaUsuario usuarioToAdd) throws Exception {
		
		for(int i = 0; i < usuario.getGrupos().size(); i++) {
			for (int j = 0; j < usuario.getGrupos().get(i).getUsuarios().size(); j++) {
				if (usuario.getGrupos().get(i).getUsuarios().get(j).equals(usuarioToAdd)) {
					usuario.getGrupos().get(i).getUsuarios().remove(usuarioToAdd);
				}
			}
		}
		gerenciadorUsuario.update(usuario);
	}

	public void remover(String login, String grupo, String loginToRemove) throws Exception {
		ContaUsuario usuario = gerenciadorUsuario.getUsuario(login);
		ContaUsuario usuarioToAdd;
		
		try {
			usuarioToAdd = gerenciadorUsuario.getUsuario(loginToRemove);
		} catch (Exception e) {
			throw new Exception("Usuário a ser removido inexistente no sistema");
		}
		
		Grupo group = getGrupo(usuario, grupo);

		if (! usuario.isLoged()) throw new Exception("Usuário não logado");
		if (!group.getUsuarios().contains(usuarioToAdd)) throw new Exception("Contato não existente no grupo " + grupo);
		group.getUsuarios().remove(usuarioToAdd);
		gerenciadorUsuario.update(usuario);
	}
	
	public Grupo getGrupo(ContaUsuario usuario, String group) throws Exception {
		for(Grupo grupo : usuario.getGrupos()) {
			if (grupo.getNome().equals(group)) return grupo;
		}
		throw new Exception("Grupo " + group + " não existe");
	}

	public ContaUsuario getMembro(String login, String friend, String group) throws Exception {
		
		ContaUsuario usuario = gerenciadorUsuario.getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		
		List<ContaUsuario> listaUsuarios = getGrupo(usuario,group).getUsuarios();
		for (ContaUsuario user : listaUsuarios) {
			String nomeCompleto = user.getNome() + " " + user.getSobrenome();
			if(nomeCompleto.equals(friend) || user.getEmail().equals(friend)) {
				return user;
			}
		}
		return null;
	}
}
