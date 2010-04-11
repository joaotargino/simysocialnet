package beans;

import interfaces.ProfileIF;
import Util.ProfileConstants;

/**
 * @author Rafael Aquino
 *
 */
public class ProfileJustMe implements ProfileIF{
	
	private String aboutMe; 
	private String photo; 
	private String country; 
	private String city;
	private String gender;
	private String contactEmail;
	private String age;
	private static ProfileJustMe profile;
	
	public synchronized static ProfileJustMe getInstance() { 
		if(profile == null) {
			profile = new ProfileJustMe();
		}
		return profile;
	}
	
	public void init() {
		aboutMe = "";
		photo = "";
		country = "";
		city = "";
		gender = "";
		contactEmail = "";
		age = "";
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public String getAboutMe() {
		return aboutMe;
	}
	
	@Override
	public String getType() {
		return ProfileConstants.JUST_ME;
	}
	
	private String imprime() {
		String string = "";
		if (contactEmail != null) string += "contactEmail=" + contactEmail + ",";
		if (age != null ) string += "age=" + age + ",";
		if (photo != null) string += "photo=" + photo + ",";
		if (aboutMe != null) string += "aboutMe=" + aboutMe + ",";
		if (gender != null) string += "gender=" + gender + ",";
		if (city != null) string += "city=" + city + ",";
		if (country != null) string += "country=" + country + ",";
		return string;

	}
	
	@Override
	public String toString() {
		String string = imprime();
		if (string.length() > 0)
			string = string.substring(0, string.length()-1);
		return string;
	}

}
