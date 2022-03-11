package board;

import java.awt.Color;
import java.awt.Font;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class View extends Thread {
	@Override
	public void run() {
		//초기화면 
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
		
		// 컴포넌트 설정
		chartPanel.setLocation(0, 150);
		chartPanel.setSize(700, 400);
		
		timeLabel.setLocation(700, 0);
		timeLabel.setSize(200, 70);
		timeLabel.setFont(new Font("Gothic",Font.ITALIC, 20));
		
		buyButton.setLocation(700, 420);
		buyButton.setSize(130, 130);
		buyButton.setFont(new Font("Gothic",Font.ITALIC, 30));
		
		sellButton.setLocation(840, 420);
		sellButton.setSize(130, 130);
		sellButton.setFont(new Font("Gothic",Font.ITALIC, 30));
		
		exit.setLocation(900, 0);
		exit.setSize(70, 70);
		
		stockName.setLocation(10, 110);
		stockName.setSize(200, 70);
		stockName.setText("종목명, 종목코드");
		
		quantityNameLabel.setLocation(700, 360);
		quantityNameLabel.setSize(60, 60);
		quantityNameLabel.setText("수량");
		quantityNameLabel.setFont(new Font("Gothic",Font.ITALIC, 25));
		
		currentPriceGuideLabel.setLocation(700,300);
		currentPriceGuideLabel.setSize(80, 60);
		currentPriceGuideLabel.setText("시장가");
		currentPriceGuideLabel.setFont(new Font("Gothic",Font.ITALIC, 25));

		inputQuantity.setLocation(785, 375);
		inputQuantity.setSize(185, 30);
		inputQuantity.setColumns(10);
		inputQuantity.setFont(new Font(null,Font.BOLD, 20));
				
		outputPrice.setLocation(785, 300);
		outputPrice.setSize(185, 60);
		outputPrice.setFont(new Font("Gothic",Font.ITALIC, 25));
		//outputPrice.setOpaque(true);
		
		// 변수
		String strCurrentTime;
		int count = 0;
		int afterTwo = 0;
		double currentPrice;
		double previousPrice;
		int skips = 0;
		
		// 이벤트리스너 부착
		buyButton.addActionListener(new TradingButtonEvent());
		sellButton.addActionListener(new TradingButtonEvent());
		inputQuantity.addActionListener(new QuantityEvent());
		exit.addActionListener(new ExitEvent());

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
		
		
		while (true) {
			try {
				
				Date currentTime = new Date();
				SimpleDateFormat simplify = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				strCurrentTime = (String) simplify.format(currentTime);
				timeLabel.setText(strCurrentTime);

				if ((count % 2) == 0) {
					chartPanel.update("C:\\Users\\cms\\eclipse-workspace\\out.csv");
					afterTwo++;
					skips++;
					JChart.skips++;
					
					if (afterTwo>1) {
						
						CSVReader reader1 = new CSVReaderBuilder(new FileReader("C:\\Users\\cms\\eclipse-workspace\\out.csv")).withSkipLines(skips).build();
						String[] nextLine1;
						nextLine1 = reader1.readNext();
						currentPrice = Double.parseDouble(nextLine1[1]);
						
						CSVReader reader2 = new CSVReaderBuilder(new FileReader("C:\\Users\\cms\\eclipse-workspace\\out.csv")).withSkipLines(skips-1).build();
						String[] nextLine2;
						nextLine2 = reader2.readNext();
						previousPrice = Double.parseDouble(nextLine2[1]);
						
						if(currentPrice > previousPrice) {
							outputPrice.setText(Double.toString(currentPrice));;
							outputPrice.setForeground(Color.RED);
						}
						
						else if(currentPrice < previousPrice) {
							outputPrice.setText(Double.toString(currentPrice));;
							outputPrice.setForeground(Color.BLUE);
						}
						
						else {

							outputPrice.setText(Double.toString(currentPrice));;
							outputPrice.setForeground(Color.BLACK);
						}
					}
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
