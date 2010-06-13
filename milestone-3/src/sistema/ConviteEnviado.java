package sistema;

import usuario.Usuario;

public class ConviteEnviado extends Convite{

	private String grupo;
	
	/**
	 * Construtor de Convite Enviado
	 * @param usuario
	 * @param string
	 */
	public ConviteEnviado(Usuario usuario, String string) {
		super(usuario, string);
		this.grupo = string;
	}
	
	/**
	 * Retorna grupo
	 * @return
	 */
	public String getGrupo(){
		return this.grupo;
	}

	@Override
	public String getConvite() throws Exception {
		return usuario.getEmail();
	}

	
	
}