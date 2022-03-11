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

	int 시장가;
	int 거래금액 = 0;
	int 수량 = 0;
	int 총보유수량;
	int 매수금액;
	
	double 손익분기점 = 0;
	int 평가금액 = 0;
	double 수익률 = 0;
	int 예수금;
	int 매도가능수량 = 0;
	int 평가손익 = 0;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			매수금액 = 0;
			총보유수량 = 0;
			예수금 = 100000000;
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
							JOptionPane.showMessageDialog(null, "매수가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);
						
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
								JOptionPane.showMessageDialog(null, "매도가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);
							}
						}
						catch (IOException e1) {e1.printStackTrace();}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "수량을 다시 입력하세요" + "\n" + "수량은 양의 정수" + "\n" + "수량 입력 후 엔터 입력", "안내", JOptionPane.PLAIN_MESSAGE);
				}
			}

			CSVReader detailReader = new CSVReader(new FileReader(transactionDetailsFilePath));
			String[] readNext;
			while ((readNext = detailReader.readNext()) != null) {
				
				시장가 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));
				거래금액 = Integer.parseInt(readNext[0]);
				수량 = Integer.parseInt(readNext[1]);
				// 수량 부호
				// buy  : +
				// sell : -
				
				총보유수량 += 수량;
				매수금액 += 거래금액 * 수량;

				if (총보유수량 == 0) {
					예수금 += -(매수금액);
					// 예수금 이외 거래 내역 초기화
					
					총보유수량 = 0;
					매수금액 = 0;
					손익분기점 = 0;
					매도가능수량 = 0;
					
				} 
				else {

					손익분기점 = 매수금액 / 총보유수량;
					
					매도가능수량 = 총보유수량;
					
				}
			}
			
			예수금 = 예수금 - 매수금액;
			
			writer = new FileWriter(userDetailsFilePath, false);
			writer.write(예수금 + "\n" + 총보유수량 + "\n" + 매도가능수량 + "\n" + 매수금액 + "\n" + 손익분기점);
			writer.close();
		}
		catch (IOException | CsvValidationException e1) {e1.printStackTrace();}
	}
}
