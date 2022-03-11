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
	private JList list;
	private JTextField inputField;
	private JButton addBtn; // �߰� ��ư
	private JButton selBtn; // ���� ��ư

	private DefaultListModel model; // JList�� ���̴� ���� ������
	private JScrollPane scrolled;

	public StockListPanel() throws CsvValidationException, IOException {
		super();
		init();
	}

	public void init() throws CsvValidationException, IOException {
		model = new DefaultListModel();
		CSVReader reader = new CSVReader(
				new FileReader("C:\\Users\\cms\\eclipse-workspace\\bowl\\accumulatedStockCode.csv"));
		String[] readNext;
		int i = 0;
		while ((readNext = reader.readNext()) != null) {
			model.add(i, readNext[0]);
		}

		list = new JList(model);
		inputField = new JTextField(9);
		addBtn = new JButton("�����߰�");
		selBtn = new JButton("����");

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // �ϳ��� ���� �� �� �ֵ���

		inputField.addKeyListener(this); // ���� ó��
		addBtn.addMouseListener(this); // ������ �߰�
		selBtn.addMouseListener(this); // ������ ����
		list.addListSelectionListener(this); // �׸� ���ý�

		this.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout(10, 10, FlowLayout.LEFT));
		topPanel.add(inputField);
		topPanel.add(addBtn);
		topPanel.add(selBtn); // ���� �г� [textfield] [add] [del]
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // ��, ��, ��, �� ����(Padding)

		scrolled = new JScrollPane(list);
		scrolled.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		this.add(topPanel, "North");
		this.add(scrolled, "Center"); // ��� list

		this.setSize(190, 150);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == addBtn) {
			String inputText = inputField.getText();

			String file = "C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCodeData.csv";
			CSVReader reader;
			try {
				reader = new CSVReader(new FileReader(file));
				String[] readNext;
				while ((readNext = reader.readNext()) != null) {
					if (inputText.equals(readNext[1])) {
						addItem();

						FileWriter writer = new FileWriter(
								"C:\\Users\\cms\\eclipse-workspace\\bowl\\" + readNext[1] + "userDetails.csv", false);
						writer.write("0" + "\n" + "0" + "\n" + "0" + "\n" + "0");
						writer.close();

						writer = new FileWriter(
								"C:\\Users\\cms\\eclipse-workspace\\bowl\\" + readNext[1] + "transactionDetails.csv",
								false);
						writer.write("0,0\n");
						writer.close();

						FileWriter writer3 = new FileWriter(
								"C:\\Users\\cms\\eclipse-workspace\\bowl\\accumulatedStockCode.csv", true);
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
			int selected = list.getSelectedIndex();
			try {
				applyItem(selected);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			View.mIsUpdated = 1;
		}
	}

	public void applyItem(int index) throws IOException {
		if (index < 0) {
			if (model.size() == 0)
				return; // �ƹ��͵� ����Ǿ� ���� ������ return
			index = 0; // �� �̻��̸� ���� ���� list index
		}

		String stockCode = (String) model.get(index);

		FileWriter writer = new FileWriter("C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCode.csv", false);
		writer.write(stockCode);
		writer.close();

		FileWriter writer2 = new FileWriter(
				"C:\\Users\\cms\\eclipse-workspace\\bowl\\" + stockCode + "transactionDetails.csv", false);
		writer2.write("0,0\n");
		writer2.close();

	}

	public void addItem() {
		String inputText = inputField.getText();
		if (inputText == null || inputText.length() == 0)
			return;
		model.addElement(inputText);
		inputField.setText(""); // ���� �����
		inputField.requestFocus(); // ���� �Է��� ���ϰ� �ޱ� ���ؼ� TextField�� ��Ŀ�� ��û
		// ���� ���������� list ��ġ �̵�
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

			String file = "C:\\Users\\cms\\eclipse-workspace\\bowl\\stockCodeData.csv";
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
		if (!e.getValueIsAdjusting()) { // �̰� ������ mouse ������, ���� ���� �ѹ��� ȣ��Ǽ� �� �ι� ȣ��
			System.out.println("selected :" + list.getSelectedValue());
		}
	}
}
