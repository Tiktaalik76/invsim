package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class TradingButtonEvent implements ActionListener {

	File tradeHistory = new File("C:\\Users\\cms\\eclipse-workspace\\tradeHistory.csv");
	File sumFile = new File("C:\\Users\\cms\\eclipse-workspace\\sumHistory.csv");
	File quantityFilePath = new File("C:\\Users\\cms\\eclipse-workspace\\quantity.csv");

	String[] currentPrice;

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String key = e.getActionCommand();
			FileWriter writer;
			currentPrice = Crawl.getPrice();
			File file = new File("C:\\Users\\cms\\eclipse-workspace\\quantity.csv");

			CSVReader reader = new CSVReaderBuilder(new FileReader(quantityFilePath)).build();
			String[] nextLine;
			nextLine = reader.readNext();
			int quantity = Integer.parseInt(nextLine[0]);
			if (quantity > 0) {

				if (key == "Buy") { // ������ ���°� �� �� ���� �̰Ϳ� ���� ������ �߰��ؾ���;
					try {

						writer = new FileWriter(tradeHistory, true);
						writer.write(currentPrice[0] + "," + "+" + quantity + "\n");
						writer.close();
						JOptionPane.showMessageDialog(null, "�ż��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (key == "Sell") {
					try {
						writer = new FileWriter(tradeHistory, true);
						writer.write(currentPrice[0] + "," + "-" + quantity + "\n");
						writer.close();
						JOptionPane.showMessageDialog(null, "�ŵ��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}

			else {
				JOptionPane.showMessageDialog(null, "������ �ٽ� �Է��ϼ���", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
			}
			FileWriter writeback = new FileWriter(file, false);
			writeback.write("0");
			writeback.close();

			CSVReader detailReader1 = new CSVReader(new FileReader(tradeHistory));
			CSVReader detailReader2 = new CSVReader(new FileReader(tradeHistory));
			String[] nextRecord1;
			String[] nextRecord2;
			
			int �ŷ��ݾ� = 0;
			int �Ѹż��ݾ� = 0;
			int ���� = 0;
			int �Ѻ������� = 0;
			int ���ͺб��� = 0;
			int ������ = 0;
			int �򰡼��� = 0;
			String[] ���尡;
			int int���尡;
			int �򰡱ݾ� = 0;
			double ���ͷ� = 0;
			int �ŵ����ɼ��� = 0;
			int �����ݾ� = 0;
			int rows = 0;
			
			while ((nextRecord1 = detailReader1.readNext()) != null) {
				
				�ŷ��ݾ� = Integer.parseInt(nextRecord1[0]);
				���� = Integer.parseInt(nextRecord1[1]); // ��ȣ�� ����
				���尡 = Crawl.getPrice();
				int���尡 = Integer.parseInt(���尡[0]);

				�Ѻ������� += ����;
				
				if (�Ѻ������� == 0) {
					
					������ += -(�ŷ��ݾ� * ����); //�ŵ� �ŷ� ������ ����� ���� �����ݿ� �ݿ���Ŵ
					CSVReader allReader = new CSVReader(new FileReader(tradeHistory));
					List<String[]> allElements = allReader.readAll();
					
					for (int i = 0; i<=rows; i++ ) {
						allElements.remove(0);
					}
					
					CSVWriter aWriter = new CSVWriter(new FileWriter(tradeHistory), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
					aWriter.writeAll(allElements);
					aWriter.close();
					
					
					while((nextRecord2 = detailReader2.readNext()) != null) {
						�ŷ��ݾ� = Integer.parseInt(nextRecord2[0]);
						���� = Integer.parseInt(nextRecord2[1]); // ��ȣ�� ����
						���尡 = Crawl.getPrice();
						int���尡 = Integer.parseInt(���尡[0]);

						�Ѻ������� += ����;
						
						�����ݾ� += �ŷ��ݾ� * ����;

						���ͺб��� = �����ݾ� / �Ѻ�������;

						�Ѹż��ݾ� = ���ͺб��� * �Ѻ�������;

						�򰡼��� = �Ѻ������� * int���尡 - �Ѹż��ݾ�;

						���ͷ� = (�򰡼��� / �����ݾ�) * 100;

						������ = 100000000 - �����ݾ�; // �ŵ��� �켼�ϸ� �����ݾ��� - ��ȣ��

						�򰡱ݾ� = �Ѻ������� * int���尡;

						�ŵ����ɼ��� = �Ѻ�������;
					}
				}
				else {

					�����ݾ� += �ŷ��ݾ� * ����;

					���ͺб��� = �����ݾ� / �Ѻ�������;

					�Ѹż��ݾ� = ���ͺб��� * �Ѻ�������;

					�򰡼��� = �Ѻ������� * int���尡 - �Ѹż��ݾ�;

					���ͷ� = (�򰡼��� / �����ݾ�) * 100;

					������ = 100000000 - �����ݾ�; // �ŵ��� �켼�ϸ� �����ݾ��� - ��ȣ��

					�򰡱ݾ� = �Ѻ������� * int���尡;

					�ŵ����ɼ��� = �Ѻ�������;
				}
				FileWriter sumWriter;

				sumWriter = new FileWriter(sumFile, false);
				sumWriter.write(������ + "\n" + �򰡼��� + "\n" + ���ͷ� + "%" + "\n" + �Ѻ������� + "\n" + �򰡱ݾ� + "\n" + �ŵ����ɼ��� + "\n"
						+ �Ѹż��ݾ� + "\n" + ���ͺб���);

				sumWriter.close();
				rows++;


			}
		} catch (IOException | CsvValidationException e1) {
			e1.printStackTrace();
		} catch (CsvException e1) {
			e1.printStackTrace();
		}
	}
}
