package board;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Gate3 implements MouseListener {

	JLabel idLabel = new JLabel();
	JLabel pwLabel = new JLabel();
	JTextField createID = new JTextField();
	JTextField createPW = new JTextField();
	JButton regBtn = new JButton("����");
	JLabel explanation = new JLabel();
	String file = "C:\\Users\\iic\\Desktop\\idpw.csv";
	JFrame frame = new JFrame("ȸ������");

	public Gate3() {

		frame.setVisible(true);
		frame.setSize(420, 370);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(idLabel);
		frame.add(createID);
		frame.add(pwLabel);
		frame.add(createPW);
		frame.add(regBtn);
		frame.add(explanation);

		idLabel.setLocation(10, 20);
		idLabel.setSize(90, 30);
		idLabel.setFont(new Font("Gothic", Font.PLAIN, 20));
		idLabel.setText("���̵�");
		idLabel.setHorizontalAlignment(JLabel.CENTER);

		createID.setLocation(110, 20);
		createID.setSize(180, 30);
		createID.setFont(new Font("Gothic", Font.PLAIN, 20));

		pwLabel.setLocation(10, 50);
		pwLabel.setSize(90, 30);
		pwLabel.setFont(new Font("Gothic", Font.PLAIN, 20));
		pwLabel.setText("��й�ȣ");
		pwLabel.setHorizontalAlignment(JLabel.CENTER);

		createPW.setLocation(110, 50);
		createPW.setSize(180, 30);
		createPW.setFont(new Font("Gothic", Font.PLAIN, 20));

		regBtn.setLocation(295, 20);
		regBtn.setSize(95, 60);
		regBtn.setFont(new Font("Gothic", Font.PLAIN, 20));
		regBtn.addMouseListener(this);

		explanation.setText(
				"<html>1. ���̵�� ��й�ȣ�� 1~12�ڸ��� �Է��ϼ���. <br><br>2. ����� �� ���� ���ڴ� ������ �����ϴ�. <br>&nbsp&nbsp&nbsp&nbsp����, ��, * , \\ , : , \" , &lt , &gt , | <br><br> 3. ��ȭ��ȣ�� ���̵� �н� �� ���˴ϴ�. <br><br> 4. ����Ȯ�������� ���̵� �н� �� ���˴ϴ�.<br><br> 5. ��ȭ��ȣ�� \"-\"���� �Է��ϼ��� </html>");
		explanation.setLocation(10, 20);
		explanation.setSize(400, 380);
		explanation.setFont(new Font("Gothic", Font.PLAIN, 15));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == regBtn) {
			String id = createID.getText();
			String pw = createPW.getText();

			if (Gate1.checking(id, pw)) {
				CSVReader reader;
				try {
					reader = new CSVReader(new FileReader(file));
					String[] bowl;

					while ((bowl = reader.readNext()) != null) {
						if (bowl[0].equals(id)) {
							JOptionPane.showMessageDialog(null, "�ߺ��Ǵ� ���̵� �ֽ��ϴ�.", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
							return;
						}
					}

					String pwCheck = JOptionPane.showInputDialog("��й�ȣ �ٽ� �ѹ� �Է��ϼ���");
				
					if (StringUtils.isEmpty(pwCheck)) {
						JOptionPane.showMessageDialog(null, "����߽��ϴ�.", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
					}
					else if (pwCheck.equals(pw)) {
						new Gate4(id, pw);
						frame.dispose();
					}
					else {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.", "�ȳ�", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (CsvValidationException e1) {
					e1.printStackTrace();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� ����, ��, *, \\, :, \", <, >, | �� ���ԵǾ� �ֽ��ϴ�.", "�ȳ�",JOptionPane.PLAIN_MESSAGE);
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
}
