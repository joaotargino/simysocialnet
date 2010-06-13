package testes;

import java.io.FileNotFoundException;
import java.io.IOException;

import sistema.Sistema;
import easyaccept.script.test.TestFacade;

public class Fachada extends TestFacade {

	private Sistema s;
	
	public Fachada() throws FileNotFoundException, IOException{
		s = new Sistema();
	}

    public void clean() throws Exception  {
        s.clean();
    }
   
    public void createUser(String nome, String sobrenome, String email,    String senha) throws Exception {
        s.createUser(nome, sobrenome, email, senha);
    }
   
    public String getUser(String login) throws Exception {
        return s.getUser(login);
    }
    
    public void updateUserProfile(String login, String aboutMe, String age, String photo,
            String country, String city, String gender, String contactEmail) throws Exception {
    		s.updateUserProfile(login, aboutMe, age, photo, country, city, gender, contactEmail);
    }
   
    public void setFieldPrivacy(String email, String field, String type) throws Exception {
        s.setFieldPrivacy(email, field, type);
    }
    
    public String checkProfile(String login, String visibility) throws Exception {       
        return s.checkProfile(login, visibility);
    }
    
    public String viewProfile(String viewer, String profileOwner) throws Exception {       
        return s.viewProfile(viewer, profileOwner);
    }
    
    public void login(String email, String senha) throws Exception {
        s.login(email, senha);
    }
   
    public void logoff(String email) throws Exception {
        s.logoff(email);
    }
 
    public void deleteUser(String login) throws Exception {
        s.deleteUser(login);
    }
    
    public String listGroupMembers(String email, String group) throws Exception {
        return s.listGroupMembers(email, group);
    }
    
    public String findGroupMember(String email, String friend, String group) throws Exception {
       return s.findGroupMember(email, friend, group);
    }
    
    public void addGroupMember(String email, String group, String user) throws Exception {
        s.addGroupMember(email, group, user);
    }

    public void removeGroupMember(String email, String group, String user) throws Exception {
        s.removeGroupMember(email, group, user);
    }
    
    public void addUserPreference(String login, String preference) throws Exception {
    	s.addUserPreference(login, preference);
    }
    
    public String listUserPreferences(String login) throws Exception {
    	return s.listUserPreference(login);
    }
    
    public String restoreFriendList (String login, String file) throws Exception {
    	return s.restoreFriendList(login, file);
	}
    
    public String listFriends(String login) throws Exception{
    	return s.listFriends(login);
    }
    
    public String findNewFriend(String login,String email) throws Exception{
    	return s.findNewFriend(login,email);
    }
    
    public void sendFriendshipRequest(String login, String email,String message, String grupo) throws Exception{
    	s.sendFriendshipRequest(login,email,message,grupo);
    }
    
    public String viewPendingFriendship(String login) throws Exception{
    	return s.viewPendingFriendship(login);
    }
    
    public String viewSentFriendship(String login) throws Exception{
    	return s.viewSentFriendship(login);
    }
    
    public void declineFriendshipRequest(String login,String contact) throws Exception {
    	s.declineFriendshipRequest(login,contact);
    }
    
    public void acceptFriendshipRequest(String login, String contact, String grupo) throws Exception{
    	s.acceptFriendshipRequest(login, contact, grupo);
    }
    
    public String getFriend(String email,String friend) throws Exception{
    	return s.getFriend(email, friend);
    }
    
    public void removeFriend(String email, String friend) throws Exception{
    	s.removeFriend(email, friend);
    }
    
    public String getRecommendFriends(String login) throws Exception{
    	return s.getRecommendFriends(login);
    }
    
    public void removeUserPreference(String login,String preference) throws Exception{
    	s.removeUserPreference(login, preference);
    }
    
    public void exportFriendList(String login, String fileName, String exportFields) throws Exception {
    	s.exportFriendList(login, fileName, exportFields);
	}

}