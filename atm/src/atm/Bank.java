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

	// ATM 프로젝트
	// 회원가입/탈퇴
	// 계좌신청/철회 (1인 3계좌까지)
	// 로그인

	// Banking 관련 메소드

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
		System.out.println("1. 회원가입");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 계좌신청");
		System.out.println("4. 계좌철회");
		System.out.println("5. 로그인");
		System.out.println("6. 로그아웃");
		System.out.println("7. 종료");
	}

	public int selectMenu() {
		System.out.println("메뉴 선택 : ");
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
			System.out.println("회원가입완료");
			this.userList.add(user);
		} else {
			System.out.println("중복된 아이디 입니다.");
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
			System.out.println("회원탈퇴완료");
			this.userList.remove(index);
		} else
			System.out.println("일치하지 않는 정보입니다.");
	}

	private void newAcc() {
		this.accountList = this.userList.get(log).getAccs();
		if (this.accountList.size() < Account.LIMIT) {
			System.out.println("계좌번호 : ");
			String accNum = this.scan.next();
			Account account = new Account(this.userList.get(log).getId(), accNum, 0);
			this.accountList.add(account);
		} else {
			System.out.println("계좌는 최대 3개까지만 개설할 수 있습니다.");
		}
	}

	private void deleteAcc() {
		this.accountList = this.userList.get(log).getAccs();
		if (this.accountList.size() == 0) {
			System.out.println("개설된 계좌가 없습니다.");
			return;
		}

		for (int i = 0; i < this.accountList.size(); i++) {
			System.out.printf("%d) %s / %d원\n", i + 1, this.accountList.get(i).getAccNum(),
					this.accountList.get(i).getMoney());
		}
		System.out.println("삭제할 계좌번호의 index를 누르시오");
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
			System.out.println("로그인성공");
			log = index;
		}
	}

	private void logOut() {
		System.out.println("로그아웃합니다.");
		this.log = -1;
	}

	private void printAll() {
		for (int i = 0; i < this.userList.size(); i++) {
			User user = userList.get(i);
			System.out.printf("%s/%s/%s\n", user.getId(), user.getPassword(), user.getName());
			ArrayList<Account> accs = user.getAccs();
			for (int j = 0; j < accs.size(); j++) {
				System.out.printf("%s님의 %d번째 계좌\n 계좌번호 : %s / 잔액 : %d원\n", user.getId(), j + 1, accs.get(j).getAccNum(),
						accs.get(j).getMoney());
			}
		}
	}

	private void printLogInUser() {
		System.out.printf("%s님 환영합니다!!\n", this.userList.get(log).getId());
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
