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
	JFrame frame = new JFrame("회원가입");

	JLabel pnLabel = new JLabel();
	JLabel ans1Label = new JLabel();
	JLabel ans2Label = new JLabel();

	JTextField pnbowl = new JTextField();
	JTextField ans1bowl = new JTextField();
	JTextField ans2bowl = new JTextField();

	JLabel explanation = new JLabel();

	JButton regiBtn = new JButton("가입하기");

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
		pnLabel.setText("전화번호를 입력하세요.");
		pnLabel.setHorizontalAlignment(JLabel.LEFT);

		pnbowl.setLocation(10, 30);
		pnbowl.setSize(180, 30);
		pnbowl.setFont(new Font("Gothic", Font.PLAIN, 15));

		ans1Label.setLocation(10, 70);
		ans1Label.setSize(400, 30);
		ans1Label.setFont(new Font("Gothic", Font.PLAIN, 15));
		ans1Label.setText("질문 1 : 당신의 고향은 어디입니까?");
		ans1Label.setHorizontalAlignment(JLabel.LEFT);

		ans1bowl.setLocation(10, 100);
		ans1bowl.setSize(180, 30);
		ans1bowl.setFont(new Font("Gothic", Font.PLAIN, 15));

		ans2Label.setLocation(10, 140);
		ans2Label.setSize(400, 30);
		ans2Label.setFont(new Font("Gothic", Font.PLAIN, 15));
		ans2Label.setText("질문 2 : 당신의 보물 1호는 무엇입니까?");
		ans2Label.setHorizontalAlignment(JLabel.LEFT);

		ans2bowl.setLocation(10, 170);
		ans2bowl.setSize(180, 30);
		ans2bowl.setFont(new Font("Gothic", Font.PLAIN, 15));

		explanation.setLocation(10, 200);
		explanation.setSize(400, 120);
		explanation.setText(
				"<html>1. 전화번호는 \"-\" 없이 11자리의 숫자로만 입력하세요. <br> 2. 전화번호는 아이디 분실 시 사용됩니다. <br> 3. 질문의 답은 1~12자리의 글자로 입력하세요. <br> 4. 질문에 사용하지 못하는 문자는 다음과 같습니다. <br>&nbsp&nbsp&nbsp&nbsp공백, 탭, * , \\ , : , \" , &lt , &gt , | <br> 5. 개인확인질문은 아이디 분실 시 사용됩니다.</html>");
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
						try (CSVReader rd = new CSVReader(new FileReader(file));
								FileWriter fw = new FileWriter(file, true)) {
							Random ran = new Random();
							String[] bowl;

							int numbering = 0;
							while (true) {
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

							fw.write(id + "," + pw + "," + inputPN + "," + inputans1 + "," + inputans2 + "," + numbering
									+ "," + "0" +"\n");

							message = "회원 가입이 완료되었습니다.";
							frame.dispose();

						} catch (IOException | CsvValidationException e1) {
							e1.printStackTrace();
						}

					} else {
						message = "질문 2를 다시 입력하세요.";
					}
				} else {
					message = "질문 1을 다시 입력하세요.";
				}
			} else {
				message = "전화번호 다시 입력하세요.";
			}

			if (message != null) {
				JOptionPane.showMessageDialog(null, message, "안내", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	private String check(String inputPN, String inputans1, String inputans2) {
		String message = null;
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
					isStr = true; // 문자 만나면 들어옴
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