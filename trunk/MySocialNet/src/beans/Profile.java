package beans;

import java.util.Map;

public class Profile {
	
	private String aboutMe; 
	private String photo; 
	private String country; 
	private String city;
	private String gender;
	private String contactEmail;
	private String age;
	private Map<String,String> all;
	private Map<String,String> justMe;
	private Map<String,String> friends;

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
			verificaField(all, field);
			organizaMapa(friends, field);
			organizaMapa(justMe, field);
		}else if (visibility.toLowerCase().equals("friends")) {
			verificaField(friends, field);
			organizaMapa(all, field);
			organizaMapa(justMe, field);
		}else if (visibility.toLowerCase().equals("just_me")) {
			verificaField(justMe, field);
			organizaMapa(friends, field);
			organizaMapa(all, field);
		}else{
			
		}
	}
		
	private void organizaMapa(Map<String,String> conjunto,String field) {
		if(conjunto.containsKey(field)) {
			conjunto.remove(field);
		}
	}
	
	private void verificaField(Map<String, String> mapa, String field ) {
		if(field.equals("contactEmail")) {
			mapa.put(field, getContactEmail());
		}else if(field.equals("photo")) {
			mapa.put(field, getPhoto());
		}else if(field.equals("age")) {
			mapa.put(field, getAge());
		}else if(field.equals("aboutMe")) {
			mapa.put(field, getAboutMe());
		}else if(field.equals("city")) {
			mapa.put(field, getCity());
		}else if(field.equals("country")) {
			mapa.put(field, getCountry());
		}else if(field.equals("gender")) {
			mapa.put(field, getGender());
		}
	}

	public String checkProfile(String visibility) {
		String resposta = "";
		if(visibility.toLowerCase().equals("all")) {
			for (String string : all.keySet()) {
				resposta+= string + "=" + all.get(string)+",";
			}
		}else if(visibility.toLowerCase().equals("friends")) {
			for (String string : friends.keySet()) {
				resposta+= string + "=" + friends.get(string)+",";
			}
		}else if(visibility.toLowerCase().equals("justme")) {
			for (String string : justMe.keySet()) {
				resposta+= string + "=" + justMe.get(string)+",";
			}
		}else {
			
		}
		resposta = resposta.substring(0, resposta.length()-2);
		return resposta;
	}
	
}