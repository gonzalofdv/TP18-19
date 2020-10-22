package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
		
	private static final long serialVersionUID = 1L;

	BodiesTable(Controller ctrl){
		JTable bodiesTable = new JTable(new BodiesTableModel(ctrl));
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder (BorderFactory.createLineBorder(Color.black, 2), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));
		
		bodiesTable.setFillsViewportHeight(true);
		
		add(new JScrollPane(bodiesTable));
	}
}
