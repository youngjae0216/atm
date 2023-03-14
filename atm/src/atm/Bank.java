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
		for(int i=0; i<this.userList.size();i++) {
			if(this.userList.get(i).getId().equals(id)) {
				index = i;
			}
		}
		if(index == -1) {
			System.out.println("���ԿϷ�");
			this.userList.add(user);
		}
	}

	private void leave() {
		System.out.println("id : ");
		String id = scan.next();
		System.out.println("pw : ");
		String pw = scan.next();
		int index = -1;
		for(int i=0; i<this.userList.size();i++) {
			if(this.userList.get(i).getId().equals(id) && this.userList.get(i).getPassword().equals(pw))
				index = i;
		}
		if(index != -1) {
			System.out.println("Ż��Ϸ�");
			this.userList.remove(index);
		}
		else
			System.out.println("��ġ���� �ʴ� ����");
	}

	private void newAcc() {
		
	}

	private void deleteAcc() {

	}

	private void logIn() {
		System.out.println("id : ");
		String id = scan.next();
		System.out.println("pw : ");
		String pw = scan.next();
		int index = -1;
		for(int i=0; i<this.userList.size();i++) {
			if(this.userList.get(i).getId().equals(id) && this.userList.get(i).getPassword().equals(pw))
				index = i;
		}	
	}
	private void logOut() {
		System.out.println("�α׾ƿ��մϴ�.");
		this.log = -1;
	}
	
	private void printAll() {
		for(int i=0;i<this.userList.size();i++) {
			User user = userList.get(i);
			System.out.printf("%s/%s/%s\n",user.getId(),user.getPassword(),user.getName());
		}
	}
	
	public void run() {
		while (true) {
			printAll();
			printMenu();
			int select = selectMenu();
			if (select == JOIN)
				join();
			else if (select == LEAVE)
				leave();
			else if (select == NEW_ACC)
				newAcc();
			else if (select == DELETE_ACC)
				deleteAcc();
			else if (select == LOG_IN)
				logIn();
			else if (select == LOG_OUT)
				logOut();
			else if (select == QUIT)
				break;
		}
	}
}
