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

	JLabel m�򰡼���GuideLabel = new JLabel();
	JLabel m�򰡼���Label = new JLabel();
	JLabel m��������GuideLabel = new JLabel();
	JLabel m�ŵ����ɼ���GuideLabel = new JLabel();
	JLabel m���ͺб���GuideLabel = new JLabel();
	JLabel m���ͷ�GuideLabel = new JLabel();
	JLabel m���ͷ�Label = new JLabel();
	JLabel m�򰡱ݾ�GuideLabel = new JLabel();
	JLabel m�򰡱ݾ�Label = new JLabel();
	JLabel m�ż��ݾ�GuideLabel = new JLabel();
	JLabel m���簡GuideLabel = new JLabel();
	JLabel m���簡Label = new JLabel();
	JLabel m��������Label = new JLabel();
	JLabel m�ŵ����ɼ���Label = new JLabel();
	JLabel m�ż��ݾ�Label = new JLabel();
	JLabel m���ͺб���Label = new JLabel();
	JLabel[] labelList = { m��������Label, m�ŵ����ɼ���Label, m�ż��ݾ�Label, m���ͺб���Label };
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

		setPanel.add(m�򰡼���GuideLabel);
		m�򰡼���GuideLabel.setText("�򰡼���");
		setPanel.add(m�򰡼���Label);

		setPanel.add(m��������GuideLabel);
		m��������GuideLabel.setText("��������");
		setPanel.add(m��������Label);

		setPanel.add(m�ŵ����ɼ���GuideLabel);
		m�ŵ����ɼ���GuideLabel.setText("�ŵ����ɼ���");
		setPanel.add(m�ŵ����ɼ���Label);

		setPanel.add(m���ͺб���GuideLabel);
		m���ͺб���GuideLabel.setText("���ͺб���");
		setPanel.add(m���ͺб���Label);

		setPanel.add(m���ͷ�GuideLabel);
		m���ͷ�GuideLabel.setText("���ͷ�");
		setPanel.add(m���ͷ�Label);

		setPanel.add(m�򰡱ݾ�GuideLabel);
		m�򰡱ݾ�GuideLabel.setText("�򰡱ݾ�");
		setPanel.add(m�򰡱ݾ�Label);

		setPanel.add(m�ż��ݾ�GuideLabel);
		m�ż��ݾ�GuideLabel.setText("�ż��ݾ�");
		setPanel.add(m�ż��ݾ�Label);

		setPanel.add(m���簡GuideLabel);
		m���簡GuideLabel.setText("���簡");
		setPanel.add(m���簡Label);

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

					m���簡Label.setText(Crawl.getPrice(mStockCode, 1));
					int ���尡 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));
					String �򰡱ݾ� = Integer.toString(���尡 * Integer.parseInt(temp.get(1)));
					m�򰡱ݾ�Label.setText(�򰡱ݾ�);

					String �򰡼��� = Integer.toString(���尡 * Integer.parseInt(temp.get(1)) - Integer.parseInt(temp.get(2)));
					m�򰡼���Label.setText(�򰡼���);

					if (temp.get(1).equals("0")) {
						m���ͷ�Label.setText("0.00%");
					} else {
						DecimalFormat df = new DecimalFormat("0.00");
						String ���ͷ� = df.format((���尡 * Integer.parseInt(temp.get(1)) - Integer.parseInt(temp.get(2)))
								/ Double.parseDouble(temp.get(2)) * 100);
						m���ͷ�Label.setText(���ͷ�);
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
