package controller;

import interfaces.ProfileIF;
import Util.ProfileConstants;
import beans.UserAccount;

public class ProfileController {
	
	private DBController DBController;
	
	public ProfileController() {
		this.DBController = new DBController();
	}
	
	public ProfileIF getProfile(UserAccount user, String visibility) {
		if (visibility.equals(ProfileConstants.ALL)) {
			return user.getProfileAll();
		}
		else if (visibility.equals(ProfileConstants.FRIENDS)) {
			return user.getProfileFriends();
		}

		return user.getProfileJustMe();
	}


	/**
	 * Seta a privacidade de um campo
	 * @param owner
	 * @param visibility
	 * @param field
	 * @throws Exception
	 */
	public void setPrivacy(String owner, String visibility, String field) throws Exception {
		UserAccount usuario = this.DBController.getUsers(owner);
		ProfileIF profileAll = usuario.getProfileAll();
		ProfileIF profileJustMe = usuario.getProfileJustMe();
		ProfileIF profileFriends = usuario.getProfileFriends();
		if (visibility.equals(ProfileConstants.ALL)) {
			setPrivacityByType(usuario, field, profileAll, profileJustMe);
			setPrivacityByType(usuario, field, profileFriends, profileJustMe);
		}
		else if (visibility.equals(ProfileConstants.FRIENDS)) {
			removePrivacy(usuario, field, profileAll, null);
			setPrivacityByType(usuario, field, profileFriends, profileJustMe);
		}
		else {
			removePrivacy(usuario, field, profileAll, null);
			removePrivacy(usuario, field, profileFriends, null);
		}
	}
	
	/**
	 * Retira a privacidade
	 * @param usuario
	 * @param field
	 * @param profile
	 * @param valor
	 */
	private void removePrivacy(UserAccount usuario, String field, ProfileIF profile, String valor) {
		
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
		
		if (profile.getType().equals(ProfileConstants.ALL)) {
			usuario.setProfileAll(profile);
			this.DBController.update(usuario);
			this.DBController.update();
		}
		else {
			usuario.setProfileFriends(profile);
			this.DBController.update(usuario);
			this.DBController.update();
		}
		
	}

	/**
	 * Seta privacidade pelo tipo
	 * @param usuario
	 * @param field
	 * @param profile
	 * @param justMe
	 */
	private void setPrivacityByType(UserAccount usuario, String field, ProfileIF profile, ProfileIF justMe) {
		
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
		
		if (profile.getType().equals(ProfileConstants.ALL)) {
			usuario.setProfileAll(profile);
			this.DBController.update(usuario);
			this.DBController.update();
		}
		else {
			usuario.setProfileFriends(profile);
			this.DBController.update(usuario);
			this.DBController.update();
		}
	}

	/**
	 * Atualiza o perfil do usuário
	 * @param usuario
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 */
	public void updateUserProfile(UserAccount usuario, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		ProfileIF all = usuario.getProfileAll();
		ProfileIF justMe = usuario.getProfileJustMe();
		ProfileIF friends = usuario.getProfileFriends();
		
		all = updateUserProfileType(all,aboutMe, age, photo, country, city, gender, contactEmail);
		friends = updateUserProfileType(friends,aboutMe, age, photo, country, city, gender, contactEmail);
		justMe = updateUserProfileType(justMe,aboutMe, age, photo, country, city, gender, contactEmail);
		
		usuario.setProfileAll(all);
		usuario.setProfileFriends(friends);
		usuario.setProfileJustMe(justMe);
		
		this.DBController.update(usuario);
	}
	
	/**
	 * Atualiza o profile pelo tipo
	 * @param profile
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 * @return
	 */
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
