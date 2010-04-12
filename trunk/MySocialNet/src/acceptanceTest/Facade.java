//package main;
//
//import java.util.List;
//
//import beans.UserAccount;
package acceptanceTest;

import java.util.List;

import main.SocialNet;


import beans.UserAccount;

/**
 * @author tellesmvn, rafael aquino
 *
 */
public class Facade {
	
	private SocialNet socialNet;
	
	/**
	 * Loga o usuario ao sistema
	 * @throws Exception 
	 */
	public void login(String login, String senha) throws Exception{
		socialNet = SocialNet.getInstance();
		socialNet.login(login, senha);
	}
	
	/**
	 * Desloga o usuario do sistema
	 * @throws Exception 
	 */
	public void logoff(String email) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.logoff(email);
		
	}
	
	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 * @throws Exception 
	 */
	public boolean estaLogado(String login) throws Exception {
		socialNet = SocialNet.getInstance();
		return socialNet.isLoged(login);
	}
	
	/**
	 * Cria um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Nome do usu�rio deve ser informado"
	 * "Sobrenome do usu�rio deve ser informado"
	 * "E-mail do usu�rio deve ser informado"
	 * "Senha deve ser informada"
	 * "E-mail inv�lido"
	 * "A senha deve ter pelo menos 6 d�gitos"
	 * "Login indispon�vel"
	 * 
	 * @param name - o nome do usuario
	 * @param lastName - o sobrenome do usuario
	 * @param email - o email do usuario
	 * @param passwd - a senha do usuario
	 * @throws Exception 
	 */
	public void createUser(String name, String lastName, String email, String passwd) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.createUser(name, lastName, email, passwd);
	}
	
	/**
	 * Recupera um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usu�rio n�o logado"
	 * 
	 * @param login - o email do usuario
	 * @return String contendo nome e sobrenome do usuario
	 * @throws Exception 
	 */
	public UserAccount getUser (String login) throws Exception {
		socialNet = SocialNet.getInstance();
		return socialNet.getUser(login);
	}
	
	/**
	 * Atualiza as informa��es do usuario,pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usu�rio n�o logado"
	 * 
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
	public void updateUserProfile(String login, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.updateUserProfile(login, aboutMe, age, photo, country, city, gender, contactEmail);
	}
	
	/**
	 * Altera a privacidade de algum campo
	 * 
	 * @param login
	 * @param field
	 * @param type
	 * @throws Exception 
	 */
	public void setFieldPrivacy(String login, String field, String type) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.setFieldPrivacy(login, field, type);
	}
	
	/**
	 * Diz o que est� disponivel no perfil dependendo da visibilidade, pode ocorrer os seguintes erros:
	 * 
	 * "Perfil inexistente"
	 * 
	 * @param login
	 * @param visibility
	 * @return Sting no formato campo(1)=valor(1),...,campo(n)=valor(n)]
	 * 			exemplo: "photo=photo.png,aboutMe=,gender=male"
	 * @throws Exception 
	 */
	public String checkProfile( String login, String visibility) throws Exception {
		socialNet = SocialNet.getInstance();
		return socialNet.checkProfile(login, visibility).toString();
	}
	
	
	public String viewProfile( String viewer, String profileOwner) throws Exception {
		socialNet = SocialNet.getInstance();
		return socialNet.viewProfile(viewer, profileOwner).toString();
	}
	
	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void addUserPreference(String login, String preference) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.addUserPreference(login, preference);
	}
	
	/**
	 * Lista as preferencias do usuario
	 * 
	 * @param login
	 * @return String com as preferencias separada so por virgula (nao sei se isso eh uma re-
	 * presentacao de lista, mas como la nos testes tava como se fosse string...
	 * @throws Exception 
	 */
	public String listUserPreferences(String login) throws Exception {
		socialNet = SocialNet.getInstance();
		List<String> listPreferences = socialNet.listUserPreferences(login);
		String preferences = listPreferences.get(0);
		if (preferences == null) return "";
		for (int i = 1; i < listPreferences.size(); i++) {
			preferences += "," + listPreferences.get(i);
		}
		return preferences;
	}
	
	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void removeUserPreference (String login, String preference) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.removeUserPreference(login, preference);
	}
	
	/**
	 * Deleta a conta do usuario
	 * @param login
	 * @throws Exception 
	 */
	public void deleteUser(String login) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.deleteUser(login);
	}
	
	/**
	 * Lista os membros de um grupo
	 * 
	 * @param email
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	public String listGroupMembers(String email, String group) throws Exception {
		socialNet = SocialNet.getInstance();
		return socialNet.listGroupMembers(email, group).toString();
	}
	
	/**
	 * Procura um mebro de um grupo
	 * 
	 * @param login
	 * @param friend
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	public String findGroupMember(String login,String friend, String group) throws Exception {
		socialNet = SocialNet.getInstance();
		if (socialNet.findGroupMember(login, friend, group) == null) return null;
		return socialNet.findGroupMember(login, friend, group).toString();
	}
	
	/**
	 * @param email
	 * @param group
	 * @param user
	 * @throws Exception 
	 */
	public void addGroupMember(String email, String group, String user) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.addGroupMember(email, group, user);
	}
	
	/**
	 * Remove um membro do grupo
	 * 
	 * @param email
	 * @param group
	 * @param user
	 * @throws Exception 
	 */
	public void removeGroupMember(String email, String group, String user) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.removeGroupMember(email, group, user);
	}
	
	/**
	 * Listar os amigos
	 * 
	 * @param email
	 * @return
	 * @throws Exception 
	 */
	public String listFriends(String email) throws Exception {
		socialNet = SocialNet.getInstance();
		String output = "";
		List<UserAccount> string = socialNet.listFriends(email);
		for (UserAccount string2 : string) {
			output += string2.toStringFullName() + ",";
		}
		if(output.length() > 0) {
			output = output.substring(0, output.length()-1);
		}
		return output;
	}
	
	/**
	 * procura um novo amigo
	 * 
	 * @param login
	 * @param friend
	 * @return
	 * @throws Exception 
	 */
	public String findNewFriend(String login, String friend) throws Exception {
		socialNet = SocialNet.getInstance();
		UserAccount usuario = socialNet.findNewFriend(login, friend);
		if(usuario != null) {
			return usuario.getName() + " " + usuario.getSurname() + " - Profile: photo=" +
			usuario.getProfileJustMe().getPhoto() + ",aboutMe="+ usuario.getProfileJustMe().getAboutMe() + 
			",country=" + usuario.getProfileJustMe().getCountry();
		}
		
		
		return "";
	}
	
	/**
	 * Envia um convite de amizade para outro usuario
	 * 
	 * @param login
	 * @param user
	 * @param message
	 * @param group
	 */
	public void sendFriendshipRequest(String login, String user, String message, String group) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.sendFriendshipRequest(login, user, message, group);
	}
	
	/**
	 * Ver os convites de amizade. que est�o pendentes, para o usuario
	 * 
	 * @param login
	 * @return
	 * @throws Exception 
	 */
	public String viewPendingFriendship(String login) throws Exception {
		socialNet = SocialNet.getInstance();
		String resposta = "";
		List<String> string = socialNet.viewPendingFriendship(login);
		for (String string2 : string) {
			resposta += string2;
		}
		return resposta;
	}
	
	/**
	 * Ver os convites de amizade enviados 
	 * pelo usuario que est�o pendentes
	 * 
	 * @param login
	 * @return
	 * @throws Exception 
	 */
	public String viewSentFriendship (String login) throws Exception {
		socialNet = SocialNet.getInstance();
		String resposta = "";
		List<String> string = socialNet.viewSentFriendship(login);
		for (int i=0;i<string.size();i++) {
			resposta += string.get(i);
			if (i != string.size() - 1) resposta += ",";
		}
		return resposta;
	}
	
	/**
	 * Rejeita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 */
	public void declineFriendshipRequest (String login, String contact)throws Exception{
		socialNet = SocialNet.getInstance();
		socialNet.declineFriendshipRequest(login, contact);
	}
	
	/**
	 * Aceita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 * @param group
	 */
	public void acceptFriendshipRequest (String login, String contact, String group) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.acceptFriendshipRequest(login, contact, group);
	}
	
	/**
	 * Pega um amigo da lista de amigos
	 * 
	 * @param email
	 * @param friend
	 * @return
	 * @throws Exception 
	 */
	public String getFriend(String email, String friend) throws Exception {
		socialNet = SocialNet.getInstance();
		if(socialNet.getFriend(email, friend) == null) return null;
		return socialNet.getFriend(email, friend) + " " + socialNet.getFriend(email, friend).getProfileFriends();
	}
	
	/**
	 * Remove um amigo da lista de amigos
	 * 
	 * @param login
	 * @param friend
	 * @throws Exception 
	 */
	public void removeFriend(String login, String friend) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.removeFriend(login, friend);
	}
	
	/**
	 * Recomenda amigos a partir da lista de amigos 
	 * por grau de similaridade ou se ele estiver nos 
	 * grupos melhores amigos ou familia
	 * 
	 * @param login
	 * @return
	 */
	public String getRecommendFriends(String login) throws Exception {
		socialNet = SocialNet.getInstance();
		String output = "";
		List<UserAccount> recommendedFriends = socialNet.getRecommendFriends(login);
		for (UserAccount user : recommendedFriends) {
			output += user.toStringFullName() + ",";
		}
		if(output.length() > 0) {
			output = output.substring(0, output.length()-1);
		}
		return output;
	}
	
	/**
	 * Gera um arquivo a partir da lista de amigos com os campos passados por parametro 
	 * 
	 * @param login
	 * @param fileName
	 * @param exportFields
	 */
	public void exportFriendList(String login, String fileName, String exportFields) throws Exception {
		socialNet = SocialNet.getInstance();
		socialNet.exportFriendList(login, fileName, exportFields);
	}
	
	/**
	 * Recupera uma lista de amigos a partir de um arquivo
	 * 
	 * @param login
	 * @param file
	 * @throws Exception 
	 */
	public String restoreFriendList(String login, String file) throws Exception {
		socialNet = SocialNet.getInstance();
		return socialNet.restoreFriendList(login, file);
	}
	
	/**
	 * Limpa o banco de dados
	 */
	public void clean() {
		socialNet = SocialNet.getInstance();
		socialNet.clean();
	}
}
