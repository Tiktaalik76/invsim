package board;

public class Main {

	public static void main(String[] args) {

		// ������ ����
		View viewThread = new View();
		Save saveThread = new Save();
		
		// ������ �켱����
		//saveThread.setPriority(10);
		//viewThread.setPriority(1);
		
		// ������ ����
		//saveThread.start();
		viewThread.start();
	}
}
