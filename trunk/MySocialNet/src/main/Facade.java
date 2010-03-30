package main;

/**
 * @author tellesmvn, rafael aquino
 *
 */
public class Facade {
	
	private static Facade facade = null;
	
	public synchronized static Facade getInstance() {
		if(facade == null) {
			facade = new Facade();
		}
		return facade;
	}
	
	/**
	 * Loga o usuario ao sistema
	 */
	public void login(){
		
		
	}
	
	/**
	 * Desloga o usuario do sistema
	 */
	public void logout() {
		
		
	}
	
	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 */
	public boolean estaLogado() {
		return true;
	}
	
	/**
	 * Cria um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Nome do usuário deve ser informado"
	 * "Sobrenome do usuário deve ser informado"
	 * "E-mail do usuário deve ser informado"
	 * "Senha deve ser informada"
	 * "E-mail inválido"
	 * "A senha deve ter pelo menos 6 dígitos"
	 * "Login indisponível"
	 * 
	 * @param name - o nome do usuario
	 * @param lastName - o sobrenome do usuario
	 * @param email - o email do usuario
	 * @param passwd - a senha do usuario
	 */
	public void createUser(String name, String lastName, String email, String passwd) {
		
	}
	
	/**
	 * Recupera um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usuário não logado"
	 * 
	 * @param login - o email do usuario
	 * @return String contendo nome e sobrenome do usuario
	 */
	public String getUser (String login) {
		return null;
	}
	
	/**
	 * Atualiza as informações do usuario,pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usuário não logado"
	 * 
	 * @param login
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 */
	public void updateUserProfile(String login, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		
	}
	
	/**
	 * Altera a privacidade de algum campo
	 * 
	 * @param login
	 * @param field
	 * @param type
	 */
	public void setFieldPrivacy(String login, String field, String type) { 
	}
	
	/**
	 * Diz o que está disponivel no perfil dependendo da visibilidade, pode ocorrer os seguintes erros:
	 * 
	 * "Perfil inexistente"
	 * 
	 * @param login
	 * @param visibility
	 * @return Sting no formato campo(1)=valor(1),...,campo(n)=valor(n)]
	 * 			exemplo: "photo=photo.png,aboutMe=,gender=male"
	 */
	public String viewProfile( String login, String visibility) {
		return null;
	}
	
	
	/**
	 * Adiciona uma preferencia para o usuario
	 * 
	 * @param login
	 * @param preference
	 */
	public void addUserPreference(String login, String preference) {

	}
	
	/**
	 * Lista as preferencias do usuario
	 * 
	 * @param login
	 * @return String com as preferencias separada so por virgula (nao sei se isso eh uma re-
	 * presentacao de lista, mas como la nos testes tava como se fosse string...
	 */
	public String listUserPreferences(String login) {
		return null;
	}
	
	/**
	 * Remove uma preferencia do usuario
	 * 
	 * @param login
	 * @param preference
	 */
	public void removeUserPreference (String login, String preference) {
		
	}
	
	/**
	 * Deleta a conta do usuario
	 * @param login
	 */
	public void deleteUser(String login) {
			
	}
	
	public String listGroupMembers(String email, String group) {
		return null;
	}
	
	public String findGroupMember(String login,String friend, String group) {
		return null;
	}
	
	public void addGroupMember(String email, String group, String user) {

	}
	
	public void removeGroupMember(String email, String group, String user) {
		
	}
	
	public String listFriends(String email) {
		return null;
	}
	
	public String findNewFriend(String login, String friend) {
		return null;
	}
}
