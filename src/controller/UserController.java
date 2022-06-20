package controller;

import java.util.ArrayList;
import model.UserDTO;

public class UserController {
	private int nextId;
	private ArrayList<UserDTO> list;
	
	public UserController() {
		nextId=1;
		list=new ArrayList<>();
	}
	
	public void insert(UserDTO u) {
		u.setId(nextId++);
		list.add(u);
	}
	
	public boolean validateUsername(String username) {
		for(UserDTO u:list) {
			if(u.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}
	
	public UserDTO auth(String username,String password) {
		for(UserDTO u:list) {
			if(u.getUsername().equalsIgnoreCase(username)&&u.getPassword().equals(password)) {
				return new UserDTO(u);
			}
		}
		
		return null;
	}
	
	public void update(UserDTO u) {
		list.set(list.indexOf(u), u);
	}
	
	public void delete(int id) {
		list.remove(new UserDTO(id));
	}
	public UserDTO selectOne(int id) {
		UserDTO u=new UserDTO(id);
		if(list.contains(u)) {
			return new UserDTO(list.get(list.indexOf(u)));
		} else {
			return null;
		}
	}
}
