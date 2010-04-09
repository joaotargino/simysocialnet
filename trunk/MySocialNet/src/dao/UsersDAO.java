package dao;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import beans.UserAccount;

/**
 * Classe responsavel pela manipula��o com o banco de dados
 * 
 */
public class UsersDAO {

	
	private static UsersDAO instance;
	// arquivo com o BD dos usuarios
	private static final File USUARIOSBD = new File("./src/dao/usuariosBD.xml");

	
	public static synchronized UsersDAO getInstance() {
		if(instance == null) instance = new UsersDAO();
		return instance;
	}
	
	/**
	 * Retorna uma lista de usuarios cadastrados no banco de dados
	 * 
	 * @return List<ContaUsuario> lista de usuarios cadastrados no BD
	 */
	public List<UserAccount> getUsuarios() {
		try {
			List<UserAccount> users = (List) recuperar(USUARIOSBD);
			return users;
		} catch (Exception e1) {
			return new ArrayList<UserAccount>();
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
	public UserAccount getUsuario(String email) {
		List<UserAccount> users = getUsuarios();
		Iterator<UserAccount> it = users.iterator();
		while (it.hasNext()) {
			UserAccount user = it.next();
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
	public UserAccount getUsuario(String nome, String sobrenome) {
		List<UserAccount> users = getUsuarios();
		Iterator<UserAccount> it = users.iterator();
		while (it.hasNext()) {
			UserAccount user = it.next();
			if (user.getName().equalsIgnoreCase(nome))
				if (user.getSurname().equalsIgnoreCase(sobrenome))
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
	public boolean create(UserAccount usuario)throws IOException {
		if (usuario == null)
			return false;
		List<UserAccount> users = getUsuarios();
		Iterator<UserAccount> it = users.iterator();
		while (it.hasNext()) {
			UserAccount user = it.next();
			if (user.equals(usuario)) {
				return false;
			}
		}
		users.add(usuario);
		salvar(USUARIOSBD, users);
		return true;
	}

	/**
	 * Remove o usu�rio, cujo email eh igual ao passado como parametro, se
	 * possivel.
	 * 
	 * @param email
	 * @return true, se for possivel remover o usuario, e false caso contrario.
	 */
	public boolean delete(String email) {
		List<UserAccount> users = getUsuarios();
		Iterator<UserAccount> it = users.iterator();
		while (it.hasNext()) {
			UserAccount user = it.next();
			if (user.getEmail().equals(email)) {
				users.remove(user);
				try {
					salvar(USUARIOSBD, users);
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
	 * Remove o usu�rio, cujo nome e sobrenome sao iguais aos passados como
	 * parametro, se possivel.
	 * 
	 * @param nome
	 *            - o primeiro nome do usuario (pode ser composto)
	 * @param sobrenome
	 *            - o sobrenome do usuario
	 * @return true, se for possivel remover o usuario, e false caso contrario.
	 */
	public boolean delete(String nome, String sobrenome) {
		List<UserAccount> users = getUsuarios();
		Iterator<UserAccount> it = users.iterator();
		while (it.hasNext()) {
			UserAccount user = it.next();
			if ((user.getName().equalsIgnoreCase(nome))
					&& (user.getSurname().equalsIgnoreCase(sobrenome))) {
				users.remove(user);
				try {
					salvar(USUARIOSBD, users);
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
	public boolean update(UserAccount usuario) {
		List<UserAccount> users = getUsuarios();
		Iterator<UserAccount> it = users.iterator();
		while (it.hasNext()) {
			UserAccount user = it.next();
			if (user.equals(usuario)) {
				int indice = users.indexOf(user);
				// u.setId(user.getId());
				users.remove(user);
				users.add(indice, usuario);
				try {
					salvar(USUARIOSBD, users);
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
	public void reset() {
		USUARIOSBD.delete();
	}

	
	// veio do serializar.java (salvarObjeto e recuperarObjeto)
	/**
	 * Esse m�todo recebe um objeto arquivo e o objeto que ser� serializado. O
	 * m�todo ir� serializar o objeto passado no arquivo (referenciado pelo
	 * objeto file).
	 */
	public void salvar(File file, Object objeto)
			throws FileNotFoundException, IOException {

		if (objeto == null) {
			throw new NullPointerException("Objeto passado � nulo");
		}
		XMLEncoder e = null;

		try {
			e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(
					file)));
			e.writeObject(objeto);

		} catch (FileNotFoundException ex) {
			throw new FileNotFoundException("O arquivo n�o pode ser encontrado");
		} catch (IOException ex) {
			throw new IOException("Erro de entrada e sa�da");
		} finally {
			if (e != null) {
				e.close();
			}

			if (e != null) {
				e.close();
			}
		}

	}

	/**
	 * Para ler o objeto serializado, n�s somente precisamos no arquivo onde
	 * esse objeto est� salvado. Sabendo o arquivo, n�s conseguimos recuperar o
	 * objeto tranquilamente.
	 */
	public Object recuperar(File file) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		Object obj = null;

		if (file == null) {
			throw new NullPointerException("Objeto FILE � Nulo.");
		}
		XMLDecoder d = null;

		try {

			d = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(file)));
			obj = d.readObject();

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Arquivo n�o encontrado");
		} catch (IOException e) {
			throw new IOException("Erro de entrada e sa�da");
		} finally {
			if (d != null) {
				d.close();
			}

		}

		return obj;
	}

}
