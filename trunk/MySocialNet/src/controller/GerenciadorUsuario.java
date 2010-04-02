package controller;

import interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;

public class GerenciadorUsuario implements Gerenciavel<ContaUsuario> {

	List<ContaUsuario> usuarios = new ArrayList<ContaUsuario>();
	
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
		ContaUsuario user = new ContaUsuario();
		user.setEmail(login);
		for (ContaUsuario usuario : usuarios) {
			if (usuario.equals(user)) return usuario;
		}
		throw new Exception("Login inexistente");
	}

}
