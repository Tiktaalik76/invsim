package board;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class StockTradePanel extends JPanel implements MouseListener, KeyListener {

	private JTextField inputQuantity;
	private JButton buyBtn;
	private JButton sellBtn;
	private JLabel guideLabel;

	public StockTradePanel() {
		super();
		init();
	}

	public void init() {
		inputQuantity = new JTextField(35);
		buyBtn = new JButton("�ż�");
		sellBtn = new JButton("�ŵ�");
		guideLabel = new JLabel();
		guideLabel.setText("����");
		guideLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

		inputQuantity.addKeyListener(this);
		buyBtn.addMouseListener(this);
		sellBtn.addMouseListener(this);

		this.add(guideLabel);
		this.add(inputQuantity);
		this.add(buyBtn);
		this.add(sellBtn);

		guideLabel.setSize(80, 60);
		guideLabel.setLocation(10, 0);

		inputQuantity.setSize(210, 60);
		inputQuantity.setLocation(100, 0);

		buyBtn.setSize(145, 100);
		buyBtn.setLocation(10, 70);

		sellBtn.setSize(145, 100);
		sellBtn.setLocation(165, 70);

		this.setLayout(null);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setSize(300, 300);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {

			String stockCodeFilePath = "C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCode.csv";
			String mStockCode;
			
			mStockCode = Tools.readOneFactor(stockCodeFilePath, 0, 0);

			String transactionDetailsFilePath1 = "C:\\Users\\iic\\eclipse-workspace\\bowl\\";
			String transactionDetailsFilePath2 = "transactionDetails.csv";
			String transactionDetailsFilePath = transactionDetailsFilePath1 + mStockCode + transactionDetailsFilePath2;

			String userDetailsFilePath1 = "C:\\Users\\iic\\eclipse-workspace\\bowl\\";
			String userDetailsFilePath2 = "userDetails.csv";
			String userDetailsFilePath = userDetailsFilePath1 + mStockCode + userDetailsFilePath2;

			FileWriter writer = new FileWriter(transactionDetailsFilePath, true);

			if (e.getSource() == buyBtn) {
				int quantity = Integer.parseInt(inputQuantity.getText());

				int ������ = Integer
						.parseInt(Tools.readOneFactor("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", 0, 0));
				int ���尡 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));

				if ((quantity > 0) || ������ > ���尡 * quantity) {
					writer.write(���尡 + "," + "+" + quantity + "\n");
					writer.close();

					������ -= ���尡 * quantity;

					writer = new FileWriter("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", false);
					writer.write(Integer.toString(������));
					writer.close();

					InfoDisplayPanel.mCashIsUpdated = 1;

					JOptionPane.showMessageDialog(null, "�ż��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "������ �ٽ� �Է��ϼ���" + "\n" + "������ ���� ����" + "\n", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
				}
			}

			if (e.getSource() == sellBtn) {
				int quantity = Integer.parseInt(inputQuantity.getText());

				CSVReader reader1 = new CSVReader(new FileReader(transactionDetailsFilePath));
				String[] readNext;
				int ����;
				int �Ѽ��� = 0;
				while ((readNext = reader1.readNext()) != null) {
					���� = Integer.parseInt(readNext[1]);
					�Ѽ��� += ����;
				}

				if (�Ѽ��� >= quantity) {
					int ���尡 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));
					writer.write(���尡 + "," + "-" + quantity + "\n");
					writer.close();

					int ������ = Integer
							.parseInt(Tools.readOneFactor("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", 0, 0));

					������ += ���尡 * quantity;

					writer = new FileWriter("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", false);
					writer.write(Integer.toString(������));
					writer.close();

					InfoDisplayPanel.mCashIsUpdated = 1;

					JOptionPane.showMessageDialog(null, "�ż��� ü��Ǿ����ϴ�", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "������ �ٽ� �Է��ϼ���" + "\n" + "������ ���� ����" + "\n", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
				}
			}

			int �ŷ��ݾ� = 0;
			int ���� = 0;
			int �Ѻ������� = 0;
			int �ż��ݾ� = 0;
			int ���ͺб��� = 0;
			int �ŵ����ɼ��� = 0;

			CSVReader reader2 = new CSVReader(new FileReader(transactionDetailsFilePath));
			String[] readNext;
			while ((readNext = reader2.readNext()) != null) {

				�ŷ��ݾ� = Integer.parseInt(readNext[0]);
				���� = Integer.parseInt(readNext[1]);
				// ���� ��ȣ
				// buy : +
				// sell : -

				�Ѻ������� += ����;
				�ż��ݾ� += �ŷ��ݾ� * ����;

				if (�Ѻ������� == 0) {
					//�ŷ� ���� �ʱ�ȭ
					�Ѻ������� = 0;
					�ż��ݾ� = 0;
					���ͺб��� = 0;
					�ŵ����ɼ��� = 0;

				} else {
					���ͺб��� = �ż��ݾ� / �Ѻ�������;
					�ŵ����ɼ��� = �Ѻ�������;
				}
			}

			writer = new FileWriter(userDetailsFilePath, false);
			writer.write(�Ѻ������� + "\n" + �ŵ����ɼ��� + "\n" + �ż��ݾ� + "\n" + ���ͺб���);
			writer.close();

		} catch (CsvValidationException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
