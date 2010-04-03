package unitTests;

import org.junit.Assert;
import org.junit.Test;

import beans.ContaUsuario;


public class ContaUsuarioTests {
	
	ContaUsuario usuario;
	
	@Test
	public void testaNome() {
		
		try {
			new ContaUsuario("", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String vazia", e.getMessage());
		}
		try {
			new ContaUsuario("Joao", "", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String vazia", e.getMessage());
		}
		try {
			new ContaUsuario("", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String vazia", e.getMessage());
		}
		try {
			new ContaUsuario("   ", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String so com espaco", e.getMessage());
		}
		try {
			new ContaUsuario(null, "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String null", e.getMessage());
		}
		try {
			new ContaUsuario("Joao Paulo   ", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String invalida", e.getMessage());
		}
		try {
			new ContaUsuario("Joao_Paulo1", "Targino", "1235", "targino@gmail.com");
		} catch (Exception e) {
			Assert.assertEquals("String invalida", e.getMessage());
		}
		
		try {
			usuario = new ContaUsuario("Joao Paulo", "Targino", "123456", "targino@gmail.com");
			Assert.assertEquals("Nome=Joao Paulo,Sobrenome=Targino", usuario.toString());
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
