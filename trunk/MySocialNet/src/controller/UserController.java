package controller;

import interfaces.ProfileIF;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import beans.UserAccount;
import dao.UsersDAO;

public class UserController {
	private DBController dbController;
	private List<UserAccount> usuariosLogados;


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

	public void addUserPreferences(String login,String preferencia) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		if (!user.isLogged()) {
			throw new Exception("Usuário não logado");
		}
		user.addUserPreferences(preferencia);
	}

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

	public void acceptFriendshipRequest(String login, String contact,String group) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		UserAccount contato = this.dbController.getUsers(contact);
		if(!verifyIfUserIsLogged(user)) throw new Exception("Usuário não logado");
		user.acceptFriendshipRequest(contact, group, contato);
		contato.removeSentFriendshipRequest(login,user);
	}

	public void clean() {
		usuariosLogados = new ArrayList<UserAccount>();
		UsersDAO.getInstance().reset();
	}

	public List<UserAccount> listFriends(String email) throws Exception {
		UserAccount usuario = this.dbController.getUsers(email);
		if(!verifyIfUserIsLogged(usuario)) throw new Exception("Usuário não logado");
		return usuario.getFriends();
	}

	public List<String> viewPendingFriendship(String login) throws Exception{
		UserAccount usuario = this.dbController.getUsers(login);

		if(!verifyIfUserIsLogged(usuario)) throw new Exception("Usuário não logado");
		return usuario.viewPendingFriendship();
	}

	public List<String> viewSentFriendship(String login)throws Exception {
		UserAccount usuario = this.dbController.getUsers(login);

		if(!verifyIfUserIsLogged(usuario)) throw new Exception("Usuário não logado");
		return usuario.viewSentFriendship();
	}

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

	private boolean verifyIfUserIsLogged(UserAccount user) {
		if(!(user.isLogged())) return false;
		return true;

	}

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

	public void updateUserProfile(String login, String aboutMe, String age,
			String photo, String country, String city, String gender,
			String contactEmail) throws Exception {

		UserAccount usuario = this.dbController.getUsers(login);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		usuario.updateUserProfile(usuario, aboutMe, age, photo, country, city,
				gender, contactEmail);

	}

	public void setFieldPrivacy(String login, String field, String type) throws Exception {
		UserAccount usuario = this.dbController.getUsers(login);
		if (!usuario.isLogged())
			throw new Exception("Usuário não logado");
		usuario.setFieldPrivacy(login, type, field);

	}

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

	public void removeUserPreference(String login, String preference) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		if (!user.isLogged())
			throw new Exception("Usuário não logado");
		user.getPreferences().remove(preference);
		this.dbController.update(user);
		
	}

	public void deleteUser(String login) throws Exception {
		UserAccount user = this.dbController.getUsers(login);
		if (!user.isLogged())
			throw new Exception("Usuário não logado");
		this.dbController.removeFromDB(login);
		
	}
	
public void exportFriendList(UserAccount usuario, String login, String fileName, String exportFields) throws Exception {
		
		String dadosExportados = "";
		//this.criaPastas(fileName);
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
		String sep = System.getenv("line.separator");
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
	}
	
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

}
