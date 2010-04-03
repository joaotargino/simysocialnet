package interfaces;


public interface ProfileIF {
	
	public String getTipo();
	
	public String getPhoto();

	public void setPhoto(String photo);

	public String getCountry();

	public void setCountry(String country);

	public String getCity();

	public void setCity(String city);

	public String getGender();

	public void setGender(String gender);

	public String getContactEmail();

	public void setContactEmail(String contactEmail);

	public String getAge();

	public void setAge(String age);

	public void setAboutMe(String aboutMe);
	
	public String getAboutMe();
	
	@Override
	public String toString();
}
