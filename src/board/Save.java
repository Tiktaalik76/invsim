package board;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

public class Save extends Thread {
	private static String mStockCode;

	@Override
	public void run() {
		String writeFilePath = "C:\\Users\\cms\\eclipse-workspace\\bowl\\dataList.csv";
		String readFilePath = "C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCode.csv";

		while (true) {
			try {
				mStockCode = Tools.readOneFactor(readFilePath, 0, 0);

				// time
				String[] temparr1 = Crawl.getTime().split("-");
				String currentTime = String.join("", temparr1);

				// file write
				FileWriter writer = new FileWriter(writeFilePath, true);
				writer.write(currentTime + "," + Crawl.getPrice(mStockCode, 0) + "\n");
				writer.close();

				Thread.sleep(1000);

				if (!this.isAlive())
					break;

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (CsvValidationException e) {
				e.printStackTrace();
			}
		}
	}
}
