package controller;

import interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;

public class GerenciadorUsuario implements Gerenciavel<ContaUsuario> {

	public void adicionar(ContaUsuario contaUsuario) {
		// TODO Auto-generated method stub

	}

	public void remover(String login) {
		// TODO Auto-generated method stub

	}
	
	public List<ContaUsuario> buscar() {
		return new ArrayList<ContaUsuario>();
	}

	public ContaUsuario getUsuario(String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
