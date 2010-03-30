package controller;

import interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

import beans.ContaUsuario;

public class GerenciadorUsuario implements Gerenciavel<ContaUsuario> {

	@Override
	public void adicionar(ContaUsuario contaUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remover(ContaUsuario contaUsuario) {
		// TODO Auto-generated method stub

	}
	
	public List<ContaUsuario> buscar() {
		return new ArrayList<ContaUsuario>();
	}

}
