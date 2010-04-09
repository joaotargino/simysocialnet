package controller;

import java.util.Collections;
import java.util.List;

import beans.UserAccount;
import beans.Group;

public class GroupController {
	
	private DBController dbController;
	

	public GroupController() {
		this.dbController = new DBController();
	}

	public void addToGroup(String login, String grupo, String loginToAdd) throws Exception {
		
		UserAccount usuario = this.dbController.getUsers(login);
		UserAccount usuarioToAdd;
		
		try {
			usuarioToAdd = this.dbController.getUsers(loginToAdd);
		} catch (Exception e) {
			throw new Exception("Usuário a ser adicionado inexistente no sistema");
		}
		
		Group group = getGroup(usuario, grupo);

		if (! usuario.isLogged()) throw new Exception("Usuário não logado");
		if (group.getUsers().contains(usuarioToAdd)) throw new Exception("Contato já existente no grupo " + grupo);
		
		removeFromOtherGroup(usuario, usuarioToAdd);
		
		group.getUsers().add(usuarioToAdd);
		Collections.sort(group.getUsers());
		this.dbController.update(usuario);
	} 
	
	private void removeFromOtherGroup(UserAccount usuario, UserAccount usuarioToAdd) throws Exception {
		
		for(int i = 0; i < usuario.getGroups().size(); i++) {
			for (int j = 0; j < usuario.getGroups().get(i).getUsers().size(); j++) {
				if (usuario.getGroups().get(i).getUsers().get(j).equals(usuarioToAdd)) {
					usuario.getGroups().get(i).getUsers().remove(usuarioToAdd);
				}
			}
		}
		this.dbController.update(usuario);
	}

	public void removeFromGroup(String login, String grupo, String loginToRemove) throws Exception {
		UserAccount usuario = this.dbController.getUsers(login);
		UserAccount usuarioToRemove;
		String conhecidos = "conhecidos";
		
		try {
			usuarioToRemove = this.dbController.getUsers(loginToRemove);
		} catch (Exception e) {
			throw new Exception("Usuário a ser removido inexistente no sistema");
		}
		
		Group group = getGroup(usuario, grupo);
		Group grupoConhecidos = getGroup(usuario, conhecidos);
		if (! usuario.isLogged()) throw new Exception("Usuário não logado");
		if (!group.getUsers().contains(usuarioToRemove)) throw new Exception("Contato não existente no grupo " + grupo);
		group.getUsers().remove(usuarioToRemove);
		grupoConhecidos.getUsers().add(usuarioToRemove);
		
		this.dbController.update(usuario);
	}
	
	public Group getGroup(UserAccount usuario, String group) throws Exception {
		for(Group grupo : usuario.getGroups()) {
			if (grupo.getName().equals(group)) return grupo;
		}
		throw new Exception("Grupo " + group + " não existe");
	}

	public UserAccount getMembro(String login, String friend, String group) throws Exception {
		
		UserAccount usuario = this.dbController.getUsers(login);
		if (!usuario.isLogged()) throw new Exception("Usuário não logado");
		
		List<UserAccount> listaUsuarios = getGroup(usuario,group).getUsers();
		for (UserAccount user : listaUsuarios) {
			String nomeCompleto = user.getName() + " " + user.getSurname();
			if(nomeCompleto.equals(friend) || user.getEmail().equals(friend)) {
				return user;
			}
		}
		return null;
	}

	public Group listGroupMembers(String email, String group) throws Exception {
		UserAccount usuario = this.dbController.getUsers(email);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		return getGroup(usuario, group);
	}
}
