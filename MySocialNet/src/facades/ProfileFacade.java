package facades;

import controller.ProfileController;
import interfaces.ProfileIF;
import Util.ProfileConstants;
import beans.UserAccount;

public class ProfileFacade {
	
	private static ProfileFacade instancia;
	private ProfileController profileController;
	protected ProfileFacade() {
		this.profileController = new ProfileController();
	}
	
	public static synchronized ProfileFacade getInstance() {
		if(instancia == null) { 
			instancia = new ProfileFacade();
		}
		return instancia;
	}
	
	public ProfileIF getProfile(UserAccount user, String visibility) {
		return this.profileController.getProfile(user, visibility);
	}


	/**
	 * Seta a privacidade de um campo
	 * @param owner
	 * @param visibility
	 * @param field
	 * @throws Exception
	 */
	public void setPrivacy(String owner, String visibility, String field) throws Exception {
		this.profileController.setPrivacy(owner, visibility, field);
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
		this.profileController.updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
	}
}
