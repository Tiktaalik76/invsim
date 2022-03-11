package board;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class CSVTools {

	public static int findLastRow(CSVReader reader) throws CsvValidationException, IOException {
		int rows = 0;
		while ((reader.readNext())!= null) {rows++;}
		return rows; // not count empty line, start with 1;
	}
	
	public static int findLastRow(String filePath) throws CsvValidationException, IOException {
		int rows = 0;
		CSVReader reader = new CSVReader(new FileReader(filePath));
		while ((reader.readNext())!= null) {rows++;}
		return rows; // not count empty line, start with 1;
	}
	
	public static String[] read1Row(String filePath, int skips) throws CsvValidationException, IOException {
		String[] temp;
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(skips).build();
		temp = reader.readNext();
		return temp; //start with 1;
	}
	
	public static List<String[]> lines() throws IOException, CsvException {
		CSVReader reader = new CSVReader(new FileReader("C:\\Users\\cms\\eclipse-workspace\\sumHistory.csv"));
		List<String[]> temp = reader.readAll();
		return temp;
	}
}
