package board;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class SecChart extends JPanel {
	DynamicTimeSeriesCollection dataSet;

	public SecChart() {
		super();
		String title = "";
		JFreeChart chart;
		dataSet = new DynamicTimeSeriesCollection(1, 1000, new Second());
		Date date = new Date();
		dataSet.setTimeBase(new Second(date));
		dataSet.addSeries(new float[1], 0, title);
		chart = ChartFactory.createTimeSeriesChart(title, "Time", "Price", dataSet, false, true, false);
		final XYPlot plot = chart.getXYPlot();

		// x��
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("s")); // Ÿ�� ����
		axis.setFixedAutoRange(100000);
		DateTickUnit unit = new DateTickUnit(DateTickUnit.SECOND,7);
		axis.setTickUnit(unit);

		// y��
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		DecimalFormat dec = new DecimalFormat();
		rangeAxis.setNumberFormatOverride(dec);
		final ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);
	}

	public static double[] readDataFromCsv(String filePath) throws IOException, Throwable {
		int skips = 0;
		skips = CSVTools.findLastRow(filePath);
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(skips-1).build();
		String[] nextLine;
		nextLine = reader.readNext();
		double times = Double.parseDouble(nextLine[0]);
		double prices = Double.parseDouble(nextLine[1]);
		return new double[] { times, prices };
	}

	public void update(String filepath) throws IOException, Throwable {
		double[] temp = new double[2];
		float[] newData = new float[1];
		temp = readDataFromCsv(filepath);
		newData[0] = (float) temp[1]; // [0] :�ð�, [1] :����
		dataSet.advanceTime(); // x�� �ð��� �ϳ��߰�
		dataSet.appendData(newData);// ��Ʈ�� ���ο� y�� ������ ������Ʈ
	}
}
