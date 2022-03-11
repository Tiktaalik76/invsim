package board;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class Tools {

	public static int findLastRow(String filePath) throws CsvValidationException, IOException {
		int rows = 0;
		CSVReader reader = new CSVReader(new FileReader(filePath));
		while ((reader.readNext()) != null) {
			rows++;
		}

		return rows; // not count last empty line, start with 1;
	}

	public static String[] readOneRow(String filePath, int lineSkips) throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(lineSkips).build();

		return reader.readNext();
	}

	public static String readOneFactor(String filePath, int lineSkips, int index)
			throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(lineSkips).build();
		String[] temp = reader.readNext();

		return temp[index];
	}

	public static List<String[]> readAll(String stockCode) throws IOException, CsvException {
		CSVReader reader = new CSVReader(
				new FileReader("C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode + "userDetails.csv"));
		List<String[]> temp = reader.readAll();
		return temp;
	}

	public static void deleteInternalFiles(String filePath) {
		File dir = new File(filePath);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			File file = fileList[i];
			file.delete();
		}
	}
}
