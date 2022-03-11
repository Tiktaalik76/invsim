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
	JButton regBtn = new JButton("가입");
	JLabel explanation = new JLabel();
	String file = "C:\\Users\\iic\\Desktop\\idpw.csv";
	JFrame frame = new JFrame("회원가입");

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
		idLabel.setText("아이디");
		idLabel.setHorizontalAlignment(JLabel.CENTER);

		createID.setLocation(110, 20);
		createID.setSize(180, 30);
		createID.setFont(new Font("Gothic", Font.PLAIN, 20));

		pwLabel.setLocation(10, 50);
		pwLabel.setSize(90, 30);
		pwLabel.setFont(new Font("Gothic", Font.PLAIN, 20));
		pwLabel.setText("비밀번호");
		pwLabel.setHorizontalAlignment(JLabel.CENTER);

		createPW.setLocation(110, 50);
		createPW.setSize(180, 30);
		createPW.setFont(new Font("Gothic", Font.PLAIN, 20));

		regBtn.setLocation(295, 20);
		regBtn.setSize(95, 60);
		regBtn.setFont(new Font("Gothic", Font.PLAIN, 20));
		regBtn.addMouseListener(this);

		explanation.setText(
				"<html>1. 아이디와 비밀번호는 1~12자리로 입력하세요. <br><br>2. 사용할 수 없는 문자는 다음과 같습니다. <br>&nbsp&nbsp&nbsp&nbsp공백, 탭, * , \\ , : , \" , &lt , &gt , | <br><br> 3. 전화번호는 아이디 분실 시 사용됩니다. <br><br> 4. 개인확인질문은 아이디 분실 시 사용됩니다.<br><br> 5. 전화번호는 \"-\"없이 입력하세요 </html>");
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
							JOptionPane.showMessageDialog(null, "중복되는 아이디가 있습니다.", "안내", JOptionPane.PLAIN_MESSAGE);
							return;
						}
					}

					String pwCheck = JOptionPane.showInputDialog("비밀번호 다시 한번 입력하세요");
				
					if (StringUtils.isEmpty(pwCheck)) {
						JOptionPane.showMessageDialog(null, "취소했습니다.", "안내", JOptionPane.PLAIN_MESSAGE);
					}
					else if (pwCheck.equals(pw)) {
						new Gate4(id, pw);
						frame.dispose();
					}
					else {
					JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.", "안내", JOptionPane.PLAIN_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "비밀번호에 공백, 탭, *, \\, :, \", <, >, | 이 포함되어 있습니다.", "안내",JOptionPane.PLAIN_MESSAGE);
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
