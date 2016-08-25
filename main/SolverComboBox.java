package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SolverComboBox extends JPanel {

	private static final long serialVersionUID = 1L;

	public SolverComboBox() {
		String[] comboTypes = {"None", "DFS"};
		// Create the combo box, and set 2nd item as Default
		JComboBox comboTypesList = new JComboBox(comboTypes);
		comboTypesList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
			}
		});
		setLayout(new BorderLayout());
		add(comboTypesList, BorderLayout.NORTH);
	}
}