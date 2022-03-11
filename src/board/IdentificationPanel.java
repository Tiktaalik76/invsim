package board;

import java.awt.GridLayout;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class IdentificationPanel extends JPanel {

	public static String mStockCode;
	public static int mStockCodeIsUpdated = 0;

	JLabel m평가손익GuideLabel = new JLabel();
	JLabel m평가손익Label = new JLabel();
	JLabel m보유수량GuideLabel = new JLabel();
	JLabel m매도가능수량GuideLabel = new JLabel();
	JLabel m손익분기점GuideLabel = new JLabel();
	JLabel m수익률GuideLabel = new JLabel();
	JLabel m수익률Label = new JLabel();
	JLabel m평가금액GuideLabel = new JLabel();
	JLabel m평가금액Label = new JLabel();
	JLabel m매수금액GuideLabel = new JLabel();
	JLabel m현재가GuideLabel = new JLabel();
	JLabel m현재가Label = new JLabel();
	JLabel m보유수량Label = new JLabel();
	JLabel m매도가능수량Label = new JLabel();
	JLabel m매수금액Label = new JLabel();
	JLabel m손익분기점Label = new JLabel();
	JLabel[] labelList = { m보유수량Label, m매도가능수량Label, m매수금액Label, m손익분기점Label };
	JPanel setPanel = new JPanel();

	JLabel stockNameLabel = new JLabel();

	public IdentificationPanel() throws CsvValidationException, IOException {

		IdentificationPanel.mStockCode = Tools.readOneFactor("C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCode.csv",
				0, 0);

		EtchedBorder eborder;
		eborder = new EtchedBorder(EtchedBorder.RAISED);
		setPanel.setBorder(eborder);
		setPanel.setLocation(0, 0);
		setPanel.setLayout(new GridLayout(2, 4));
		setPanel.setSize(700, 120);

		setPanel.add(m평가손익GuideLabel);
		m평가손익GuideLabel.setText("평가손익");
		setPanel.add(m평가손익Label);

		setPanel.add(m보유수량GuideLabel);
		m보유수량GuideLabel.setText("보유수량");
		setPanel.add(m보유수량Label);

		setPanel.add(m매도가능수량GuideLabel);
		m매도가능수량GuideLabel.setText("매도가능수량");
		setPanel.add(m매도가능수량Label);

		setPanel.add(m손익분기점GuideLabel);
		m손익분기점GuideLabel.setText("손익분기점");
		setPanel.add(m손익분기점Label);

		setPanel.add(m수익률GuideLabel);
		m수익률GuideLabel.setText("수익률");
		setPanel.add(m수익률Label);

		setPanel.add(m평가금액GuideLabel);
		m평가금액GuideLabel.setText("평가금액");
		setPanel.add(m평가금액Label);

		setPanel.add(m매수금액GuideLabel);
		m매수금액GuideLabel.setText("매수금액");
		setPanel.add(m매수금액Label);

		setPanel.add(m현재가GuideLabel);
		m현재가GuideLabel.setText("현재가");
		setPanel.add(m현재가Label);

		stockNameLabel.setText(Crawl.getStockName(mStockCode) + "(" + mStockCode + ")");
		stockNameLabel.setHorizontalAlignment(JLabel.RIGHT);
		stockNameLabel.setLocation(500, 120);
		stockNameLabel.setSize(200, 24);

		setLayout(null);
		add(setPanel);
		add(stockNameLabel);

		Thread updateThread = new Thread(new Update());
		updateThread.start();

	}

	class Update implements Runnable {
		ArrayList<String> temp = new ArrayList<String>();

		@Override
		public void run() {
			while (true) {
				try {

					String[] detailItem;
					List<String[]> userDetails = Tools.readAll(mStockCode);
					for (int i = 0; i < userDetails.size(); i++) {
						detailItem = userDetails.get(i);
						labelList[i].setText(detailItem[0].toString());
						temp.add(detailItem[0]);
					}

					m현재가Label.setText(Crawl.getPrice(mStockCode, 1));
					int 시장가 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));
					String 평가금액 = Integer.toString(시장가 * Integer.parseInt(temp.get(1)));
					m평가금액Label.setText(평가금액);

					String 평가손익 = Integer.toString(시장가 * Integer.parseInt(temp.get(1)) - Integer.parseInt(temp.get(2)));
					m평가손익Label.setText(평가손익);

					if (temp.get(1).equals("0")) {
						m수익률Label.setText("0.00%");
					} else {
						DecimalFormat df = new DecimalFormat("0.00");
						String 수익률 = df.format((시장가 * Integer.parseInt(temp.get(1)) - Integer.parseInt(temp.get(2)))
								/ Double.parseDouble(temp.get(2)) * 100);
						m수익률Label.setText(수익률);
					}

					if (mStockCodeIsUpdated == 1) {
						IdentificationPanel.mStockCode = Tools
								.readOneFactor("C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCode.csv", 0, 0);
						IdentificationPanel.mStockCodeIsUpdated = 0;
					}

					temp.clear();

					stockNameLabel.setText(Crawl.getStockName(mStockCode) + "(" + mStockCode + ")");

					Thread.sleep(1000);

				} catch (IOException | CsvException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
