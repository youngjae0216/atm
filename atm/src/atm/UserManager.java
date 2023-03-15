package atm;

import java.util.ArrayList;

public class UserManager {
	
	private static ArrayList<User> list = new ArrayList<User>();
	
	
	// Create 
	public User addUser(User user) {
		// ∞À¡ı »ƒ add 
		User check = getUserById(user.getId());
		if(check == null) {
			list.add(user);
			return user;
		}
		return null;
	}
	
	// Read 
	public User getUser(int index) {
		User user = this.list.get(index);
		
		User reqObj = new User(user.getId(), user.getPassword(), user.getName());
		return reqObj;
	}
	
	public User getUserById(String id) {
		User user = null;
		
		int index = indexOfById(id);
		if(index != -1)
			user = getUser(index);
		
		return user;
	}
	
	public int indexOfById(String id) {
		int index = -1;
		for(User user : this.list) {
			if(user.getId().equals(id))
				index = this.list.indexOf(user);
		}
		return index;
	}
	public void printAll() {
		for(User user : this.list) {
			System.out.printf("id:%s/pw:%s/name:%s\n",user.getId(),user.getPassword(),user.getName());
		}
		
	}
	public int getUserListSize() {
		return this.list.size();
	}
	
	// Update
	public void setUser(int index, User user) {
		this.list.set(index, user);
	}
	
	public void setUser(User user, Account account) {
		int index = indexOfById(user.getId());
		
		list.get(index).addAccount(account);
	}
	// Delete 
	public void deleteUser(int index) {
		this.list.remove(index);
	}
	
	public void deleteUserById(String id) {
		int index = indexOfById(id);
		this.list.remove(index);
	}
}