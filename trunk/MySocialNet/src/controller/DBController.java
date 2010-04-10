package controller;

import java.util.Iterator;
import java.util.List;

import Util.Util;
import beans.UserAccount;
import dao.UsersDAO;

public class DBController {
	
	public void addToDB(UserAccount contaUsuario) throws Exception {
		if (UsersDAO.getInstance().getUsuarios().contains(contaUsuario)) throw new Exception("Login indisponível");
		UsersDAO.getInstance().create(contaUsuario);
	}

	public void removeFromDB(String login) throws Exception {
		UserAccount user = new UserAccount();
		user.setEmail(login);
		UsersDAO.getInstance().delete(login);

	}	
	
	public UserAccount getUsers(String login) throws Exception {
		if (login.trim().isEmpty()) throw new Exception("Login não pode ser vazio");
		for (UserAccount usuario : UsersDAO.getInstance().getUsuarios()) {
			String nomeCompleto = usuario.getName() + " " + usuario.getSurname();
			if (usuario.getEmail().equals(login) || nomeCompleto.equals(login)) return usuario;
		}
		throw new Exception("Login inexistente");
	}
	
	public List<UserAccount> getAllUsers() {
		return UsersDAO.getInstance().getUsuarios();
	}
	
	public UserAccount findNewFriend(String login, String friend) throws Exception {
		UserAccount usuario;
		try{
			usuario = getUsers(login);
		}catch (Exception e) {
			throw new Exception("Login inexistente");
		}
		if(!(usuario.isLogged())) throw new Exception("Usuário não logado");
		try {
			if(Util.verificaEmail(friend)) {
				return getUsers(friend);
			}
		}catch (Exception e) {
			for (UserAccount user : UsersDAO.getInstance().getUsuarios()) {
				if((user.getName() + " " + user.getSurname()).equalsIgnoreCase(friend)) {
					return user;
				}
			}
		}
		return null;
	}
	
	public void update(UserAccount user) {
		UsersDAO.getInstance().update(user);
	}

	public UserAccount getUser(String login) throws Exception {
		UserAccount usuario = getUsers(login);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		return usuario;
	}
	
	public UserAccount getUserFromCompleteName(String completeName) throws Exception {
		List<UserAccount> allUsers = this.getAllUsers();
		Iterator<UserAccount> allUsersIterator = allUsers.iterator();
		UserAccount iteratedUser;
		while (allUsersIterator.hasNext()) {
			iteratedUser = allUsersIterator.next();
			String iteratedUserCompleteName = iteratedUser.getName() + " " +iteratedUser.getSurname();
			if (iteratedUserCompleteName.equals(completeName)) return iteratedUser;
		}
		throw new Exception(completeName);
	}
	
}