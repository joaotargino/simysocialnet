package unitTests;

import org.junit.Assert;
import org.junit.Test;

import beans.ContaUsuario;


public class ContaUsuarioTests {
	
	ContaUsuario usuario;
	
	@Test
	public void testaConstrutor() {
//		try {
//			new ContaUsuario("", "Targino", "1235", "targino@gmail.com");
//		} catch (Exception e) {
//			Assert.assertEquals("String vazia", e.getMessage());
//		}
//		try {
//			new ContaUsuario("Joao", "", "1235", "targino@gmail.com");
//		} catch (Exception e) {
//			Assert.assertEquals("String vazia", e.getMessage());
//		}
//		try {
//			new ContaUsuario("", "Targino", "1235", "targino@gmail.com");
//		} catch (Exception e) {
//			Assert.assertEquals("String vazia", e.getMessage());
//		}
		try {
			usuario = new ContaUsuario("Joao Paulo", "Targino", "1235", "targino@gmail.com");
			Assert.assertEquals("Joao Paulo Targino", usuario.toString());
		} catch (Exception e) {
			Assert.fail();
		}	
	}

}
