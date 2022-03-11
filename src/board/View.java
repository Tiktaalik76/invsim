package board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class View extends Thread {
	String file = "C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCode.csv";
	String stockCode;
	String transactionDetailsFilePath;
	public static int mIsUpdated = 0;
	public static int cash = 0;

	@Override
	public void run() {
		try {
			// 새로 시작할지 결정
			int result = JOptionPane.showConfirmDialog(null, "새로 시작하시겠습니까?", "안내", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			} else if (result == JOptionPane.YES_OPTION) {
				while (true) {
					this.stockCode = JOptionPane.showInputDialog("종목 코드를 입력하세요");

					// 입력된 종목코드 검사
					CSVReader reader1 = new CSVReader(
							new FileReader("C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCodeData.csv"));
					String[] readNext;
					int check = 0;
					while ((readNext = reader1.readNext()) != null) {
						if (readNext[1].equals(stockCode)) {
							check = 1;
						}
					}

					if (stockCode.length() == 0 || stockCode.equals("") || check == 0) {
						continue;
					}

					// 이전 데이터 삭제
					Tools.deleteInternalFiles("C:\\Users\\cms\\eclipse-workspace\\bowl");

					// 종목코드 저장
					FileWriter writer = new FileWriter(file, false);
					writer.write(stockCode);
					writer.close();

					// 빈 데이터 생성
					writer = new FileWriter("C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode + "userDetails.csv",
							false);
					writer.write("0" + "\n" + "0" + "\n" + "0" + "\n" + "0");
					writer.close();

					writer = new FileWriter(
							"C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode + "transactionDetails.csv", false);
					writer.write("0,0\n");
					writer.close();

					writer = new FileWriter("C:\\Users\\cms\\eclipse-workspace\\bowl\\accumulatedStockCode.csv", false);
					writer.write(stockCode + "\n");
					writer.close();

					writer = new FileWriter("C:\\Users\\cms\\eclipse-workspace\\bowl\\cash.csv", false);
					writer.write("100000000");
					writer.close();
					
					cash = 10000000;

					break;
				}
			} else {
				stockCode = Tools.readOneFactor(file, 0, 0);
			}
			transactionDetailsFilePath = "C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode
					+ "transactionDetails.csv";

			// 스레드 실행
			Save saveThread = new Save();
			saveThread.start();

			// 컴포넌트 생성
			StockListPanel stockList = new StockListPanel();
			StockTradePanel stockTrade = new StockTradePanel();
			SecChart secChartPanel = new SecChart();
			DayChart dayChartPanel = new DayChart();
			JPanel parentPanel = new JPanel();

			parentPanel.add(dayChartPanel);

			JTabbedPane tabPane = new JTabbedPane();
			JLabel timeLabel = new JLabel();
			JLabel stockNameLabel = new JLabel();
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
			JLabel[] list = { 보유수량Label, 매도가능수량Label, 매수금액Label, 손익분기점Label };

			// 보더 설정
			EtchedBorder eborder;
			eborder = new EtchedBorder(EtchedBorder.RAISED);

			// 컴포넌트 설정
			stockTrade.setLocation(710, 380);
			stockTrade.setSize(320, 180);

			tabPane.setLocation(5, 130);
			tabPane.setSize(700, 430);

			timeLabel.setLocation(720, 2);
			timeLabel.setSize(300, 40);
			timeLabel.setBorder(eborder);
			timeLabel.setOpaque(true);
			timeLabel.setBackground(Color.WHITE);
			timeLabel.setFont(new Font("Gothic", Font.ITALIC, 20));
			timeLabel.setHorizontalAlignment(JLabel.CENTER);

			stockNameLabel.setLocation(500, 120);
			stockNameLabel.setSize(200, 24);
			stockNameLabel.setText(Crawl.getStockName(stockCode) + "(" + stockCode + ")");
			stockNameLabel.setHorizontalAlignment(JLabel.RIGHT);

			currentPriceGuideLabel.setLocation(720, 310);
			currentPriceGuideLabel.setSize(80, 60);
			currentPriceGuideLabel.setText("시장가");
			currentPriceGuideLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

			outputPrice.setLocation(810, 310);
			outputPrice.setSize(210, 60);
			outputPrice.setFont(new Font("Gothic", Font.ITALIC, 25));
			outputPrice.setHorizontalAlignment(JLabel.CENTER);

			stockList.setLocation(720, 100);
			stockList.setSize(300, 200);
			stockList.setBorder(eborder);

			// 라벨 부착
			identificationPanel.setLayout(new GridLayout(2, 4));
			identificationPanel.setLocation(5, 2);
			identificationPanel.setSize(700, 120);
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
			tabPane.addTab("실시간", secChartPanel);
			tabPane.addTab("일간", parentPanel);

			// 라벨 부착
			예수금Panel.setLayout(new GridLayout(1, 2));
			예수금Panel.setLocation(720, 45);
			예수금Panel.setSize(300, 40);
			예수금Panel.setBorder(eborder);
			예수금Panel.setBackground(Color.WHITE);
			예수금Panel.add(예수금GuideLabel);
			예수금Panel.add(예수금Label);
			예수금GuideLabel.setText("예수금");

			// 최상위프레임 설정
			JFrame frame = new JFrame("주식모의투자");
			frame.setSize(1050, 600);
			frame.setVisible(true);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);

			// 컴포넌트 부착
			frame.add(timeLabel);
			frame.add(stockTrade);
			frame.add(stockNameLabel);
			frame.add(currentPriceGuideLabel);
			frame.add(outputPrice);
			frame.add(identificationPanel);
			frame.add(예수금Panel);
			frame.add(tabPane);
			frame.add(stockList);

			while (true) {
				try {
					String[] detailItem;
					List<String[]> items = Tools.lines(stockCode);
					for (int i = 0; i < items.size(); i++) {
						detailItem = items.get(i);
						list[i].setText(detailItem[0].toString());
					}

					secChartPanel.update();
					
					예수금Label.setText(Integer.toString(cash));

					timeLabel.setText(Crawl.getTime());
					현재가Label.setText(Crawl.getPrice(stockCode, 1));
					outputPrice.setText(Crawl.getPrice(stockCode, 1));

					String[] wholequantity;
					String[] wholeprice;

					CSVReader reader = new CSVReaderBuilder(
							new FileReader("C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode + "userDetails.csv"))
									.withSkipLines(1).build();
					wholequantity = reader.readNext();

					CSVReader reader1 = new CSVReaderBuilder(
							new FileReader("C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode + "userDetails.csv"))
									.withSkipLines(2).build();
					wholeprice = reader1.readNext();

					int 시장가 = Integer.parseInt(Crawl.getPrice(stockCode, 0));
					String 평가금액 = Integer.toString(시장가 * Integer.parseInt(wholequantity[0]));
					평가금액Label.setText(평가금액);

					if (wholeprice[0].equals("0") || wholequantity.equals("0")) {
						평가손익Label.setText("0");
						수익률Label.setText("0");
					} else {
						String 평가손익 = Integer
								.toString(시장가 * Integer.parseInt(wholequantity[0]) - Integer.parseInt(wholeprice[0]));
						평가손익Label.setText(평가손익);

						DecimalFormat df = new DecimalFormat("0.00");

						String 수익률 = df
								.format((시장가 * Integer.parseInt(wholequantity[0]) - Integer.parseInt(wholeprice[0]))
										/ Double.parseDouble(wholeprice[0]) * 100);

						수익률Label.setText(수익률 + "%");
					}
					if (mIsUpdated == 1) {
						this.stockCode = Tools.readOneFactor("C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCode.csv",
								0, 0);

						this.transactionDetailsFilePath = "C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode
								+ "transactionDetails.csv";

						dayChartPanel.repaint();

						stockNameLabel.setText(Crawl.getStockName(stockCode) + "(" + stockCode + ")");

						parentPanel.removeAll();

						DayChart newDayChartPanel = new DayChart();

						parentPanel.add(newDayChartPanel);

						View.mIsUpdated = 0;

					}
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
