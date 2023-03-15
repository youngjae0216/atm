package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {

	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;

	private File file;
	private String fileName;
	private UserManager um;
	private AccountManager am;

	public FileManager() {
		this.um = new UserManager();
		this.am = new AccountManager();
		this.fileName = "atm.txt";
		this.file = new File(fileName);
	}

	public void save() {
		String data = "";

		for (int i = 0; i < this.um.getUserListSize(); i++) {
			User user = this.um.getUser(i);
			data += user.getId() + "/" + user.getPassword() + "/" + user.getName();
			if (user.getAccountSize() > 0) {
				data += ",";
			}
			for (int j = 0; j < user.getAccountSize(); j++) {
				Account account = user.getAccount(j);
				data += account.getAccNum() + "/" + account.getMoney();
				if(j<user.getAccountSize()-1) {
					data+=",";
				}
			}
			if (i < this.um.getUserListSize() - 1)
				data += "\n";
		}
		System.out.println(data);
		try {
			this.fileWriter = new FileWriter(fileName);
			this.fileWriter.write(data);

			this.fileWriter.close();
			System.err.println("저장완료!");
		} catch (Exception e) {
			System.err.println("저장실패!");
		}
	}

	public void load() {
		try {
			this.fileReader = new FileReader(fileName);
			this.bufferedReader = new BufferedReader(fileReader);
			
			if(file.exists()) {
				while(bufferedReader.ready()) {
					
					
				}
			}
			
			System.err.println("로드성공!");
		} catch (Exception e) {
			System.err.println("로드실패!");
		}
	}

}
