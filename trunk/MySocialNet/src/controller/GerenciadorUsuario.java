package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import Util.Util;
import beans.ContaUsuario;
import beans.Grupo;
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
		for (ContaUsuario usuario : UsersDAO.getInstance().getUsuarios()) {
			String nomeCompleto = usuario.getNome() + " " + usuario.getSobrenome();
			if (usuario.getEmail().equals(login) || nomeCompleto.equals(login)) return usuario;
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
		Map<String,String> sentFriendship = logado.getSentFriendship();
		Map<String,String> pendingFriendship = convidado.getPendingFriendship();
		if (sentFriendship.keySet().contains(user)) throw new Exception("Você já enviou um convite para esse usuário");
		sentFriendship.put(user,group);
		pendingFriendship.put(user, logado.getNome() + " " + logado.getSobrenome() + " <" + login + "> - mensagem: " + message);
		logado.setSentFriendship(sentFriendship);
		convidado.setPendingFriendship(pendingFriendship);
		update(logado);
		update(convidado);
		
	}
	
	public void acceptFriendshipRequest(String login, String contact,String group) throws Exception {
		ContaUsuario user = getUsuario(login);
		ContaUsuario contato = getUsuario(contact);
		if(!(user.isLoged())) throw new Exception("Usuário não logado");
		for (String string : user.getPendingFriendship().keySet()) {
			if(string.equals(login)) {
				Grupo grupo = user.getGrupo(user,group);
				grupo.getUsuarios().add(contato);
				Collections.sort(grupo.getUsuarios());
			}
		}
		Grupo grupo = contato.getGrupo(contato, contato.getSentFriendship().get(login));
		grupo.getUsuarios().add(user);
		Collections.sort(grupo.getUsuarios());
		
		Map<String,String> sent = contato.getSentFriendship();
		sent.remove(login);
		contato.setSentFriendship(sent);
		Map<String,String> pending = user.getPendingFriendship();
		pending.remove(contact);
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
		Map<String,String> pending = usuario.getPendingFriendship();
		List<String> resposta = new ArrayList<String>();
		if(pending.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");
		for (String value : pending.values()) {
			resposta.add(value);
		}
		return resposta;
	}

	public List<String> viewSentFriendship(String login)throws Exception {
		ContaUsuario usuario = getUsuario(login);
		
		if(!(usuario.isLoged())) throw new Exception("Usuário não logado");
		Map<String,String> sent = usuario.getSentFriendship();
		List<String> resposta = new ArrayList<String>();
		if(sent.isEmpty()) resposta.add("Não há nenhuma solicitação de amizade pendente");;
		for (String key : sent.keySet()) {
			resposta.add(key);
		}
		return resposta;
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
		Map<String,String> pending = contato.getPendingFriendship();
		String chave = "";
		for (String key : pending.keySet()) {
			if(key.equals(contact)) {
				chave = key;
			}
		}
		pending.remove(chave);
		usuario.setPendingFriendship(pending);

		Map<String,String> sent = usuario.getSentFriendship();
		String index = "";
		for (String string : sent.keySet()) {
			if(string.equals(login)) {
				index = string;
			}
		}
		sent.remove(index);
		contato.setSentFriendship(sent);
		update(usuario);
		update(contato);
	}

	public ContaUsuario getAmigo(String email, String friend) throws Exception {
		ContaUsuario user = getUsuario(email);
		ContaUsuario amigo;
		if (!user.isLoged()) {
			amigo = getUsuario(friend);
			throw new Exception("Usuário não logado");
		}
		try {
			amigo = getUsuario(friend);
		} catch (Exception e) {
			return null;
		}

		for(ContaUsuario usuario : user.getAmigos()) {
			String nomeCompleto = usuario.getNome() + " " + usuario.getSobrenome();
			if (usuario.equals(amigo) || nomeCompleto.equals(friend)) return usuario;
		}
		return null;
	}
	
	

}
