package board;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class View {
	String file = "C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCode.csv";
	String stockCode;
	String transactionDetailsFilePath;
	public static int cash = 0;
	public static JPanel topPanel = new JPanel();

	@SuppressWarnings("removal")
	public View() throws CsvValidationException, IOException {
		
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

			EtchedBorder eborder;
			eborder = new EtchedBorder(EtchedBorder.RAISED);
			
			
			StockListPanel stockList = new StockListPanel();
			stockList.setLocation(720, 100);
			stockList.setSize(300, 200);
			stockList.setBorder(eborder);
			
			StockTradePanel stockTrade = new StockTradePanel();
			stockTrade.setLocation(710, 380);
			stockTrade.setSize(320, 180);
			
			IdentificationPanel identificationPanel = new IdentificationPanel();
			identificationPanel.setLocation(5, 2);
			identificationPanel.setSize(700, 144);
			
			JTabbedPane tabPane = new JTabbedPane();
			tabPane.setLocation(5, 130);
			tabPane.setSize(700, 430);
			
			SecChartPanel secChartPanel = new SecChartPanel();
			DayChartPanel dayChartPanel = new DayChartPanel();
			topPanel.add(dayChartPanel);
			tabPane.addTab("�ǽð�", secChartPanel);
			tabPane.addTab("�ϰ�", topPanel);
			
			InfoDisplayPanel infoDisplayPanel = new InfoDisplayPanel();
			infoDisplayPanel.setLocation(720, 2);
			infoDisplayPanel.setSize(300, 370);
			
			
			JLayeredPane layer = new JLayeredPane();
			layer.setLayout(null);
			layer.setSize(1050, 600);
			

			// �ֻ��������� ����
			JFrame frame = new JFrame("�ֽĸ�������");
			frame.setSize(1050, 600);
			frame.setVisible(true);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.add(layer);
			
			// ������Ʈ ����
			layer.add(stockTrade);
			layer.add(identificationPanel);
			layer.add(tabPane, new Integer(400));
			layer.add(stockList);
			layer.add(infoDisplayPanel);
	}
}
