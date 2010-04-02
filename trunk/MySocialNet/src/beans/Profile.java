package beans;

public class Profile<Preferenciavel> {
	
	private String aboutMe; 
	private String foto; 
	private String pais; 
	private String cidade;
	private String sexo;
	private String emailContato;
	private Preferenciavel tipoDePreferencia;
	
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public String getAboutMe() {
		return aboutMe;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPais() {
		return pais;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSexo() {
		return sexo;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setTipoDePreferencia(Preferenciavel tipoDePreferencia) {
		this.tipoDePreferencia = tipoDePreferencia;
	}

	public Preferenciavel getTipoDePreferencia() {
		return tipoDePreferencia;
	}
	
}