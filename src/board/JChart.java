package board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.swing.JScrollBar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class JChart extends ChartPanel {

	public JChart() {
		super(null);
		DateAxis domainAxis = new DateAxis("Date");
		NumberAxis rangeAxis = new NumberAxis("Price");
		CandlestickRenderer renderer = new CandlestickRenderer();

		DefaultOHLCDataset result;
		OHLCDataItem[] data; 
		String file = "asdf";
		FileWriter writer = new FileWriter(file, false);
		writer.write(Crawl.getHistoricalData());
		writer.close();
		
		CSVReader reader = new CSVReader(new FileReader(file));
		String[] line;
		DateFormat df = new SimpleDateFormat("y-M-d");
		Date date;
		double open;
		double high;
		double low;
		double close;
		double volume;

		ArrayList<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
		
		while((line = reader.readNext()) != null) {
			date = df.parse(line[0]);
			open = Double.parseDouble(line[1]);
			high = Double.parseDouble(line[2]);
			low = Double.parseDouble(line[3]);
			close = Double.parseDouble(line[4]);
			volume = Double.parseDouble(line[6]);

			OHLCDataItem item = new OHLCDataItem(date,open,high,low,close,volume);
			dataItems.add(item);
		}
		Collections.reverse(dataItems);
		OHLCDataItem[] data2 = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
		
		XYDataset dataset = data2;
		XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
		
		renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setDrawVolume(false);
        rangeAxis.setAutoRangeIncludesZero(false);

        //Now create the chart and chart panel
        JFreeChart chart = new JFreeChart(null, null, mainPlot, false);
        mainPlot.setDomainPannable(true);
        mainPlot.setRangePannable(true);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		super(null);
		String title = "";
		XYSeriesCollection dataSet = new XYSeriesCollection();
		JFreeChart mainChart = ChartFactory.createXYLineChart(title, "time", "price", dataSet, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(mainChart);
		XYPlot plot = mainChart.getXYPlot();
		
		
		
		
		
		
		
		
		
		
		
		setChart(ChartFactory.createXYLineChart(title, "time", "price", dataSet, PlotOrientation.VERTICAL, true, true, false));
		XYPlot plot = (XYPlot) getChart().getPlot();
		DefaultXYItemRenderer renderer = new DefaultXYItemRenderer();
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);
		setDomainZoomable(true);
		setRangeZoomable(false);
		ValueAxis axis = plot.getDomainAxis();
		axis.setFixedAutoRange(10000);
		axis = plot.getRangeAxis();
		axis.setRange(100,100);
		XYSeries xys = new XYSeries("asdf");
		xys.add(1,1);
		
		dataSet.addSeries(xys);
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesLinesVisible(0, false);
		renderer.setSeriesVisibleInLegend(0, false);
		
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
		 */
		
	}
}
