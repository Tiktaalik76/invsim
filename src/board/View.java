package board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class View extends Thread {
	String file = "C:\\Users\\cms\\eclipse-workspace\\stockCode.csv";
	String mStockCode;

	@Override
	public void run() {
		try {
			// 초기화면
			int result = JOptionPane.showConfirmDialog(null, "새로 시작하시겠습니까?", "안내", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			} 
			else if (result == JOptionPane.YES_OPTION) {
				while (true) {
					this.mStockCode = JOptionPane.showInputDialog("종목 코드를 입력하세요");

					CSVReader reader1 = new CSVReader(new FileReader("C:\\Users\\cms\\eclipse-workspace\\stockCodeData.csv"));
					String[] readNext;
					
					int asdf = 2;
					
					while((readNext = reader1.readNext()) != null) {
						if (readNext[1].equals(mStockCode)) {
							asdf = 3;
						}
					}
					
					if (mStockCode.length()==0||mStockCode.equals("")||asdf == 2) {
						continue;
					}
					
					FileWriter writer = new FileWriter(file, false);
					writer.write(mStockCode);
					writer.close();
					
					File userDetailsFilePath = new File("C:\\Users\\cms\\eclipse-workspace\\userDetails.csv");
					File transactionDetailsFilePath = new File("C:\\Users\\cms\\eclipse-workspace\\transactionDetails.csv");
					File dataList = new File("C:\\Users\\cms\\eclipse-workspace\\dataList.csv");
					
					userDetailsFilePath.delete();
					transactionDetailsFilePath.delete();
					dataList.delete();
					
					writer = new FileWriter("C:\\Users\\cms\\eclipse-workspace\\userDetails.csv", false);
					writer.write("100000000" + "\n" + "0" + "\n" + "0" + "\n" + "0"+ "\n" + "0" + "\n" + "0" + "\n" + "0" + "\n");
					writer.close();
					
					break;
				}
			}
			else {
				mStockCode = CSVTools.readOneFactor(file, 0, 0);
			}

			Save saveThread = new Save();
			saveThread.start();

			// 컴포넌트 생성
			SecChart secChartPanel = new SecChart();
			DayChart dayChartPanel = new DayChart();
			JTabbedPane tabPane = new JTabbedPane();
			JLabel timeLabel = new JLabel();
			JLabel quantityNameLabel = new JLabel();
			JButton buyButton = new JButton("Buy");
			JButton sellButton = new JButton("Sell");
			JButton exit = new JButton("Exit");
			JLabel stockNameLabel = new JLabel();
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
			JLabel[] list = { 예수금Label, 평가손익Label, 수익률Label, 보유수량Label, 평가금액Label, 매도가능수량Label, 매수금액Label, 손익분기점Label };

			// 보더 설정
			EtchedBorder eborder;
			eborder = new EtchedBorder(EtchedBorder.RAISED);

			// 컴포넌트 설정
			secChartPanel.setLocation(0, 150);
			secChartPanel.setSize(700, 400);

			tabPane.setLocation(0, 120);
			tabPane.setSize(700, 430);

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

			stockNameLabel.setLocation(500, 120);
			stockNameLabel.setSize(200, 24);
			stockNameLabel.setText(Crawl.getStockName(mStockCode) + "(" + mStockCode + ")");
			stockNameLabel.setHorizontalAlignment(JLabel.RIGHT);

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

			// 탭 부착
			tabPane.addTab("tab 1", secChartPanel);
			tabPane.addTab("tab 2", dayChartPanel);

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
			frame.add(buyButton);
			frame.add(sellButton);
			frame.add(inputQuantity);
			frame.add(exit);
			frame.add(stockNameLabel);
			frame.add(quantityNameLabel);
			frame.add(currentPriceGuideLabel);
			frame.add(outputPrice);
			frame.add(identificationPanel);
			frame.add(예수금Panel);
			frame.add(tabPane);
			while (true) {
				try {
					String[] detailItem;
					List<String[]> items = CSVTools.lines();
					for (int i = 0; i<items.size(); i++) {
						detailItem = items.get(i);
						list[i].setText(detailItem[0].toString());
					}
					
					secChartPanel.update();

					timeLabel.setText(Crawl.getTime());
					현재가Label.setText(Crawl.getPrice(mStockCode, 1));
					outputPrice.setText(Crawl.getPrice(mStockCode, 1));

					sleep(1000);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}

	}
}
