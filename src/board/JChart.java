package board;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class JChart extends JPanel {
	DynamicTimeSeriesCollection dataSet;

	public JChart() {
		super();
		String title = "";
		setLayout(new BorderLayout());
		JFreeChart chart;
		dataSet = new DynamicTimeSeriesCollection(1, 1000, new Second());
		dataSet.setTimeBase(new Second(Calendar.getInstance().get(Calendar.SECOND),
				Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
				Calendar.getInstance().get(Calendar.DATE), 8, Calendar.getInstance().get(Calendar.YEAR)));
		dataSet.addSeries(new float[1], 0, title);
		chart = ChartFactory.createTimeSeriesChart(title, "Time", "Price", dataSet, true, true, false);
		final XYPlot plot = chart.getXYPlot();

		// x축
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("ss.SS")); // 타임 포맷
		ValueAxis vaxis = (ValueAxis) plot.getDomainAxis();
		vaxis.setFixedAutoRange(10000);
		add(getScrollBar(vaxis),BorderLayout.SOUTH);

		// y축
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
		newData[0] = (float) temp[1]; // [0] :시간, [1] :가격
		dataSet.advanceTime(); // x축 시간을 하나추가
		dataSet.appendData(newData);// 차트에 새로운 y축 데이터 업데이트
	}
	
	private JScrollBar getScrollBar(final ValueAxis domainAxis) {
		final double r1 = domainAxis.getLowerBound();
		final double r2 = domainAxis.getUpperBound();
	    JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 100, 0, 400);
	    scrollBar.addAdjustmentListener( new AdjustmentListener() {
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            double x = e.getValue();
	            domainAxis.setRange(r1+x, r2+x);
	        }
	    });
	    return scrollBar;
		
	}
}
