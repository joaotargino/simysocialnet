package controller;

import interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;
import beans.Grupo;

public class GerenciadorGrupo implements Gerenciavel<Grupo> {

	public void adicionar(String login, String grupo) {
		// TODO Auto-generated method stub

	}

	public void remover(String login, String group) {
		// TODO Auto-generated method stub

	}
	
	public List<ContaUsuario> listar() {
		return new ArrayList<ContaUsuario>();
	}

	public List<ContaUsuario> getMembros(String group) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContaUsuario getMembro(String friend, String group) {
		// TODO Auto-generated method stub
		return null;
	}

}
