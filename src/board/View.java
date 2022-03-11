package board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class View extends Thread {
	@Override
	public void run() {
		// 초기화면
		JOptionPane.showMessageDialog(null, "테스트 중", "안내", JOptionPane.PLAIN_MESSAGE);

		// 컴포넌트 생성
		JChart chartPanel = new JChart();
		JLabel timeLabel = new JLabel();
		JLabel quantityNameLabel = new JLabel();
		JButton buyButton = new JButton("Buy");
		JButton sellButton = new JButton("Sell");
		JButton exit = new JButton("Exit");
		JLabel stockName = new JLabel();
		JTextField inputQuantity = new JTextField();
		JLabel currentPriceGuideLabel = new JLabel();
		JTextField outputPrice = new JTextField();
		JPanel identificationPanel = new JPanel();
		JLabel 현재가GuideLabel = new JLabel();
		JLabel 현재가Label = new JLabel();
		JLabel 평가손익GuideLabel = new JLabel();
		JLabel 평가손익Label = new JLabel();
		JLabel 수익률GuideLabel = new JLabel();
		JLabel 수익률Label = new JLabel();
		JLabel 보유수량GuideLabel = new JLabel();
		JLabel 보유수량Label = new JLabel();
		JLabel 평가금액GuideLabel = new JLabel();
		JLabel 평가금액Label = new JLabel();
		JLabel 매도가능수량GuideLabel = new JLabel();
		JLabel 매도가능수량Label = new JLabel();
		JLabel 매수금액GuideLabel = new JLabel();
		JLabel 매수금액Label = new JLabel();
		JLabel 손익분기점GuideLabel = new JLabel();
		JLabel 손익분기점Label = new JLabel();
		JPanel 예수금Panel = new JPanel();
		JLabel 예수금GuideLabel = new JLabel();
		JLabel 예수금Label = new JLabel();
		JLabel[] list = {예수금Label, 평가손익Label, 수익률Label, 보유수량Label, 평가금액Label, 매도가능수량Label, 매수금액Label, 손익분기점Label};
		
		// 보더 설정
		EtchedBorder eborder;
		eborder = new EtchedBorder(EtchedBorder.RAISED);

		// 컴포넌트 설정
		chartPanel.setLocation(0, 150);
		chartPanel.setSize(700, 400);

		timeLabel.setLocation(700, 2);
		timeLabel.setSize(200, 70);
		timeLabel.setBorder(eborder);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		timeLabel.setFont(new Font("Gothic", Font.ITALIC, 20));

		buyButton.setLocation(700, 420);
		buyButton.setSize(130, 130);
		buyButton.setFont(new Font("Gothic", Font.ITALIC, 30));

		sellButton.setLocation(840, 420);
		sellButton.setSize(130, 130);
		sellButton.setFont(new Font("Gothic", Font.ITALIC, 30));

		exit.setLocation(901, 3);
		exit.setSize(68, 69);

		stockName.setLocation(10, 126);
		stockName.setSize(200, 24);
		stockName.setText(" 종목명 (종목코드)");
		stockName.setBorder(eborder);

		quantityNameLabel.setLocation(700, 360);
		quantityNameLabel.setSize(60, 60);
		quantityNameLabel.setText("수량");
		quantityNameLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

		currentPriceGuideLabel.setLocation(700, 300);
		currentPriceGuideLabel.setSize(80, 60);
		currentPriceGuideLabel.setText("시장가");
		currentPriceGuideLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

		inputQuantity.setLocation(785, 375);
		inputQuantity.setSize(185, 30);
		inputQuantity.setColumns(10);
		inputQuantity.setFont(new Font(null, Font.BOLD, 20));

		outputPrice.setLocation(785, 300);
		outputPrice.setSize(185, 60);
		outputPrice.setFont(new Font("Gothic", Font.ITALIC, 25));

		// 변수
		int count = 0;

		// 이벤트리스너 부착
		buyButton.addActionListener(new Event());
		sellButton.addActionListener(new Event());
		inputQuantity.addActionListener(new Event2());
		exit.addActionListener(new Event());
		

		// 라벨 부착
		identificationPanel.setLayout(new GridLayout(2, 4));
		identificationPanel.setLocation(10, 2);
		identificationPanel.setSize(680, 120);
		identificationPanel.setBackground(Color.WHITE);
		identificationPanel.setBorder(eborder);

		identificationPanel.add(평가손익GuideLabel);
		평가손익GuideLabel.setText("평가손익");
		identificationPanel.add(평가손익Label);

		identificationPanel.add(보유수량GuideLabel);
		보유수량GuideLabel.setText("보유수량");
		identificationPanel.add(보유수량Label);

		identificationPanel.add(매도가능수량GuideLabel);
		매도가능수량GuideLabel.setText("매도가능수량");
		identificationPanel.add(매도가능수량Label);

		identificationPanel.add(손익분기점GuideLabel);
		손익분기점GuideLabel.setText("손익분기점");
		identificationPanel.add(손익분기점Label);

		identificationPanel.add(수익률GuideLabel);
		수익률GuideLabel.setText("수익률");
		identificationPanel.add(수익률Label);

		identificationPanel.add(평가금액GuideLabel);
		평가금액GuideLabel.setText("평가금액");
		identificationPanel.add(평가금액Label);

		identificationPanel.add(매수금액GuideLabel);
		매수금액GuideLabel.setText("매수금액");
		identificationPanel.add(매수금액Label);

		identificationPanel.add(현재가GuideLabel);
		현재가GuideLabel.setText("현재가");
		identificationPanel.add(현재가Label);

		// 라벨 부착
		예수금Panel.setLayout(new GridLayout(1, 2));
		예수금Panel.setLocation(700, 82);
		예수금Panel.setSize(270, 40);
		예수금Panel.setBorder(eborder);
		예수금Panel.setBackground(Color.WHITE);
		예수금Panel.add(예수금GuideLabel);
		예수금Panel.add(예수금Label);
		예수금GuideLabel.setText("예수금");

		// 최상위프레임 설정
		JFrame frame = new JFrame("주식모의투자");
		frame.setSize(1000, 600);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// 컴포넌트 부착
		frame.add(timeLabel);
		frame.add(chartPanel);
		frame.add(buyButton);
		frame.add(sellButton);
		frame.add(inputQuantity);
		frame.add(exit);
		frame.add(stockName);
		frame.add(quantityNameLabel);
		frame.add(currentPriceGuideLabel);
		frame.add(outputPrice);
		frame.add(identificationPanel);
		frame.add(예수금Panel);

		while (true) {
			try {
				timeLabel.setText(Crawl.getTime());
				
				
				String[] detailItems;
				List<String[]> items = CSVTools.lines();
				for (int i = 0; i<items.size(); i++) {
					detailItems = items.get(i);
					list[i].setText(detailItems[0].toString());
				}
				
				if ((count % 7) == 0) {
					chartPanel.update("C:\\Users\\cms\\eclipse-workspace\\out.csv");
					String[] temp = Crawl.getPrice();
					현재가Label.setText(temp[1]);
					outputPrice.setText(temp[1]);
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
