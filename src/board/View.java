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
		//초기화면 *****************************버튼 있는것으로 바꿀 것 **********************************
		JOptionPane.showMessageDialog(null, "테스트 중", "안내", JOptionPane.PLAIN_MESSAGE); 
		
		
		// 컴포넌트 생성
		JChart chartPanel = new JChart();
		JLabel timeLabel = new JLabel();
		JLabel quantityGuideLabel = new JLabel();
		JButton buyButton = new JButton("Buy");
		JButton sellButton = new JButton("Sell");
		JTextField inputQuantity = new JTextField();

		// 컴포넌트 설정
		quantityGuideLabel.setText("수량을 입력하고 엔터를 누르세요.");

		// 변수
		String strCurrentTime;
		int count = 0;

		// 이벤트리스너 부착
		buyButton.addActionListener(new TradingButtonEvent());
		sellButton.addActionListener(new TradingButtonEvent());
		inputQuantity.addActionListener(new QuantityEvent());

		// 최상위프레임 설정
		JFrame frame = new JFrame();
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(2, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 컴포넌트 부착
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
