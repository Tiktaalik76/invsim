package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class Event2 implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	JTextField inputQuantity = (JTextField) e.getSource();
	String quantity = inputQuantity.getText();
	
	Event.mQuantity = Integer.parseInt(quantity);
	}
	
	
}
