package atm;

import java.util.ArrayList;

public class AccountManager {

	private static ArrayList<Account> list = new ArrayList<Account>();
	
	public ArrayList<Account> getList(){
		return this.list;
	}
	
	// Account 에 대한

	// Create
	public void addAccount(Account account) {
		this.list.add(account);
	}
	// Read
	public Account getAccount(int index) {
		Account account = this.list.get(index);
		int acc = account.getAccount();
		//사본
		Account reqAcc = new Account(acc);
		return reqAcc;
	}
	public Account getAccountByNum(int account) {
		int index = -1;
		//??
		return getAccount(index);
	}
	
	// Update
	public void setAccount(int index, Account account) {
		this.list.set(index, account);
	}
	
	// Delete
	public void deleteAccount(int index) {
		this.list.remove(index);
	}
	public void deleteAccountByNum(int account) {
		this.list.remove(account);
	}
}
