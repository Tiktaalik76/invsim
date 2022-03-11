package stocksimulation;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class TimeChecker extends Thread {
	public JLabel timerLabel; // Ÿ�̸� ���� ��µǴ� ���̺�

	public static String currentTime;

	public TimeChecker(JLabel timerLabel) {
		this.timerLabel = timerLabel; // ������
	}

	@Override
	public void run() {
		while (true) {
			Date current = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			currentTime = (String) simple.format(current);
			timerLabel.setText(currentTime);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}