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
			// �ʱ�ȭ��
			int result = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "�ȳ�", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			} 
			else if (result == JOptionPane.YES_OPTION) {
				while (true) {
					this.mStockCode = JOptionPane.showInputDialog("���� �ڵ带 �Է��ϼ���");

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

			// ������Ʈ ����
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
			JLabel[] list = { ������Label, �򰡼���Label, ���ͷ�Label, ��������Label, �򰡱ݾ�Label, �ŵ����ɼ���Label, �ż��ݾ�Label, ���ͺб���Label };

			// ���� ����
			EtchedBorder eborder;
			eborder = new EtchedBorder(EtchedBorder.RAISED);

			// ������Ʈ ����
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
			quantityNameLabel.setText("����");
			quantityNameLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

			currentPriceGuideLabel.setLocation(700, 300);
			currentPriceGuideLabel.setSize(80, 60);
			currentPriceGuideLabel.setText("���尡");
			currentPriceGuideLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

			inputQuantity.setLocation(785, 375);
			inputQuantity.setSize(185, 30);
			inputQuantity.setColumns(10);
			inputQuantity.setFont(new Font(null, Font.BOLD, 20));

			outputPrice.setLocation(785, 300);
			outputPrice.setSize(185, 60);
			outputPrice.setFont(new Font("Gothic", Font.ITALIC, 25));

			// �̺�Ʈ������ ����
			buyButton.addActionListener(new Event());
			sellButton.addActionListener(new Event());
			inputQuantity.addActionListener(new Event2());
			exit.addActionListener(new Event());

			// �� ����
			identificationPanel.setLayout(new GridLayout(2, 4));
			identificationPanel.setLocation(10, 2);
			identificationPanel.setSize(680, 120);
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
			tabPane.addTab("tab 1", secChartPanel);
			tabPane.addTab("tab 2", dayChartPanel);

			// �� ����
			������Panel.setLayout(new GridLayout(1, 2));
			������Panel.setLocation(700, 82);
			������Panel.setSize(270, 40);
			������Panel.setBorder(eborder);
			������Panel.setBackground(Color.WHITE);
			������Panel.add(������GuideLabel);
			������Panel.add(������Label);
			������GuideLabel.setText("������");

			// �ֻ��������� ����
			JFrame frame = new JFrame("�ֽĸ�������");
			frame.setSize(1000, 600);
			frame.setVisible(true);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);

			// ������Ʈ ����
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
			frame.add(������Panel);
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
					���簡Label.setText(Crawl.getPrice(mStockCode, 1));
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
