package controller;

import java.util.ArrayList;
import java.util.List;

import Util.Util;
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
		ContaUsuario convidado; 
		try {
			convidado = getUsuario(user);
		}catch(Exception e) {
			throw new Exception("Contato inexistente");
		}
		if(login.equals(user)) throw new Exception("Operação não permitida");
		ContaUsuario logado = getUsuario(login);
		if(!(logado.isLoged())) throw new Exception("Usuário não logado");
		List<String> sentFriendship = logado.getSentFriendship();
		List<String> pendingFriendship = convidado.getPendingFriendship();
		if (sentFriendship.contains(user)) throw new Exception("Você já enviou um convite para esse usuário");
		sentFriendship.add(user);
		pendingFriendship.add(logado.getNome() + " " + logado.getSobrenome() + " <" + login + "> - mensagem: " + message);
		logado.setSentFriendship(sentFriendship);
		convidado.setPendingFriendship(pendingFriendship);
		update(logado);
		update(convidado);
		
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
		List<String> sent = contato.getSentFriendship();
		sent.remove(contact);
		contato.setSentFriendship(sent);
		List<String> pending = user.getPendingFriendship();
		for (String string : pending) {
			String[] array = string.split("<");
			String[] arrayAux = array[1].split(">");
			if(arrayAux[0].equals(contact)) {
				pending.remove(string);
			}
		}
		update(contato);
		update(user);
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

	public List<String> viewPendingFriendship(String login) throws Exception{
		ContaUsuario usuario = getUsuario(login);
		
		if(!(usuario.isLoged())) throw new Exception("Usuário não logado");
		List<String> pending = usuario.getPendingFriendship();
		if(pending.isEmpty()) pending.add("Não há nenhuma solicitação de amizade pendente");
		return pending;
	}

	public List<String> viewSentFriendship(String login)throws Exception {
		ContaUsuario usuario = getUsuario(login);
		
		if(!(usuario.isLoged())) throw new Exception("Usuário não logado");
		List<String> sent = usuario.getSentFriendship();
		if(sent.isEmpty()) sent.add("Não há nenhuma solicitação de amizade pendente");;
		return sent;
	}

	public ContaUsuario findNewFriend(String login, String friend) throws Exception {
		ContaUsuario usuario;
		try{
			usuario = getUsuario(login);
		}catch (Exception e) {
			throw new Exception("Login inexistente");
		}
		if(!(usuario.isLoged())) throw new Exception("Usuário não logado");
		try {
			if(Util.verificaEmail(friend)) {
				return getUsuario(friend);
			}
		}catch (Exception e) {
			for (ContaUsuario user : UsersDAO.getInstance().getUsuarios()) {
				if((user.getNome() + " " + user.getSobrenome()).equalsIgnoreCase(friend)) {
					return user;
				}
			}
		}
		return null;
	}

	public void declineFriendshipRequest(String login, String contact) throws Exception{
		ContaUsuario usuario;
		ContaUsuario contato;
		try{
			usuario = getUsuario(login);
			contato = getUsuario(contact);
		}catch (Exception e) {
			throw new Exception("Login inexistente");
		}
		if(!(usuario.isLoged())) throw new Exception("Usuário não logado");
		List<String> pending = usuario.getPendingFriendship();
		pending.clear();
		usuario.setPendingFriendship(pending);
		List<String> sent = contato.getSentFriendship();
		sent.clear();
		contato.setSentFriendship(sent);
		update(usuario);
		update(contato);
	}
	
	

}
