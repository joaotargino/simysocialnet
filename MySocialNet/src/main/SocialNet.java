package main;

import interfaces.ProfileIF;

import java.util.List;

import beans.ContaUsuario;
import beans.Grupo;
import controller.GerenciadorGrupo;
import controller.GerenciadorUsuario;

public class SocialNet {

	private static SocialNet social;
	private GerenciadorGrupo gerenciadorGrupo;
	private GerenciadorUsuario gerenciadorUsuario;

	public synchronized static SocialNet getInstance() {
		if(social == null) {
			social = new SocialNet();
		}
		return social;
	}
	
	public void init() {
		gerenciadorGrupo = new GerenciadorGrupo();
		gerenciadorUsuario = new GerenciadorUsuario();
		gerenciadorGrupo.init();
	}

	/**
	 * Loga o usuario ao sistema
	 * @throws Exception 
	 * @throws Exception 
	 */
	public void login(String login, String senha) throws Exception {
		ContaUsuario usuario;
		try {
			usuario = gerenciadorUsuario.getUsuario(login);
			if ( usuario == null) throw new Exception("Login inválido ou senha incorreta");
			else if (!senha.equals(usuario.getSenha())) throw new Exception("Login inválido ou senha incorreta");
		} catch (Exception e) {
			throw new Exception("Login inválido ou senha incorreta");
		}
		if (usuario.isLoged()) throw new Exception("Usuário já logado");
		usuario.setLoged(true);
		gerenciadorUsuario.update(usuario);
	}

	/**
	 * Desloga o usuario do sistema
	 * @throws Exception 
	 */
	public void logoff(String email) throws Exception {
		ContaUsuario user;
		try {
			user = gerenciadorUsuario.getUsuario(email);
		} catch (Exception e) {
			throw new Exception("Login inválido");
		}
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		user.setLoged(false);
		gerenciadorUsuario.update(user);
	}

	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 * @throws Exception 
	 */
	public boolean estaLogado(String login) throws Exception {
		ContaUsuario usuario;
		usuario = gerenciadorUsuario.getUsuario(login);
		return usuario.isLoged();
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
		gerenciadorUsuario.adicionar(contaUsuario);
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
		ContaUsuario usuario = gerenciadorUsuario.getUsuario(login);
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
		ContaUsuario usuario = gerenciadorUsuario.getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		usuario.updateUserProfile(usuario, aboutMe, age, photo, country, city, gender, contactEmail);
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
		ContaUsuario usuario = gerenciadorUsuario.getUsuario(login);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		usuario.setFieldPrivacy(login, type, field);
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
			user = gerenciadorUsuario.getUsuario(login);
		} catch (Exception e) {
			throw new Exception("Perfil inexistente");
		}

