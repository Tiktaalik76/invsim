package stocksimulation;

import javax.swing.JLabel;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		//생성
		new Display();
		Save Saveth = new Save();
		//Chart Chartth = new Chart();
		
		//스레드 우선순위
		Saveth.setPriority(10);
		//Chartth.setPriority(1);
		
		//스레드 실행
		Saveth.start();
		//Thread.sleep(2000);
		//Chartth.start();
	}
}
