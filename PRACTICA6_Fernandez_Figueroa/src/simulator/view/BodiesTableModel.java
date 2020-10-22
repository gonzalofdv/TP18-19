package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	
	//...
	private final static String[] columnNames = {"Id", "Mass", "Position", "Velocity", "Acceleration" };
	private List<Body> _bodies;
	
	BodiesTableModel(Controller ctrl){
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	public int getRowCount() {
		return _bodies.size();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		String valor = columnNames[columnIndex];
//		switch (columnIndex) {
//		case value:
//			
//			break;/
//
//		default:
//			break;
//		}
		if(valor == "Id") {
			return _bodies.get(rowIndex).getId();
		}
		else if(valor == "Mass") {
			return _bodies.get(rowIndex).getMass();
		}
		else if(valor == "Position") {
			return _bodies.get(rowIndex).getPosition();
		}
		else if(valor == "Velocity") {
			return _bodies.get(rowIndex).getVelocity();
		}
		else if (valor == "Acceleration") {
			return _bodies.get(rowIndex).getAcceleration();	
		}
		
		return "";
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {	}
}
