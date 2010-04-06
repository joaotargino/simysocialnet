package controller;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;
import dao.UsersDAO;

public class GerenciadorUsuario {

	public void adicionar(ContaUsuario contaUsuario) throws Exception {
		if (UsersDAO.getInstance().getUsuarios().contains(contaUsuario)) throw new Exception("Login indisponível");
		UsersDAO.getInstance().create(contaUsuario);
	}

	public void remover(String login) throws Exception {
		ContaUsuario user = new ContaUsuario();
		user.setEmail(login);
		UsersDAO.getInstance().delete(login);

	}

	public List<ContaUsuario> buscar() {
		return new ArrayList<ContaUsuario>();
	}

	public ContaUsuario getUsuario(String login) throws Exception {
		if (login.trim().isEmpty()) throw new Exception("Login não pode ser vazio");
		ContaUsuario user = new ContaUsuario();
		user.setEmail(login);
		for (ContaUsuario usuario : UsersDAO.getInstance().getUsuarios()) {
			if (usuario.equals(user)) return usuario;
		}
		throw new Exception("Login inexistente");
	}

	public void addUserPreferences(ContaUsuario user,String preferencia) {
		if (!user.getPreferencias().contains(preferencia)) {
			user.getPreferencias().add(preferencia);
			UsersDAO.getInstance().update(user);
		}
	}
	
	public void sendFriendshipRequest(String login, String user, String message, String group) throws Exception {
		ContaUsuario logado = getUsuario(login);
		if(!(logado.isLoged())) throw new Exception("Usuário não logado");
		ContaUsuario convidado = getUsuario(user);
		convidado.addFriendshipRequest(logado.getNome(), logado.getSobrenome(), logado.getEmail(), message);
		
	}
	
	public void acceptFriendshipRequest(String login, String contact,String group) throws Exception {
		ContaUsuario user = getUsuario(login);
		ContaUsuario contato = getUsuario(contact);
		if(!(user.isLoged())) throw new Exception("Usuário não logado");
		for (String string : contato.getSentFriendship()) {
			if(string.equals(login)) {
				user.acceptFriendshipRequest(contact,group);
			}
		}
	}

	public void clean() {
		UsersDAO.getInstance().reset();
	}
	
	public void update(ContaUsuario user) {
		UsersDAO.getInstance().update(user);
	}

	public List<ContaUsuario> getAmigos(String email) throws Exception {
		ContaUsuario usuario = getUsuario(email);
		if(!usuario.isLoged()) throw new Exception("Usuário não logado");
		return usuario.getAmigos();
	}

	

}
