package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Event implements ActionListener {
	public static int mQuantity = 0;
	private static String mStockCode;
	
	String transactionDetailsFilePath 	= "C:\\Users\\cms\\eclipse-workspace\\transactionDetails.csv";
	String userDetailsFilePath 			= "C:\\Users\\cms\\eclipse-workspace\\userDetails.csv";
	String readFilePath 				= "C:\\Users\\cms\\eclipse-workspace\\stockCode.csv";

	int ���尡;
	int �ŷ��ݾ� = 0;
	int ���� = 0;
	int �Ѻ�������;
	int �ż��ݾ�;
	
	double ���ͺб��� = 0;
	int �򰡱ݾ� = 0;
	double ���ͷ� = 0;
	int ������;
	int �ŵ����ɼ��� = 0;
	int �򰡼��� = 0;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			�ż��ݾ� = 0;
			�Ѻ������� = 0;
			������ = 100000000;
			mStockCode = CSVTools.readOneFactor(readFilePath, 0, 0);
			
			String key = e.getActionCommand();
			FileWriter writer;


			if (key == "Exit") {System.exit(0);}

			else {
				if (mQuantity > 0) {
					if (key == "Buy") {
						try {
						
							writer = new FileWriter(transactionDetailsFilePath, true);
							writer.write(Crawl.getPrice(mStockCode, 0) + "," + "+" + mQuantity + "\n");
							writer.close();
							JOptionPane.showMessageDialog(null, "�ż��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
						
						} 
						catch (IOException e1) {e1.printStackTrace();}
					} 
					else {
						try {
							CSVReader reader = new CSVReader(new FileReader(transactionDetailsFilePath));
							String[] readNext;
							int asdf;
							int ffff = 0;
							while((readNext = reader.readNext()) != null) {
								asdf = Integer.parseInt(readNext[1]);
								ffff += asdf;
							}
							if(mQuantity > ffff) {}
							else {
								
								writer = new FileWriter(transactionDetailsFilePath, true);
								writer.write(Crawl.getPrice(mStockCode, 0) + "," + "-" + mQuantity + "\n");
								writer.close();
								JOptionPane.showMessageDialog(null, "�ŵ��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
							}
						}
						catch (IOException e1) {e1.printStackTrace();}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "������ �ٽ� �Է��ϼ���" + "\n" + "������ ���� ����" + "\n" + "���� �Է� �� ���� �Է�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
				}
			}

			CSVReader detailReader = new CSVReader(new FileReader(transactionDetailsFilePath));
			String[] readNext;
			while ((readNext = detailReader.readNext()) != null) {
				
				���尡 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));
				�ŷ��ݾ� = Integer.parseInt(readNext[0]);
				���� = Integer.parseInt(readNext[1]);
				// ���� ��ȣ
				// buy  : +
				// sell : -
				
				�Ѻ������� += ����;
				�ż��ݾ� += �ŷ��ݾ� * ����;

				if (�Ѻ������� == 0) {
					������ += -(�ż��ݾ�);
					// ������ �̿� �ŷ� ���� �ʱ�ȭ
					
					�Ѻ������� = 0;
					�ż��ݾ� = 0;
					���ͺб��� = 0;
					�ŵ����ɼ��� = 0;
					
				} 
				else {

					���ͺб��� = �ż��ݾ� / �Ѻ�������;
					
					�ŵ����ɼ��� = �Ѻ�������;
					
				}
			}
			
			������ = ������ - �ż��ݾ�;
			
			writer = new FileWriter(userDetailsFilePath, false);
			writer.write(������ + "\n" + �Ѻ������� + "\n" + �ŵ����ɼ��� + "\n" + �ż��ݾ� + "\n" + ���ͺб���);
			writer.close();
		}
		catch (IOException | CsvValidationException e1) {e1.printStackTrace();}
	}
}
