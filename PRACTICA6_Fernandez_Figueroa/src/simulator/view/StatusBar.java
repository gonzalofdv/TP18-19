package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private JLabel _currTime;
	private JLabel _currLaws;
	private JLabel _numOfBodies;
	
	StatusBar(Controller ctrl) {
		
		_ctrl = ctrl;
		_currTime = new JLabel("0.0");
		_currLaws = new JLabel("null");
		_numOfBodies = new JLabel("0");
		
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		this.add(new JLabel("Time: "));
		this.add(_currTime);
		this.add(new JLabel("Bodies: "));
		this.add(_numOfBodies);
		this.add(new JLabel("Laws: "));
		this.add(_currLaws);
		
		//TODO complete the code to build the tool bar
	}
	
	private void setCurrTime(double time) {
		
		_currTime.setText(Double.toString(time));
	}
	
	private void setNumBodies(int n) {
		_numOfBodies.setText(Integer.toString(n));
	}
	
	private void setLawsLabel(String law) {
		_currLaws.setText(law);
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setLawsLabel(gLawsDesc);
			}
		});	
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setCurrTime(time);
			}
		});
		//no cambiamos el valor de la etiqueta que tiene el numero de cuerpos, ya que tras hacer el reset al cargar
		//un fichero nuevo, luego se pondrá el valor del numero de cuerpos del nuevo fichero.
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setNumBodies(bodies.size());
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setCurrTime(time);
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setLawsLabel(gLawsDesc);
			}
		});	
	}

}
