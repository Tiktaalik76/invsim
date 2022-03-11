package board;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Gate4 implements MouseListener {
	JFrame frame = new JFrame("ȸ������");

	JLabel pnLabel = new JLabel();
	JLabel ans1Label = new JLabel();
	JLabel ans2Label = new JLabel();

	JTextField pnbowl = new JTextField();
	JTextField ans1bowl = new JTextField();
	JTextField ans2bowl = new JTextField();

	JLabel explanation = new JLabel();

	JButton regiBtn = new JButton("�����ϱ�");

	String file = "C:\\Users\\iic\\Desktop\\idpw.csv";
	String id;
	String pw;

	public Gate4(String inputid, String inputpw) {

		this.id = inputid;
		this.pw = inputpw;

		frame.setVisible(true);
		frame.setSize(420, 420);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(pnLabel);
		frame.add(pnbowl);
		frame.add(ans1Label);
		frame.add(ans1bowl);
		frame.add(ans2Label);
		frame.add(ans2bowl);
		frame.add(explanation);
		frame.add(regiBtn);

		pnLabel.setLocation(10, 0);
		pnLabel.setSize(400, 30);
		pnLabel.setFont(new Font("Gothic", Font.PLAIN, 15));
		pnLabel.setText("��ȭ��ȣ�� �Է��ϼ���.");
		pnLabel.setHorizontalAlignment(JLabel.LEFT);

		pnbowl.setLocation(10, 30);
		pnbowl.setSize(180, 30);
		pnbowl.setFont(new Font("Gothic", Font.PLAIN, 15));

		ans1Label.setLocation(10, 70);
		ans1Label.setSize(400, 30);
		ans1Label.setFont(new Font("Gothic", Font.PLAIN, 15));
		ans1Label.setText("���� 1 : ����� ������ ����Դϱ�?");
		ans1Label.setHorizontalAlignment(JLabel.LEFT);

		ans1bowl.setLocation(10, 100);
		ans1bowl.setSize(180, 30);
		ans1bowl.setFont(new Font("Gothic", Font.PLAIN, 15));

		ans2Label.setLocation(10, 140);
		ans2Label.setSize(400, 30);
		ans2Label.setFont(new Font("Gothic", Font.PLAIN, 15));
		ans2Label.setText("���� 2 : ����� ���� 1ȣ�� �����Դϱ�?");
		ans2Label.setHorizontalAlignment(JLabel.LEFT);

		ans2bowl.setLocation(10, 170);
		ans2bowl.setSize(180, 30);
		ans2bowl.setFont(new Font("Gothic", Font.PLAIN, 15));

		explanation.setLocation(10, 200);
		explanation.setSize(400, 120);
		explanation.setText(
				"<html>1. ��ȭ��ȣ�� \"-\" ���� 11�ڸ��� ���ڷθ� �Է��ϼ���. <br> 2. ��ȭ��ȣ�� ���̵� �н� �� ���˴ϴ�. <br> 3. ������ ���� 1~12�ڸ��� ���ڷ� �Է��ϼ���. <br> 4. ������ ������� ���ϴ� ���ڴ� ������ �����ϴ�. <br>&nbsp&nbsp&nbsp&nbsp����, ��, * , \\ , : , \" , &lt , &gt , | <br> 5. ����Ȯ�������� ���̵� �н� �� ���˴ϴ�.</html>");
		explanation.setFont(new Font("Gothic", Font.PLAIN, 12));

		regiBtn.addMouseListener(this);
		regiBtn.setLocation(160, 330);
		regiBtn.setSize(100, 40);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == regiBtn) {
			
			String inputPN = pnbowl.getText();
			String inputans1 = ans1bowl.getText();
			String inputans2 = ans2bowl.getText();

			String message = null;

			if (checking(inputPN)) {
				if (Gate1.checking("pass", inputans1)) {
					if (Gate1.checking("pass", inputans2)) {
						message = check(inputPN, inputans1, inputans2);
					} else {
						message = "���� 2�� �ٽ� �Է��ϼ���.";
					}
				} else {
					message = "���� 1�� �ٽ� �Է��ϼ���.";
				}
			} else {
				message = "��ȭ��ȣ �ٽ� �Է��ϼ���.";
			}

			if (message != null) {
				JOptionPane.showMessageDialog(null, message, "�ȳ�", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	private String check(String inputPN, String inputans1, String inputans2) {
		String message = null;
		try (CSVReader rd = new CSVReader(new FileReader(file)); FileWriter fw = new FileWriter(file, true)) {
			Random ran = new Random();
			String[] bowl;

			int numbering = 0;
			/*while (true) {
				numbering = ran.nextInt(1000000);
				String comp = Integer.toString(numbering);
				boolean exist = false;

				if ((bowl = rd.readNext()) != null) {
					if (bowl[5].equals(comp)) {
						exist = true;
					}
				} else if (exist) {
					continue;
				} else if (!(exist)) {
					break;
				}
			}
*/
			fw.write(id + "," + pw + "," + inputPN + "," + inputans1 + "," + inputans2 + "," + numbering + "\n");

			message = "ȸ�� ������ �Ϸ�Ǿ����ϴ�.";
			frame.dispose();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return message;
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

	public static boolean checking(String pn) {
		boolean check = false;
		boolean isStr = false;
		char[] bowl = pn.toCharArray();
		if (bowl.length == 11) {
			for (int i = 0; i < bowl.length; i++) {
				if (Character.isDigit(bowl[i]) == false) {
					isStr = true; // ���� ������ ����
				}
			}
			if (isStr == true) {
				check = false;
			} else {
				check = true;
			}

		}
		return check;
	}
}