		if (!(estaLogado(login))) throw new Exception("Usuário não logado");
		ProfileIF profile = user.getProfile(user, visibility);
		return profile;
	}


	public ProfileIF viewProfile(String viewer, String profileOwner) throws Exception {
		ContaUsuario viewerUser;
		ContaUsuario ownerUser;
		try {
			viewerUser = gerenciadorUsuario.getUsuario(viewer);
		} catch (Exception e) {
			throw new Exception("Login do viewer não existente no sistema");
		}
		try {
			ownerUser = gerenciadorUsuario.getUsuario(profileOwner);
		} catch (Exception e) {
			throw new Exception("Perfil inexistente");
		}
		if(!viewerUser.isLoged()) throw new Exception("Usuário não logado");
		if (ownerUser.getAmigos().contains(viewerUser)) return ownerUser.getProfileFriends();
		return ownerUser.getProfileAll();
	}


	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void addUserPreference(String login, String preference) throws Exception {
		ContaUsuario user = gerenciadorUsuario.getUsuario(login);
		if(!user.isLoged()) {
			throw new Exception("Usuário não logado");
		}
		gerenciadorUsuario.addUserPreferences(user, preference);
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
		return gerenciadorUsuario.getUsuario(login).getPreferencias();
	}

	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 * @throws Exception 
	 */
	public void removeUserPreference (String login, String preference) throws Exception {
		ContaUsuario user = gerenciadorUsuario.getUsuario(login);
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		user.getPreferencias().remove(preference);
		gerenciadorUsuario.update(user);
	}

	/**
	 * Deleta a conta do usuario
	 * @param login
	 * @throws Exception 
	 */
	public void deleteUser(String login) throws Exception {
		ContaUsuario user = gerenciadorUsuario.getUsuario(login);
		if (!user.isLoged()) throw new Exception("Usuário não logado");
		gerenciadorUsuario.remover(login);
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
		ContaUsuario usuario = gerenciadorUsuario.getUsuario(email);
		if (!usuario.isLoged()) throw new Exception("Usuário não logado");
		return gerenciadorGrupo.getGrupo(usuario, group);
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
		return gerenciadorGrupo.getMembro(login, friend, group);
	}

	/**
	 * @param email
	 * @param group
	 * @param user
	 * @throws Exception 
	 */
	public void addGroupMember(String email, String group, String user) throws Exception {
		gerenciadorGrupo.adicionar(email, group, user);
	}

	/**
	 * Remove um membro do grupo
	 * 
	 * @param email
	 * @param group
	 * @param user
	 * @throws Exception 
	 */
	public void removeGroupMember(String email, String group, String user) throws Exception {
		gerenciadorGrupo.remover(email, group, user);
	}

	/**
	 * Listar os amigos
	 * 
	 * @param email
	 * @return
	 * @throws Exception 
	 */
	public List<ContaUsuario> listFriends(String email) throws Exception {
		return gerenciadorUsuario.getAmigos(email);
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
		return gerenciadorUsuario.findNewFriend(login,friend);
	}

	/**
	 * Envia um convite de amizade para outro usuario
	 * 
	 * @param login
	 * @param user
	 * @param message
	 * @param group
	 */
	public void sendFriendshipRequest(String login, String user, String message, String group) throws Exception{
		gerenciadorUsuario.sendFriendshipRequest(login, user, message, group);
	}

	/**
	 * Ver os convites de amizade. que est�o pendentes, para o usuario
	 * 
	 * @param login
	 * @return
	 * @throws Exception 
	 */
	public List<String> viewPendingFriendship(String login) throws Exception {
		return gerenciadorUsuario.viewPendingFriendship(login);
	}

	/**
	 * Ver os convites de amizade enviados 
	 * pelo usuario que est�o pendentes
	 * 
	 * @param login
	 * @return
	 * @throws Exception 
	 */
	public List<String> viewSentFriendship (String login) throws Exception {
		return gerenciadorUsuario.viewSentFriendship(login);
	}

	/**
	 * Rejeita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 */
	public void declineFriendshipRequest (String login, String contact)throws Exception {
		gerenciadorUsuario.declineFriendshipRequest(login, contact);
	}

	/**
	 * Aceita o convite de amizade
	 * 
	 * @param login
	 * @param contact
	 * @param group
	 */
	public void acceptFriendshipRequest (String login, String contact, String group) throws Exception { 
		gerenciadorUsuario.acceptFriendshipRequest (login, contact, group);
	}

	/**
	 * Pega um amigo da lista de amigos
	 * 
	 * @param email
	 * @param friend
	 * @return
	 * @throws Exception 
	 */
	public ContaUsuario getFriend(String email, String friend) throws Exception {
		ContaUsuario user = gerenciadorUsuario.getUsuario(email);
		ContaUsuario amigo = gerenciadorUsuario.getUsuario(friend);
		for(ContaUsuario usuario : user.getAmigos()) {
			if (usuario.equals(amigo)) return usuario;
		}
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
		gerenciadorUsuario.clean();
	}
}
