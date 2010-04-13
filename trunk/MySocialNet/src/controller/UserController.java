//package controller;
//
//import interfaces.ProfileIF;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//
//import beans.Group;
//import beans.UserAccount;
//import dao.UsersDAO;
package controller;

import interfaces.ProfileIF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import beans.Group;
import beans.UserAccount;
import dao.UsersDAO;

public class UserController {
	private DBController dbController;
	private List<UserAccount> usuariosLogados;


	/**
	 * Construtor do obejto
	 */
	public UserController() {
		usuariosLogados = new ArrayList<UserAccount>();
		dbController = new DBController();
	}

	public List<UserAccount> getUsuariosLogados() {
		return usuariosLogados;
	}

	public boolean isLoged(String login) throws Exception {
		UserAccount user = new UserAccount();
		user.setEmail(login);
		return getUsuariosLogados().contains(user);
	}

	/**
	 * Faz o login no sistema
	 * @param login
	 * @param senha
	 * @throws Exception
	 */
	public void login(String login, String senha) throws Exception {
		UserAccount usuario;
		try {
			usuario = this.dbController.getUsers(login);
			if (usuario == null)
				throw new Exception("Login inválido ou senha incorreta");
			else if (!senha.equals(usuario.getPassword()))
				throw new Exception("Login inválido ou senha incorreta");
		} catch (Exception e) {
			throw new Exception("Login inválido ou senha incorreta");
		}
		if (isLoged(login)) {
			throw new Exception("Usuário já logado");
		}
		usuario.setLogged(true);
		usuariosLogados.add(usuario);
		this.dbController.update(usuario);
	}

	/**
	 * Desloga do sistema
	 * @param login
	 * @throws Exception
	 */
	public void logoff(String login) throws Exception {
		UserAccount user;
		try {
			user = dbController.getUsers(login);
		} catch (Exception e) {
			throw new Exception("Login inválido");
		}
		if (!isLoged(login))
			throw new Exception("Usuário não logado");
		user.setLogged(false);
		usuariosLogados.remove(user);
		dbController.update(user);
	}

