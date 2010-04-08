package beans;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String name;
	private String description;
	private List<UserAccount> users;
	
	public Group(String nome) {
		this.name = nome;
		users = new ArrayList<UserAccount>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String descricao) {
		this.description = descricao;
	}
	public List<UserAccount> getUsers() {
		return users;
	}
	public void setUsers(List<UserAccount> usuarios) {
		this.users = usuarios;
	}
	
	@Override
	public String toString() {
		String string = "";
		for(UserAccount usuario : getUsers()) {
			string += usuario.getName() + " " + usuario.getSurname() + ",";
		}
		if (string.length() > 0) string = string.substring(0, string.length() - 1);
		return string;
	}
}
