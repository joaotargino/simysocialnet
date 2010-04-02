package controller;

import interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;

public class GerenciadorUsuario implements Gerenciavel<ContaUsuario> {

	private static GerenciadorUsuario gerenciadorUsuario;
	private List<ContaUsuario> usuarios = new ArrayList<ContaUsuario>();

	public synchronized static GerenciadorUsuario getInstance() {
		if (gerenciadorUsuario == null) {
			gerenciadorUsuario = new GerenciadorUsuario();
		}
		return gerenciadorUsuario;
	}

	public void adicionar(ContaUsuario contaUsuario) throws Exception {
		if (usuarios.contains(contaUsuario)) throw new Exception("Login indisponível");
		usuarios.add(contaUsuario);
	}

	public void remover(String login) throws Exception {
		ContaUsuario user = new ContaUsuario();
		user.setEmail(login);
		usuarios.remove(user);

	}

	public List<ContaUsuario> buscar() {
		return new ArrayList<ContaUsuario>();
	}

	public ContaUsuario getUsuario(String login) throws Exception {
		if (login.trim().isEmpty()) throw new Exception("Login não pode ser vazio");
		ContaUsuario user = new ContaUsuario();
		user.setEmail(login);
		for (ContaUsuario usuario : usuarios) {
			if (usuario.equals(user)) return usuario;
		}
		throw new Exception("Login inexistente");
	}

	public void updateUserProfile(ContaUsuario contaUsuario) {
		// TODO Auto-generated method stub

	}

	public void addUserPreferences(ContaUsuario user,String preferencia) {
		if (!user.getPreferencias().contains(preferencia)) {
			user.getPreferencias().add(preferencia);
		}
	}

}