	/**
	 * Adiciona as preferências do usuário
	 * @param login
	 * @param preferencia
	 * @throws Exception
	 */
	public void addUserPreferences(String login,String preferencia) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		if (!user.isLogged()) {
			throw new Exception("Usuário não logado");
		}
		user.addUserPreferences(preferencia);
	}

	/**
	 * Envia um convite para um amigo
	 * @param login
	 * @param user
	 * @param message
	 * @param group
	 * @throws Exception
	 */
	public void sendFriendshipRequest(String login, String user, String message, String group) throws Exception {
		UserAccount convidado; 
		try {
			convidado = this.dbController.getUsers(user);
		}catch(Exception e) {
			throw new Exception("Contato inexistente");
		}
		if(login.equals(user)) throw new Exception("Operação não permitida");
		UserAccount logado = this.dbController.getUsers(login);
		if(!verifyIfUserIsLogged(logado)) throw new Exception("Usuário não logado");
		logado.sendFriendshipRequest(user, group);
		convidado.receiveFriendshipRequest(logado, login, message);
	}

	/**
	 * Aceita convite do usuário
	 * @param login
	 * @param contact
	 * @param group
	 * @throws Exception
	 */
	public void acceptFriendshipRequest(String login, String contact,String group) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		UserAccount contato = this.dbController.getUsers(contact);
		if(!verifyIfUserIsLogged(user)) throw new Exception("Usuário não logado");
		user.acceptFriendshipRequest(contact, group, contato);
		contato.removeSentFriendshipRequest(login,user);
	}

	/**
	 * Limpa o DB
	 */
	public void clean() {
		usuariosLogados = new ArrayList<UserAccount>();
		UsersDAO.getInstance().reset();
	}

	/**
	 * Lista os amigos
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public List<UserAccount> listFriends(String email) throws Exception {
		UserAccount usuario = this.dbController.getUsers(email);
		if(!verifyIfUserIsLogged(usuario)) throw new Exception("Usuário não logado");
		return usuario.getFriends();
	}

	/**
	 * Ver lista de convites recebidos
	 * @param login
	 * @return a lista de convites recebidos
	 * @throws Exception
	 */
	public List<String> viewPendingFriendship(String login) throws Exception{
		UserAccount usuario = this.dbController.getUsers(login);

		if(!verifyIfUserIsLogged(usuario)) throw new Exception("Usuário não logado");
		return usuario.viewPendingFriendship();
	}

	/**
	 * Ver lista de convites enviados
	 * @param login
	 * @return a lista de convites enviados
	 * @throws Exception
	 */
	public List<String> viewSentFriendship(String login)throws Exception {
		UserAccount usuario = this.dbController.getUsers(login);

		if(!verifyIfUserIsLogged(usuario)) throw new Exception("Usuário não logado");
		return usuario.viewSentFriendship();
	}

	/**
	 * Rejeita um convite enviado
	 * @param login
	 * @param contact
	 * @throws Exception
	 */
	public void declineFriendshipRequest(String login, String contact) throws Exception{
		UserAccount usuario;
		UserAccount contato;
		try{
			usuario = this.dbController.getUsers(login);
			contato = this.dbController.getUsers(contact);
		}catch (Exception e) {
			throw new Exception("Login inexistente");
		}
		if(!verifyIfUserIsLogged(usuario)) throw new Exception("Usuário não logado");
		usuario.declineFriendshipRequest(contact, usuario.getPendingFriendship());
		contato.declineFriendshipRequest(login, contato.getSentFriendship());
	}

	public UserAccount getFriend(String email, String friend) throws Exception {
		UserAccount user = this.dbController.getUsers(email);
		UserAccount amigo;
		if (!verifyIfUserIsLogged(user)) {
			amigo = this.dbController.getUsers(friend);
			throw new Exception("Usuário não logado");
		}
		try {
			amigo = this.dbController.getUsers(friend);
		} catch (Exception e) {
			return null;
		}
		return user.getFriend(amigo, friend);
	}

	public List<UserAccount> getRecommendedFriends(String login) throws Exception{
		UserAccount user;
		try {
			user = this.dbController.getUsers(login);
		}catch (Exception e) {
			throw new Exception("Usuário inexistente");
		}
		return user.getRecommendedFriends();
	}

	/**
	 * Verifica se usuário está logado
	 * @param user
	 * @return true se tiver logado, false caso contrário
	 */
	private boolean verifyIfUserIsLogged(UserAccount user) {
		if(!(user.isLogged())) return false;
		return true;

	}

	/**
	 * Cria um novo usuário
	 * @param name
	 * @param lastName
	 * @param email
	 * @param passwd
	 * @throws Exception
	 */
	public void createUser(String name, String lastName, String email,
			String passwd) throws Exception {

		UserAccount contaUsuario;
		try {
			contaUsuario = new UserAccount(name, lastName, passwd, email);
		} catch (Exception e) {
			if (e.getMessage().equals("String invalida"))
				throw new Exception("Login indisponível");
			else
				throw new Exception(e.getMessage());
		}
		this.dbController.addToDB(contaUsuario);

	}

	/**
	 * Atualiza profile do usuário
	 * @param login
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 * @throws Exception
	 */
	public void updateUserProfile(String login, String aboutMe, String age,
			String photo, String country, String city, String gender,
			String contactEmail) throws Exception {

		UserAccount usuario = this.dbController.getUsers(login);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		usuario.updateUserProfile(usuario, aboutMe, age, photo, country, city,
				gender, contactEmail);

	}

	/**
	 * Seta a privacidade de um campo
	 * @param login
	 * @param field
	 * @param type
	 * @throws Exception
	 */
	public void setFieldPrivacy(String login, String field, String type) throws Exception {
		UserAccount usuario = this.dbController.getUsers(login);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		usuario.setFieldPrivacy(login, type, field);

	}

	/**
	 * Checa um profile
	 * @param login
	 * @param visibility a visibilidade que se deseja checar
	 * @return o profile
	 * @throws Exception
	 */
	public ProfileIF checkProfile(String login, String visibility) throws Exception {

		UserAccount user;
		try {
			user = this.dbController.getUsers(login);
		} catch (Exception e) {
			throw new Exception("Perfil inexistente");
		}

		if (!(isLoged(login)))
			throw new Exception("Usuário não logado");
		ProfileIF profile = user.getProfile(user, visibility);
		return profile;

	}

	/**
	 * Mostra o profile
	 * @param viewer
	 * @param profileOwner
	 * @return
	 * @throws Exception
	 */
	public ProfileIF viewProfile(String viewer, String profileOwner) throws Exception {
		UserAccount viewerUser;
		UserAccount ownerUser;
		try {
			viewerUser = this.dbController.getUsers(viewer);
		} catch (Exception e) {
			throw new Exception("Login do viewer não existente no sistema");
		}
		try {
			ownerUser = this.dbController.getUsers(profileOwner);
		} catch (Exception e) {
			throw new Exception("Perfil inexistente");
		}
		if (!viewerUser.isLogged())
			throw new Exception("Usuário não logado");
		if (ownerUser.getFriends().contains(viewerUser))
			return ownerUser.getProfileFriends();
		return ownerUser.getProfileAll();

	}

	/**
	 * Remove preferência de um usuário
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void removeUserPreference(String login, String preference) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		if (!user.isLogged())
			throw new Exception("Usuário não logado");
		user.getPreferences().remove(preference);
		this.dbController.update(user);

	}

	/**
	 * Remove um usuário
	 * @param login
	 * @throws Exception
	 */
	public void deleteUser(String login) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		if (!user.isLogged())
			throw new Exception("Usuário não logado");
		this.dbController.removeFromDB(login);

	}


	/**
	 * Exporta lista de amigos
	 * @param usuario
	 * @param login
	 * @param fileName
	 * @param exportFields
	 * @throws Exception
	 */
	public void exportFriendList(String login, String fileName, String exportFields) throws Exception {
		UserAccount usuario = this.getUser(login);
		if (!usuario.isLogged())	throw new Exception("Usuário não logado");
		String dadosExportados = "";
		//		this.criaPastas(fileName);
		File file;
		FileWriter writer;
		PrintWriter saida;
		try {
			file = new File(fileName);
			writer = new FileWriter(file);
			saida = new PrintWriter(writer,true);
		} catch(Exception erro) {
			throw new Exception("Falha na exportação do arquivo");
		}
		String sep = System.getProperty("line.separator");
		if (exportFields.trim().equals("")) dadosExportados += "name,lastName";
		else dadosExportados += "name,lastName,";

		dadosExportados += exportFields + sep;
		List<UserAccount> listaDosAmigos = usuario.getFriends();
		Collections.sort(listaDosAmigos);
		Iterator<UserAccount> IteradorDaListaDeAmigos = listaDosAmigos.iterator();
		while (IteradorDaListaDeAmigos.hasNext()) {
			UserAccount amigoIterado = IteradorDaListaDeAmigos.next();
			dadosExportados += this.organizeStringToExport(amigoIterado, exportFields);			
			if (IteradorDaListaDeAmigos.hasNext()) dadosExportados += sep;
		}
		saida.println(dadosExportados);
		writer.close();
		saida.close();
	}

	/**
	 * Organiza string para ser exportada
	 * @param usuario
	 * @param exportFields
	 * @return a string organizada
	 */
	private String organizeStringToExport(UserAccount usuario,String exportFields) {
		ProfileIF profile = usuario.getProfileAll();
		String dadosDoUsuario = "";
		dadosDoUsuario += usuario.getName() + "," + usuario.getSurname();
		if (exportFields.contains("photo"))  dadosDoUsuario += "," + profile.getPhoto();
		if (exportFields.contains("aboutMe"))  dadosDoUsuario += "," + profile.getAboutMe();
		if (exportFields.contains("age"))  dadosDoUsuario += "," + profile.getAge();
		if (exportFields.contains("gender"))  dadosDoUsuario += "," + profile.getGender();
		if (exportFields.contains("contactEmail"))  dadosDoUsuario += "," + profile.getContactEmail();
		if (exportFields.contains("country"))  dadosDoUsuario += "," + profile.getCountry();
		if (exportFields.contains("city"))  dadosDoUsuario += "," + profile.getCity();
		return dadosDoUsuario;
	}

	/**
	 * Recupera lista de amigos de um arquivo
	 * @param usuario
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String restoreFriendList(String login, String fileName) throws Exception{
		UserAccount usuario = this.getUser(login);
		if (!usuario.isLogged())	throw new Exception("Usuário não logado");
		List<String> notImportedContacts = new ArrayList<String>();
		FileReader fileReader;
		BufferedReader reader;
		try {
			fileReader  = new FileReader(fileName);
			reader = new BufferedReader(fileReader);
		} catch (Exception erro) {
			throw new Exception("Arquivo não encontrado");
		}
		testValidFile(reader);
		fileReader  = new FileReader(fileName);
		reader = new BufferedReader(fileReader);
		String friendInformation = reader.readLine();
		String linhaLida = reader.readLine();
		while (linhaLida != null) {
			String errorInRestore = restoreSingleFriend(usuario, linhaLida);
			if (!errorInRestore.equals("")) notImportedContacts.add(errorInRestore);
			linhaLida = reader.readLine();
		}
		if (notImportedContacts.size() == 0) return "Todos os contatos foram importados";
		return ("Contatos não importados: "+organizeNotImportedContacts(notImportedContacts));
		

	}
	
	/**
	 * Organiza contatos não importados
	 * @param notImportedContacts
	 * @return
	 */
	private String organizeNotImportedContacts(List<String> notImportedContacts) {
		Iterator<String> notImportedContactsIterator = notImportedContacts.iterator();
		String notImportedNames = "";
		while (notImportedContactsIterator.hasNext()) {
			String IteratedName = notImportedContactsIterator.next();
			notImportedNames += IteratedName;
			if (notImportedContactsIterator.hasNext()) notImportedNames += ",";
		}
		return notImportedNames;
	}

	/**
	 * Verifica se o arquivo é válido
	 * @param reader
	 * @throws Exception
	 */
	private void testValidFile(BufferedReader reader) throws Exception{
		String linhaLida = reader.readLine();
		if (!linhaLida.contains("name,lastName")) throw new Exception("Arquivo não suportado pelo sistema");
		int numberOfColumns = linhaLida.split(",").length;
		while (linhaLida != null) {
			if (linhaLida.split(",").length != numberOfColumns ) throw new Exception("Arquivo não suportado pelo sistema");
			linhaLida = reader.readLine();
		}
	}

	/**
	 * Recupera um único amigo
	 * @param usuario
	 * @param friendInformation
	 * @return
	 */
	public String restoreSingleFriend(UserAccount usuario,String friendInformation) {
		String[] separatedFriendInformation = friendInformation.split(",");
		String completeName = separatedFriendInformation[0] + " " + separatedFriendInformation[1];
		String errorMessage = "";
		try {
			UserAccount newFriend = dbController.getUserFromCompleteName(completeName);
			try {
				usuario.getFriend(newFriend.getEmail());
				errorMessage += "Usuários "+usuario.getName()+" "+usuario.getSurname()+" e "+completeName+" já são amigos";
				return errorMessage;
			} catch(Exception e){}
			if (newFriend.getPendingFriendship().containsKey(usuario.getEmail())) errorMessage = "Você já enviou um convite para o usuário "+completeName;
			else {
				String message = "Olá, "+newFriend.getEmail()+" me adicione como seu amigo.";
				this.sendFriendshipRequest(usuario.getEmail(), newFriend.getEmail(), message, "conhecidos");
			}
		} catch(Exception erro) {
			errorMessage += completeName;
		}
		return errorMessage;
	}

	/**
	 * Remove amigo
	 * @param login
	 * @param friend
	 * @throws Exception
	 */
	public void removeFriend(String login, String friend) throws Exception {
		UserAccount usuario = this.dbController.getUsers(login);
		UserAccount amigo;
		try {
			amigo = this.dbController.getUsers(friend);
		} catch (Exception e) {
			throw new Exception("Amigo não existente no sistema");
		}
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		amigo = getFriend(login, friend);
		if (amigo == null)
			throw new Exception("Amigo não encontrado em nenhum grupo");

		removeFriendFromGroup(usuario, amigo);
		removeFriendFromGroup(amigo, usuario);
	}

	/**
	 * Remove amigo do grupo
	 * @param user
	 * @param friend
	 */
	private void removeFriendFromGroup(UserAccount user, UserAccount friend) {
		for (Group grupo : user.getGroups().values()) {
			if (grupo.getUsers().contains(friend)) {
				grupo.getUsers().remove(friend);
				user.getGroups().put(grupo.getName(), grupo);
				this.dbController.update(user);
				user.updateFriends();
				break;
			}
		}
	}

	public UserAccount getUser(String login) throws Exception{
		return this.dbController.getUser(login);
	}

	public UserAccount getUsers(String login) throws Exception{
		return this.dbController.getUsers(login);
	}

	public UserAccount findNewFriend(String login, String friend) throws Exception{
		return this.dbController.findNewFriend(login, friend);
	}
}
