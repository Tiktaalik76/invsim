package board;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class Update {
	public Update() {}
	static String filePath = "C:\\Users\\cms\\eclipse-workspace\\sumHistory.csv";
	
	
	public static String bring������() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(0).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}
	
	public static String bring�򰡼���() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring���ͷ�() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(2).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring��������() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(3).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring�򰡱ݾ�() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(4).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring�ŵ����ɼ���() throws CsvValidationException, IOException {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(5).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring�ż��ݾ�() throws CsvValidationException, IOException { //��ư ���� ������ ������Ʈ ��
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(6).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}

	public static String bring���ͺб���() throws CsvValidationException, IOException { // ��ư ���� ������ ������Ʈ ��
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(7).build();
		String[] nextLine;
		nextLine = reader.readNext();
		return nextLine[0];
	}
}
