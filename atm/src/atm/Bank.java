package atm;

import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
	private final int JOIN = 1;
	private final int LEAVE = 2;
	private final int NEW_ACC = 3;
	private final int DELETE_ACC = 4;
	private final int LOG_IN = 5;
	private final int LOG_OUT = 6;
	private final int QUIT = 7;

	private UserManager um;
	private AccountManager am;

	private String name;
	private Scanner scan;
	private int log;

	private ArrayList<User> userList;
	private ArrayList<Account> accountList;

	// ATM ������Ʈ
	// ȸ������/Ż��
	// ���½�û/öȸ (1�� 3���±���)
	// �α���

	// Banking ���� �޼ҵ�

	public Bank(String name) {
		this.name = name;
		init();
	}

	private void init() {
		this.um = new UserManager();
		this.am = new AccountManager();
		this.scan = new Scanner(System.in);
		this.userList = um.getList();
		this.accountList = am.getList();
		this.log = -1;
	}

	private void printMenu() {
		System.out.println("1. ȸ������");
		System.out.println("2. ȸ��Ż��");
		System.out.println("3. ���½�û");
		System.out.println("4. ����öȸ");
		System.out.println("5. �α���");
		System.out.println("6. �α׾ƿ�");
		System.out.println("7. ����");
	}

	public int selectMenu() {
		System.out.println("�޴� ���� : ");
		return this.scan.nextInt();
	}

	private void join() {
		System.out.println("id : ");
		String id = scan.next();
		System.out.println("pw : ");
		String pw = scan.next();
		System.out.println("name : ");
		String name = scan.next();
		User user = new User(id, pw, name);
		int index = -1;
		for (int i = 0; i < this.userList.size(); i++) {
			if (this.userList.get(i).getId().equals(id)) {
				index = i;
			}
		}
		if (index == -1) {
			System.out.println("ȸ�����ԿϷ�");
			this.userList.add(user);
		} else {
			System.out.println("�ߺ��� ���̵� �Դϴ�.");
		}
	}

	private void leave() {
		System.out.println("id : ");
		String id = scan.next();
		System.out.println("pw : ");
		String pw = scan.next();
		int index = -1;
		for (int i = 0; i < this.userList.size(); i++) {
			if (this.userList.get(i).getId().equals(id) && this.userList.get(i).getPassword().equals(pw))
				index = i;
		}
		if (index != -1) {
			System.out.println("ȸ��Ż��Ϸ�");
			this.userList.remove(index);
		} else
			System.out.println("��ġ���� �ʴ� �����Դϴ�.");
	}

	private void newAcc() {
		this.accountList = this.userList.get(log).getAccs();
		if (this.accountList.size() < Account.LIMIT) {
			System.out.println("���¹�ȣ : ");
			String accNum = this.scan.next();
			Account account = new Account(this.userList.get(log).getId(), accNum, 0);
			this.accountList.add(account);
		} else {
			System.out.println("���´� �ִ� 3�������� ������ �� �ֽ��ϴ�.");
		}
	}

	private void deleteAcc() {
		this.accountList = this.userList.get(log).getAccs();
		if (this.accountList.size() == 0) {
			System.out.println("������ ���°� �����ϴ�.");
			return;
		}

		for (int i = 0; i < this.accountList.size(); i++) {
			System.out.printf("%d) %s / %d��\n", i + 1, this.accountList.get(i).getAccNum(),
					this.accountList.get(i).getMoney());
		}
		System.out.println("������ ���¹�ȣ�� index�� �����ÿ�");
		int select = this.scan.nextInt();
		this.accountList.remove(select);
	}

	private void logIn() {
		System.out.println("id : ");
		String id = scan.next();
		System.out.println("pw : ");
		String pw = scan.next();
		int index = -1;
		for (int i = 0; i < this.userList.size(); i++) {
			if (this.userList.get(i).getId().equals(id) && this.userList.get(i).getPassword().equals(pw))
				index = i;
		}
		if (index != -1) {
			System.out.println("�α��μ���");
			log = index;
		}
	}

	private void logOut() {
		System.out.println("�α׾ƿ��մϴ�.");
		this.log = -1;
	}

	private void printAll() {
		for (int i = 0; i < this.userList.size(); i++) {
			User user = userList.get(i);
			System.out.printf("%s/%s/%s\n", user.getId(), user.getPassword(), user.getName());
			ArrayList<Account> accs = user.getAccs();
			for (int j = 0; j < accs.size(); j++) {
				System.out.printf("%s���� %d��° ����\n ���¹�ȣ : %s / �ܾ� : %d��\n", user.getId(), j + 1, accs.get(j).getAccNum(),
						accs.get(j).getMoney());
			}
		}
	}

	private void printLogInUser() {
		System.out.printf("%s�� ȯ���մϴ�!!\n", this.userList.get(log).getId());
	}

	public void run() {
		while (true) {
			printAll();
			printMenu();
			if (this.log != -1) {
				printLogInUser();
			}
			int select = selectMenu();
			if (select == JOIN)
				join();
			else if (select == LEAVE)
				leave();
			else if (select == NEW_ACC && this.log != -1)
				newAcc();
			else if (select == DELETE_ACC && this.log != -1)
				deleteAcc();
			else if (select == LOG_IN && this.log == -1)
				logIn();
			else if (select == LOG_OUT && this.log != -1)
				logOut();
			else if (select == QUIT)
				break;
		}
	}
}
