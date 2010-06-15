package usuario;

import java.util.ArrayList;

public class Perfil {
	private String stringDePreferences;
	private Campo aboutMe, gender, contactEmail, country, city, preferences, photo, age;
	private ArrayList<String> listPreferences;
	private ArrayList<Campo> camposDoPerfil;

	public Perfil(String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail){
		this.aboutMe = new Campo("aboutMe", aboutMe, Visibilidade.JUST_ME);
		this.age = new Campo("age", age, Visibilidade.JUST_ME);
		this.photo = new Campo("photo", photo, Visibilidade.JUST_ME);
		this.country = new Campo("country", country, Visibilidade.JUST_ME);
		this.city = new Campo("city", city, Visibilidade.JUST_ME);
		this.gender = new Campo("gender", gender, Visibilidade.JUST_ME);
		this.contactEmail = new Campo("contactEmail", contactEmail, Visibilidade.JUST_ME);
		this.preferences = new Campo("preferences", "", Visibilidade.JUST_ME);
		listPreferences = new ArrayList<String>();
		stringDePreferences = "";
		atualizaListaDeCamposDoPerfil();
	}
	
	public Campo getAboutMe(){
		return aboutMe;
	}
	
	public Campo getGender(){
		return gender;
	}
	
	public Campo getContactEmail(){
		return contactEmail;
	}
	
	public Campo getCountry(){
		return country;
	}
	
	public Campo getCity(){
		return city;
	}
	
	public ArrayList<String> getListPreferences(){
		return this.listPreferences;
	}
	
	public String getPreferences() {
		if (preferences.getCampo() == null) {
			return "";
		}
		String preferencias = "";
		for (int i = 0; i < listPreferences.size(); i++) {
			if(i < listPreferences.size() -1){
				preferencias += listPreferences.get(i) + ",";
			} else {
				preferencias += listPreferences.get(i);
			}
		}
		return preferencias;
	}
	
	public Campo getPhoto(){
		return photo;
	}
	
	public Campo getAge(){
		return age;
	}
	
	public void setAboutMe(Campo aboutMe){
		if(aboutMe.getCampo() == null){
			aboutMe.setCampo(" ");
		}
		this.aboutMe = aboutMe;
	}
	
	public void setGender(Campo gender){
		if(gender.getCampo() != "male" &&
				gender.getCampo() != "female" &&
				gender.getCampo() == null){
			gender.setCampo(" ");
		}
		this.gender = gender;
	}
	
	public void setContactEmail(Campo contactEmail){
		this.contactEmail = contactEmail;
	}
	
	public void setCountry(Campo country){
		if(country.getCampo() == null){
			country.setCampo(" ");
		}
		this.country = country;
	}
	
	public void setCity(Campo city){
		if(city.getCampo() == null){
			city.setCampo(" ");
		}
		this.city = city;
	}
	
	public void setPreferences(String preference){
		preference = preference.toLowerCase().trim();
		if(!listPreferences.contains(preference)){
			listPreferences.add(preference);
			stringDePreferences += "," + preference ;
		}
		preferences.setCampo(preference);
	}
	
	public void setPhoto(Campo photo){
		this.photo = photo;
	}
	
	public void setAge(Campo age){
		this.age = age;
	}
	

	public void atualizaListaDeCamposDoPerfil() {
		this.camposDoPerfil = new ArrayList<Campo>();
		camposDoPerfil.add(contactEmail);
		camposDoPerfil.add(age);
		camposDoPerfil.add(photo);
		camposDoPerfil.add(aboutMe);
		camposDoPerfil.add(gender);
		camposDoPerfil.add(city);
		camposDoPerfil.add(country);
	}

	public void removePreference(String preference) throws Exception{
		if(listPreferences.contains(preference)){
			listPreferences.remove(preference);
		} else {
			throw new Exception("Preferencia inexistente");
		}
	}
	
	public String getVisibilidade(String visibilidade) {
		atualizaListaDeCamposDoPerfil();
		String visivel = "";
		if (visibilidade.equals("JUST_ME")) {
			for (Campo c : camposDoPerfil) {
				visivel += c.getNomeCampo()+"="+c.getCampo()+",";
			}
		} else if (visibilidade.equals("ALL")) {
			for (Campo c : camposDoPerfil) {
				if (c.getVisibilidade().toString().equals("ALL")) {
					visivel += c.getNomeCampo()+"="+c.getCampo()+",";
				}
			}
		} else if (visibilidade.equals("FRIENDS")) {
			for (Campo c : camposDoPerfil) {
				if (!c.getVisibilidade().toString().equals("JUST_ME")) {
					visivel += c.getNomeCampo()+"="+c.getCampo()+",";
				}
			}
		}
		if (visivel.equals("")) return visivel;
		return visivel.substring(0, visivel.length()-1);
	}
	
	
	public ArrayList<Campo> getCamposDoPerfil() {
		atualizaListaDeCamposDoPerfil();
		return camposDoPerfil;
	}

	public String viewAll(){
		return "photo=" + getPhoto() + ",aboutMe=" + getAboutMe()
		+ ",city="+ getCity() + ",country=" + getCountry();
	}
	
}
