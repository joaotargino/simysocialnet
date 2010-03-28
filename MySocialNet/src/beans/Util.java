package beans;

public class Util {
	
	public static boolean verificaString(String texto) {
		for (Character caractere : texto.toCharArray()) {
			if(!Character.isLetter(caractere)) {
				return false;
			}
		}
		return true;
	}

}
