package sistema;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import usuario.Campo;
import usuario.Perfil;
import usuario.Usuario;
import usuario.Visibilidade;
import database.FacadeDB;


public class Sistema {
	
	private Map<String, Usuario> listUsers = new HashMap<String, Usuario>();
	private FacadeDB facadeDB = new FacadeDB();
	private ArrayList<Usuario> usuariosLogadosAtuais = new ArrayList<Usuario>();
	private ArrayList<String> grupos = new ArrayList<String>();
	
	/**
	 * Construtor da Classe Sistema - Realiza a conexao com o BD
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Sistema() throws FileNotFoundException, IOException{
		facadeDB.conectionDB();
		grupos.add("conhecidos");
		grupos.add("escola");
		grupos.add("familia");
		grupos.add("melhores amigos");
		grupos.add("trabalho");
	}
	
	
	/**
	 * Metodo que limpa o BD do sistema
	 *  @throws Exception
	 */
	public void clean() throws Exception {
		listUsers = new HashMap<String, Usuario>();
		facadeDB.write(listUsers);
	}
	
	// Metodos US01
	
	/**
	 * Cria um usuario a partir do nome, sobrenome, email(login) e senha
	 * @param nome
	 * @param sobrenome
	 * @param email
	 * @param senha
	 * @throws Exception
	 */
	public void createUser(String nome, String sobrenome, String email,
			String senha) throws Exception {
		try {
			if (listUsers.containsKey(email)) throw new Exception("Login indisponível");
			listUsers.put(email, new Usuario(nome, sobrenome, email, senha));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Retorna o nome e o sobrenome do usuario a partir do login
	 * @param login
	 * @return nome e sobrenome
	 * @throws Exception
	 */
	public String getUser(String login) throws Exception {
		Usuario user = getLogin(login);
		return "Nome="+user.getNome()+",Sobrenome="+user.getSobrenome();
	}
	
	/**
	 * Retorna o Usuario (objeto) a partir de seu login
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public Usuario getObjUsuario(String login) throws Exception{
        return getLogin(login);
	}
	
	/**
	 * Atualiza os dados do perfil do usuario
	 * @param login
	 * @param aboutMe
	 * @param age
	 * @param photo
	 * @param country
	 * @param city
	 * @param gender
	 * @param contactEmail
	 * @throws Exception
	 */
	public void updateUserProfile(String login, String aboutMe, String age,
			String photo, String country, String city, String gender,
			String contactEmail) throws Exception {
		Usuario user = getLogin(login);
		user.setPerfil(aboutMe, age, photo, country, city, gender, contactEmail);
	}
	
	/**
	 * Metodo que altera a privacidade dos campos do perfil
	 * @param email
	 * @param field
	 * @param type
	 * @throws Exception
	 */
	public void setFieldPrivacy(String email, String field, String type) throws Exception {
		Usuario user = getLogin(email);
		
		Visibilidade v;
		if (type.equals("JUST_ME"))v = Visibilidade.JUST_ME;
		else if (type.equals("FRIENDS"))v = Visibilidade.FRIENDS;
		else if (type.equals("ALL")) v = Visibilidade.ALL;
		else return;
		
		ArrayList<Campo> campos = user.getPerfil().getCamposDoPerfil();
		for (Campo c : campos) {
			if (c.getNomeCampo().equals(field)) {
				c.setVisibilidade(v);
				return;
			}
		}
	}
	
	
	/**
	 * Metodo que checa a visibilidade do campo do perfil
	 * @param login
	 * @param visibility
	 * @return a string da visibilidade
	 * @throws Exception
	 */
	public String checkProfile(String login, String visibility) throws Exception {
		Usuario u = listUsers.get(login);
		if (u == null) throw new Exception("Perfil inexistente");
		if (!usuariosLogadosAtuais.contains(u)) throw new Exception("Usuário não logado");
		return u.checkProfile(visibility);
	}
	
	/**
	 * 
	 * @param viewer
	 * @param profileOwner
	 * @return
	 * @throws Exception
	 */
	public String viewProfile(String viewer, String profileOwner) throws Exception {
		Usuario v = listUsers.get(viewer);
		if (v == null) throw new Exception("Login do viewer não existente no sistema");
		Usuario p = listUsers.get(profileOwner);
		if (p == null) throw new Exception("Perfil inexistente");
		if (!usuariosLogadosAtuais.contains(v)) throw new Exception("Usuário não logado");
		return p.viewProfile();
	}
	
	/**
	 * Set uma preferencia ao perfil do usuario
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void addUserPreference(String login, String preference) throws Exception {
		Usuario user = getLogin(login);
		user.addUserPreference(preference);
	}
	
	/**
	 * Remove a preferencia uma preferencia
	 * @param login
	 * @param preference
	 * @throws Exception
	 */
	public void removeUserPreference(String login,String preference) throws Exception{
		Usuario user = getLogin(login);
		user.removeUserPreference(preference);
	}
	
	/**
	 * Lista as preferencias do usuario
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public String listUserPreference(String login) throws Exception {
		Usuario user = getLogin(login);
		return user.listUserPreference();
	}
	
	/**
	 * Deleta usuario do sistema
	 * @param login
	 * @throws Exception
	 */
	public void deleteUser(String login) throws Exception {
		if(login.length() == 0) {
			throw new Exception("Login não pode ser vazio");
		}
		getLogin(login);
		listUsers.remove(login);
	}
	
	//US02
	
	/**
	 * Realiza login do usuario
	 * @param email
	 * @param senha
	 * @throws Exception
	 */
	public void login(String email, String senha) throws Exception {
		Usuario usuarioLogando = listUsers.get(email);
		if (usuarioLogando == null) {
			throw new Exception("Login inválido ou senha incorreta");
		}
		if (usuariosLogadosAtuais.contains(usuarioLogando)) {
			throw new Exception("Usuário já logado");
		}
		if (!usuarioLogando.getSenha().equals(senha)) {
			throw new Exception("Login inválido ou senha incorreta");
		}
		usuariosLogadosAtuais.add(usuarioLogando);
	}
	
	//US03
	
	/**
	 * Realiza o logoff do usuario
	 * @param email
	 * @throws Exception
	 */
	public void logoff(String email) throws Exception {
		Usuario usuarioSaindo = listUsers.get(email);
		if (usuarioSaindo == null) {
			throw new Exception("Login inválido");
		}
		if (!usuariosLogadosAtuais.contains(usuarioSaindo)) {
			throw new Exception("Usuário não logado");
		}
		usuariosLogadosAtuais.remove(usuarioSaindo);
	}
	
	//US04
	
	/**
	 * Lista os amigos do usuario
	 * @param login o login do usuario
	 * @return a string com o nome dos amigos do usuario
	 */
	public String listFriends(String login) throws Exception{
		Usuario user = getLogin(login);
		return user.listFriends();
	}
	
	/**
	 * Busca um usuario a partir do email
	 * @param login o login do usuario
	 * @param email o email do amigo
	 * @return
	 * @throws Exception
	 */
	public String findNewFriend(String login, String email) throws Exception{
		Usuario contato = listUsers.get(email);
		if (!listUsers.containsKey(login)) {
			throw new Exception("Login inexistente");
		}
		if(!listUsers.containsKey(email)) {
			contato = findUser(email);
			if(contato == null) return "";
		}
		if(!usuariosLogadosAtuais.contains(listUsers.get(login))) {
			throw new Exception("Usuário não logado");
		}
		return contato.viewPerfil();
	}
	
	
	/**
	 * Envia um convite de amizade a um usuario
	 * @param login (envia)
	 * @param email (recebe)
	 * @param message
	 * @param grupo
	 * @return
	 * @throws Exception
	 */
	public void sendFriendshipRequest(String login, String email, String message, String grupo) throws Exception{
		Usuario usuario = listUsers.get(login);
		if (!listUsers.containsKey(login)) {
			throw new Exception("Login inexistente");
		}
		if(!listUsers.containsKey(email)){
			throw new Exception("Contato inexistente");
		}
		if(!usuariosLogadosAtuais.contains(usuario)) {
			throw new Exception("Usuário não logado");
		}
		if(login.equals(email)){
			throw new Exception("Operação não permitida");
		}
		Usuario contato = listUsers.get(email);
		if(usuario.containsContato(contato)){
			throw new Exception("Usuários "+usuario.getNomeCompleto()+" e "+contato.getNomeCompleto()+" já são amigos");
		}
		usuario.enviaConvite(contato,grupo);
		contato.recebeConvite(usuario,message);
	}
	
	/**
	 * Visualiza os convites recebidos pendentes de amizade
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public String viewPendingFriendship(String login) throws Exception{
		Usuario user = getLogin(login);
		return user.viewPendingFriendship();
	}
	
	/**
	 * Visualiza os convites de amizade enviados.
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public String viewSentFriendship(String login) throws Exception{
		Usuario user = getLogin(login);
		return user.viewSentFriendship();
	}
	
	/**
	 * Recusa convite de amizade
	 * @param login
	 * @param contact
	 * @throws Exception
	 */
	public void declineFriendshipRequest(String login,String contact) throws Exception{
		if(!listUsers.containsKey(contact)){
			throw new Exception("Contato inexistente");
		}
		if(login.equals(contact)){
			throw new Exception("Operação não permitida");
		}
		Usuario user = getLogin(login);
		Usuario contato = listUsers.get(contact);
		user.declineFriendshipRequest(contato);
		contato.removeConvite(user);
	}
	
	/**
	 * Aceita um convite de amizade
	 * @param login
	 * @param contact
	 * @param grupo
	 * @throws Exception
	 */
	public void acceptFriendshipRequest(String login, String contact, String grupo) throws Exception{
		Usuario user = getLogin(login);
		if(!listUsers.containsKey(contact)){
			throw new Exception("Contato inexistente");
		}else if(login.equals(contact)){
			throw new Exception("Operação não permitida");
		}
		Usuario contato = listUsers.get(contact);
		user.acceptFriendshipRequest(contato, grupo);
		contato.addFriend(user);
	}
	
	
	/**
	 * Procura um amigo em um determinado grupo
	 * @param login
	 * @param friend
	 * @param group
	 * @throws Exception
	 */
	public String findGroupMember(String login, String friend, String group) throws Exception {
		Usuario user = getLogin(login);
		Usuario contato = listUsers.get(friend);
		if (contato == null) {
			contato = findUser(friend);
			if(contato == null) return null;
			return user.findGroupMember(contato,group);
			
		}
		if (!grupos.contains(group)) throw new Exception("Grupo invalido");
		return user.findGroupMember(contato,group);
	}
	
	/**
	 * Retorna nome e sobrenome do amigo (friend) do usuario (email)
	 * @param email
	 * @param friend
	 * @throws Exception
	 */
	public String getFriend(String email,String friend) throws Exception{
		Usuario user = getLogin(email);
		Usuario contato = listUsers.get(friend);
		if(contato == null){
			if(user.getFriend(friend) == null){
				return null;
			}
			return user.getFriend(friend);
		}
		return user.getFriend(contato);
	}
	
	
	/**
	 * Remove amigo da lista do usuario e o usuario da lista do amigo
	 * @param email
	 * @param friend
	 * @throws Exception
	 */
	public void removeFriend(String email, String friend) throws Exception{
		Usuario user = getLogin(email);
		Usuario contato = listUsers.get(friend);
		if(contato == null){
			throw new Exception("Amigo não existente no sistema");
		}
		user.removeFriend(contato);
		contato.removeFriend(user);
	}
	
	//US05
	
	/**
	 * Listar amigos por grupo
	 * @param email
	 * @param group
	 * @throws Exception
	 */
	public String listGroupMembers(String email, String grupo) throws Exception {
		if (!grupos.contains(grupo)) throw new Exception("Grupo " + grupo + " não existe");
		Usuario user = getLogin(email);
		return user.listGroupMembers(grupo);
	}
	
	/**
	 * Adiciona amigo em um grupo do usuario
	 * @param email
	 * @param group
	 * @param userInterface
	 * @throws Exception
	 */
	public void addGroupMember(String email, String grupo, String user) throws Exception {
		if (!grupos.contains(grupo)) throw new Exception("Grupo " + grupo + " não existe");
		Usuario contato = listUsers.get(user);
		if(contato == null){
			throw new Exception("Usuário a ser adicionado inexistente no sistema");
		}
		Usuario usuario = getLogin(email);
		usuario.addGroupMember(contato,grupo);
	}
	
	/**
	 * Remove amigo do grupo
	 * @param email
	 * @param group
	 * @param userInterface
	 * @throws Exception
	 */
	public void removeGroupMember(String email, String group, String user) throws Exception {
		Usuario usuario = getLogin(email);
		Usuario contato = listUsers.get(user);
		if (!grupos.contains(group)) throw new Exception("Grupo " + group + " não existe");
		if(contato == null)throw new Exception("Usuário a ser removido inexistente no sistema");
		usuario.removeGroupMember(contato,group);
	}
	
	//US06
	
	/**
	 * Remove amigo do grupo
	 * @param login
	 * @throws Exception
	 */
	public String getRecommendFriends(String login) throws Exception{
		Usuario user = getLogin(login);
		return user.getRecommendFriends();
	}
	
	//US07
	
	/**
	 * Testa Login de usuario e apartir do resultado tenta exportar as informaï¿œï¿œes dadas como parametros
	 * @param login
	 * @param fileName
	 * @param exportFields
	 * @throws Exception
	 */
	public void exportFriendList(String login, String fileName, String exportFields) throws Exception {
		Usuario usuario = getLogin(login);
		String stringParaExportar = "";
		File file;
		FileWriter writer;
		PrintWriter saida;
		try {
			file = new File(fileName);
			writer = new FileWriter(file);
			saida = new PrintWriter(writer,true);
		} catch(Exception erro) {
			throw new Exception("Falha na exportação do arquivo");
		}
		if (exportFields.trim().equals("")) stringParaExportar += "name,lastName";
		else stringParaExportar += "name,lastName,";
		stringParaExportar += exportFields + System.getProperty("line.separator");
		List<Usuario> listaDosAmigos = usuario.getFriends();
		Collections.sort(listaDosAmigos);
		Iterator<Usuario> IteradorDaListaDeAmigos = listaDosAmigos.iterator();
		while (IteradorDaListaDeAmigos.hasNext()) {
			Usuario amigoIterado = IteradorDaListaDeAmigos.next();
			stringParaExportar += this.formataSaidaParaExportar(amigoIterado, exportFields);                    
			if (IteradorDaListaDeAmigos.hasNext()) stringParaExportar += System.getProperty("line.separator");
		}
		saida.println(stringParaExportar);
		writer.close();
		saida.close();
	}
	
	
	//US08
	
	/**
	 * Retorna a string com a lista de amigos do arquivo
	 * @param login
	 * @param file
	 * @throws Exception
	 */
	public String restoreFriendList (String login, String file) throws Exception {
		Usuario usuario = getLogin(login);
		List<String> contatosNaoImportados = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (Exception erro) {
			throw new Exception("Arquivo não encontrado");
		}
		testaArquivo(reader);
		reader = new BufferedReader(new FileReader(file));
		reader.readLine();
		String linha = reader.readLine();
		while (linha != null) {
			String restore = restoreSingleFriend(usuario, linha);
			if (!restore.equals("")) contatosNaoImportados.add(restore);
			linha = reader.readLine();
		}
		if (contatosNaoImportados.size() == 0) return "Todos os contatos foram importados";
		return ("Contatos não importados: "+organizaContatosNaoImportados(contatosNaoImportados));
	}
	
	/**
	 * Retorna a string com um unico amigo
	 * @param login
	 * @param file
	 * @throws Exception
	 */
	public String restoreSingleFriend(Usuario usuario,String friendInformation) {
		String[] dadosSeparadosDoAmigo = friendInformation.split(",");
		String nomeCompletoDoAmigo = dadosSeparadosDoAmigo[0]+" "+dadosSeparadosDoAmigo[1];
		String msg = "";
		try {
			Usuario newFriend = null;
			for(Usuario u : listUsers.values()) {
				if (u.getNomeCompleto().equals(nomeCompletoDoAmigo)) {
					newFriend = u;
				}
			}
			if (usuario.getFriends().contains(newFriend)) {
				return "Usuários "+usuario.getNomeCompleto()+" e "+nomeCompletoDoAmigo+" já são amigos";
			}
			if (newFriend.viewPendingFriendship().contains(usuario.getEmail())) {
				msg = "Você já enviou um convite para o usuário "+nomeCompletoDoAmigo;
			} else {
				String message = "Olá, "+newFriend.getEmail()+" me adicione como seu amigo.";
				this.sendFriendshipRequest(usuario.getEmail(), newFriend.getEmail(), message, "conhecidos");
			}
		} catch(Exception erro) {
			msg += nomeCompletoDoAmigo;
		}
		return msg;
		
	}
	
	
	//METODOS AUXILIARES
	
	/**
	 * Testa o login, retornando o Usuario deste.
	 * @param login
	 * @throws Exception
	 */
	private Usuario getLogin(String login) throws Exception {
		Usuario u = listUsers.get(login);
		if (u == null) {
			throw new Exception("Login inexistente");
		}
		if (!usuariosLogadosAtuais.contains(u)) {
			throw new Exception("Usuário não logado");
		}
		return u;
	}
	
	/**
	 * Busca um usuario a partir do nome
	 * @param nome
	 * @return usuario encontrado
	 * @throws Exception
	 */
	private Usuario findUser(String nome) throws Exception{
		nome = nome.toLowerCase();
		Iterator<Usuario> it = listUsers.values().iterator();
		while(it.hasNext()){
			Usuario u = (Usuario)it.next();
			if(u.getNomeCompleto().toLowerCase().equals(nome)){
				return u;
			}
		}
		return null;
	}
	
	/**
	 * Formata string a ser exportada
	 * @param usuario
	 * @param exportFields
	 */
	private String formataSaidaParaExportar(Usuario usuario,String exportFields) {
		Perfil p = usuario.getPerfil();
		String dadosPerfil = usuario.getNome()+","+usuario.getSobrenome();
		if (exportFields.contains("photo")) dadosPerfil += ","+p.getPhoto();
		if (exportFields.contains("aboutMe")) dadosPerfil += ","+p.getAboutMe();
		if (exportFields.contains("age")) dadosPerfil += ","+p.getAge();
		if (exportFields.contains("gender")) dadosPerfil += ","+p.getGender();
		if (exportFields.contains("contactEmail")) dadosPerfil += ","+p.getContactEmail();
		if (exportFields.contains("country")) dadosPerfil += ","+p.getCountry();
		if (exportFields.contains("city")) dadosPerfil += ","+p.getCity();
		return dadosPerfil;
	}
	
	/**
	 * Organiza contatos nao importados
	 * @param contatosNaoImportados
	 * @return
	 */
	private String organizaContatosNaoImportados(List<String> contatosNaoImportados) {
		Iterator<String> itContatosNaoImportados = contatosNaoImportados.iterator();
		String nomesDeContatosNaoImportados = "";
		while (itContatosNaoImportados.hasNext()) {
			String contatoNaoImportado =  itContatosNaoImportados.next();
			nomesDeContatosNaoImportados += contatoNaoImportado;
			if (itContatosNaoImportados.hasNext()) nomesDeContatosNaoImportados += ",";
		}
		return nomesDeContatosNaoImportados;
	}
	
	/**
	 * Testa se o arquivo é suportado pelo sistema
	 * @param reader
	 * @throws Exception
	 */
	private void testaArquivo(BufferedReader reader) throws Exception{
		String linha = reader.readLine();
		if (!linha.contains("name,lastName")) throw new Exception("Arquivo não suportado pelo sistema");
		int numeroDeColunas = linha.split(",").length;
		while (linha != null) {
			if (linha.split(",").length != numeroDeColunas) throw new Exception("Arquivo não suportado pelo sistema");
			linha = reader.readLine();
		}
	}
	
}
