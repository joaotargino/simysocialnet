package facades;

public class ProfileUtilFacade {
	
	private static ProfileUtilFacade instancia;
	
	protected ProfileUtilFacade() {
		
	}
	
	public static synchronized ProfileUtilFacade getInstance() {
		if(instancia == null) { 
			instancia = new ProfileUtilFacade();
		}
		return instancia;
	}
	
	
}
