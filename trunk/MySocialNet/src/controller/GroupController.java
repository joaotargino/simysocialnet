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

	/**
	 * Adiciona usuário ao grupo
	 * @param login
	 * @param grupo
	 * @param loginToAdd
	 * @throws Exception
	 */
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
		List<UserAccount> users = group.getUsers();
		users.add(usuarioToAdd);
		group.setUsers(users);
		Collections.sort(group.getUsers());
		usuario.getGroups().put(grupo, group);
		this.dbController.update();
	} 
	
	/**
	 * Remove usuário de um grupo
	 * @param usuario
	 * @param usuarioToRemove
	 * @throws Exception
	 */
	private void removeFromOtherGroup(UserAccount usuario, UserAccount usuarioToRemove) throws Exception {
		boolean found = false;
		for(Group grupo : usuario.getGroups().values()) {
			for (UserAccount user : grupo.getUsers()) {
				if (user.equals(usuarioToRemove)) {
					grupo.getUsers().remove(usuarioToRemove);
					usuario.getGroups().put(grupo.getName(), grupo);
					found = true;
					break;
				}
			}
			if (found) break;
		}
		this.dbController.update();
	}

	/**
	 * Remove usuário do grupo
	 * @param login
	 * @param grupo
	 * @param loginToRemove
	 * @throws Exception
	 */
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
		
		this.dbController.update();
	}
	
	public Group getGroup(UserAccount usuario, String group) throws Exception {
		Group grupo = usuario.getGroups().get(group);
		if (grupo != null) return grupo;
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

	/**
	 * Lista todos os membros do grupo
	 * @param email
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Group listGroupMembers(String email, String group) throws Exception {
		UserAccount usuario = this.dbController.getUsers(email);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		return getGroup(usuario, group);
	}
}
