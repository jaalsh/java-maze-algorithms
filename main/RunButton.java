package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RunButton extends JPanel {

	private static final long serialVersionUID = 2041381802457069780L;
	
	public RunButton() {
		JButton run = new JButton("Run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//generator = genComboBox.getSelectedItem();
			}
		});
		add(run);
	}
}
