package controller;

import beans.ContaUsuario;
import interfaces.ProfileIF;
import Util.ProfileConstants;
import dao.usersDAO;

public class GerenciadorProfile {
	
	private static GerenciadorProfile instance;
	
	public synchronized static GerenciadorProfile getInstance() {
		if (instance == null) {
			instance = new GerenciadorProfile();
		}
		return instance;
	}


	public ProfileIF getProfile(String owner, String visibility) {
		if (visibility.equals(ProfileConstants.ALL)) {
			return usersDAO.getUsuario(owner).getProfileAll();
		}
		else if (visibility.equals(ProfileConstants.FRIENDS)) {
			return usersDAO.getUsuario(owner).getProfileFriends();
		}

		return usersDAO.getUsuario(owner).getProfileJustMe();
	}


	public void setPrivacity(String owner, String visibility, String field) {
		ContaUsuario usuario = usersDAO.getUsuario(owner);
		ProfileIF profileAll = usuario.getProfileAll();
		ProfileIF profileJustMe = usuario.getProfileJustMe();
		ProfileIF profileFriends = usuario.getProfileFriends();
		if (visibility.equals(ProfileConstants.ALL)) {
			setPrivacityPorTipo(owner, field, profileAll, profileJustMe);
		}
		else if (visibility.equals(ProfileConstants.FRIENDS)) {
			retiraPrivacidade(owner, field, profileAll, null);
			setPrivacityPorTipo(owner, field, profileFriends, profileJustMe);
		}
		else {
			retiraPrivacidade(owner, field, profileAll, null);
			retiraPrivacidade(owner, field, profileFriends, null);
		}
		usersDAO.atualizaUsuario(usuario);
	}
	
	private void retiraPrivacidade(String owner, String field, ProfileIF profile, String valor) {
		
		if(field.equals(ProfileConstants.AGE)) {
			profile.setAge(valor);
		}
		else if(field.equals(ProfileConstants.ABOUT_ME)) {
			profile.setAboutMe(valor);
		}
		else if(field.equals(ProfileConstants.CITY)) {
			profile.setCity(valor);
		}
		else if(field.equals(ProfileConstants.COUNTRY)) {
			profile.setCountry(valor);
		}
		else if(field.equals(ProfileConstants.GENDER)) {
			profile.setGender(valor);
		}
		else if(field.equals(ProfileConstants.PHOTO)) {
			profile.setPhoto(valor);
		}
		else {
			profile.setContactEmail(valor);
		}
	}

	private void setPrivacityPorTipo(String owner, String field, ProfileIF profile, ProfileIF justMe) {
		
		if(field.equals(ProfileConstants.AGE)) {
			profile.setAge(justMe.getAge());
		}
		else if(field.equals(ProfileConstants.ABOUT_ME)) {
			profile.setAboutMe(justMe.getAboutMe());
		}
		else if(field.equals(ProfileConstants.CITY)) {
			profile.setCity(justMe.getCity());
		}
		else if(field.equals(ProfileConstants.COUNTRY)) {
			profile.setCountry(justMe.getCountry());
		}
		else if(field.equals(ProfileConstants.GENDER)) {
			profile.setGender(justMe.getGender());
		}
		else if(field.equals(ProfileConstants.PHOTO)) {
			profile.setPhoto(justMe.getPhoto());
		}
		else {
			profile.setContactEmail(justMe.getContactEmail());
		}
	}

	public void updateUserProfile(ContaUsuario usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		updateUserProfilePorTipo(usuario.getProfileAll(),aboutMe, age, photo, country, city, gender, contactEmail);
		updateUserProfilePorTipo(usuario.getProfileFriends(),aboutMe, age, photo, country, city, gender, contactEmail);
		updateUserProfilePorTipo(usuario.getProfileJustMe(),aboutMe, age, photo, country, city, gender, contactEmail);
		usersDAO.atualizaUsuario(usuario);
	}
	
	private void updateUserProfilePorTipo(ProfileIF profile, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		profile.setAboutMe(aboutMe);
		profile.setAge(age);
		profile.setCity(city);
		profile.setCountry(country);
		profile.setGender(gender);
		profile.setContactEmail(contactEmail);
		profile.setPhoto(photo);
	}
}
