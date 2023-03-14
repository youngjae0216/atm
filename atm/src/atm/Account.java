package atm;

public class Account {
	
	public static int LIMIT = 3;
	
	private int account;

	public Account(int account) {
		this.account = account;
	}

	public int getAccount() {
		return this.account;
	}
}
