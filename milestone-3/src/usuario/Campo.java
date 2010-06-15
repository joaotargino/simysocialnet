package usuario;

public class Campo {

	private String nomeDoCampo;
	private String conteudo;
	private Visibilidade visibilidade;
	
	/**
	 * Construtor da classe campo, criada para tratar os campos do perfil do usuario
	 * @param nomeDoCampo
	 * @param campo
	 * @param visibilidade
	 */
	public Campo(String nomeDoCampo, String campo, Visibilidade visibilidade){
		this.nomeDoCampo = nomeDoCampo;
		this.conteudo = campo;
		this.visibilidade = visibilidade;
	}
	
	public String getNomeCampo(){
		return nomeDoCampo;
	}
	
	public String getCampo(){
		return conteudo;
	}
	
	public Visibilidade getVisibilidade(){
		return visibilidade;
	}
	
	public void setCampo(String campo){
		this.conteudo = campo;
	}
	
	public void setVisibilidade(Visibilidade visibilidade){
		this.visibilidade = visibilidade;
	}
	
	public String toString(){
		return this.conteudo;
	}
	
}
