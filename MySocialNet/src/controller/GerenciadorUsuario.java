package controller;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;
import dao.usersDAO;

public class GerenciadorUsuario {

	private static GerenciadorUsuario gerenciadorUsuario;

	public synchronized static GerenciadorUsuario getInstance() {
		if (gerenciadorUsuario == null) {
			gerenciadorUsuario = new GerenciadorUsuario();
		}
		return gerenciadorUsuario;
	}

	public void adicionar(ContaUsuario contaUsuario) throws Exception {
		if (usersDAO.getUsuarios().contains(contaUsuario)) throw new Exception("Login indisponível");
		usersDAO.cadastraUsuario(contaUsuario);
	}

	public void remover(String login) throws Exception {
		ContaUsuario user = new ContaUsuario();
		user.setEmail(login);
		usersDAO.removeUsuario(login);

	}

	public List<ContaUsuario> buscar() {
		return new ArrayList<ContaUsuario>();
	}

	public ContaUsuario getUsuario(String login) throws Exception {
		if (login.trim().isEmpty()) throw new Exception("Login não pode ser vazio");
		ContaUsuario user = new ContaUsuario();
		user.setEmail(login);
		for (ContaUsuario usuario : usersDAO.getUsuarios()) {
			if (usuario.equals(user)) return usuario;
		}
		throw new Exception("Login inexistente");
	}

	public void updateUserProfile(ContaUsuario contaUsuario) {
		usersDAO.atualizaUsuario(contaUsuario);

	}

	public void addUserPreferences(ContaUsuario user,String preferencia) {
		if (!user.getPreferencias().contains(preferencia)) {
			user.getPreferencias().add(preferencia);
			usersDAO.atualizaUsuario(user);
		}
	}

}
