package stocksimulation;

import javax.swing.JLabel;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		//����
		new Display();
		Save Saveth = new Save();
		//Chart Chartth = new Chart();
		
		//������ �켱����
		Saveth.setPriority(10);
		//Chartth.setPriority(1);
		
		//������ ����
		Saveth.start();
		//Thread.sleep(2000);
		//Chartth.start();
	}
}
