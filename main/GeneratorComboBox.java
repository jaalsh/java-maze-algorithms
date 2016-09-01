package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GeneratorComboBox extends JPanel {

	private static final long serialVersionUID = 4134581125514711543L;
	
	private String selectedItem;
	
	public GeneratorComboBox() {
		String[] comboTypes = {"None", "DFS", "Hunt & Kill"};
		// Create the combo box, and set 2nd item as Default
		JComboBox comboTypesList = new JComboBox(comboTypes);
		comboTypesList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				selectedItem = (String) jcmbType.getSelectedItem();
			}
		});
		//setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setBorder(new EmptyBorder(0, 20, 0, 0)); 
		add(comboTypesList);
	}
	
	public String getSelectedItem() {
		return selectedItem;
	}
	
}
