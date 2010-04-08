package controller;

import java.util.List;

import beans.UserAccount;
import dao.UsersDAO;

public class UserController {
	private DBController DBController;
	
	public UserController() {
		DBController = new DBController();
		
	}
	
	public void addUserPreferences(UserAccount user,String preferencia) {
		user.addUserPreferences(preferencia);
	}
	
	public void sendFriendshipRequest(String login, String user, String message, String group) throws Exception {
		UserAccount convidado; 
		try {
			convidado = this.DBController.getUsers(user);
		}catch(Exception e) {
			throw new Exception("Contato inexistente");
		}
		if(login.equals(user)) throw new Exception("Operação não permitida");
		UserAccount logado = this.DBController.getUsers(login);
		if(!(logado.isLogged())) throw new Exception("Usuário não logado");
		logado.sendFriendshipRequest(user, group);
		convidado.receiveFriendshipRequest(logado, login, message);
	}
	
	public void acceptFriendshipRequest(String login, String contact,String group) throws Exception {
		UserAccount user = this.DBController.getUsers(login);
		UserAccount contato = this.DBController.getUsers(contact);
		if(!(user.isLogged())) throw new Exception("Usuário não logado");
		user.acceptFriendshipRequest(contact, group, contato);
		contato.removeSentFriendshipRequest(login,user);
	}

	public void clean() {
		UsersDAO.getInstance().reset();
	}
	
	public List<UserAccount> getAmigos(String email) throws Exception {
		UserAccount usuario = this.DBController.getUsers(email);
		if(!usuario.isLogged()) throw new Exception("Usuário não logado");
		return usuario.getFriends();
	}

	public List<String> viewPendingFriendship(String login) throws Exception{
		UserAccount usuario = this.DBController.getUsers(login);
		
		if(!(usuario.isLogged())) throw new Exception("Usuário não logado");
		return usuario.viewPendingFriendship();
	}

	public List<String> viewSentFriendship(String login)throws Exception {
		UserAccount usuario = this.DBController.getUsers(login);
		
		if(!(usuario.isLogged())) throw new Exception("Usuário não logado");
		return usuario.viewSentFriendship();
	}

	public void declineFriendshipRequest(String login, String contact) throws Exception{
		UserAccount usuario;
		UserAccount contato;
		try{
			usuario = this.DBController.getUsers(login);
			contato = this.DBController.getUsers(contact);
		}catch (Exception e) {
			throw new Exception("Login inexistente");
		}
		if(!(usuario.isLogged())) throw new Exception("Usuário não logado");
		usuario.declineFriendshipRequest(contact, usuario.getPendingFriendship());
		contato.declineFriendshipRequest(login, contato.getSentFriendship());
	}

	public UserAccount getFriend(String email, String friend) throws Exception {
		UserAccount user = this.DBController.getUsers(email);
		UserAccount amigo;
		if (!user.isLogged()) {
			amigo = this.DBController.getUsers(friend);
			throw new Exception("Usuário não logado");
		}
		try {
			amigo = this.DBController.getUsers(friend);
		} catch (Exception e) {
			return null;
		}
		return user.getFriend(amigo, friend);
	}
	
	

}
