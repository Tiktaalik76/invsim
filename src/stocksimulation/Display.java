package stocksimulation;

import java.awt.*;
import javax.swing.*;

public class Display extends JFrame{

	public Display() {
		JFrame frame = new JFrame("title!");
		frame.setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,2));
		
		
		JLabel timerLabel = new JLabel();//Ÿ�̸Ӹ� ����� ���̺�
		timerLabel.setFont(new Font("Gothic",Font.ITALIC, 20)); //Ÿ�̸� �۾� ũ��
		TimeChecker timeth = new TimeChecker(timerLabel);//Ÿ�̸� ������ ��ü ����. Ÿ�̸� ���� ����� ���̺��� �����ڿ� ����
		
		JPanel ChartPanel123 = new JPanel();
		
					/*
		
		
		ChartPanel123 �� ���� ������ ���⼭ ����
		
		
					*/
		
		//Chart chartth = new Chart(ChartPanel123);
		
		
		/*
		-frame
			-panel
				-panel_1
					-label
					-JTextbox
					-JCheckbox
				-panel_2
					-label
					-JTextbox
					-JCheckbox
				-panel_3
					-label
					-JTextbox
					-JCheckbox
		*/
		
		
		
		//Ÿ�̸ӷ��̺��� �гο� ����
		JPanel timerPanel = new JPanel();
		timerPanel.add(timerLabel);
		
		
		//Ÿ�̸��г��� �����ӿ� ����
		frame.add(timerPanel);
		
		
		//��Ʈ�г��� �����ӿ� ����
		frame.add(ChartPanel123);
		
		
		frame.setVisible(true);
		timeth.start();
		//chartth.start();
	}
}
