package board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileReader;
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

import com.opencsv.CSVReader;

public class DayChart extends JPanel {
	
	public DayChart() {
		super(null);
		setLayout(new BorderLayout());
		DateAxis domainAxis = new DateAxis("Date");
		NumberAxis rangeAxis = new NumberAxis("Price");
		domainAxis.setAutoRange(true);
		rangeAxis.setAutoRange(true);
		
		CandlestickRenderer renderer = new CandlestickRenderer();
		XYDataset dataset = getDataSet();
		XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

		renderer.setDrawVolume(true);
		renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
		JFreeChart chart = new JFreeChart(null, null, mainPlot, false);
		mainPlot.setDomainPannable(true);
		mainPlot.setRangePannable(true);
		final ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);
	}

	public static AbstractXYDataset getDataSet() {
		DefaultOHLCDataset result;
		OHLCDataItem[] data;
		data = getData();
		result = new DefaultOHLCDataset("asdf", data);
		return result;
	}

	public static OHLCDataItem[] getData() {
		String file = "C:\\Users\\cms\\eclipse-workspace\\ffff.csv";
		ArrayList<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
		try {
			CSVReader reader = new CSVReader(new FileReader(file));

			DateTimeFormatterBuilder formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().parseLenient()
					.appendPattern("[MMM d yyyy]");

			DateTimeFormatter formatter2 = formatter.toFormatter(Locale.ENGLISH);

			double open;
			double high;
			double low;
			double close;
			double volume;
			String[] line2;
			String[] line;
			String line3;
			
			while ((line = reader.readNext()) != null) {

				line3 = String.join("", line);
				line2 = line3.split("@");

				if ((line2[1].contains("Dividend"))||(line2[1].equals("-"))) {
					continue;
				}
				LocalDate dateTime = LocalDate.parse(line2[0], formatter2);
				Date date = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
				open = Double.parseDouble(line2[1]);
				high = Double.parseDouble(line2[2]);
				low = Double.parseDouble(line2[3]);
				close = Double.parseDouble(line2[4]);
				volume = Double.parseDouble(line2[5]);
				OHLCDataItem item = new OHLCDataItem(date, open, high, low, close, volume);
				dataItems.add(item);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		Collections.reverse(dataItems);

		OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
		return data;
	}
}
