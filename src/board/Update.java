package board;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class Update {
	public Update() {}
	static String filePath = "C:\\Users\\cms\\eclipse-workspace\\sumHistory.csv";
	
	
	public static String bring예수금() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(0).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}
	
	public static String bring평가손익() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring수익률() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(2).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring보유수량() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(3).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring평가금액() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(4).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring매도가능수량() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(5).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring매수금액() throws CsvValidationException, IOException { //버튼 누를 때마다 업데이트 됨
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(6).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring손익분기점() throws CsvValidationException, IOException { // 버튼 누를 때마다 업데이트 됨
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(7).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}
}
