package board;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class DailyChartPanel extends JPanel {

	public static int mStockCodeIsUpdated = 0;
	public static String mStockCode;
	public static JPanel topPanel = new JPanel();
	private static double mLowest;
	private static double mHighest;
	private static Date mEnd;
	private static Date mStart;

	public DailyChartPanel() throws CsvValidationException, IOException {

		// crawl data
		String file = "C:\\Users\\iic\\eclipse-workspace\\bowl\\historicaldata.csv";
		FileWriter writer = new FileWriter(file, false);
		String historicalData = getHistoricalData();
		writer.write(historicalData);
		writer.close();

		// set data
		XYDataset dataSet = getDataSet();

		// renderer
		CandlestickRenderer renderer = new CandlestickRenderer();
		renderer.setDrawVolume(false);
		renderer.setSeriesPaint(0, Color.BLACK);
		renderer.setUpPaint(new Color(0xFF0000));
		renderer.setDownPaint(new Color(0x0000FF));

		// x-axis
		setLayout(new FlowLayout());
		DateAxis domainAxis = new DateAxis("Date");
		domainAxis.setRange(mStart, mEnd);

		// y-axis
		NumberAxis rangeAxis = new NumberAxis("Price");
		rangeAxis.setLowerBound(mLowest);
		rangeAxis.setUpperBound(mHighest);

		// apply to panel
		XYPlot mainPlot = new XYPlot(dataSet, domainAxis, rangeAxis, renderer);
		JFreeChart chart = new JFreeChart(null, null, mainPlot, false);
		final ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);
	}

	private static AbstractXYDataset getDataSet() {
		DefaultOHLCDataset dataSet;
		OHLCDataItem[] data;
		data = getData();
		dataSet = new DefaultOHLCDataset("", data);
		return dataSet;
	}

	private static OHLCDataItem[] getData() {
		String file = "C:\\Users\\iic\\eclipse-workspace\\bowl\\historicaldata.csv";
		ArrayList<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();

		try {
			DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder().parseCaseInsensitive()
					.parseLenient().appendPattern("[MMM d yyyy]");
			DateTimeFormatter format = formatterBuilder.toFormatter(Locale.ENGLISH);

			double open;
			double high;
			double low;
			double close;
			double volume;

			String[] arrangedLine = null;
			String[] line1;
			String line3;

			CSVReader reader1 = new CSVReader(new FileReader(file));
			while ((line1 = reader1.readNext()) != null) {

				line3 = String.join("", line1);
				arrangedLine = line3.split("@");

				if ((arrangedLine[1].contains("Dividend")) || (arrangedLine[1].equals("-"))) {
					continue;
				}

				mLowest = Double.parseDouble(arrangedLine[3]);
				mHighest = Double.parseDouble(arrangedLine[2]);

				LocalDate dateTime = LocalDate.parse(arrangedLine[0], format);
				mEnd = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

				reader1.close();
				break;
			}

			line1 = Tools.readOneRow(file, Tools.findLastRow(file) - 1);
			line3 = String.join("", line1);
			arrangedLine = line3.split("@");
			LocalDate dateTime = LocalDate.parse(arrangedLine[0], format);
			mStart = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

			CSVReader reader2 = new CSVReader(new FileReader(file));
			while ((line1 = reader2.readNext()) != null) {

				line3 = String.join("", line1);
				arrangedLine = line3.split("@");

				if ((arrangedLine[1].contains("Dividend")) || (arrangedLine[1].equals("-"))) {
					continue;
				}

				dateTime = LocalDate.parse(arrangedLine[0], format);
				Date date = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
				open = Double.parseDouble(arrangedLine[1]);
				high = Double.parseDouble(arrangedLine[2]);
				low = Double.parseDouble(arrangedLine[3]);
				close = Double.parseDouble(arrangedLine[4]);
				volume = Double.parseDouble(arrangedLine[5]);

				OHLCDataItem item = new OHLCDataItem(date, open, high, low, close, volume);

				dataItems.add(item);

				if (mLowest > low) {
					mLowest = low;
				}

				else if (mHighest < high) {
					mHighest = high;
				}
			}

			reader2.close();

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		Collections.reverse(dataItems);

		OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
		return data;
	}
	private static String getHistoricalData() throws CsvValidationException, IOException {
		String url1 = "https://finance.yahoo.com/quote/";
		String url2 = "/history?p=";
		String file = "C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCode.csv";

		String stockCode = Tools.readOneFactor(file, 0, 0);

		String segment = stockCode + ".KS";
		String url = url1 + segment + url2 + segment;

		Document doc = Jsoup.connect(url).timeout(100000).get();

		Elements stockTableBody = doc.select("table tbody tr");
		StringBuilder stringBuilder = new StringBuilder();
		for (Element element : stockTableBody) {
			for (Element td : element.select("td")) {
				String text;
				text = td.text();
				stringBuilder.append(text);
				stringBuilder.append("@");
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}
