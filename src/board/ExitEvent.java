package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitEvent implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		//그 동안의 사용자 정보를 저장하는 부분이 추가 필요
		System.exit(0);
	}

}
