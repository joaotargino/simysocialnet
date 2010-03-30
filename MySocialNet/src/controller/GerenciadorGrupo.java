package controller;

import interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;
import beans.Grupo;

public class GerenciadorGrupo implements Gerenciavel<Grupo> {

	@Override
	@Deprecated
	public void adicionar(Grupo grupo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remover(Grupo grupo) {
		// TODO Auto-generated method stub

	}
	
	public List<ContaUsuario> listar() {
		return new ArrayList<ContaUsuario>();
	}

}
