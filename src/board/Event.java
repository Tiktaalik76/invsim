package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Event implements ActionListener {
	public static int mQuantity = 0;
	
	File tradeHistory = new File("C:\\Users\\cms\\eclipse-workspace\\tradeHistory.csv");
	File sumFile = new File("C:\\Users\\cms\\eclipse-workspace\\sumHistory.csv");

	int �ŷ��ݾ� = 0;
	int �Ѹż��ݾ� = 0;
	int ���� = 0;
	int �Ѻ������� = 0;
	double ���ͺб��� = 0;
	int ������ = 0;
	String[] ���尡;
	int int���尡;
	int �򰡱ݾ� = 0;
	double ���ͷ� = 0;
	int �ŵ����ɼ��� = 0;
	int �����ݾ� = 0;
	int ��� = 0;
	
	int �򰡼��� = 0;
	int ���ðŷ��ݾ�;
	int ���ü���;
	int �����Ѻ������� = 0;
	int ���ú����ݾ� = 0;
	int ������� = 0;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String key = e.getActionCommand();
			FileWriter writer;
			
			String[] currentPrice = Crawl.getPrice();
		
			if (key == "Exit") {
				System.exit(0);
			}
			
			else {
				if (mQuantity > 0) {
					if (key == "Buy") {
						try {
							
							writer = new FileWriter(tradeHistory, true);
							writer.write(currentPrice[0] + "," + "+" + mQuantity + "\n");
							writer.close();
							JOptionPane.showMessageDialog(null, "�ż��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else {
						try {

							CSVReader allReader = new CSVReader(new FileReader(tradeHistory));
							String[] readNext;
						
							�����Ѻ������� = 0;
							���ú����ݾ� = 0;
							������� = 0;
							
							while ((readNext = allReader.readNext()) != null) {
								
								���ðŷ��ݾ� = Integer.parseInt(readNext[0]);
								���ü��� = Integer.parseInt(readNext[1]); // ��ȣ�� ����
								
								�����Ѻ������� += ���ü���;
								���ú����ݾ� += ���ðŷ��ݾ� * ���ü���;
								
							}
							������� = ���ú����ݾ�/�����Ѻ�������;
							writer = new FileWriter(tradeHistory, true);
							writer.write(currentPrice[0] + "," + "-" + mQuantity + "\n");
							writer.close();
							JOptionPane.showMessageDialog(null, "�ŵ��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
							
							�򰡼��� = Integer.parseInt(currentPrice[0])*mQuantity - �������* mQuantity;

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "������ �ٽ� �Է��ϼ���"+"\n"+"������ ���� ����"+"\n"+"���� �Է� �� ���� �Է�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
			�ŷ��ݾ� = 0;
			�Ѹż��ݾ� = 0;
			���� = 0;
			�Ѻ������� = 0;
			���ͺб��� = 0;
			������ = 0;
			�򰡱ݾ� = 0;
			���ͷ� = 0;
			�ŵ����ɼ��� = 0;
			�����ݾ� = 0;
			��� = 0;
			
			CSVReader detailReader1 = new CSVReader(new FileReader(tradeHistory));
			String[] nextRecord1;
			while ((nextRecord1 = detailReader1.readNext()) != null) {
				
				�ŷ��ݾ� = Integer.parseInt(nextRecord1[0]);
				���� = Integer.parseInt(nextRecord1[1]); // ��ȣ�� ����
				���尡 = Crawl.getPrice();
				int���尡 = Integer.parseInt(���尡[0]);

				�Ѻ������� += ����;
				
				//�ϴ��� �ѹ� �� ��ĵ �ؾߵ� �ֳĸ� �߰��� ������ 0�� �Ǿ��� �� ���������� �ȵǴϱ� ó������ ������ �� ���� -> �ٽ� ���� �ٶ�/ �߰��� 0�� �Ǹ� �� �ϰ���
				//������ ����ߴ��� 0�̸� ���������� �ƴϸ� ���ͷ� ����� ���� ������ �����͸� ����ؾ���
				
				
				
				if (�Ѻ������� == 0) {
					
					������ += -(�ŷ��ݾ� * ����); //�ŵ� �ŷ� ������ ����� ���� �����ݿ� �ݿ���Ŵ
					�Ѻ������� = 0;
					�����ݾ� = 0;
					���ͺб��� = 0;
					�Ѹż��ݾ� = 0;
					�򰡼��� = 0;
					���ͷ� = 0;
					�򰡱ݾ� = 0;
					�ŵ����ɼ��� = 0;
					
				}
				else {

					�����ݾ� += �ŷ��ݾ� * ����;

					���ͺб��� = �����ݾ� / �Ѻ�������;

					�Ѹż��ݾ� = (int)���ͺб��� * �Ѻ�������;//**********************

					���ͷ� = (�򰡼��� / �����ݾ�) * 100;//*******************

					������ = 100000000 - �����ݾ�; // �ŵ��� �켼�ϸ� �����ݾ��� - ��ȣ��

					�򰡱ݾ� = �Ѻ������� * int���尡;

					�ŵ����ɼ��� = �Ѻ�������;
					
				}
				writer = new FileWriter(sumFile, false);
				writer.write(������ + "\n" + �򰡼��� + "\n" + ���ͷ� + "\n" + �Ѻ������� + "\n" + �򰡱ݾ� + "\n" + �ŵ����ɼ��� + "\n" + �Ѹż��ݾ� + "\n" + ���ͺб���);
				writer.close();
			}
		} catch (IOException | CsvValidationException e1) {
			e1.printStackTrace();
		}
	}
}
