package sistema;

import usuario.Usuario;

public abstract class Convite {

	protected String string;
	protected Usuario usuario;
	
	/**
	 * Construtor de convite
	 * @param usuario
	 * @param string
	 */
	public Convite(Usuario usuario, String string){
		this.usuario = usuario;
		this.string = string;
	}
	
	/**
	 * Retorna o convite
	 * @return
	 * @throws Exception
	 */
	public abstract String getConvite() throws Exception;
	
	/**
	 * Retorna o usuario
	 * @return
	 */
	public Usuario getUsuario() {
		return this.usuario;
	}
	
}