package usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import sistema.Convite;
import sistema.ConviteEnviado;
import sistema.ConviteRecebido;

public class Usuario implements Serializable, Comparable<Usuario> {

	private String nome, sobrenome, email, senha;
	private Perfil perfil;
	private ArrayList<ConviteRecebido> convitesPendentes;
	private ArrayList<ConviteEnviado> convitesEnviados;
	private ArrayList<Usuario> recommendFriends;
	private Grupo conhecidos;
	private ArrayList<Grupo> grupos;
	private double similaridade = 0.35;


	public Usuario(String nome, String sobrenome, String email, String senha) throws Exception {
		this.setNome(nome);
		this.setSobrenome(sobrenome);
		this.setEmail(email);
		this.setSenha(senha);
		this.perfil = new Perfil("", "", "", "", "", "", "");
		this.conhecidos = new Grupo("conhecidos",0.0);
		this.convitesPendentes = new ArrayList<ConviteRecebido>();
		this.convitesEnviados = new ArrayList<ConviteEnviado>();
		this.recommendFriends = new ArrayList<Usuario>();
		this.grupos = new ArrayList<Grupo>();
		grupos.add(conhecidos);

	}

	public void criarNovoGrupo(String nome, double peso) throws Exception {
		if (this.possuiGrupo(nome)) {
			throw new Exception("Grupo já existe");
		}
		Grupo novoGrupo = new Grupo(nome, peso);
		grupos.add(novoGrupo);
	}

	public void removerGrupo(String nome) throws Exception {
		if (!this.possuiGrupo(nome)) {
			throw new Exception("Grupo não existe");
		}
		if (nome.equals("conhecidos")) {
			throw new Exception("Grupo conhecidos não pode ser removido");
		}
		Grupo grupoParaRemover = new Grupo(nome, 0);
		Iterator<Grupo> it = grupos.iterator();
		while (it.hasNext()) {
			Grupo grupo = it.next();
			if(grupo.equals(grupoParaRemover)) {
				Iterator<Usuario> usersIterator = grupo.getContatos().iterator();
				while(usersIterator.hasNext()) {
					Usuario contato = usersIterator.next();
					this.addGroupMember(contato, "conhecidos");
				}
				break;
			}
		}
		grupos.remove(grupoParaRemover);
	}

	public void renomearGrupo(String nomeAntigo, String novoNome) throws Exception {
		if(!this.possuiGrupo(nomeAntigo)) {
			throw new Exception("Grupo não existe");
		}
		Iterator<Grupo> it = grupos.iterator();
		while (it.hasNext()) {
			Grupo grupo = it.next();
			if (grupo.getNome().equals(nomeAntigo)) {
				grupo.setNome(novoNome);
			}
		}
	}

	public void setSenha(String senha) throws Exception {
		if (senha == null || senha.equals("")) {
			throw new Exception("Senha deve ser informada");
		}
		if (senha.length() < 6) {
			throw new Exception("A senha deve ter pelo menos 6 dígitos");
		}
		this.senha = senha;
	}

	public String getSenha() throws Exception {
		return senha;
	}

	public void setEmail(String email) throws Exception {
		if (email == null || email.equals("")) {
			throw new Exception("E-mail do usuário deve ser informado");
		}
		if (!emailValido(email)) {
			throw new Exception("E-mail inválido");
		}
		this.email = email;
	}

	public double getSimilaridade() {
		return similaridade;
	}

	public void setSimilaridade(double similaridade) {
		this.similaridade = similaridade;
	}

	/**
	 * Verifica se o email é válido
	 * @throws Exception 
	 */
	private boolean emailValido(String email) {
		return Pattern.compile(".+@.+\\.[a-z]+").matcher(email).matches();
	}

	public String getEmail() throws Exception {
		return email;
	}

