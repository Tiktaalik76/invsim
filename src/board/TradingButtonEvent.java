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

				if (key == "Buy") { // 수량이 없는걸 팔 순 없음 이것에 대한 조건을 추가해야함;
					try {

						writer = new FileWriter(tradeHistory, true);
						writer.write(currentPrice[0] + "," + "+" + quantity + "\n");
						writer.close();
						JOptionPane.showMessageDialog(null, "매수가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (key == "Sell") {
					try {
						writer = new FileWriter(tradeHistory, true);
						writer.write(currentPrice[0] + "," + "-" + quantity + "\n");
						writer.close();
						JOptionPane.showMessageDialog(null, "매도가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}

			else {
				JOptionPane.showMessageDialog(null, "수량을 다시 입력하세요", "안내", JOptionPane.PLAIN_MESSAGE);
			}
			FileWriter writeback = new FileWriter(file, false);
			writeback.write("0");
			writeback.close();

			CSVReader detailReader1 = new CSVReader(new FileReader(tradeHistory));
			CSVReader detailReader2 = new CSVReader(new FileReader(tradeHistory));
			String[] nextRecord1;
			String[] nextRecord2;
			
			int 거래금액 = 0;
			int 총매수금액 = 0;
			int 수량 = 0;
			int 총보유수량 = 0;
			int 손익분기점 = 0;
			int 예수금 = 0;
			int 평가손익 = 0;
			String[] 시장가;
			int int시장가;
			int 평가금액 = 0;
			double 수익률 = 0;
			int 매도가능수량 = 0;
			int 변동금액 = 0;
			int rows = 0;
			
			while ((nextRecord1 = detailReader1.readNext()) != null) {
				
				거래금액 = Integer.parseInt(nextRecord1[0]);
				수량 = Integer.parseInt(nextRecord1[1]); // 부호가 있음
				시장가 = Crawl.getPrice();
				int시장가 = Integer.parseInt(시장가[0]);

				총보유수량 += 수량;
				
				if (총보유수량 == 0) {
					
					예수금 += -(거래금액 * 수량); //매도 거래 내역을 지우기 전에 예수금에 반영시킴
					CSVReader allReader = new CSVReader(new FileReader(tradeHistory));
					List<String[]> allElements = allReader.readAll();
					
					for (int i = 0; i<=rows; i++ ) {
						allElements.remove(0);
					}
					
					CSVWriter aWriter = new CSVWriter(new FileWriter(tradeHistory), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
					aWriter.writeAll(allElements);
					aWriter.close();
					
					
					while((nextRecord2 = detailReader2.readNext()) != null) {
						거래금액 = Integer.parseInt(nextRecord2[0]);
						수량 = Integer.parseInt(nextRecord2[1]); // 부호가 있음
						시장가 = Crawl.getPrice();
						int시장가 = Integer.parseInt(시장가[0]);

						총보유수량 += 수량;
						
						변동금액 += 거래금액 * 수량;

						손익분기점 = 변동금액 / 총보유수량;

						총매수금액 = 손익분기점 * 총보유수량;

						평가손익 = 총보유수량 * int시장가 - 총매수금액;

						수익률 = (평가손익 / 변동금액) * 100;

						예수금 = 100000000 - 변동금액; // 매도가 우세하면 변동금액이 - 부호임

						평가금액 = 총보유수량 * int시장가;

						매도가능수량 = 총보유수량;
					}
				}
				else {

					변동금액 += 거래금액 * 수량;

					손익분기점 = 변동금액 / 총보유수량;

					총매수금액 = 손익분기점 * 총보유수량;

					평가손익 = 총보유수량 * int시장가 - 총매수금액;

					수익률 = (평가손익 / 변동금액) * 100;

					예수금 = 100000000 - 변동금액; // 매도가 우세하면 변동금액이 - 부호임

					평가금액 = 총보유수량 * int시장가;

					매도가능수량 = 총보유수량;
				}
				FileWriter sumWriter;

				sumWriter = new FileWriter(sumFile, false);
				sumWriter.write(예수금 + "\n" + 평가손익 + "\n" + 수익률 + "%" + "\n" + 총보유수량 + "\n" + 평가금액 + "\n" + 매도가능수량 + "\n"
						+ 총매수금액 + "\n" + 손익분기점);

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
