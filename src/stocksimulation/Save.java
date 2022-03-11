package stocksimulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save extends Thread {
	
	@Override
	public void run() {
		while(true) {
			File file = new File("C:\\Users\\cms\\eclipse-workspace\\out.csv");
			String[] currentPrice = Stockstorage.getPricefromsite();
			
			String tempstr1 = TimeChecker.currentTime;				//"2022-01-05-05-03-30"
			String[] temparr1 = tempstr1.split("-");		//"2022", "01", "05", "05", "03", "30"
			String tempstr2 = String.join("", temparr1);	// "20220105050330"
			
			FileWriter writer = null;

			try {
				writer = new FileWriter(file, true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				writer.write(tempstr2+","+currentPrice[0]+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
