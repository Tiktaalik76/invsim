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
		buyBtn = new JButton("매수");
		sellBtn = new JButton("매도");
		guideLabel = new JLabel();
		guideLabel.setText("수량");
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

				int 예수금 = Integer
						.parseInt(Tools.readOneFactor("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", 0, 0));
				int 시장가 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));

				if ((quantity > 0) || 예수금 > 시장가 * quantity) {
					writer.write(시장가 + "," + "+" + quantity + "\n");
					writer.close();

					예수금 -= 시장가 * quantity;

					writer = new FileWriter("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", false);
					writer.write(Integer.toString(예수금));
					writer.close();

					InfoDisplayPanel.mCashIsUpdated = 1;

					JOptionPane.showMessageDialog(null, "매수가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "수량을 다시 입력하세요" + "\n" + "수량은 양의 정수" + "\n", "안내", JOptionPane.PLAIN_MESSAGE);
				}
			}

			if (e.getSource() == sellBtn) {
				int quantity = Integer.parseInt(inputQuantity.getText());

				CSVReader reader1 = new CSVReader(new FileReader(transactionDetailsFilePath));
				String[] readNext;
				int 수량;
				int 총수량 = 0;
				while ((readNext = reader1.readNext()) != null) {
					수량 = Integer.parseInt(readNext[1]);
					총수량 += 수량;
				}

				if (총수량 >= quantity) {
					int 시장가 = Integer.parseInt(Crawl.getPrice(mStockCode, 0));
					writer.write(시장가 + "," + "-" + quantity + "\n");
					writer.close();

					int 예수금 = Integer
							.parseInt(Tools.readOneFactor("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", 0, 0));

					예수금 += 시장가 * quantity;

					writer = new FileWriter("C:\\Users\\iic\\eclipse-workspace\\bowl\\cash.csv", false);
					writer.write(Integer.toString(예수금));
					writer.close();

					InfoDisplayPanel.mCashIsUpdated = 1;

					JOptionPane.showMessageDialog(null, "매수가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "수량을 다시 입력하세요" + "\n" + "수량은 양의 정수" + "\n", "안내", JOptionPane.PLAIN_MESSAGE);
				}
			}

			int 거래금액 = 0;
			int 수량 = 0;
			int 총보유수량 = 0;
			int 매수금액 = 0;
			int 손익분기점 = 0;
			int 매도가능수량 = 0;

			CSVReader reader2 = new CSVReader(new FileReader(transactionDetailsFilePath));
			String[] readNext;
			while ((readNext = reader2.readNext()) != null) {

				거래금액 = Integer.parseInt(readNext[0]);
				수량 = Integer.parseInt(readNext[1]);
				// 수량 부호
				// buy : +
				// sell : -

				총보유수량 += 수량;
				매수금액 += 거래금액 * 수량;

				if (총보유수량 == 0) {
					//거래 내역 초기화
					총보유수량 = 0;
					매수금액 = 0;
					손익분기점 = 0;
					매도가능수량 = 0;

				} else {
					손익분기점 = 매수금액 / 총보유수량;
					매도가능수량 = 총보유수량;
				}
			}

			writer = new FileWriter(userDetailsFilePath, false);
			writer.write(총보유수량 + "\n" + 매도가능수량 + "\n" + 매수금액 + "\n" + 손익분기점);
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
