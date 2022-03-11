package board;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class StockListPanel extends JPanel implements MouseListener, KeyListener, ListSelectionListener {
	private JList<String> list;
	private JTextField inputField;
	private JButton addBtn;
	private JButton selBtn;

	private DefaultListModel<String> model;
	private JScrollPane scrolled;

	public StockListPanel() throws CsvValidationException, IOException {
		super();
		init();
	}

	public void init() throws CsvValidationException, IOException {
		model = new DefaultListModel<String>();
		CSVReader reader = new CSVReader(
				new FileReader("C:\\Users\\iic\\eclipse-workspace\\bowl\\accumulatedStockCode.csv"));
		String[] readNext;
		int i = 0;
		while ((readNext = reader.readNext()) != null) {
			model.add(i, readNext[0]);
		}

		list = new JList<String>(model);
		inputField = new JTextField(9);
		addBtn = new JButton("종목추가");
		selBtn = new JButton("적용");

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		inputField.addKeyListener(this);
		addBtn.addMouseListener(this);
		selBtn.addMouseListener(this);
		list.addListSelectionListener(this);

		this.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout(10, 10, FlowLayout.LEFT));
		topPanel.add(inputField);
		topPanel.add(addBtn);
		topPanel.add(selBtn);
		topPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

		scrolled = new JScrollPane(list);
		scrolled.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		this.add(topPanel, "North");
		this.add(scrolled, "Center");

		this.setSize(190, 150);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == addBtn) {
			String inputText = inputField.getText();

			String file = "C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCodeData.csv";
			CSVReader reader;
			try {
				reader = new CSVReader(new FileReader(file));
				String[] readNext;
				while ((readNext = reader.readNext()) != null) {
					if (inputText.equals(readNext[1])) {
						addItem();

						FileWriter writer = new FileWriter(
								"C:\\Users\\iic\\eclipse-workspace\\bowl\\" + readNext[1] + "userDetails.csv", false);
						writer.write("0" + "\n" + "0" + "\n" + "0" + "\n" + "0");
						writer.close();

						writer = new FileWriter(
								"C:\\Users\\iic\\eclipse-workspace\\bowl\\" + readNext[1] + "transactionDetails.csv",
								false);
						writer.write("0,0\n");
						writer.close();

						FileWriter writer3 = new FileWriter(
								"C:\\Users\\iic\\eclipse-workspace\\bowl\\accumulatedStockCode.csv", true);
						writer3.write(readNext[1] + "\n");
						writer3.close();
						return;
					}
				}
			} catch (IOException | CsvValidationException | NumberFormatException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == selBtn) {
			try {
				int selected = list.getSelectedIndex();
				applyItem(selected);
			
				View.topPanel.removeAll();
				DailyChartPanel chartPanel = new DailyChartPanel();				
				View.topPanel.add(chartPanel);
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (CsvValidationException e1) {
				e1.printStackTrace();
			}

			IdentificationPanel.mStockCodeIsUpdated = 1;
			//DayChartPanel.mStockCodeIsUpdated = 1;
			SecChartPanel.mStockCodeIsUpdated = 1;
			InfoDisplayPanel.mStockCodeIsUpdated = 1;

		}
	}

	public void applyItem(int index) throws IOException {
		if (index < 0) {
			if (model.size() == 0)
				return; // 아무것도 저장되어 있지 않으면 return
			index = 0; // 그 이상이면 가장 상위 list index
		}

		String stockCode = (String) model.get(index);

		FileWriter writer1 = new FileWriter("C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCode.csv", false);
		writer1.write(stockCode);
		writer1.close();

		FileWriter writer2 = new FileWriter(
				"C:\\Users\\iic\\eclipse-workspace\\bowl\\" + stockCode + "transactionDetails.csv", false);
		writer2.write("0,0\n");
		writer2.close();

	}

	public void addItem() {
		String inputText = inputField.getText();
		if (inputText == null || inputText.length() == 0)
			return;
		model.addElement(inputText);
		inputField.setText("");
		inputField.requestFocus();
		scrolled.getVerticalScrollBar().setValue(scrolled.getVerticalScrollBar().getMaximum());
	}

	// MouseListener
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

	// KeyListener
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			JTextField input = (JTextField) e.getSource();
			String stockCode = input.getText();

			String file = "C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCodeData.csv";
			CSVReader reader;
			try {
				reader = new CSVReader(new FileReader(file));
				String[] readNext;
				while ((readNext = reader.readNext()) != null) {
					if (stockCode.equals(readNext[1])) {
						addItem();
						return;
					}
				}
			} catch (IOException | CsvValidationException | NumberFormatException e1) {
				e1.printStackTrace();
			}

		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
	}
}
