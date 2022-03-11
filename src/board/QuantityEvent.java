package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextField;

public class QuantityEvent implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField inputQuantity = (JTextField) e.getSource();
		String quantity = inputQuantity.getText();

		File file = new File("C:\\Users\\cms\\eclipse-workspace\\quantity.csv");

		try {
			FileWriter writer = new FileWriter(file, false);
			writer.write(quantity + "\n");
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
