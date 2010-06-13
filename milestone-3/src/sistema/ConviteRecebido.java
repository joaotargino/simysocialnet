package sistema;

import usuario.Usuario;

public class ConviteRecebido extends Convite{

	/**
	 * Construtor de Convite Recebido
	 * @param usuario
	 * @param string
	 */
	public ConviteRecebido(Usuario usuario, String string) {
		super(usuario, string);
	}

	@Override
	public String getConvite() throws Exception {
		return usuario.getNome() + " " + usuario.getSobrenome() + " <" + usuario.getEmail() + "> - mensagem: " + string;
	}

}