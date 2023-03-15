package atm;

import java.util.Scanner;

public class Bank {

	private Scanner scan;

	private String brandName;

	private UserManager um;
	private AccountManager am;
	private int log;

	public Bank(String brandName) {
		this.brandName = brandName;
		this.scan = new Scanner(System.in);
		this.um = new UserManager();
		this.am = new AccountManager();
		this.log = -1;
	}

	private void printMainMenu() {
		if(this.log != -1 )
			System.out.printf("%s�� ȯ���մϴ�!!\n",this.um.getUser(log).getName());
		System.out.println("=== " + this.brandName + " === ");
		System.out.println("1. ȸ������");
		System.out.println("2. ȸ��Ż��");
		System.out.println("3. ���½�û");
		System.out.println("4. ����öȸ");
		System.out.println("5. �α���");
		System.out.println("0. ����");
	}

	private int inputNumber() {
		int number = -1;

		System.out.print("input : ");
		String input = this.scan.next();

		try {
			number = Integer.parseInt(input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return number;
	}
	
	private void printAcc() {
		for(int i=0; i<this.um.getUserListSize();i++) {
			for(int j=0;j<this.um.getUser(i).getAccountSize();j++) {
				Account temp = this.um.getUser(i).getAccount(j);
				System.out.printf("%s���� %d��° ���� : ",this.um.getUser(i).getId(),j+1);
				temp.printAccount();
			}
		}
	}
	
	public void run() {
		while (true) {
			this.um.printAll();
			printAcc();
			printMainMenu();
			int sel = inputNumber();

			if (sel == 1)
				joinUser();
			else if (sel == 2 && log != -1)
				leaveUser(); // �α��� ���ִ� ���� Ż��
			else if (sel == 3 && log != -1)
				createAccount();
			else if (sel == 4 && log != -1)
				deleteAccount();
			else if (sel == 5 && log == -1)
				login();
			else if (sel == 6 && log != -1)
				logout();
			else if (sel == 0)
				break;
		}
		System.out.println("�ý����� ����Ǿ����ϴ�.");
	}

	private void logout() {
		// TODO Auto-generated method stub
		
	}

	private void joinUser() {
		System.out.print("id : ");
		String id = this.scan.next();
		System.out.print("password : ");
		String password = this.scan.next();
		System.out.print("name : ");
		String name = this.scan.next();

		User user = new User(id, password, name);
		if (this.um.addUser(user) != null) {
			System.out.println("ȸ������ ����");
		} else {
			System.out.println("�ߺ��� ���̵� �����մϴ�.");
		}
	}

	private void leaveUser() {
		System.out.println("ȸ��Ż�� �Ϸ�");
		this.um.deleteUser(this.log);
		this.log = -1;
	}

	private void createAccount() {
		// ������ ��ȯ ����
		User user = this.um.getUser(this.log);

		if (user.getAccountSize() < Account.LIMIT) {
			Account account = this.am.createAccount(new Account(user.getId()));
			this.um.setUser(user, account);
			System.out.println("���� ���� �Ϸ�!");
		}
		else {
			System.out.println("���´� �� ������ �ִ� 3�������� ���������մϴ�.");
		}
	}

	private void deleteAccount() {
		User user = this.um.getUser(this.log);
		if (user.getAccountSize() > 0) {
			for (int i = 0; i < user.getAccountSize(); i++) {
				System.out.printf("%d) %s %d��\n", i + 1, user.getAccount(i).getAccNum(), user.getAccount(i).getMoney());
			}
			System.out.println("öȸ�Ͻ� ���� ����");

			int index = inputNumber() - 1;
			if (0 <= index && index <= user.getAccountSize() - 1) {
				Account account = user.getAccount(index);
				this.am.deleteAccount(index);
				this.um.setUser(user, account);
			}
			else
				System.out.printf("0~%d������ ���ڸ� �Է��ϼ���\n",user.getAccountSize()-1);
		} else {
			System.out.println("���°� �������� �ʽ��ϴ�.");
		}
	}

	private void login() {
		System.out.print("id : ");
		String id = this.scan.next();
		System.out.print("password : ");
		String password = this.scan.next();
		User user = this.um.getUserById(id);
		if(this.um.getUserById(id) != null) {
			if(user.getPassword().equals(password)) {
				this.log = this.um.indexOfById(id);
			}
		}
		else {
			System.out.println("�������� �ʴ� ���̵��Դϴ�.");
		}
		
	}

}