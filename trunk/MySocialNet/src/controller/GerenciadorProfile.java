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
			setPrivacityPorTipo(usuario, field, profileAll, profileJustMe);
			setPrivacityPorTipo(usuario, field, profileFriends, profileJustMe);
		}
		else if (visibility.equals(ProfileConstants.FRIENDS)) {
			retiraPrivacidade(usuario, field, profileAll, null);
			setPrivacityPorTipo(usuario, field, profileFriends, profileJustMe);
		}
		else {
			retiraPrivacidade(usuario, field, profileAll, null);
			retiraPrivacidade(usuario, field, profileFriends, null);
		}
	}
	
	private void retiraPrivacidade(ContaUsuario usuario, String field, ProfileIF profile, String valor) {
		
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
		
		if (profile.getTipo().equals(ProfileConstants.ALL)) {
			usuario.setProfileAll(profile);
			usersDAO.update(usuario);
		}
		else {
			usuario.setProfileFriends(profile);
			usersDAO.update(usuario);
		}
		
	}

	private void setPrivacityPorTipo(ContaUsuario usuario, String field, ProfileIF profile, ProfileIF justMe) {
		
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
		
		if (profile.getTipo().equals(ProfileConstants.ALL)) {
			usuario.setProfileAll(profile);
			usersDAO.update(usuario);
		}
		else {
			usuario.setProfileFriends(profile);
			usersDAO.update(usuario);
		}
	}

	public void updateUserProfile(ContaUsuario usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		ProfileIF all = usuario.getProfileAll();
		ProfileIF justMe = usuario.getProfileJustMe();
		ProfileIF friends = usuario.getProfileFriends();
		
		all = updateUserProfileType(all,aboutMe, age, photo, country, city, gender, contactEmail);
		friends = updateUserProfileType(friends,aboutMe, age, photo, country, city, gender, contactEmail);
		justMe = updateUserProfileType(justMe,aboutMe, age, photo, country, city, gender, contactEmail);
		
		usuario.setProfileAll(all);
		usuario.setProfileFriends(friends);
		usuario.setProfileJustMe(justMe);
		
		usersDAO.update(usuario);
	}
	
	private ProfileIF updateUserProfileType(ProfileIF profile, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		profile.setAboutMe(aboutMe);
		profile.setAge(age);
		profile.setCity(city);
		profile.setCountry(country);
		profile.setGender(gender);
		profile.setContactEmail(contactEmail);
		profile.setPhoto(photo);
		return profile;
	}
}