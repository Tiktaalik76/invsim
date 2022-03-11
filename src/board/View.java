package board;

import java.awt.GridLayout;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JOptionPane;

public class View extends Thread {
	@Override
	public void run() {
		//�ʱ�ȭ�� *****************************��ư �ִ°����� �ٲ� �� **********************************
		JOptionPane.showMessageDialog(null, "�׽�Ʈ ��", "�ȳ�", JOptionPane.PLAIN_MESSAGE); 
		
		
		// ������Ʈ ����
		JChart chartPanel = new JChart();
		JLabel timeLabel = new JLabel();
		JLabel quantityGuideLabel = new JLabel();
		JButton buyButton = new JButton("Buy");
		JButton sellButton = new JButton("Sell");
		JTextField inputQuantity = new JTextField();

		// ������Ʈ ����
		quantityGuideLabel.setText("������ �Է��ϰ� ���͸� ��������.");

		// ����
		String strCurrentTime;
		int count = 0;

		// �̺�Ʈ������ ����
		buyButton.addActionListener(new TradingButtonEvent());
		sellButton.addActionListener(new TradingButtonEvent());
		inputQuantity.addActionListener(new QuantityEvent());

		// �ֻ��������� ����
		JFrame frame = new JFrame();
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(2, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ������Ʈ ����
		frame.add(timeLabel);
		frame.add(chartPanel);
		frame.add(buyButton);
		frame.add(sellButton);
		frame.add(quantityGuideLabel);
		frame.add(inputQuantity);

		while (true) {
			try {

				Date currentTime = new Date();
				SimpleDateFormat simplify = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				strCurrentTime = (String) simplify.format(currentTime);
				timeLabel.setText(strCurrentTime);

				if ((count % 7) == 0) {
					chartPanel.update("C:\\Users\\cms\\eclipse-workspace\\out.csv");
				}

				sleep(1000);
				count++;

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
