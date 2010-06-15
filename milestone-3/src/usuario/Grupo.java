package usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class Grupo {

	private ArrayList<Usuario> usuariosDoGrupo;
	private String nomeDoGrupo;
	private String [] usuarios;
	
	/**
	 * Construtor da classe Grupo criada para gerenciar os grupos
	 * @param nome
	 */
	public Grupo(String nome) {
		this.nomeDoGrupo = nome;
		this.usuariosDoGrupo = new ArrayList<Usuario>();
	}
	
	/**
	 * Adiciona usuario a um grupo
	 * @throws Exception 
	 */
	public void addContato(Usuario amigo){
		usuariosDoGrupo.add(amigo);
	}
	
	/**
	 * Remove contato do grupo
	 * @param amigo
	 * @return true se tiver sido removido false do contrario
	 */
	public void removeContato(Usuario amigo){
		usuariosDoGrupo.remove(amigo);
	}
	
	
	/**
	 * Retorna o nome do grupo
	 * @return nome o nome do grupo
	 */
	public String getNome(){
		return nomeDoGrupo;
	}
	
	/**
	 * Retorna um usuario presente no grupo
	 * @param email
	 * @return userInterface caso ele esteja no grupo null caso contrario
	 * @throws Exception
	 */
	public Usuario getUsuario(String email) throws Exception{
		Iterator<Usuario> users = usuariosDoGrupo.iterator();
		while(users.hasNext()){
			Usuario usuario = users.next();
			if(usuario.getEmail() == email){
				return usuario;
			}
		} return null;
	}
	
	
	/**
	 * Lista os contatos do grupo
	 * @return
	 * @throws Exception
	 */
	public String listContatos() throws Exception{
		String contatos = "";
		if(usuariosDoGrupo.size() == 0){
			return "";
		}

		usuarios = new String [usuariosDoGrupo.size()];
		for(int index = 0; index < usuarios.length ; index ++){
			usuarios[index] = usuariosDoGrupo.get(index).getNomeCompleto();//.toLowerCase();
		}
		
		Arrays.sort(usuarios);

		if(usuarios.length > 0){
			for(int j = 0; j < usuarios.length ; j++){
				contatos += usuarios[j] + ",";
			}

			return contatos.substring(0, contatos.length()-1);
		}
		return "";
	}
	
	/**
	 * Retorna uma lista dos contatos
	 * @return
	 */
	public ArrayList<Usuario> getContatos(){
		return this.usuariosDoGrupo;
	}
	

}
