package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import beans.ContaUsuario;
import beans.Grupo;

/**
 * Classe responsavel pela manipulação com o banco de dados
 * 
 * @author Arnett Rufino
 * @author Erickson Filipe
 * @author Jessica Priscila
 * @author Joao Paulo
 * @version 1.0 - 2009
 */
public class BD {

	// arquivo com o BD dos usuarios
	private static final File USUARIOSBD = new File("./src/dao/usuariosBD.xml");

	/**
	 * Retorna uma lista de usuarios cadastrados no banco de dados
	 * 
	 * @return List<ContaUsuario> lista de usuarios cadastrados no BD
	 */
	public static List<ContaUsuario> getUsuarios() {
		try {
			List<ContaUsuario> users = (List) Serializar.recuperar(USUARIOSBD);
			return users;
		} catch (Exception e1) {
			return new ArrayList<ContaUsuario>();
		}
	}

	/**
	 * Retorna um usuario cadastrado no banco de dados, cujo email eh passado
	 * como parametro
	 * 
	 * @param email
	 * @return Um usuario cujo email eh passado como parametro, e null caso o
	 *         usuario nao seja encontrado
	 */
	public static ContaUsuario getUsuario(String email) {
		List<ContaUsuario> users = getUsuarios();
		Iterator<ContaUsuario> it = users.iterator();
		while (it.hasNext()) {
			ContaUsuario user = it.next();
			if (user.getEmail().equals(email))
				return user;
		}
		return null;
	}

	/**
	 * Retorna um usuario cadastrado no banco de dados, cujo Nome e Sobrenome
	 * sao passados por parametro
	 * 
	 * @param nome
	 *            - o primeiro nome do usuario (pode ser composto)
	 * @param sobrenome
	 *            - o sobrenome do usuario
	 * @return Um usuario cujo nome e sobrenome sao passados por parametro, e
	 *         null caso o usuario nao seja encontrado
	 */
	public static ContaUsuario getUsuario(String nome, String sobrenome) {
		List<ContaUsuario> users = getUsuarios();
		Iterator<ContaUsuario> it = users.iterator();
		while (it.hasNext()) {
			ContaUsuario user = it.next();
			if (user.getNome().equalsIgnoreCase(nome))
				if (user.getSobrenome().equalsIgnoreCase(sobrenome))
					return user;
		}
		return null;
	}

	/**
	 * Cadastra qualquer tipo de usuario no banco de dados.
	 * 
	 * @param usuario
	 * @return true se o cadastro for efetuado com sucesso, false caso
	 *         contrario.
	 * @throws IOException
	 */
	public static boolean cadastraUsuario(ContaUsuario usuario) throws IOException {
		if (usuario == null)
			return false;
		List<ContaUsuario> users = getUsuarios();
		Iterator<ContaUsuario> it = users.iterator();
		while (it.hasNext()) {
			ContaUsuario user = it.next();
			if (user.equals(usuario)) {
				return false;
			}
		}
		users.add(usuario);
		Serializar.salvar(USUARIOSBD, users);
		return true;
	}

	/**
	 * Remove o usuário, cujo email eh igual ao passado como parametro, se
	 * possivel.
	 * 
	 * @param email
	 * @return true, se for possivel remover o usuario, e false caso contrario.
	 */
	public static boolean removeUsuario(String email) {
		List<ContaUsuario> users = getUsuarios();
		Iterator<ContaUsuario> it = users.iterator();
		while (it.hasNext()) {
			ContaUsuario user = it.next();
			if (user.getEmail().equals(email)) {
				users.remove(user);
				try {
					Serializar.salvar(USUARIOSBD, users);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove o usuário, cujo nome e sobrenome sao iguais aos passados como
	 * parametro, se possivel.
	 * 
	 * @param nome
	 *            - o primeiro nome do usuario (pode ser composto)
	 * @param sobrenome
	 *            - o sobrenome do usuario
	 * @return true, se for possivel remover o usuario, e false caso contrario.
	 */
	public static boolean removeUsuario(String nome, String sobrenome) {
		List<ContaUsuario> users = getUsuarios();
		Iterator<ContaUsuario> it = users.iterator();
		while (it.hasNext()) {
			ContaUsuario user = it.next();
			if ((user.getNome().equalsIgnoreCase(nome))
					&& (user.getSobrenome().equalsIgnoreCase(sobrenome))) {
				users.remove(user);
				try {
					Serializar.salvar(USUARIOSBD, users);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Atualiza um usuario cadastrado no banco de dados
	 * 
	 * @param usuario
	 * @return true, se for possivel atualizar o usuario, e false caso
	 *         contrario.
	 */
	public static boolean atualizaUsuario(ContaUsuario usuario) {
		List<ContaUsuario> users = getUsuarios();
		Iterator<ContaUsuario> it = users.iterator();
		while (it.hasNext()) {
			ContaUsuario user = it.next();
			if (user.equals(usuario)) {
				int indice = users.indexOf(user);
				// u.setId(user.getId());
				users.remove(user);
				users.add(indice, usuario);
				try {
					Serializar.salvar(USUARIOSBD, users);
					return true;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * Reseta o banco de dados.
	 */
	public static void reset() {
		USUARIOSBD.delete();
	}


	// a nivel de teste =P
	 public static void main(String[] args) throws Exception {
	 ContaUsuario usuario1 = new ContaUsuario("Joao", "Targino",
	 "123456", "joaotargino@lsd.ufcg.edu.br");
	 ContaUsuario usuario2 = new ContaUsuario("Telles", "Nobrega", "123456",
	 "telles@lsd.ufcg.edu.br");
	 ContaUsuario usuario3 = new ContaUsuario("Rafael", "Carvalho",
	 "123456", "rafael@lsd.ufcg.edu.br");
	 BD dao = new BD();
	 dao.cadastraUsuario(usuario1);
	 dao.cadastraUsuario(usuario2);
	 dao.cadastraUsuario(usuario3);
	
	 usuario1.setNome("Joao Paulo");
	
	 dao.atualizaUsuario(usuario1);
	
	 // dao.reset();
	 // dao.cadastraUsuario(usuario3);
	 ContaUsuario userRecuperado = dao
	 .getUsuario("joaotargino@lsd.ufcg.edu.br");

	 dao.removeUsuario("telles@lsd.ufcg.edu.br");
	 System.out.println(dao.getUsuarios());
	 }

}
