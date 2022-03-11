package board;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Save extends Thread {
	@Override
	public void run() {
		File file = new File("C:\\Users\\cms\\eclipse-workspace\\out.csv");
		String url = "https://finance.naver.com/item/sise.naver?code=373220";

		while (true) {
			try {
				/* Price */
				Document doc = Jsoup.connect(url).get();
				Elements element = doc.select("div.rate_info");
				Elements ie = element.select("p.no_today");
				String Tempstr1 = ie.text();
				String[] Temparr1 = Tempstr1.split(" ");
				String[] Temparr2 = Temparr1[0].split(",");
				String Tempstr2 = String.join("", Temparr2);
				String currentPrice = Tempstr2;

				/* Time */
				Date currentDate = new Date();
				SimpleDateFormat simplify = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String currentTime = (String) simplify.format(currentDate);
				String[] temparr1 = currentTime.split("-");
				String tempstr1 = String.join("", temparr1);
				currentTime = tempstr1;

				/* Write */
				FileWriter writer = new FileWriter(file, true);
				writer.write(currentTime + "," + currentPrice + "\n");
				writer.close();

				Thread.sleep(7000);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
