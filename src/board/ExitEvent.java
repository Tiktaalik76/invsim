package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitEvent implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		//�� ������ ����� ������ �����ϴ� �κ��� �߰� �ʿ�
		System.exit(0);
	}

}
