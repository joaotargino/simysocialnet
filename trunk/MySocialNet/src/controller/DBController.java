package controller;

import java.util.Iterator;
import java.util.List;

import Util.Util;
import beans.UserAccount;
import dao.UsersDAO;

public class DBController {
	
	/**
	 * Adiciona um amigo ao DB
	 * @param contaUsuario
	 * @throws Exception
	 */
	public void addToDB(UserAccount contaUsuario) throws Exception {
		if (UsersDAO.getInstance().getUsuarios().contains(contaUsuario)) throw new Exception("Login indisponível");
		UsersDAO.getInstance().create(contaUsuario);
	}

	/**
	 * Remove amigo do DB
	 * @param login
	 * @throws Exception
	 */
	public void removeFromDB(String login) throws Exception {
		UserAccount user = new UserAccount();
		user.setEmail(login);
		UsersDAO.getInstance().delete(login);

	}	
	
	/**
	 * Retorna um usuário do bd
	 * @param login o login do usuário
	 * @return o usuário
	 * @throws Exception
	 */
	public UserAccount getUsers(String login) throws Exception {
		if (login.trim().isEmpty()) throw new Exception("Login não pode ser vazio");
		for (UserAccount usuario : UsersDAO.getInstance().getUsuarios()) {
			String nomeCompleto = usuario.getName() + " " + usuario.getSurname();
			if (usuario.getEmail().equals(login) || nomeCompleto.equals(login)) return usuario;
		}
		throw new Exception("Login inexistente");
	}
	
	/**
	 * @return todos os usuários 
	 */
	public List<UserAccount> getAllUsers() {
		return UsersDAO.getInstance().getUsuarios();
	}
	
	/**
	 * Procura um usuário no DB
 	 * @param login o login do usuário
	 * @param friend o login do amigo
	 * @return o amigo
	 * @throws Exception 
	 */
	public UserAccount findNewFriend(String login, String friend) throws Exception {
		UserAccount usuario;
		try{
			usuario = getUsers(login);
		}catch (Exception e) {
			throw new Exception("Login inexistente");
		}
		if(!(usuario.isLogged())) throw new Exception("Usuário não logado");
		try {
			if(Util.verifyEmail(friend)) {
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
	
	/**
	 * Atualiza o DB
	 * @param user
	 */
	public void update(UserAccount user) {
		UsersDAO.getInstance().update(user);
	}

	/**
	 * Retorna usuário
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public UserAccount getUser(String login) throws Exception {
		UserAccount usuario = getUsers(login);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		return usuario;
	}
	
	/**
	 * Procura um usuário pelo nome completo dele
	 * @param completeName
	 * @return o usário, se encontrar
	 * @throws Exception
	 */
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