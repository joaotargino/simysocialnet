package main;

import interfaces.ProfileIF;

import java.util.List;

import beans.ContaUsuario;
import beans.Grupo;
import controller.GerenciadorGrupo;
import controller.GerenciadorProfile;
import controller.GerenciadorUsuario;
import dao.usersDAO;

public class SocialNet {

	private static SocialNet social;
	private ContaUsuario usuario;
	private GerenciadorGrupo gerenciadorGrupo;

	public synchronized static SocialNet getInstance() {
		if(social == null) {
			social = new SocialNet();
		}
		return social;
	}

	/**
	 * Loga o usuario ao sistema
	 * @throws Exception 
	 * @throws Exception 
	 */
	public void login(String login, String senha) throws Exception {
		try {
			usuario = GerenciadorUsuario.getInstance().getUsuario(login);
			if ( usuario == null) throw new Exception("Login inválido ou senha incorreta");
			else if (!senha.equals(usuario.getSenha())) throw new Exception("Login inválido ou senha incorreta");
		} catch (Exception e) {
			throw new Exception("Login inválido ou senha incorreta");
		}
		if (usuario.isLoged()) throw new Exception("Usuário já logado");
		usuario.setLoged(true);
		usersDAO.update(usuario);
	}

	/**
	 * Desloga o usuario do sistema
	 * @throws Exception 
	 */
	public void logoff(String email) throws Exception {
		ContaUsuario user;
		try {
			user = GerenciadorUsuario.getInstance().getUsuario(email);
		} catch (Exception e) {
			throw new Exception("Login inválido");
		}
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		user.setLoged(false);
		usersDAO.update(user);
		usuario = null;
	}

	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 */
	public boolean estaLogado() {
		return (usuario != null);
	}

	/**
	 * Cria um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Nome do usu�rio deve ser informado"
	 * "Sobrenome do usu�rio deve ser informado"
	 * "E-mail do usu�rio deve ser informado"
	 * "Senha deve ser informada"
	 * "E-mail inv�lido"
	 * "A senha deve ter pelo menos 6 d�gitos"
	 * "Login indispon�vel"
	 * 
	 * @param name - o nome do usuario
	 * @param lastName - o sobrenome do usuario
	 * @param email - o email do usuario
	 * @param passwd - a senha do usuario
	 * @throws Exception 
	 */
	public void createUser(String name, String lastName, String email, String passwd) throws Exception {
		ContaUsuario contaUsuario;
		try {
			contaUsuario = new ContaUsuario(name, lastName, passwd, email);
		}
		catch (Exception e) {
			if (e.getMessage().equals("String invalida")) throw new Exception("Login indisponível");
			else throw new Exception(e.getMessage());
		}
		GerenciadorUsuario.getInstance().adicionar(contaUsuario);
	}

