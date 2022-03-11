package board;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Gate1 implements MouseListener {

	private JTextField idField;
	private JTextField pwField;
	private JButton loginBtn;
	private JButton registerBtn;
	private JButton findIDBtn;
	private JButton findPWBtn;
	private JLabel title = new JLabel();
	private JPanel topPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private String file = "C:\\Users\\iic\\Desktop\\idpw.csv";
	private JFrame frame = new JFrame("�ֽĸ�������");
	
	public Gate1() {

		frame.setVisible(true);
		frame.setSize(1050, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);

		EtchedBorder eborder;
		eborder = new EtchedBorder(EtchedBorder.RAISED);

		topPanel.setVisible(true);
		topPanel.setLocation(75, 75);
		topPanel.setSize(900, 100);
		topPanel.setLayout(new GridLayout());
		title.setFont(new Font("Gothic", Font.PLAIN, 60));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setText("���� �ֽ� ���� �ùķ�����");
		topPanel.add(title);
		frame.add(topPanel);

		bottomPanel.setLayout(null);// new GridLayout()
		bottomPanel.setVisible(true);
		bottomPanel.setLocation(325, 300);
		bottomPanel.setSize(400, 150);
		bottomPanel.setBorder(eborder);
		frame.add(bottomPanel);

		JLabel idGuide = new JLabel();
		idField = new JTextField();
		JLabel pwGuide = new JLabel();
		pwField = new JTextField();
		loginBtn = new JButton("�α���");
		findIDBtn = new JButton("���̵� ã��");
		findPWBtn = new JButton("��й�ȣ ã��");
		registerBtn = new JButton("ȸ�� ����");

		loginBtn.addMouseListener(this);
		findIDBtn.addMouseListener(this);
		findPWBtn.addMouseListener(this);
		registerBtn.addMouseListener(this);

		bottomPanel.add(idGuide);
		idGuide.setLocation(10, 20);
		idGuide.setSize(90, 30);
		idGuide.setFont(new Font("Gothic", Font.PLAIN, 20));
		idGuide.setText("���̵�");
		idGuide.setHorizontalAlignment(JLabel.CENTER);

		bottomPanel.add(idField);
		idField.setLocation(110, 20);
		idField.setSize(180, 30);
		idField.setFont(new Font("Gothic", Font.PLAIN, 20));

		bottomPanel.add(pwGuide);
		pwGuide.setLocation(10, 50);
		pwGuide.setSize(90, 30);
		pwGuide.setFont(new Font("Gothic", Font.PLAIN, 20));
		pwGuide.setText("��й�ȣ");
		pwGuide.setHorizontalAlignment(JLabel.CENTER);

		bottomPanel.add(pwField);
		pwField.setLocation(110, 50);
		pwField.setSize(180, 30);
		pwField.setFont(new Font("Gothic", Font.PLAIN, 20));

		bottomPanel.add(loginBtn);
		loginBtn.setLocation(295, 20);
		loginBtn.setSize(95, 60);
		loginBtn.setFont(new Font("Gothic", Font.PLAIN, 20));

		bottomPanel.add(findIDBtn);
		findIDBtn.setLocation(10, 100);
		findIDBtn.setSize(120, 30);
		findIDBtn.setFont(new Font("Gothic", Font.PLAIN, 13));

		bottomPanel.add(findPWBtn);
		findPWBtn.setLocation(140, 100);
		findPWBtn.setSize(120, 30);
		findPWBtn.setFont(new Font("Gothic", Font.PLAIN, 13));
		
		
		bottomPanel.add(registerBtn);
		registerBtn.setLocation(270, 100);
		registerBtn.setSize(120, 30);
		registerBtn.setFont(new Font("Gothic", Font.PLAIN, 13));

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		if (e.getSource() == loginBtn) {
			try {
				String inputid = idField.getText();
				String inputpw = pwField.getText();

				if (checking(inputid, inputpw)) {
					String file = "C:\\Users\\iic\\Desktop\\idpw.csv";
					CSVReader inspector = new CSVReader(new FileReader(file));
					String[] readNext;
					while ((readNext = inspector.readNext()) != null) {
						if (inputid.equals(readNext[0])) {
							if (inputpw.equals(readNext[1])) {
								new Gate2(inputid, inputpw);
								frame.dispose();
								return;
							}
						}
					}
				}
			} catch (IOException | CsvValidationException e1) {
				e1.printStackTrace();
			}

			JOptionPane.showMessageDialog(null, "���̵�/��й�ȣ Ʋ�Ƚ��ϴ�.", "�ȳ�", JOptionPane.PLAIN_MESSAGE);

		}
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		else if (e.getSource() == registerBtn) {
			new Gate3();
		}
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		else if (e.getSource() == findIDBtn) {
			try {
				// ��ȭ��ȣ üŷ
				int i = 0;
				String question = JOptionPane.showInputDialog("��ȭ��ȣ�� �Է��ϼ���.");
				
				if(StringUtils.isEmpty(question)) {
					JOptionPane.showMessageDialog(null, "���̵� ã�⸦ ����մϴ�.", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
					i = 1;
				}

				else if (question.equals("")) {
					JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �ٽ� Ȯ���ϼ���", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
					i = 1;
				}

				else if (i == 1){ // ��ȭ��ȣ üŷ�ϴ� ���ǹ� ���� ������ �ӽ÷� ����� ����
					CSVReader reader = new CSVReader(new FileReader(file));
					String[] bowl;
					while ((bowl = reader.readNext()) != null) {
						if (question.equals(bowl[2]))
							JOptionPane.showMessageDialog(null, "���̵�� \"" + bowl[0] +"\" �Դϴ�.", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
						i = 1;
					}					
					
				}
				if (i == 0) {
					JOptionPane.showMessageDialog(null, "ȸ�������� �����ϴ�.", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
				}

			} catch (CsvValidationException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static boolean checking(String id, String pw) {
		boolean check = false;
		if ((id.length() < 12) && (id.length() > 0)) {
			if ((pw.length() < 12) && (pw.length() > 0)) {
				if (!((id.contains(" ")) || (pw.contains(" ")) || (id.contains("\t")) || (pw.contains("\t"))
						|| (id.contains("\\")) || (pw.contains("\\")) || (id.contains(":")) || (pw.contains(":"))
						|| (id.contains("*")) || (pw.contains("*")) || (id.contains("?")) || (pw.contains("?"))
						|| (id.contains("\"")) || (pw.contains("\"")) || (id.contains("<")) || (pw.contains("<"))
						|| (id.contains(">")) || (pw.contains(">")) || (id.contains("|")) || (pw.contains("|"))

				)) {
					check = true;
				}
			}
		}
		return check;
	}
}