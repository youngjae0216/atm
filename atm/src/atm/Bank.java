package atm;

import java.util.Scanner;

public class Bank {

	private Scanner scan;

	private String brandName;

	private UserManager um;
	private AccountManager am;
	private FileManager fm;
	private int log;

	public Bank(String brandName) {
		this.brandName = brandName;
		this.scan = new Scanner(System.in);
		this.um = new UserManager();
		this.am = new AccountManager();
		this.fm = new FileManager();
		this.log = -1;
	}

	private void printMainMenu() {
		if (this.log != -1)
			System.out.printf("%s님 환영합니다!!\n", this.um.getUser(log).getName());
		System.out.println("=== " + this.brandName + " === ");
		System.out.println("1. 회원가입");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 계좌신청");
		System.out.println("4. 계좌철회");
		System.out.println("5. 로그인");
		System.out.println("6. 로그아웃");
		System.out.println("7. 뱅킹기능");
		System.out.println("8. 파일기능");
		System.out.println("0. 종료");
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
		for (int i = 0; i < this.um.getUserListSize(); i++) {
			for (int j = 0; j < this.um.getUser(i).getAccountSize(); j++) {
				Account temp = this.um.getUser(i).getAccount(j);
				System.out.printf("%s님의 %d번째 계좌 : ", this.um.getUser(i).getId(), j + 1);
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
				leaveUser(); // 로그인 되있는 계정 탈퇴
			else if (sel == 3 && log != -1)
				createAccount();
			else if (sel == 4 && log != -1)
				deleteAccount();
			else if (sel == 5 && log == -1)
				login();
			else if (sel == 6 && log != -1)
				logout();
			else if (sel == 7 && log != -1)
				banking();
			else if (sel == 8)
				file();
			else if (sel == 0)
				break;
		}
		System.out.println("시스템이 종료되었습니다.");
	}

	private void file() {
		System.out.println("1. 저장하기");
		System.out.println("2. 로드하기");
		int sel = inputNumber();
		if (sel == 1)
			this.fm.save();
		else if (sel == 2) {
			this.fm.load();
		}
		
	}

	private void banking() {
		if (this.um.getUser(log).getAccountSize() == 0) {
			System.out.println("계좌를 먼저 신청하세요.");
			return;
		}
		while (true) {
			printBankingMenu();
			int sel = inputNumber();
			if (sel == 1)
				deposit();
			else if (sel == 2)
				withdraw();
			else if (sel == 3)
				accountInquiry();
			else if (sel == 4)
				transfer();
			else if (sel == 0)
				break;
		}

	}

	private void transfer() {
		User user = this.um.getUser(log);
		System.out.println("이용하실 계좌를 선택하세요.");
		for (int i = 0; i < user.getAccountSize(); i++) {
			System.out.printf("%d) ", i + 1);
			user.getAccount(i).printAccount();
		}
		int index = this.scan.nextInt() - 1;
		if (0 <= index && index < user.getAccountSize()) {
			Account myAcc = user.getAccount(index);
			System.out.println("이체하실 상대의 계좌번호 : ");
			String accNum = this.scan.next();
			Account yourAcc = this.am.getAccountByNum(accNum);
			if (yourAcc != null) {
				System.out.println("이체하실 금액 : ");
				int money = this.scan.nextInt();
				if (myAcc.getMoney() >= money) {
					myAcc.setMoney(myAcc.getMoney() - money);
					yourAcc.setMoney(yourAcc.getMoney() + money);
					System.out.println("이체완료!");
				} else {
					System.out.println("잔액부족!");
				}
			} else {
				System.out.println("존재하지 않는 계좌번호입니다.");
			}

		}
	}

	private void accountInquiry() {
		User user = this.um.getUser(log);
		System.out.printf("%s님의 계좌목록\n", user.getId());
		for (int i = 0; i < user.getAccountSize(); i++) {
			user.getAccount(i).printAccount();
		}
	}

	private void withdraw() {
		User user = this.um.getUser(log);
		System.out.println("이용하실 계좌를 선택하세요.");
		for (int i = 0; i < user.getAccountSize(); i++) {
			System.out.printf("%d) ", i + 1);
			user.getAccount(i).printAccount();
		}
		int index = this.scan.nextInt() - 1;
		if (0 <= index && index < user.getAccountSize()) {
			Account account = user.getAccount(index);
			System.out.println("출금하실 금액 : ");
			int getMoney = this.scan.nextInt();
			if (account.getMoney() >= getMoney) {
				account.setMoney(account.getMoney() - getMoney);
				System.out.println("출금완료!");
			} else {
				System.out.println("잔액부족!");
			}
		}

	}

	private void deposit() {
		User user = this.um.getUser(log);
		System.out.println("이용하실 계좌를 선택하세요.");
		for (int i = 0; i < user.getAccountSize(); i++) {
			System.out.printf("%d) ", i + 1);
			user.getAccount(i).printAccount();
		}
		int index = this.scan.nextInt() - 1;
		if (0 <= index && index < user.getAccountSize()) {
			Account account = user.getAccount(index);
			System.out.println("입금하실 금액 : ");
			account.setMoney(account.getMoney() + this.scan.nextInt());
			System.out.println("입금완료!");
		}

	}

	private void printBankingMenu() {
		System.out.println("1. 입금하기");
		System.out.println("2. 출금하기");
		System.out.println("3. 계좌조회");
		System.out.println("4. 이체하기");
		System.out.println("0. 뒤로가기");

	}

	private void logout() {
		System.out.println("로그아웃합니다.");
		this.log = -1;
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
			System.out.println("회원가입 성공");
		} else {
			System.out.println("중복된 아이디가 존재합니다.");
		}
	}

	private void leaveUser() {
		System.out.println("회원탈퇴 완료");
		this.um.deleteUser(this.log);
		this.log = -1;
	}

	private void createAccount() {
		// 복제본 반환 받음
		User user = this.um.getUser(this.log);

		System.out.println(user.getAccountSize());
		if (user.getAccountSize() < Account.LIMIT) {
			Account account = this.am.createAccount(new Account(user.getId()));
			this.um.setUser(user, account);
			System.out.println("계좌 개설 완료!");
		} else {
			System.out.println("계좌는 한 계정당 최대 3개까지만 개설가능합니다.");
		}
	}

	private void deleteAccount() {
		User user = this.um.getUser(this.log);
		if (user.getAccountSize() > 0) {
			for (int i = 0; i < user.getAccountSize(); i++) {
				System.out.printf("%d) %s %d원\n", i + 1, user.getAccount(i).getAccNum(), user.getAccount(i).getMoney());
			}
			System.out.println("철회하실 계좌 선택");

			int index = inputNumber() - 1;
			if (0 <= index && index <= user.getAccountSize() - 1) {
				user.deleteAccount(index);
				this.um.setUser(this.log, user);
				System.out.println("계좌가 삭제되었습니다.");
			} else
				System.out.printf("1~%d사이의 숫자를 입력하세요\n", user.getAccountSize());
		} else {
			System.out.println("계좌가 존재하지 않습니다.");
		}
	}

	private void login() {
		System.out.print("id : ");
		String id = this.scan.next();
		System.out.print("password : ");
		String password = this.scan.next();
		User user = this.um.getUserById(id);
		if (this.um.getUserById(id) != null) {
			if (user.getPassword().equals(password)) {
				this.log = this.um.indexOfById(id);
			}
		} else {
			System.out.println("존재하지 않는 아이디입니다.");
		}

	}

}