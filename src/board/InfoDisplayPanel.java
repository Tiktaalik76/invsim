package board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.opencsv.exceptions.CsvValidationException;

public class InfoDisplayPanel extends JPanel {
	public static String mStockCode;
	public static int mStockCodeIsUpdated = 0;
	public static String cash;
	public static int mCashIsUpdated = 0;
	
	JLabel timeLabel = new JLabel();
	JLabel cashGuideLabel = new JLabel();
	JLabel cashLabel = new JLabel();
	JPanel cashPanel = new JPanel();
	JLabel priceGuideLabel = new JLabel();
	JTextField priceLabel = new JTextField();
	
	String stockCodeFilePath = "C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCode.csv";
	String cashFilePath =  "C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv";
	
	public InfoDisplayPanel() throws CsvValidationException, IOException {
		
		mStockCode = Tools.readOneFactor(stockCodeFilePath, 0, 0);
		cash = Tools.readOneFactor(cashFilePath, 0, 0);
		
		EtchedBorder eborder;
		eborder = new EtchedBorder(EtchedBorder.RAISED);

		add(timeLabel);
		add(cashPanel);
		add(priceLabel);
		add(priceGuideLabel);
		setLayout(null);
		setSize(100, 100); // (300, 370)
		
		timeLabel.setLocation(0, 0);
		timeLabel.setSize(300, 40);
		timeLabel.setFont(new Font("Gothic", Font.ITALIC, 20));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		timeLabel.setBorder(eborder);
		
		
		cashPanel.add(cashGuideLabel);
		cashPanel.add(cashLabel);
		cashPanel.setLayout(new GridLayout());
		cashPanel.setLocation(0, 45);
		cashPanel.setSize(300, 40);
		cashPanel.setBorder(eborder);
		cashPanel.setBackground(Color.WHITE);
		
		cashGuideLabel.setText("예수금");
		cashLabel.setText(cash);

		priceGuideLabel.setLocation(0, 310);
		priceGuideLabel.setSize(80, 60);
		priceGuideLabel.setText("시장가");
		priceGuideLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

		priceLabel.setLocation(90, 310);
		priceLabel.setSize(210, 60);
		priceLabel.setFont(new Font("Gothic", Font.ITALIC, 25));
		priceLabel.setHorizontalAlignment(JLabel.CENTER);
		priceLabel.setText(Crawl.getPrice(mStockCode, 1));

		Thread updateThread = new Thread(new Update());
		updateThread.start();
	}

	class Update implements Runnable {

		@Override
		public void run() {

			while (true) {
				try {
					if (mStockCodeIsUpdated == 1) {
						mStockCode = Tools.readOneFactor(stockCodeFilePath, 0, 0);
						mStockCodeIsUpdated = 0;
					}
					
					if (mCashIsUpdated == 1) {
						cashLabel.setText(Tools.readOneFactor(cashFilePath, 0, 0));
						mCashIsUpdated = 0;
					}

					timeLabel.setText(Crawl.getTime());
					priceLabel.setText(Crawl.getPrice(mStockCode, 1));

					Thread.sleep(1000);

				} catch (IOException | CsvValidationException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
