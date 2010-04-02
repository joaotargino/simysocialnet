package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	public static boolean verificaString(String texto, String campo) throws Exception {
		
		if (texto == null) throw new Exception(campo + " null");
		else if (texto.isEmpty()) throw new Exception(campo + " do usuário deve ser informado");
		else if (texto.trim().isEmpty()) throw new Exception ("String so com espaco");
		
		char[] caracteres = texto.toCharArray();
		
		for (int i = 0; i < caracteres.length; i++) {
			if(!(Character.isLetter(caracteres[i]) || Character.isWhitespace(caracteres[i]))) {
				throw new Exception("String invalida");
			}
			else if (Character.isWhitespace(caracteres[i])) {
				if (!(Character.isLetter(caracteres[i + 1]))) {
					throw new Exception("String invalida");
				}
			}
		}
		return true;
	}
	
	public static boolean verificaSenha(String senha) throws Exception {
		
		if (senha == null) throw new Exception("Senha null");
		else if (senha.isEmpty()) throw new Exception("Senha deve ser informada");
		else if (senha.trim().isEmpty()) throw new Exception ("Senha só com espaço");
		
		if (senha.length() < 6) throw new Exception("A senha deve ter pelo menos 6 dígitos");
		
		return true;
	}
	
	public static boolean verificaEmail(String email) throws Exception {
		if (email == null) throw new Exception("E-mail null");
		else if (email.isEmpty()) throw new Exception("E-mail do usuário deve ser informado");
		else if (email.trim().isEmpty()) throw new Exception ("E-mail só com espaço");

		String expression = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = pattern.matcher(email);
		if (!pesquisa.matches()) throw new Exception("E-mail inválido");
		return true;
	}
	
	public static boolean verificaData(String data) throws Exception {
		if (data == null) throw new Exception("Data null");
		else if (data.isEmpty()) throw new Exception("Data vazia");
		else if (data.trim().isEmpty()) throw new Exception ("Data só com espaço");

		
		String expression = "/^((([0][1-9]|[12][0-9])02(19|20)([13579][26]|" +
				"[02468][048]))|(([0][1-9]|[12][0-8])02(19|20)([02468][12356]|[13579][13579]))|" +
				"((([0][1-9]|[12][0-9]|30)(0[469]|11)|([0][1-9]|[12][0-9]|3[01])(0[13578]|1[02]))((19|20)[0-9][0-9])))$/";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = pattern.matcher(data);
		return pesquisa.matches();
	}
	public static void main(String[] args) {
		try {
			System.out.println(Util.verificaData("10102010"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

