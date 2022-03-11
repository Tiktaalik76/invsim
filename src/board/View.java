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
			// ���� �������� ����
			int result = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "�ȳ�", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			} else if (result == JOptionPane.YES_OPTION) {
				while (true) {
					this.stockCode = JOptionPane.showInputDialog("���� �ڵ带 �Է��ϼ���");

					// �Էµ� �����ڵ� �˻�
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

					// ���� ������ ����
					Tools.deleteInternalFiles("C:\\Users\\cms\\eclipse-workspace\\bowl");

					// �����ڵ� ����
					FileWriter writer = new FileWriter(file, false);
					writer.write(stockCode);
					writer.close();

					// �� ������ ����
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

			// ������ ����
			Save saveThread = new Save();
			saveThread.start();

			// ������Ʈ ����
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
			JLabel ���簡GuideLabel = new JLabel();
			JLabel ���簡Label = new JLabel();
			JLabel �򰡼���GuideLabel = new JLabel();
			JLabel �򰡼���Label = new JLabel();
			JLabel ���ͷ�GuideLabel = new JLabel();
			JLabel ���ͷ�Label = new JLabel();
			JLabel ��������GuideLabel = new JLabel();
			JLabel ��������Label = new JLabel();
			JLabel �򰡱ݾ�GuideLabel = new JLabel();
			JLabel �򰡱ݾ�Label = new JLabel();
			JLabel �ŵ����ɼ���GuideLabel = new JLabel();
			JLabel �ŵ����ɼ���Label = new JLabel();
			JLabel �ż��ݾ�GuideLabel = new JLabel();
			JLabel �ż��ݾ�Label = new JLabel();
			JLabel ���ͺб���GuideLabel = new JLabel();
			JLabel ���ͺб���Label = new JLabel();
			JPanel ������Panel = new JPanel();
			JLabel ������GuideLabel = new JLabel();
			JLabel ������Label = new JLabel();
			JLabel[] list = { ��������Label, �ŵ����ɼ���Label, �ż��ݾ�Label, ���ͺб���Label };

			// ���� ����
			EtchedBorder eborder;
			eborder = new EtchedBorder(EtchedBorder.RAISED);

			// ������Ʈ ����
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
			currentPriceGuideLabel.setText("���尡");
			currentPriceGuideLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

			outputPrice.setLocation(810, 310);
			outputPrice.setSize(210, 60);
			outputPrice.setFont(new Font("Gothic", Font.ITALIC, 25));
			outputPrice.setHorizontalAlignment(JLabel.CENTER);

			stockList.setLocation(720, 100);
			stockList.setSize(300, 200);
			stockList.setBorder(eborder);

			// �� ����
			identificationPanel.setLayout(new GridLayout(2, 4));
			identificationPanel.setLocation(5, 2);
			identificationPanel.setSize(700, 120);
			identificationPanel.setBackground(Color.WHITE);
			identificationPanel.setBorder(eborder);

			identificationPanel.add(�򰡼���GuideLabel);
			�򰡼���GuideLabel.setText("�򰡼���");
			identificationPanel.add(�򰡼���Label);

			identificationPanel.add(��������GuideLabel);
			��������GuideLabel.setText("��������");
			identificationPanel.add(��������Label);

			identificationPanel.add(�ŵ����ɼ���GuideLabel);
			�ŵ����ɼ���GuideLabel.setText("�ŵ����ɼ���");
			identificationPanel.add(�ŵ����ɼ���Label);

			identificationPanel.add(���ͺб���GuideLabel);
			���ͺб���GuideLabel.setText("���ͺб���");
			identificationPanel.add(���ͺб���Label);

			identificationPanel.add(���ͷ�GuideLabel);
			���ͷ�GuideLabel.setText("���ͷ�");
			identificationPanel.add(���ͷ�Label);

			identificationPanel.add(�򰡱ݾ�GuideLabel);
			�򰡱ݾ�GuideLabel.setText("�򰡱ݾ�");
			identificationPanel.add(�򰡱ݾ�Label);

			identificationPanel.add(�ż��ݾ�GuideLabel);
			�ż��ݾ�GuideLabel.setText("�ż��ݾ�");
			identificationPanel.add(�ż��ݾ�Label);

			identificationPanel.add(���簡GuideLabel);
			���簡GuideLabel.setText("���簡");
			identificationPanel.add(���簡Label);

			// �� ����
			tabPane.addTab("�ǽð�", secChartPanel);
			tabPane.addTab("�ϰ�", parentPanel);

			// �� ����
			������Panel.setLayout(new GridLayout(1, 2));
			������Panel.setLocation(720, 45);
			������Panel.setSize(300, 40);
			������Panel.setBorder(eborder);
			������Panel.setBackground(Color.WHITE);
			������Panel.add(������GuideLabel);
			������Panel.add(������Label);
			������GuideLabel.setText("������");

			// �ֻ��������� ����
			JFrame frame = new JFrame("�ֽĸ�������");
			frame.setSize(1050, 600);
			frame.setVisible(true);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);

			// ������Ʈ ����
			frame.add(timeLabel);
			frame.add(stockTrade);
			frame.add(stockNameLabel);
			frame.add(currentPriceGuideLabel);
			frame.add(outputPrice);
			frame.add(identificationPanel);
			frame.add(������Panel);
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
					
					������Label.setText(Integer.toString(cash));

					timeLabel.setText(Crawl.getTime());
					���簡Label.setText(Crawl.getPrice(stockCode, 1));
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

					int ���尡 = Integer.parseInt(Crawl.getPrice(stockCode, 0));
					String �򰡱ݾ� = Integer.toString(���尡 * Integer.parseInt(wholequantity[0]));
					�򰡱ݾ�Label.setText(�򰡱ݾ�);

					if (wholeprice[0].equals("0") || wholequantity.equals("0")) {
						�򰡼���Label.setText("0");
						���ͷ�Label.setText("0");
					} else {
						String �򰡼��� = Integer
								.toString(���尡 * Integer.parseInt(wholequantity[0]) - Integer.parseInt(wholeprice[0]));
						�򰡼���Label.setText(�򰡼���);

						DecimalFormat df = new DecimalFormat("0.00");

						String ���ͷ� = df
								.format((���尡 * Integer.parseInt(wholequantity[0]) - Integer.parseInt(wholeprice[0]))
										/ Double.parseDouble(wholeprice[0]) * 100);

						���ͷ�Label.setText(���ͷ� + "%");
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
