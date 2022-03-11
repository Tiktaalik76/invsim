package board;

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

public class SecChart extends JPanel {
	DynamicTimeSeriesCollection mDataSet;

	public SecChart() {
		super();
		
		// chart
		String title = "";
		mDataSet = new DynamicTimeSeriesCollection(1, 1000, new Second());
		Date date = new Date();
		mDataSet.setTimeBase(new Second(date));
		mDataSet.addSeries(new float[1], 0, title);
		
		JFreeChart chart;
		chart = ChartFactory.createTimeSeriesChart(title, "Time", "Price", mDataSet, false, true, false);
		XYPlot plot = chart.getXYPlot();

		// x-axis
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("s"));
		axis.setFixedAutoRange(100000);
		@SuppressWarnings("deprecation")
		DateTickUnit unit = new DateTickUnit(DateTickUnit.SECOND, 7);
		axis.setTickUnit(unit);

		// y-axis
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		DecimalFormat dec = new DecimalFormat();
		rangeAxis.setNumberFormatOverride(dec);
		
		// apply to panel
		final ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);
	}

	public void update() throws IOException, Throwable {
		String file = "C:\\Users\\cms\\eclipse-workspace\\datalist.csv";
		
		String[] line = CSVTools.readOneRow(file, CSVTools.findLastRow(file)-1);
		float[] tempArray = {Float.parseFloat(line[1])};
		
		mDataSet.advanceTime(); // x축 시간을 하나추가
		mDataSet.appendData(tempArray);// 차트에 새로운 y축 데이터 업데이트
	}
}
