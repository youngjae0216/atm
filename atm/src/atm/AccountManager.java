package atm;

import java.util.ArrayList;
import java.util.Random;

public class AccountManager {

	private static ArrayList<Account> list = new ArrayList<Account>();

	// Create
	public Account createAccount(Account account) {
		String accountNum = accNumGenerator();
		account.setAccNum(accountNum);
		list.add(account);
		return account;
	}

	// Read
	public Account getAccount(int index) {
		Account account = list.get(index);

		Account reqObj = new Account(account.getUserId(), account.getAccNum(), account.getMoney());
		;
		return reqObj;
	}

	public Account getAccountByNum(String accountNum) {
		Account account = null;

		for (Account object : list) {
			if (object.getAccNum().equals(accountNum))
				account = object;
		}

		return account;
	}

	public void printAll() {
		for (Account account : this.list) {
			int n = 1;
			System.out.printf("%d¹ø °èÁÂ : %s / %d¿ø\n", n++, account.getAccNum(), account.getMoney());
		}
	}

	// Update
	public void setAccount(int index, Account account) {
		list.set(index, account);
	}

	// Delete
	public void deleteAccount(int index) {
		list.remove(index);
	}

	private String accNumGenerator() {
		// ####-####
		String num = "";

		Random ran = new Random();
		while (true) {
			int first = ran.nextInt(8999) + 1000;
			int second = ran.nextInt(8999) + 1000;

			num = first + "-" + second;

			Account account = getAccountByNum(num);

			if (account == null)
				break;
		}

		return num;
	}

}