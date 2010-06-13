package userInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

import sistema.Sistema;
import usuario.Usuario;


public class FacadeUser {

	private Sistema s;
	
	public FacadeUser() throws FileNotFoundException, IOException{
		s = new Sistema();
	}
	
	public Usuario getUsuario(String login) throws Exception{
		return s.getObjUsuario(login);
	}
	
	public void createUser(String nome,String sobrenome,String email,String senha) throws Exception{
		s.createUser(nome,sobrenome,email,senha);
	}
	
	public void deleteUser(String login) throws Exception {
		s.deleteUser(login);
	}
	
	public void login(String email, String senha) throws Exception {
		s.login(email, senha);
	}
	
	public void logoff(String email) throws Exception {
		s.logoff(email);
	}
	
	public void updateUserProfile(String login, String aboutMe, String age,
			String photo, String country, String city, String gender,
			String contactEmail) throws Exception{
		s.updateUserProfile(login, aboutMe, age,photo, country, city, gender,contactEmail);
	}
	
	public void setFieldPrivacy(String email, String field, String type) throws Exception {
		s.setFieldPrivacy(email, field, type);
	}
	
	public String viewProfile(String viewer, String profileOwner) throws Exception {
		return s.viewProfile(viewer, profileOwner);
	}
	
	public void addUserPreference(String login, String preference) throws Exception {
		s.addUserPreference(login, preference);
	}
	
	public void removeUserPreference(String login,String preference) throws Exception{
		s.removeUserPreference(login, preference);
	}
	
	public String listFriends(String login) throws Exception{
		return s.listFriends(login);
	}
	
	public String findNewFriend(String login, String email) throws Exception{
		return s.findNewFriend(login, email);
	}
	
	public void sendFrienshipRequest(String login, String email,String message,String grupo) throws Exception{
		s.sendFriendshipRequest(login, email, message, grupo);
	}
	
	public String viewPendingFriendship(String login) throws Exception{
		return s.viewPendingFriendship(login);
	}
	
	public String viewSentFriendship(String login) throws Exception{
		return s.viewSentFriendship(login);
	}
	
	public void declineFriendshipRequest(String login,String contact) throws Exception{
		s.declineFriendshipRequest(login, contact);
	}
	
	public void acceptFriendshipRequest(String login, String contact, String grupo) throws Exception{
		s.acceptFriendshipRequest(login, contact, grupo);
	}
	
	public String findGroupMember(String login, String friend, String group) throws Exception {
		return s.findGroupMember(login, friend, group);
	}
	
	public void removeFriend(String email, String friend) throws Exception{
		s.removeFriend(email, friend);
	}
	
	public String listGroupMembers(String email, String grupo) throws Exception {
		return s.listGroupMembers(email, grupo);
	}
	
	public void addGroupMember(String email, String grupo, String user) throws Exception {
		s.addGroupMember(email, grupo, user);
	}
	
	public void removeGroupMember(String email, String group, String user) throws Exception {
		s.removeGroupMember(email, group, user);
	}
	
	public String getRecommendFriends(String login) throws Exception{
		return s.getRecommendFriends(login);
	}
	
}