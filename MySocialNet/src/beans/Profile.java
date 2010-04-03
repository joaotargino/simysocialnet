package beans;

import java.util.Set;

public class Profile<Preferenciavel> {
	
	private String aboutMe; 
	private String photo; 
	private String country; 
	private String city;
	private String gender;
	private String contactEmail;
	private String age;
	private Preferenciavel tipoDePreferencia;
	private Set<String> all;
	private Set<String> justMe;
	private Set<String> friends;

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
	
		
	public void setTipoDePreferencia(Preferenciavel tipoDePreferencia) {
		this.tipoDePreferencia = tipoDePreferencia;
	}
	
	public void setFieldPrivacy(String field, String visibility) {
		if(visibility.toLowerCase().equals("all")) {
			all.add(field);
			organizaSet(friends, field);
			organizaSet(justMe, field);
		}else if (visibility.toLowerCase().equals("friends")) {
			friends.add(field);
			organizaSet(all, field);
			organizaSet(justMe, field);
		}else if (visibility.toLowerCase().equals("just_me")) {
			justMe.add(field);
			organizaSet(friends, field);
			organizaSet(all, field);
		}else{
			
		}
	}
		
	private void organizaSet(Set<String> conjunto,String field) {
		for (String string : conjunto) {
			if(string.toLowerCase().equals(field)) {
				conjunto.remove(string);
			}
		}
	}
	public Preferenciavel getTipoDePreferencia() {
		return tipoDePreferencia;
	}

	public String checkProfile(String visibility) {
		if(visibility.toLowerCase().equals("all")) {
			for (String string : all) {
			}
		}else if(visibility.toLowerCase().equals("friends")) {
			
		}else if(visibility.toLowerCase().equals("justme")) {
			
		}else {
			
		}
		return null;
	}
	
}