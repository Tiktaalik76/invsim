package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.JOptionPane;

public class TradingButtonEvent implements ActionListener {

	File buyHistory = new File("C:\\Users\\cms\\eclipse-workspace\\buyHistory.csv");
	File sellHistory = new File("C:\\Users\\cms\\eclipse-workspace\\sellHistory.csv");
	String quantityFilePath = "C:\\Users\\cms\\eclipse-workspace\\quantity.csv";

	String[] currentPrice;

	@Override
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		FileWriter writer;
		currentPrice = Crawl.getPrice();
		File file = new File("C:\\Users\\cms\\eclipse-workspace\\quantity.csv");
		try {
			CSVReader reader = new CSVReaderBuilder(new FileReader(quantityFilePath)).build();
			String[] nextLine;
			nextLine = reader.readNext();
			int quantity = Integer.parseInt(nextLine[0]);

			if (quantity > 0) {

				if (key == "Buy") {
					try {

						writer = new FileWriter(buyHistory, true);
						writer.write(currentPrice[0] + "," + quantity+"\n");
						writer.close();
						JOptionPane.showMessageDialog(null, "매수가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);
						FileWriter writeback = new FileWriter(file, false);
						writeback.write("0");
						writeback.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else if (key == "Sell") {
					try {
						writer = new FileWriter(sellHistory, true);
						writer.write(currentPrice[0] + "," + quantity+"\n");
						writer.close();
						JOptionPane.showMessageDialog(null, "매도가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);
						FileWriter writeback = new FileWriter(file, false);
						writeback.write("0");
						writeback.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "수량을 다시 입력하세요", "안내", JOptionPane.PLAIN_MESSAGE);
			}

		} catch (IOException | CsvValidationException e2) {
			e2.printStackTrace();
		}
	}
}
