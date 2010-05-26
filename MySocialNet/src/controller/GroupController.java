package controller;

import java.util.Collections;
import java.util.List;

import beans.Group;
import beans.UserAccount;
import facades.DBFacade;

public class GroupController {
	
	private DBFacade dbFacade;
	

	public GroupController() {
		this.dbFacade = DBFacade.getInstance();;
	}

	/**
	 * Adiciona usuário ao grupo
	 * @param login
	 * @param grupo
	 * @param loginToAdd
	 * @throws Exception
	 */
	public void addToGroup(String login, String grupo, String loginToAdd) throws Exception {
		
		UserAccount usuario = this.dbFacade.getUsers(login);
		UserAccount usuarioToAdd;
		
		try {
			usuarioToAdd = this.dbFacade.getUsers(loginToAdd);
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
		this.dbFacade.update(usuario);
		usuario.updateFriends();
		this.dbFacade.update();
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
		this.dbFacade.update(usuario);
	}

	/**
	 * Remove usuário do grupo
	 * @param login
	 * @param grupo
	 * @param loginToRemove
	 * @throws Exception
	 */
	public void removeFromGroup(String login, String grupo, String loginToRemove) throws Exception {
		UserAccount usuario = this.dbFacade.getUsers(login);
		UserAccount usuarioToRemove;
		String conhecidos = "conhecidos";
		
		try {
			usuarioToRemove = this.dbFacade.getUsers(loginToRemove);
		} catch (Exception e) {
			throw new Exception("Usuário a ser removido inexistente no sistema");
		}
		
		Group group = getGroup(usuario, grupo);
		Group grupoConhecidos = getGroup(usuario, conhecidos);
		if (! usuario.isLogged()) throw new Exception("Usuário não logado");
		if (!group.getUsers().contains(usuarioToRemove)) throw new Exception("Contato não existente no grupo " + grupo);
		group.getUsers().remove(usuarioToRemove);
		grupoConhecidos.getUsers().add(usuarioToRemove);
		
		this.dbFacade.update(usuario);
	}
	
	public Group getGroup(UserAccount usuario, String group) throws Exception {
		Group grupo = usuario.getGroups().get(group);
		if (grupo != null) return grupo;
		throw new Exception("Grupo " + group + " não existe");
	}

	public UserAccount getMembro(String login, String friend, String group) throws Exception {
		
		UserAccount usuario = this.dbFacade.getUsers(login);
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
		UserAccount usuario = this.dbFacade.getUsers(email);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		return getGroup(usuario, group);
	}
}
