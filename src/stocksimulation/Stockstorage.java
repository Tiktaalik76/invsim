/* Name : Stockstorage.java
 * Comtent : html으로부터 주가 정보 가져오기
 * Implementation : CMS
 *
 * Last modified 2022/01/05
 */

package stocksimulation;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Stockstorage {

	public static String[] getPricefromsite() {
		String url = "https://finance.naver.com/item/sise.naver?code=066570";
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements element = doc.select("div.rate_info");
		Elements ie = element.select("p.no_today");

		String Tempstr1 = ie.text();					// "212,000 212,000"
		String[] Temparr1 = Tempstr1.split(" ");		// "212,000 212,000" 	-> "212,000", "212,000"
		String[] Temparr2 = Temparr1[0].split(",");		// "212,000", "212,000" -> "212", "000"
		String Tempstr2 = String.join("", Temparr2);	// "212", "000" 		-> "212000"

		String method_currentPrice[] = {"0", "0"};
		
		method_currentPrice[0] = Tempstr2;
		method_currentPrice[1] = Temparr1[0];
		
		return method_currentPrice; // { "212000", "212,000" }
	}
}