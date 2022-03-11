package stocksimulation;

import java.awt.*;
import javax.swing.*;

public class Display extends JFrame{

	public Display() {
		JFrame frame = new JFrame("title!");
		frame.setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,2));
		
		
		JLabel timerLabel = new JLabel();//타이머를 출력할 레이블
		timerLabel.setFont(new Font("Gothic",Font.ITALIC, 20)); //타이머 글씨 크기
		TimeChecker timeth = new TimeChecker(timerLabel);//타이머 스레드 객체 생성. 타이머 값을 출력할 레이블을 생성자에 전달
		
		JPanel ChartPanel123 = new JPanel();
		
					/*
		
		
		ChartPanel123 에 대한 설정을 여기서 해줌
		
		
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
		
		
		
		//타이머레이블을 패널에 부착
		JPanel timerPanel = new JPanel();
		timerPanel.add(timerLabel);
		
		
		//타이머패널을 프레임에 부착
		frame.add(timerPanel);
		
		
		//차트패널을 프레임에 부착
		frame.add(ChartPanel123);
		
		
		frame.setVisible(true);
		timeth.start();
		//chartth.start();
	}
}
