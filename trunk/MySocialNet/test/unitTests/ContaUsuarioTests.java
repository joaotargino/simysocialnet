package unitTests;

import org.junit.Assert;
import org.junit.Test;

import beans.UserAccount;


public class ContaUsuarioTests {
	
	UserAccount usuario;
	
	@Test
	public void testaNome() {
		
		try {
			new UserAccount("", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String vazia", e.getMessage());
		}
		try {
			new UserAccount("Joao", "", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String vazia", e.getMessage());
		}
		try {
			new UserAccount("", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String vazia", e.getMessage());
		}
		try {
			new UserAccount("   ", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String so com espaco", e.getMessage());
		}
		try {
			new UserAccount(null, "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String null", e.getMessage());
		}
		try {
			new UserAccount("Joao Paulo   ", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String invalida", e.getMessage());
		}
		try {
			new UserAccount("Joao_Paulo1", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String invalida", e.getMessage());
		}
		
		try {
			usuario = new UserAccount("Joao Paulo", "Targino", "123456", "targino@gmail.com");
			Assert.assertEquals("Nome=Joao Paulo,Sobrenome=Targino", usuario.toString());
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
