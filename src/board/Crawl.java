package board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawl {
	public static synchronized String[] getPrice() {
		String url = "https://finance.naver.com/item/sise.naver?code=373220";
		Document doc = null;
		String method_currentPrice[] = { "0", "0" };

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements element = doc.select("div.rate_info");
		Elements ie = element.select("p.no_today");

		String Tempstr1 = ie.text(); // "212,000 212,000"
		String[] Temparr1 = Tempstr1.split(" "); // "212,000 212,000" -> "212,000", "212,000"
		String[] Temparr2 = Temparr1[0].split(","); // "212,000", "212,000" -> "212", "000"
		String Tempstr2 = String.join("", Temparr2); // "212", "000" -> "212000"

		method_currentPrice[0] = Tempstr2;
		method_currentPrice[1] = Temparr1[0];

		return method_currentPrice; // { "212000", "212,000" }
	}
	
	public static synchronized float[] fgetPrice() {
		String url = "https://finance.naver.com/item/sise.naver?code=373220";
		Document doc = null;
		String method_currentPrice[] = { "0", "0" };

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements element = doc.select("div.rate_info");
		Elements ie = element.select("p.no_today");

		String Tempstr1 = ie.text(); // "212,000 212,000"
		String[] Temparr1 = Tempstr1.split(" "); // "212,000 212,000" -> "212,000", "212,000"
		String[] Temparr2 = Temparr1[0].split(","); // "212,000", "212,000" -> "212", "000"
		String Tempstr2 = String.join("", Temparr2); // "212", "000" -> "212000"
		
		float f = Float.parseFloat(Tempstr2);
		float[] ff = null;
		ff[0] = f;
		ff[1] = f;
		return ff;
	}
	
	public static String getHistoricalData() throws IOException {
		String url = "https://finance.yahoo.com/quote/AAPL/history?p=AAPL";
		Document doc = null;
		doc = Jsoup.connect(url).get();
		
		Elements stockTableBody = doc.select("table tbody tr");
		StringBuilder sb = new StringBuilder();
		for(Element element : stockTableBody) {
			for(Element td : element.select("td")) {
				String text;
				text = td.text();
				sb.append(text);
				sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
	public static String getTime() {
		String strCurrentTime;
		Date currentTime = new Date();
		SimpleDateFormat simplify = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		strCurrentTime = (String) simplify.format(currentTime);
		return strCurrentTime;
	}
	
}
