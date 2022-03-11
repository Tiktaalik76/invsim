package board;

public class Main {

	public static void main(String[] args) {

		// 스레드 생성
		View viewThread = new View();
		Save saveThread = new Save();

		// 스레드 시작
		saveThread.start();
		viewThread.start();
	}
}
