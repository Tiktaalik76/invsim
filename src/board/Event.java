package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Event implements ActionListener {
	public static int mQuantity = 0;
	
	File tradeHistory = new File("C:\\Users\\cms\\eclipse-workspace\\tradeHistory.csv");
	File sumFile = new File("C:\\Users\\cms\\eclipse-workspace\\sumHistory.csv");

	int 거래금액 = 0;
	int 총매수금액 = 0;
	int 수량 = 0;
	int 총보유수량 = 0;
	double 손익분기점 = 0;
	int 예수금 = 0;
	String[] 시장가;
	int int시장가;
	int 평가금액 = 0;
	double 수익률 = 0;
	int 매도가능수량 = 0;
	int 변동금액 = 0;
	int 평단 = 0;
	
	int 평가손익 = 0;
	int 로컬거래금액;
	int 로컬수량;
	int 로컬총보유수량 = 0;
	int 로컬변동금액 = 0;
	int 로컬평단 = 0;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String key = e.getActionCommand();
			FileWriter writer;
			
			String[] currentPrice = Crawl.getPrice();
		
			if (key == "Exit") {
				System.exit(0);
			}
			
			else {
				if (mQuantity > 0) {
					if (key == "Buy") {
						try {
							
							writer = new FileWriter(tradeHistory, true);
							writer.write(currentPrice[0] + "," + "+" + mQuantity + "\n");
							writer.close();
							JOptionPane.showMessageDialog(null, "매수가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else {
						try {

							CSVReader allReader = new CSVReader(new FileReader(tradeHistory));
							String[] readNext;
						
							로컬총보유수량 = 0;
							로컬변동금액 = 0;
							로컬평단 = 0;
							
							while ((readNext = allReader.readNext()) != null) {
								
								로컬거래금액 = Integer.parseInt(readNext[0]);
								로컬수량 = Integer.parseInt(readNext[1]); // 부호가 있음
								
								로컬총보유수량 += 로컬수량;
								로컬변동금액 += 로컬거래금액 * 로컬수량;
								
							}
							로컬평단 = 로컬변동금액/로컬총보유수량;
							writer = new FileWriter(tradeHistory, true);
							writer.write(currentPrice[0] + "," + "-" + mQuantity + "\n");
							writer.close();
							JOptionPane.showMessageDialog(null, "매도가 체결되었습니다", "안내", JOptionPane.PLAIN_MESSAGE);
							
							평가손익 = Integer.parseInt(currentPrice[0])*mQuantity - 로컬평단* mQuantity;

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "수량을 다시 입력하세요"+"\n"+"수량은 양의 정수"+"\n"+"수량 입력 후 엔터 입력", "안내", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
			거래금액 = 0;
			총매수금액 = 0;
			수량 = 0;
			총보유수량 = 0;
			손익분기점 = 0;
			예수금 = 0;
			평가금액 = 0;
			수익률 = 0;
			매도가능수량 = 0;
			변동금액 = 0;
			평단 = 0;
			
			CSVReader detailReader1 = new CSVReader(new FileReader(tradeHistory));
			String[] nextRecord1;
			while ((nextRecord1 = detailReader1.readNext()) != null) {
				
				거래금액 = Integer.parseInt(nextRecord1[0]);
				수량 = Integer.parseInt(nextRecord1[1]); // 부호가 있음
				시장가 = Crawl.getPrice();
				int시장가 = Integer.parseInt(시장가[0]);

				총보유수량 += 수량;
				
				//일단은 한번 다 스캔 해야됨 왜냐면 중간에 수량이 0이 되었을 때 빠져버리면 안되니까 처음부터 끝까지 다 봐서 -> 다시 생각 바람/ 중간에 0이 되면 손 턴거임
				//수량을 계산했더니 0이면 빠져버리고 아니면 수익률 계산을 위해 각각의 데이터를 사용해야함
				
				
				
				if (총보유수량 == 0) {
					
					예수금 += -(거래금액 * 수량); //매도 거래 내역을 지우기 전에 예수금에 반영시킴
					총보유수량 = 0;
					변동금액 = 0;
					손익분기점 = 0;
					총매수금액 = 0;
					평가손익 = 0;
					수익률 = 0;
					평가금액 = 0;
					매도가능수량 = 0;
					
				}
				else {

					변동금액 += 거래금액 * 수량;

					손익분기점 = 변동금액 / 총보유수량;

					총매수금액 = (int)손익분기점 * 총보유수량;//**********************

					수익률 = (평가손익 / 변동금액) * 100;//*******************

					예수금 = 100000000 - 변동금액; // 매도가 우세하면 변동금액이 - 부호임

					평가금액 = 총보유수량 * int시장가;

					매도가능수량 = 총보유수량;
					
				}
				writer = new FileWriter(sumFile, false);
				writer.write(예수금 + "\n" + 평가손익 + "\n" + 수익률 + "\n" + 총보유수량 + "\n" + 평가금액 + "\n" + 매도가능수량 + "\n" + 총매수금액 + "\n" + 손익분기점);
				writer.close();
			}
		} catch (IOException | CsvValidationException e1) {
			e1.printStackTrace();
		}
	}
}