	public void setNome(String nome) throws Exception {
		if (nome == null || nome.equals("")) {
			throw new Exception("Nome do usuário deve ser informado");
		}
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setSobrenome(String sobrenome) throws Exception {
		if (sobrenome == null || sobrenome.equals("")) {
			throw new Exception("Sobrenome do usuário deve ser informado");
		}
		this.sobrenome = sobrenome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	@Override
	public String toString() {
		try {
			return "Usuario: "+getNome()+" "+getSobrenome()+" ("+getEmail()+")";
		} catch (Exception e) {
			return "Usuario com Erro!";
		}
	}

	public void setPerfil(String aboutMe, String age,
			String photo, String country, String city, String gender,
			String contactEmail) throws Exception {
		perfil = new Perfil(aboutMe, age, photo, country, city, gender, contactEmail);
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public String getNomeCompleto(){
		return this.nome + " " + this.sobrenome;
	}

	public String listFriends() throws Exception {
		List<String> nomesAmigos = new ArrayList<String>();
		for(int index = 0 ; index < grupos.size(); index ++){
			String nome = grupos.get(index).listContatos();
			if (!nome.equals("")) nomesAmigos.add(nome);
		}
		Collections.sort(nomesAmigos);
		return nomesAmigos.toString().substring(1, nomesAmigos.toString().length()-1).replace(", ", ",");
	}

	public String viewPerfil() throws Exception {
		return getNome() + " " + getSobrenome() + " - Profile: photo=" + perfil.getPhoto() +
		",aboutMe=" + perfil.getAboutMe() + ",country=" + perfil.getCountry();
	}

	/**
	 * Envia um convite para o usuario contato
	 * @param contato
	 * @param grupo
	 * @throws Exception
	 */
	public void enviaConvite(Usuario contato, String grupo) throws Exception{
		for(Convite c : convitesEnviados){
			if(c.getUsuario() == contato){
				throw new Exception("Você já enviou um convite para o usuário Fulano de Tal");
			}
		}

		ConviteEnviado convite = new ConviteEnviado(contato,grupo);
		convitesEnviados.add(convite);
	}

	public void recebeConvite(Usuario usuario, String message) {
		ConviteRecebido convite = new ConviteRecebido(usuario,message);
		convitesPendentes.add(convite);
	}

	/**
	 * Lista os convites pendentes recebidos pelo usuario
	 * @return
	 * @throws Exception 
	 */
	public String viewPendingFriendship() throws Exception {
		String pendingFriendship = "";
		if(convitesPendentes.size() == 0){
			pendingFriendship =  "Não há nenhuma solicitação de amizade pendente";
		}
		Iterator<ConviteRecebido> convites = convitesPendentes.iterator();
		while(convites.hasNext()){
			ConviteRecebido c = convites.next();
			pendingFriendship += c.getConvite();
		}
		return pendingFriendship;
	}

	/**
	 * Visualiza os convites de amizade enviados.
	 * @return
	 * @throws Exception
	 */
	public String viewSentFriendship() throws Exception {
		String sentFriendship = "";
		if(convitesEnviados.size() == 0){
			sentFriendship =  "Não há nenhuma solicitação de amizade pendente";
		}

		Iterator<ConviteEnviado> convites = convitesEnviados.iterator();
		while(convites.hasNext()){
			ConviteEnviado c = convites.next();
			sentFriendship += c.getConvite();
			if (convites.hasNext()) sentFriendship += ',';
		}
		return sentFriendship;
	}

	/**
	 * Recusa convite de amizade
	 * @param contato
	 */
	public void declineFriendshipRequest(Usuario contato) {
		for(int index = 0; index < convitesPendentes.size(); index++) {
			if(convitesPendentes.get(index).getUsuario() == contato){
				convitesPendentes.remove(index);
			}
		}
	}

	/**
	 * Remove convite
	 * @param contato
	 */
	public void removeConvite(Usuario contato) {
		for(int index = 0; index < convitesEnviados.size(); index++) {
			if(convitesEnviados.get(index).getUsuario() == contato){
				convitesEnviados.remove(index);
			}
		}
	}

	/**
	 * Aceita pedido de amizade
	 * @param contato
	 * @param grupo
	 */
	public void acceptFriendshipRequest(Usuario contato, String grupo) {
		Grupo g = getGrupo(grupo);
		for(int index = 0; index < convitesPendentes.size(); index++) {
			if(convitesPendentes.get(index).getUsuario() == contato){
				g.addContato(contato);
				convitesPendentes.remove(index);
				return;
			}
		}

	}

	/**
	 * Adiciona amigo
	 * @param contato
	 */
	public void addFriend(Usuario contato){
		for(int index = 0; index < convitesEnviados.size(); index++) {
			if(convitesEnviados.get(index).getUsuario() == contato){
				Grupo g = getGrupo(convitesEnviados.get(index).getGrupo());
				g.addContato(contato);
				convitesEnviados.remove(index);
			}
		}

	}

	/**
	 * Busca amigo no grupo
	 * @param friend
	 * @param grupo
	 * @return
	 * @throws Exception
	 */
	public String findGroupMember(Usuario friend, String grupo) throws Exception {
		Grupo g = getGrupo(grupo);
		if(g.getContatos().contains(friend))return "Nome=" + friend.getNome() + ",Sobrenome=" + friend.getSobrenome();
		return null;
	}

	/**
	 * Retorna amigo pelo seu login
	 * @param friend
	 * @return
	 * @throws Exception
	 */
	public String getFriend(String friend) throws Exception {
		for(int index = 0; index < grupos.size(); index++) {
			ArrayList<Usuario> amigosGrupo = grupos.get(index).getContatos();

			Iterator<Usuario> u = amigosGrupo.iterator();
			while(u.hasNext()){
				Usuario c = u.next();
				if(c.getNomeCompleto().equals(friend)){
					return "Nome=" + c.getNome() + ",Sobrenome=" + c.getSobrenome() +" " +
					c.getPerfil().viewAll();
				}
			}
		}
		return null;
	}

	/**
	 * Retorna amigo pelo objeto Usuario
	 * @param contato
	 * @return
	 * @throws Exception
	 */
	public String getFriend(Usuario contato) throws Exception {
		for(int index = 0; index < grupos.size(); index++) {
			ArrayList<Usuario> amigosGrupo = grupos.get(index).getContatos();
			for(int j = 0; j < amigosGrupo.size(); j++) {
				if(amigosGrupo.get(j) == contato){
					return "Nome=" + amigosGrupo.get(j).getNome() 
					+ ",Sobrenome="+ amigosGrupo.get(j).getSobrenome() + " " +
					amigosGrupo.get(j).getPerfil().viewAll();
				}
			}
		}
		return null;
	}

	/**
	 * Remove amigo
	 * @param contato
	 * @throws Exception
	 */
	public void removeFriend(Usuario contato) throws Exception {
		if(getFriend(contato) == null){
			throw new Exception ("Amigo não encontrado em nenhum grupo");
		}

		for(int index = 0; index < grupos.size(); index++) {
			ArrayList<Usuario> amigosGrupo = grupos.get(index).getContatos();
			for(int j = 0; j < amigosGrupo.size(); j++) {
				if(amigosGrupo.get(j) == contato){
					grupos.get(index).removeContato(contato);
					return;
				}
			}
		}

	}

	/**
	 * Lista membros do grupo
	 * @param grupo
	 * @return
	 * @throws Exception
	 */
	public String listGroupMembers(String grupo) throws Exception {
		Grupo g = getGrupo(grupo);
		return g.listContatos();
	}

	/**
	 * Adiciona contato no grupo
	 * @param contato
	 * @param grupo
	 * @throws Exception
	 */
	public void addGroupMember(Usuario contato, String grupo) throws Exception {
		Grupo g = getGrupo(grupo);
		if(g.getContatos().contains(contato)){
			throw new Exception ("Contato já existente no grupo trabalho");
		}
		for(int index = 0; index < grupos.size(); index++) {
			ArrayList<Usuario> amigosGrupo = grupos.get(index).getContatos();
			if(!grupos.get(index).getNome().toLowerCase().equals(grupo)){
				for(int j = 0; j < amigosGrupo.size(); j++) {
					if(amigosGrupo.get(j).equals(contato)){
						g.getContatos().add(contato);
						grupos.get(index).removeContato(amigosGrupo.get(j));
					}
				}
			}
		}
	}

	/**
	 * Remove contato do grupo
	 * @param contato
	 * @param grupo
	 * @throws Exception
	 */
	public void removeGroupMember(Usuario contato,String grupo) throws Exception {
		Grupo g = getGrupo(grupo);
		if(!g.getContatos().contains(contato)){
			throw new Exception("Contato não existente no grupo trabalho");
		}
		g.getContatos().remove(contato);
		conhecidos.addContato(contato);

	}

//	public List<Usuario> getGruposFamiliaEMelhoresAmigos(){
//		List<Usuario> familiaAmigos = new ArrayList<Usuario>();
//		familiaAmigos.addAll(familia.getContatos());
//		familiaAmigos.addAll(melhoresAmigos.getContatos());
//		return familiaAmigos;
//	}

	public String getRecommendFriends() throws Exception {
//		String saida = "";
//		recommend();
//		if(recommendFriends.size() > 0){
//			Iterator<Usuario> itRecommends = recommendFriends.iterator();
//			while(itRecommends.hasNext()){
//				Usuario u = itRecommends.next();
//				saida += u.getNomeCompleto() + ",";
//			}
//			saida = saida.substring(0, saida.length() - 1);
//		}
//		return saida;
		
		Iterator<Grupo> iteradorDosMeusGrupos = this.getGrupos().iterator();
		while (iteradorDosMeusGrupos.hasNext()) {
			Grupo meuGrupo = iteradorDosMeusGrupos.next();
			if (meuGrupo.getPeso() >= this.getSimilaridade()) {
				procurarAmigoParaRecomendar(meuGrupo);
			}
		}
		return recommendFriends.toString();
	}

	private void procurarAmigoParaRecomendar(Grupo meuGrupo) throws Exception {
		Iterator<Usuario> procuraAmigos = meuGrupo.getContatos().iterator();
		while (procuraAmigos.hasNext()) {
			Usuario a = procuraAmigos.next();
			Iterator<Grupo> iteraGruposDoAmigo = a.getGrupos().iterator();
			while (iteraGruposDoAmigo.hasNext()) {
				Grupo grupoDoAmigo = iteraGruposDoAmigo.next();
				if (grupoDoAmigo.getPeso() >= this.getSimilaridade()) {
					recomendaGrupoDeAmigos(grupoDoAmigo);
				}
			}
		}
	}

	private void recomendaGrupoDeAmigos(Grupo grupoDoAmigo) throws Exception {
		Iterator<Usuario> iteraPossiveisAmigos = grupoDoAmigo.getContatos().iterator();
		while(iteraPossiveisAmigos.hasNext()) {
			Usuario possivelAmigo = iteraPossiveisAmigos.next();
			if(!recommendFriends.contains(possivelAmigo) && !possivelAmigo.getEmail().equals(this.email)
					&& !getFriends().contains(possivelAmigo)){
				recommendFriends.add(possivelAmigo);
			}
		}
	}

//	/**
//	 * Metodo auxiliar para recomendar amigos
//	 * @throws Exception
//	 */
//	private void recommend() throws Exception{
//		ArrayList<Usuario> amigos = new ArrayList<Usuario>();
//		amigos.addAll(melhoresAmigos.getContatos());
//		amigos.addAll(familia.getContatos());
//		for(Usuario user : getFriends()){
//			getSimilarityFriends(user);
//		}
//	}

//	private void getSimilarityFriends(Usuario usuario) throws Exception{
//		for(Usuario contato : usuario.getGruposFamiliaEMelhoresAmigos()){
//			double similaridadeFriend = calcSimilaridade(contato.getPerfil().getListPreferences());
//			if(similaridadeFriend >= similaridade){
//				if(!recommendFriends.contains(contato) && !contato.getEmail().equals(this.email)
//						&& !getFriends().contains(contato)){
//					recommendFriends.add(contato);
//				}
//			}
//		}
//	}

	public void addUserPreference(String preference) {
		perfil.setPreferences(preference);
	}

//	private double calcSimilaridade(ArrayList<String> preferenciasContato){
//		ArrayList<String> minhasPreferencias = this.perfil.getListPreferences();
//		double preferenciasComuns = 0.0 , uniao = 0.0;
//		double similaridade = 0.0;
//		for(int index = 0; index < minhasPreferencias.size(); index ++){
//			if(preferenciasContato.contains(minhasPreferencias.get(index))){
//				preferenciasComuns++;
//			}
//		}
//		uniao = (minhasPreferencias.size() + preferenciasContato.size());
//
//		if(preferenciasComuns == 0.0 && uniao == 0.0){
//			return 0.40;
//		}
//
//		similaridade = preferenciasComuns / (minhasPreferencias.size() + preferenciasContato.size());
//
//		return similaridade;
//	}
	
	public boolean possuiGrupo(String nomeGrupo) throws Exception {
		Grupo grupo = new Grupo(nomeGrupo,0);
		return grupos.contains(grupo);
	}

	/**
	 * Retorna o grupo a apartir a string correspondente ao seu nome
	 * @param nome o nome do grupo
	 * @return grupo o grupo pesquisado ou null caso o grupo nao exista
	 */
	 public Grupo getGrupo(String nome){
		 Iterator<Grupo> grupo = grupos.iterator();
		 while(grupo.hasNext()){
			 Grupo g = grupo.next();
			 if(g.getNome().equals(nome)){
				 return g;
			 }
		 } return null;
	 }

	 public boolean containsContato(Usuario contato) {
		 for (int index = 0; index < grupos.size(); index++) {
			 Grupo g = grupos.get(index);
			 if(g.getContatos().contains(contato)){
				 return true;
			 }
		 }
		 return false;
	 }

	 public ArrayList<Grupo> getGrupos(){
		 return this.grupos;
	 }


	 public List<Usuario> getFriends(){
		 List<Usuario> friends = new ArrayList<Usuario>();

		 for(Grupo g : grupos){
			 friends.addAll(g.getContatos());
		 }

		 return friends;

	 }

	 @Override
	 public int compareTo(Usuario u) {
		 return (getNome()+" "+getSobrenome()).compareTo(u.getNome()+" "+u.getSobrenome());
	 }

	 public String checkProfile(String visibility) {
		 return getPerfil().getVisibilidade(visibility);
	 }

	 public String viewProfile() {
		 return getPerfil().getVisibilidade("ALL");
	 }

	 public void removeUserPreference(String preference) throws Exception {
		 getPerfil().removePreference(preference);
	 }

	 public String listUserPreference() {
		 return getPerfil().getPreferences();
	 }


}
