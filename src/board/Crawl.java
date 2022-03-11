package board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawl {
	public static String getPrice(String stockCode, int i) throws IOException {
		String url = "https://finance.naver.com/item/sise.naver?code="+stockCode;

		Document doc = Jsoup.connect(url).get();
	
		Elements element = doc.select("div.rate_info");
		Elements ie = element.select("p.no_today");
			
		String tempString1 = ie.text(); 					// "212,000 212,000"
		String[] tempArray1 = tempString1.split(" "); 		// "212,000 212,000" 	-> "212,000", "212,000"
		String[] tempArray2 = tempArray1[0].split(","); 	// "212,000", "212,000" -> "212", "000"
		String tempString2 = String.join("", tempArray2); 	// "212", "000" 		-> "212000"
			
		String[] currentPrice = {tempString2, tempArray1[0]};

		return currentPrice[i]; // { "212000", "212,000" }
	}

	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat simplify = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		
		return (String) simplify.format(currentTime);
	}

	public static String getStockName(String stockCode) throws IOException {
		String url = "https://finance.naver.com/item/main.naver?code="+stockCode;
		
		Document doc = Jsoup.connect(url).get();
		
		Elements element = doc.select("div.wrap_company");
		Elements ie = element.select("a");
		
		return ie.text();
	}
}
