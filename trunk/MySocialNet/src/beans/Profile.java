package beans;


import interfaces.ProfileIF;

public class Profile {
	
	private static Profile profile = null;
	private String aboutMe; 
	private String photo; 
	private String country; 
	private String city;
	private String gender;
	private String contactEmail;
	private String age;
	
	public synchronized static Profile getInstance() {
		if(profile == null) {
			profile = new Profile();
		}
		return profile;
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
	
	public void setFieldPrivacy(String field, String visibility) {
		if(visibility.toLowerCase().equals("all")) {
			setField(ProfileAll.getInstance(), field);
			setFieldNull(ProfileFriends.getInstance(), field);
			setFieldNull(ProfileJustMe.getInstance(), field);
		}else if (visibility.toLowerCase().equals("friends")) {
			setField(ProfileFriends.getInstance(), field);
			setFieldNull(ProfileAll.getInstance(), field);
			setFieldNull(ProfileJustMe.getInstance(), field);
		}else if (visibility.toLowerCase().equals("just_me")) {
			setField(ProfileJustMe.getInstance(), field);
			setFieldNull(ProfileFriends.getInstance(), field);
			setFieldNull(ProfileAll.getInstance(), field);
		}else{
			
		}
	}
	private void setFieldNull(ProfileIF profile, String field ) {
		if(field.equals("contactEmail")) {
			profile.setContactEmail(null);
		}else if(field.equals("photo")) {
			profile.setPhoto(null);
		}else if(field.equals("age")) {
			profile.setAge(null);
		}else if(field.equals("aboutMe")) {
			profile.setAboutMe(null);
		}else if(field.equals("city")) {
			profile.setCity(null);
		}else if(field.equals("country")) {
			profile.setCountry(null);
		}else if(field.equals("gender")) {
			profile.setGender(null);
		}
	}
		
	
	
	private void setField(ProfileIF profile, String field ) {
		if(field.equals("contactEmail")) {
			profile.setContactEmail(this.contactEmail);
		}else if(field.equals("photo")) {
			profile.setPhoto(this.photo);
		}else if(field.equals("age")) {
			profile.setAge(this.age);
		}else if(field.equals("aboutMe")) {
			profile.setAboutMe(this.aboutMe);
		}else if(field.equals("city")) {
			profile.setCity(this.city);
		}else if(field.equals("country")) {
			profile.setCountry(this.country);
		}else if(field.equals("gender")) {
			profile.setGender(this.gender);
		}
	}

	public String checkProfile(String visibility) {
		String resposta = "";
		if(visibility.toLowerCase().equals("all")) {
			resposta += ProfileAll.getInstance().toString();
		}else if(visibility.toLowerCase().equals("friends")) {
			resposta += ProfileFriends.getInstance().toString();
		}else if(visibility.toLowerCase().equals("justme")) {
			resposta += ProfileJustMe.getInstance().toString();
		}else {
			
		}
//		resposta = resposta.substring(0, resposta.length()-);
		return resposta;
	}
	
}