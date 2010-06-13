package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import usuario.Usuario;

public class FacadeDB {

	private DataBase db = null;
	
	/**
	 * Construtor que assume a função de criar apenas uma instancia da classe DataBase
	 * implementando assim o singleton
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public DataBase conectionDB() throws FileNotFoundException, IOException{
		if(db == null) this.db = new DataBase();
		return db;
	}
	
	/**
	 * Responsavel pela fachada onde delega-se ao Banco de Dados criado
	 * a escrita no arquivo
	 * 
	 * @param sistemData
	 * @throws IOException
	 */
	public void write(Map<String, Usuario> sistemData) throws IOException{
		db.write(sistemData);
	}
	
	/**
	 * Responsavel pela fachada onde delega-se ao Banco de Dados criado
	 * a leitura no arquivo
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Map<String, Usuario> read() throws IOException, ClassNotFoundException{
		return db.read();
	}
}