package controller;

import interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import beans.ContaUsuario;
import beans.Grupo;

public class GerenciadorGrupo implements Gerenciavel<Grupo> {

	public void adicionar(String login, String grupo) {
		// TODO Auto-generated method stub

	}

	public void remover(String login, String group) {
		// TODO Auto-generated method stub

	}
	
	public List<ContaUsuario> getMembros(String group) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContaUsuario getMembro(String friend, String group) {
		List<ContaUsuario> listaUsuarios = getMembros(group);
		for (ContaUsuario usuario : listaUsuarios) {
			if(usuario.getEmail().equals(friend)) {
				return usuario;
			}
		}
		return null;
	}

}
