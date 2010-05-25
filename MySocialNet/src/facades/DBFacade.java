package facades;

import interfaces.ProfileIF;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import beans.Group;
import beans.UserAccount;
import controller.DBController;

public class DBFacade {
	private DBController dbController;
	private static DBFacade instancia;
	
	protected DBFacade() {
		this.dbController = new DBController();
	}
	
	public synchronized static DBFacade getInstance() {
		if(instancia == null) { 
			instancia = new DBFacade();
		}
		return instancia;
	}
	
	/**
	 * Adiciona um amigo ao DB
	 * @param contaUsuario
	 * @throws Exception
	 */
	public void addToDB(UserAccount contaUsuario) throws Exception {
		this.dbController.addToDB(contaUsuario);
	}

	/**
	 * Remove amigo do DB
	 * @param login
	 * @throws Exception
	 */
	public void removeFromDB(String login) throws Exception {
		this.dbController.removeFromDB(login);
	}	

	/**
	 * Retorna um usuário do bd
	 * @param login o login do usuário
	 * @return o usuário
	 * @throws Exception
	 */
	public UserAccount getUsers(String login) throws Exception {
		return this.dbController.getUsers(login);
	}

	/**
	 * @return todos os usuários 
	 */
	public List<UserAccount> getAllUsers() {
		return this.dbController.getAllUsers();
	}

	/**
	 * Procura um usuário no DB
	 * @param login o login do usuário
	 * @param friend o login do amigo
	 * @return o amigo
	 * @throws Exception 
	 */
	public UserAccount findNewFriend(String login, String friend) throws Exception {
		return this.dbController.findNewFriend(login, friend);
	}

	/**
	 * Atualiza o DB
	 * @param user
	 */
	public void update(UserAccount user) {
		this.dbController.update(user);
	}

	/**
	 * Atualiza o DB
	 * @param user
	 */
	public void update() {
		this.dbController.update();
	}


	/**
	 * Retorna usuário
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public UserAccount getUser(String login) throws Exception {
		return this.dbController.getUser(login);
	}

	/**
	 * Procura um usuário pelo nome completo dele
	 * @param completeName
	 * @return o usário, se encontrar
	 * @throws Exception
	 */
	public UserAccount getUserFromCompleteName(String completeName) throws Exception {
		return this.dbController.getUserFromCompleteName(completeName);
	}


}
