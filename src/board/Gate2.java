package board;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class Gate2 implements MouseListener {

	private JButton newStart = new JButton("새로 시작");
	private JButton oldStart = new JButton("이어서 시작");
	private JButton withdrawalBtn = new JButton("회원 탈퇴");
	private JButton changeBtn = new JButton("비밀번호 변경");
	private String file = "C:\\Users\\iic\\Desktop\\idpw.csv";
	private String stockCodefile = "C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCode.csv";
	private String id;
	private String pw;
	private JPanel topPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JLabel title = new JLabel();
	JFrame frame = new JFrame("주식모의투자");
	private JLabel explanation = new JLabel();

	public Gate2(String inputid, String inputpw) {

		this.id = inputid;
		this.pw = inputpw;

		withdrawalBtn.addMouseListener(this);
		changeBtn.addMouseListener(this);

		EtchedBorder eborder;
		eborder = new EtchedBorder(EtchedBorder.RAISED);

		frame.setVisible(true);
		frame.setSize(1050, 600);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		topPanel.setLayout(null);
		topPanel.setLocation(125, 20);
		topPanel.setSize(800, 180);
		topPanel.setVisible(true);
		topPanel.setBorder(eborder);

		title.setLocation(0, 10);
		title.setSize(800, 100);
		title.setFont(new Font("Gothic", Font.PLAIN, 55));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setText("'" + id + "'" + "님 안녕하세요.");
		topPanel.add(title);

		changeBtn.setLocation(560, 130);
		changeBtn.setSize(120, 30);
		topPanel.add(changeBtn);

		withdrawalBtn.setLocation(680, 130);
		withdrawalBtn.setSize(110, 30);
		topPanel.add(withdrawalBtn);

		bottomPanel.setLayout(null);
		bottomPanel.setLocation(225, 240);
		bottomPanel.setSize(600, 300);
		bottomPanel.setVisible(true);
		bottomPanel.setBorder(eborder);

		newStart.addMouseListener(this);
		oldStart.addMouseListener(this);

		newStart.setLocation(10, 10);
		newStart.setSize(285, 100);
		newStart.setFont(new Font("Gothic", Font.PLAIN, 25));
		bottomPanel.add(newStart);

		oldStart.setLocation(305, 10);
		oldStart.setSize(285, 100);
		oldStart.setFont(new Font("Gothic", Font.PLAIN, 25));
		bottomPanel.add(oldStart);

		explanation.setLocation(10, 120);
		explanation.setSize(500, 180);
		explanation.setFont(new Font("Gothic", Font.PLAIN, 15));
		explanation.setText("<html> ※ 주의사항 1 <br> ※ 주의사항 2 <br> ※ 주의사항 3 <br> ※ 주의사항 4 </html>");
		bottomPanel.add(explanation);

		frame.add(topPanel);
		frame.add(bottomPanel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == newStart) {
			try {
				while (true) {
					String stockCode = JOptionPane.showInputDialog("종목 코드를 입력하세요");
					if (StringUtils.isEmpty(stockCode)) {
						System.exit(0);
					}
					// 입력된 종목코드 검사
					CSVReader reader1;
					reader1 = new CSVReader(
							new FileReader("C:\\Users\\iic\\eclipse-workspace\\bowl\\stockCodeData.csv"));
					String[] readNext;
					int check = 0;
					while ((readNext = reader1.readNext()) != null) {
						if (readNext[1].equals(stockCode)) {
							check = 1;
						}
					}
					if (stockCode.length() == 0 || stockCode.equals("") || check == 0) {
						continue;
					}

					FileWriter writer = new FileWriter(stockCodefile, false);
					writer.write(stockCode);
					writer.close();
				}
				
				// 그 후 사용자 정보 맨 뒤에 1을 넣는다.@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				
				// 이전 데이터 지우기 (사용자 고유번호)로 식별해서 지운다.@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CsvValidationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == oldStart) {
			// 사용자 정보 맨 뒤에 1인 경우에만 실행할 수 있도록 함
			// 그대로 실행함

		} else if (e.getSource() == withdrawalBtn) {
			try {
				CSVReader inspector = new CSVReader(new FileReader(file));
				String[] readNext;
				int i = -1;
				while ((readNext = inspector.readNext()) != null) {
					i++;
					if (id.equals(readNext[0])) {
						if (pw.equals(readNext[1])) {
							int result = JOptionPane.showConfirmDialog(null, "탈퇴하시겠습니까?", "안내",
									JOptionPane.YES_NO_OPTION);
							if (result == JOptionPane.YES_OPTION) {
								String dummy = "";
								BufferedReader br = new BufferedReader(
										new InputStreamReader(new FileInputStream(file)));
								String line;
								for (int k = 0; k < i; k++) {
									line = br.readLine();
									dummy += (line + "\r\n"); // @@@@@@@@ \r\n 순서 수정필요
								}
								br.readLine(); // 한 줄 없애기

								while ((line = br.readLine()) != null) {
									dummy += (line + "\r\n");
								}

								FileWriter fw = new FileWriter(file);
								fw.write(dummy);
								fw.close();
								br.close();

								JOptionPane.showMessageDialog(null, "탈퇴 완료", "안내", JOptionPane.PLAIN_MESSAGE);
								new Gate1();
								frame.dispose();
							}
						}
					}
				}
			} catch (IOException | CsvValidationException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == changeBtn) {
			try {
				CSVReader inspector = new CSVReader(new FileReader(file));
				String[] readNext;
				int i = -1;
				while ((readNext = inspector.readNext()) != null) {
					i++;
					if (id.equals(readNext[0])) {
						if (pw.equals(readNext[1])) {

							String newpw = JOptionPane.showInputDialog("변경할 비밀번호를 입력하세요");

							if (StringUtils.isEmpty(newpw)) {
								JOptionPane.showMessageDialog(null, "취소되었습니다.", "안내", JOptionPane.PLAIN_MESSAGE);
							}

							else if (pw.equals(newpw)) {
								JOptionPane.showMessageDialog(null, "동일한 비밀번호입니다.", "안내", JOptionPane.PLAIN_MESSAGE);
								continue;
							}

							else if (Gate1.checking("id", newpw)) {

								String checkpw = JOptionPane.showInputDialog("다시한번 입력하세요");

								if (newpw.equals(checkpw)) {
									String dummy = "";
									BufferedReader br = new BufferedReader(
											new InputStreamReader(new FileInputStream(file)));
									String line;

									for (int k = 0; k < i; k++) {
										line = br.readLine();
										dummy += (line + "\r\n"); // @@@@@@@@ \r\n 순서 수정필요
									}

									br.readLine(); // 한 줄 없애기
									CSVReader rr = new CSVReaderBuilder(new FileReader(file)).withSkipLines(i).build();

									String[] bowl = rr.readNext();

									dummy += (bowl[0] + "," + newpw + "," + bowl[2] + "\r\n");

									while ((line = br.readLine()) != null) {
										dummy += (line + "\r\n");
									}

									FileWriter fw = new FileWriter(file);
									fw.write(dummy);
									fw.close();
									br.close();

									pw = newpw;

									JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다.", "안내",
											JOptionPane.PLAIN_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.", "안내", JOptionPane.PLAIN_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "아이디,비밀번호 조건을 확인하세요.", "안내",
										JOptionPane.PLAIN_MESSAGE);
							}
						}
					}
				}

			} catch (IOException | CsvValidationException e1) {
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

}
