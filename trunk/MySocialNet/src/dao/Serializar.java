package dao;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */
public class Serializar {

	// com XML

	/**
	 * Esse m�todo recebe um objeto arquivo e o objeto que ser� serializado. O
	 * m�todo ir� serializar o objeto passado no arquivo (referenciado pelo
	 * objeto file).
	 */
	public static void salvar(File file, Object objeto)
			throws FileNotFoundException, IOException {

		if (objeto == null) {
			throw new NullPointerException("Objeto passado � nulo");
		}
		// FileOutputStream fileOut = null;
		// ObjectOutputStream objOut = null;
		XMLEncoder e = null;

		try {
			// fileOut = new FileOutputStream(file, false);
			// objOut = new ObjectOutputStream(fileOut);
			// objOut.writeObject(objeto);
			e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(
					file)));
			e.writeObject(objeto);

		} catch (FileNotFoundException ex) {
			throw new FileNotFoundException("O arquivo n�o pode ser encontrado");
		} catch (IOException ex) {
			throw new IOException("Erro de entrada e sa�da");
		} finally {
			// if (fileOut != null) {
			if (e != null) {
				e.close();
				// fileOut.close();
			}

			// if (objOut != null) {
			if (e != null) {
				e.close();
				// objOut.close();
			}
		}

	}

	/**
	 * Para ler o objeto serializado, n�s somente precisamos no arquivo onde
	 * esse objeto est� salvado. Sabendo o arquivo, n�s conseguimos recuperar o
	 * objeto tranquilamente.
	 */
	public static Object recuperar(File file) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		Object obj = null;

		if (file == null) {
			throw new NullPointerException("Objeto FILE � Nulo.");
		}
		XMLDecoder d = null;
		// FileInputStream fileIn = null;
		// ObjectInputStream objIn = null;

		try {
			// fileIn = new FileInputStream(file);
			// objIn = new ObjectInputStream(fileIn);

			d = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(file)));
			obj = d.readObject();

			// obj = objIn.readObject();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Arquivo n�o encontrado");
		} catch (IOException e) {
			throw new IOException("Erro de entrada e sa�da");
		} finally {
			// if (fileIn != null) {
			if (d != null) {
				d.close();
				// fileIn.close();
			}

			// if (objIn != null) {
			// if (d != null) {
			// d.close();
			// objIn.close();
			// }
		}

		return obj;
	}

}