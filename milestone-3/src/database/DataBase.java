package database;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import usuario.Usuario;

public class DataBase {

	private ObjectOutputStream fileOut;
	private ObjectInputStream fileIn;
	private String dataFile = "src/database/Usuarios.txt";

	public DataBase() throws FileNotFoundException, IOException {
		fileOut = null;
		fileIn = null;
	}

	/**
	 * Usa ObjectOutputStream para escrever o objeto no arquivo.
	 * @param dadosDoSistema dados a serem armazenados
	 * @throws IOException
	 */
	public void write(Map<String, Usuario> dadosDoSistema) throws IOException {
		try {
			fileOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
			fileOut.writeObject(dadosDoSistema);
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {
			fileOut.close();
		}
	}
	
	/**
	 * Lê um objeto do arquivo via ObjectInputStream.
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Map<String, Usuario> read() throws IOException, ClassNotFoundException {
		try{
			fileIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
			return (Map<String, Usuario>) fileIn.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			fileIn.close();
		}
	}
}