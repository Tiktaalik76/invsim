package stocksimulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

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


public class Chart extends JPanel {
	public JPanel ChartPanel;
	private final DynamicTimeSeriesCollection dataset;
	private final JFreeChart chart;
	static int skips = 0;
	
	public Chart(JPanel ChartPanel) {
		this.ChartPanel = ChartPanel;
		this.dataset = null;
		this.chart = null;
	}
	
	public static double[] readDataFromCsv(String filePath) throws IOException, Throwable {
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(skips).build();
		String[] nextLine;
		nextLine = reader.readNext();
		double times = Double.parseDouble(nextLine[0]);
		double prices = Double.parseDouble(nextLine[1]);

		return new double[] { times, prices };
	}
	
	public Chart(final String title) {
		dataset = new DynamicTimeSeriesCollection(1, 1000, new Second()); // n개의 종목, n개의 저장용량, x축 기본단위
		dataset.setTimeBase(new Second(Calendar.getInstance().get(Calendar.SECOND),
				Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
				Calendar.getInstance().get(Calendar.DATE), 8, Calendar.getInstance().get(Calendar.YEAR))); // 시작 시기
		dataset.addSeries(new float[1], 0, title);
		chart = ChartFactory.createTimeSeriesChart(title, "Time", "Price", dataset, true, true, false);
		final XYPlot plot = chart.getXYPlot();

		// x축
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("ss.SS")); // 타임 포맷
		ValueAxis vaxis = (ValueAxis) plot.getDomainAxis();
		vaxis.setFixedAutoRange(10000);

		// y축
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		DecimalFormat dec = new DecimalFormat();
		rangeAxis.setNumberFormatOverride(dec);
		final ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);
	}
	
	public void update(String filepath) throws IOException, Throwable {
		double[] temp = new double[2];
		float[] newData = new float[1];
		temp = readDataFromCsv(filepath);
		newData[0] = (float) temp[1]; // [0] :시간, [1] :가격
		dataset.advanceTime(); // x축 시간을 하나추가
		dataset.appendData(newData);// 차트에 새로운 y축 데이터 업데이트
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("Chart");
				frame.setLayout(new GridLayout(2,2)); // 큰 틀도 레이아웃 설정해줘야됨
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				final Chart chart = new Chart("");
				
				final JPanel gjp = new JPanel(new GridLayout(2,2));
				gjp.add(new JLabel("여기는"));
				gjp.add(new JButton("b1"));
				gjp.add(new JLabel("그리드"));
				gjp.add(new JButton("b2"));
				frame.add(gjp);
				frame.add(chart);
				frame.setSize(1000, 1000);
				Timer timer = new Timer(7000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							chart.update("C:\\Users\\cms\\eclipse-workspace\\out.csv");
							skips++;
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (Throwable e1) {
							e1.printStackTrace();
						}
					}
				});
				timer.start();
				frame.setVisible(true);
			}
		});
	}
}