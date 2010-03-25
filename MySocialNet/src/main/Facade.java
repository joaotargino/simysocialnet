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
	public void logar(){}
	
	/**
	 * Desloga o usuario do sistema
	 */
	public void deslogar() {}
	
	/**
	 * @return true se um usuario estiver logado, falso caso contrario
	 */
	public boolean estaLogado() {
		return true;
	}
	
	
}
