package board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class View extends Thread {
	@Override
	public void run() {
		// �ʱ�ȭ��
		JOptionPane.showMessageDialog(null, "�׽�Ʈ ��", "�ȳ�", JOptionPane.PLAIN_MESSAGE);

		// ������Ʈ ����
		JChart chartPanel = new JChart();
		JLabel timeLabel = new JLabel();
		JLabel quantityNameLabel = new JLabel();
		JButton buyButton = new JButton("Buy");
		JButton sellButton = new JButton("Sell");
		JButton exit = new JButton("Exit");
		JLabel stockName = new JLabel();
		JTextField inputQuantity = new JTextField();
		JLabel currentPriceGuideLabel = new JLabel();
		JTextField outputPrice = new JTextField();
		JPanel identificationPanel = new JPanel();
		JLabel ���簡GuideLabel = new JLabel();
		JLabel ���簡Label = new JLabel();
		JLabel �򰡼���GuideLabel = new JLabel();
		JLabel �򰡼���Label = new JLabel();
		JLabel ���ͷ�GuideLabel = new JLabel();
		JLabel ���ͷ�Label = new JLabel();
		JLabel ��������GuideLabel = new JLabel();
		JLabel ��������Label = new JLabel();
		JLabel �򰡱ݾ�GuideLabel = new JLabel();
		JLabel �򰡱ݾ�Label = new JLabel();
		JLabel �ŵ����ɼ���GuideLabel = new JLabel();
		JLabel �ŵ����ɼ���Label = new JLabel();
		JLabel �ż��ݾ�GuideLabel = new JLabel();
		JLabel �ż��ݾ�Label = new JLabel();
		JLabel ���ͺб���GuideLabel = new JLabel();
		JLabel ���ͺб���Label = new JLabel();
		JPanel ������Panel = new JPanel();
		JLabel ������GuideLabel = new JLabel();
		JLabel ������Label = new JLabel();
		JLabel[] list = {������Label, �򰡼���Label, ���ͷ�Label, ��������Label, �򰡱ݾ�Label, �ŵ����ɼ���Label, �ż��ݾ�Label, ���ͺб���Label};
		
		// ���� ����
		EtchedBorder eborder;
		eborder = new EtchedBorder(EtchedBorder.RAISED);

		// ������Ʈ ����
		chartPanel.setLocation(0, 150);
		chartPanel.setSize(700, 400);

		timeLabel.setLocation(700, 2);
		timeLabel.setSize(200, 70);
		timeLabel.setBorder(eborder);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		timeLabel.setFont(new Font("Gothic", Font.ITALIC, 20));

		buyButton.setLocation(700, 420);
		buyButton.setSize(130, 130);
		buyButton.setFont(new Font("Gothic", Font.ITALIC, 30));

		sellButton.setLocation(840, 420);
		sellButton.setSize(130, 130);
		sellButton.setFont(new Font("Gothic", Font.ITALIC, 30));

		exit.setLocation(901, 3);
		exit.setSize(68, 69);

		stockName.setLocation(10, 126);
		stockName.setSize(200, 24);
		stockName.setText(" ����� (�����ڵ�)");
		stockName.setBorder(eborder);

		quantityNameLabel.setLocation(700, 360);
		quantityNameLabel.setSize(60, 60);
		quantityNameLabel.setText("����");
		quantityNameLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

		currentPriceGuideLabel.setLocation(700, 300);
		currentPriceGuideLabel.setSize(80, 60);
		currentPriceGuideLabel.setText("���尡");
		currentPriceGuideLabel.setFont(new Font("Gothic", Font.ITALIC, 25));

		inputQuantity.setLocation(785, 375);
		inputQuantity.setSize(185, 30);
		inputQuantity.setColumns(10);
		inputQuantity.setFont(new Font(null, Font.BOLD, 20));

		outputPrice.setLocation(785, 300);
		outputPrice.setSize(185, 60);
		outputPrice.setFont(new Font("Gothic", Font.ITALIC, 25));

		// ����
		int count = 0;

		// �̺�Ʈ������ ����
		buyButton.addActionListener(new Event());
		sellButton.addActionListener(new Event());
		inputQuantity.addActionListener(new Event2());
		exit.addActionListener(new Event());
		

		// �� ����
		identificationPanel.setLayout(new GridLayout(2, 4));
		identificationPanel.setLocation(10, 2);
		identificationPanel.setSize(680, 120);
		identificationPanel.setBackground(Color.WHITE);
		identificationPanel.setBorder(eborder);

		identificationPanel.add(�򰡼���GuideLabel);
		�򰡼���GuideLabel.setText("�򰡼���");
		identificationPanel.add(�򰡼���Label);

		identificationPanel.add(��������GuideLabel);
		��������GuideLabel.setText("��������");
		identificationPanel.add(��������Label);

		identificationPanel.add(�ŵ����ɼ���GuideLabel);
		�ŵ����ɼ���GuideLabel.setText("�ŵ����ɼ���");
		identificationPanel.add(�ŵ����ɼ���Label);

		identificationPanel.add(���ͺб���GuideLabel);
		���ͺб���GuideLabel.setText("���ͺб���");
		identificationPanel.add(���ͺб���Label);

		identificationPanel.add(���ͷ�GuideLabel);
		���ͷ�GuideLabel.setText("���ͷ�");
		identificationPanel.add(���ͷ�Label);

		identificationPanel.add(�򰡱ݾ�GuideLabel);
		�򰡱ݾ�GuideLabel.setText("�򰡱ݾ�");
		identificationPanel.add(�򰡱ݾ�Label);

		identificationPanel.add(�ż��ݾ�GuideLabel);
		�ż��ݾ�GuideLabel.setText("�ż��ݾ�");
		identificationPanel.add(�ż��ݾ�Label);

		identificationPanel.add(���簡GuideLabel);
		���簡GuideLabel.setText("���簡");
		identificationPanel.add(���簡Label);

		// �� ����
		������Panel.setLayout(new GridLayout(1, 2));
		������Panel.setLocation(700, 82);
		������Panel.setSize(270, 40);
		������Panel.setBorder(eborder);
		������Panel.setBackground(Color.WHITE);
		������Panel.add(������GuideLabel);
		������Panel.add(������Label);
		������GuideLabel.setText("������");

		// �ֻ��������� ����
		JFrame frame = new JFrame("�ֽĸ�������");
		frame.setSize(1000, 600);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// ������Ʈ ����
		frame.add(timeLabel);
		frame.add(chartPanel);
		frame.add(buyButton);
		frame.add(sellButton);
		frame.add(inputQuantity);
		frame.add(exit);
		frame.add(stockName);
		frame.add(quantityNameLabel);
		frame.add(currentPriceGuideLabel);
		frame.add(outputPrice);
		frame.add(identificationPanel);
		frame.add(������Panel);

		while (true) {
			try {
				timeLabel.setText(Crawl.getTime());
				
				
				String[] detailItems;
				List<String[]> items = CSVTools.lines();
				for (int i = 0; i<items.size(); i++) {
					detailItems = items.get(i);
					list[i].setText(detailItems[0].toString());
				}
				
				if ((count % 7) == 0) {
					chartPanel.update("C:\\Users\\cms\\eclipse-workspace\\out.csv");
					String[] temp = Crawl.getPrice();
					���簡Label.setText(temp[1]);
					outputPrice.setText(temp[1]);
				}

				sleep(1000);
				count++;

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
