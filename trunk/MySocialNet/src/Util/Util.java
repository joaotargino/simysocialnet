package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	public static boolean verificaString(String texto) {
		for (Character caractere : texto.trim().toCharArray()) {
			if(!Character.isLetter(caractere)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean verificaEmail(String texto) {
		String expression = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = pattern.matcher(texto);
		return pesquisa.matches();
	}
	
	public static boolean verificaData(String texto) {
		String expression = "/^((([0][1-9]|[12][0-9])02(19|20)([13579][26]|" +
				"[02468][048]))|(([0][1-9]|[12][0-8])02(19|20)([02468][12356]|[13579][13579]))|" +
				"((([0][1-9]|[12][0-9]|30)(0[469]|11)|([0][1-9]|[12][0-9]|3[01])(0[13578]|1[02]))((19|20)[0-9][0-9])))$/";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = pattern.matcher(texto);
		return pesquisa.matches();
	}
	public static void main(String[] args) {
		System.out.println(Util.verificaData("10102010"));
	}
}
