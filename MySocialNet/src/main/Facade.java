package main;

/**
 * @author tellesmvn
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
	public void login(){}
	
	/**
	 * Desloga o usuario do sistema
	 */
	public void logout() {}
	
	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 */
	public boolean estaLogado() {
		return true;
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
	 */
	public void createUser(String name, String lastName, String email, String passwd) {
		
	}
	
	/**
	 * Recupera um usuario, pode ocorrer os seguintes erros:
	 * 
	 * "Login inexistente"
	 * "Usu�rio n�o logado"
	 * 
	 * @param login - o email do usuario
	 * @return String contendo nome e sobrenome do usuario
	 */
	public String getUser (String login) {
		return null;
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
	 */
	public void updateUserProfile(String login, String aboutMe, String age, String photo, String country, String city, String gender, String contactEmail) {
		
	}
	
	public void setFieldPrivacy(String login, String field, String type) {
		
	}
	
}