	/**
	 * Recupera um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usu�rio n�o logado"
	 * 
	 * @param login - o email do usuario
	 * @return String contendo nome e sobrenome do usuario
	 * @throws Exception 
	 */
	public ContaUsuario getUser (String login) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado"); 
		return usuario;
	}

	/**
	 * Atualiza as informa��es do usuario,pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usu�rio n�o logado"
	 * 
	 * @param login
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 * @throws Exception 
	 */
	public void updateUserProfile(String login, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		GerenciadorProfile.getInstance().updateUserProfile(usuario, aboutMe, age, photo, country, city,gender, contactEmail);
	}

	/**
	 * Altera a privacidade de algum campo
	 * 
	 * @param login
	 * @param field
	 * @param type
	 * @throws Exception 
	 */
	public void setFieldPrivacy(String login, String field, String type) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		GerenciadorProfile.getInstance().setPrivacity(login, type, field);
	}

	/**
	 * Diz o que est� disponivel no perfil dependendo da visibilidade, pode ocorrer os seguintes erros:
	 * 
	 * "Perfil inexistente"
	 * 
	 * @param login
	 * @param visibility
	 * @return Sting no formato campo(1)=valor(1),...,campo(n)=valor(n)]
	 * 			exemplo: "photo=photo.png,aboutMe=,gender=male"
	 * @throws Exception 
	 */
	public ProfileIF checkProfile(String login, String visibility) throws Exception {

		ContaUsuario user;
		try {
			user = GerenciadorUsuario.getInstance().getUsuario(login);
		} catch (Exception e) {
			throw new Exception("Perfil inexistente");
		}

		if (!(estaLogado())) throw new Exception("Usuário não logado");
		ProfileIF profile = GerenciadorProfile.getInstance().getProfile(user, visibility);
		return profile;
	}


	public String viewProfile(String viewer, String profileOwner) throws Exception {
		ContaUsuario viewerUser;
		ContaUsuario ownerUser;
		try {
			viewerUser = GerenciadorUsuario.getInstance().getUsuario(viewer);
		} catch (Exception e) {
			throw new Exception("Login do viewer não existente no sistema");
		}
		try {
			ownerUser = GerenciadorUsuario.getInstance().getUsuario(profileOwner);
		} catch (Exception e) {
			throw new Exception("Perfil inexistente");
		}
		if(!viewerUser.isLoged()) throw new Exception("Usuário não logado");
		return null;
	}


	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void addUserPreference(String login, String preference) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(login);
		if(!user.isLoged()) {
			throw new Exception("Usuário não logado");
		}
		GerenciadorUsuario.getInstance().addUserPreferences(user, preference);
	}

	/**
	 * Lista as preferencias do usuario
	 * 
	 * @param login
	 * @return String com as preferencias separada so por virgula (nao sei se isso eh uma re-
	 * presentacao de lista, mas como la nos testes tava como se fosse string...
	 * @throws Exception 
	 */
	public List<String> listUserPreferences(String login) throws Exception {
		return GerenciadorUsuario.getInstance().getUsuario(login).getPreferencias();
	}

	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void removeUserPreference (String login, String preference) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		user.getPreferencias().remove(preference);
		usersDAO.update(user);
	}

	/**
	 * Deleta a conta do usuario
	 * @param login
	 * @throws Exception 
	 */
	public void deleteUser(String login) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		GerenciadorUsuario.getInstance().remover(login);
	}

	/**
	 * Lista os membros de um grupo
	 * 
	 * @param email
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	public Grupo listGroupMembers(String email, String group) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(email);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		return GerenciadorGrupo.getInstance().getGrupo(usuario, group);
	}

	/**
	 * Procura um mebro de um grupo
	 * 
	 * @param login
	 * @param friend
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	public ContaUsuario findGroupMember(String login,String friend, String group) throws Exception {
		ContaUsuario usuario = GerenciadorUsuario.getInstance().getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		return GerenciadorGrupo.getInstance().getMembro(usuario, friend, group);
	}

	/**
	 * @param email
	 * @param group
	 * @param user
	 */
	public void addGroupMember(String email, String group, String user) {
		gerenciadorGrupo.adicionar(user, group);
	}

	/**
	 * Remove um membro do grupo
	 * 
	 * @param email
	 * @param group
	 * @param user
	 */
	public void removeGroupMember(String email, String group, String user) {
		gerenciadorGrupo.remover(user, group);
	}

	/**
	 * Listar os amigos
	 * 
	 * @param email
	 * @return
	 * @throws Exception 
	 */
	public List<ContaUsuario> listFriends(String email) throws Exception {
		ContaUsuario user = GerenciadorUsuario.getInstance().getUsuario(email);
		return user.getAmigos();
	}

	/**
	 * procura um novo amigo
	 * 
	 * @param login
	 * @param friend
	 * @return
	 * @throws Exception 
	 */
	public ContaUsuario findNewFriend(String login, String friend) throws Exception {
		return GerenciadorUsuario.getInstance().getUsuario(friend);
	}

	/**
	 * Envia um convite de amizade para outro usuario
	 * 
	 * @param login
	 * @param user
	 * @param message
	 * @param group
	 */
	public void sendFriendshipRequest(String login, String user, String message, String group) {

	}

	/**
	 * Ver os convites de amizade. que est�o pendentes, para o usuario
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> viewPendingFriendship(String login) {
		return null;
	}

	/**
	 * Ver os convites de amizade enviados 
	 * pelo usuario que est�o pendentes
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> viewSentFriendship (String login) {
		return null;
	}

	/**
	 * Rejeita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 */
	public void declineFriendshipRequest (String login, String contact){

	}

	/**
	 * Aceita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 * @param group
	 */
	public void acceptFriendshipRequest (String login, String contact, String group){

	}

	/**
	 * Pega um amigo da lista de amigos
	 * 
	 * @param email
	 * @param friend
	 * @return
	 */
	public ContaUsuario getFriend(String email, String friend) {
		return null;
	}

	/**
	 * Remove um amigo da lista de amigos
	 * 
	 * @param login
	 * @param friend
	 */
	public void removeFriend(String login, String friend) {

	}

	/**
	 * Recomenda amigos a partir da lista de amigos 
	 * por grau de similaridade ou se ele estiver nos 
	 * grupos melhores amigos ou familia
	 * 
	 * @param login
	 * @return
	 */
	public List<ContaUsuario> getRecommendFriends(String login) {
		return null;
	}

	/**
	 * Gera um arquivo a partir da lista de amigos com os campos passados por parametro 
	 * 
	 * @param login
	 * @param fileName
	 * @param exportFields
	 */
	public void exportFriendList(String login, String fileName, String exportFields) {

	}

	/**
	 * Recupera uma lista de amigos a partir de um arquivo
	 * 
	 * @param login
	 * @param file
	 */
	public void restoreFriendList(String login, String file) {

	}

	/**
	 * Limpar o banco de dados
	 */
	public void clean() {
		usersDAO.reset();
	}
}
