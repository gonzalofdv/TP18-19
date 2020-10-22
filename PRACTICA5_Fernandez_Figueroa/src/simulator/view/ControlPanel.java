	package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private boolean _stopped;
	private JButton runButton;
	private JButton fileButton;
	private JButton gravityButton;
	private JButton stopButton;
	private JButton offButton;
	private JSpinner steps;
	private JFileChooser filechooser;
	private JTextField deltatime;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		
		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setLayout(new BorderLayout());
		this.add(toolBar, BorderLayout.LINE_START);
		
		fileButton = createButton("file", "resources/icons/open.png");
		
		fileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//llamada a metodo, que desarrolle lo que hay que hacer
				actionPerformedFileButton();
			}
		});
		
		toolBar.add(fileButton);
		
		filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources"));
		filechooser.setMultiSelectionEnabled(false);
		
		toolBar.add(new JSeparator(JSeparator.VERTICAL));
		
		gravityButton = createButton("gravity laws", "resources/icons/physics.png");
		gravityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedGravity();
			}
		});
		toolBar.add(gravityButton);
		
		toolBar.add(new JSeparator(JSeparator.VERTICAL));
		
		runButton = createButton("run", "resources/icons/run.png");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedRun();
			}
		});
		toolBar.add(runButton);
		
		stopButton = createButton("stop", "resources/icons/stop.png");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedStop();
			}
		});
		toolBar.add(stopButton);
		
		toolBar.add(new JLabel("Steps: "));
		SpinnerNumberModel num = new SpinnerNumberModel(1000, 500, 100000, 500);
		steps = new JSpinner(num);
		toolBar.add(steps);
		
		toolBar.add(new JLabel("Delta-time: "));
		deltatime = new JTextField(10);
		toolBar.add(deltatime);
		
		toolBar.add(Box.createHorizontalGlue());
		
		toolBar.add(new JSeparator(JSeparator.VERTICAL));
		
		offButton = createButton("off", "resources/icons/exit.png");
		offButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedOff();
			}
		});
		this.add(offButton, BorderLayout.LINE_END);
	}

	private JButton createButton(String tipText, String ruta) {
		JButton button = new JButton();
		button.setToolTipText(tipText);
		ImageIcon icon = new ImageIcon(ruta);
		button.setIcon(icon);
		
		return button;
	}
	
	private void run_sim(int n) {
		if(n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				//TODO show the error in dialog box
				JOptionPane.showMessageDialog(null, "Excepción producida", "Error", JOptionPane.ERROR_MESSAGE);
				//TODO enable all buttons
				fileButton.setEnabled(true);
				gravityButton.setEnabled(true);
				runButton.setEnabled(true);
				offButton.setEnabled(true);
				
				_stopped = true;
				return;
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					run_sim(n-1);
				}
			});
			
		} else {
			_stopped = true;
			//TODO enable all buttons
			fileButton.setEnabled(true);
			gravityButton.setEnabled(true);
			runButton.setEnabled(true);
			offButton.setEnabled(true);
			
		}
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {

		deltatime.setText(dt+"");
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {

		deltatime.setText(dt+"");

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) { }

	@Override
	public void onAdvance(List<Body> bodies, double time) { }

	@Override
	public void onDeltaTimeChanged(double dt) {	
		deltatime.setText(dt+"");
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {	}
	
	private void actionPerformedFileButton() {
		if(filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			_ctrl.reset();
		
			try {
				_ctrl.loadBodies(new FileInputStream(filechooser.getSelectedFile()));
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void actionPerformedGravity() {
		List<JSONObject> lawsList = _ctrl.getGravityLawsFactory().getInfo();
		
		String[] possibilities = new String[lawsList.size()];
		int i = 0;
		
		for(JSONObject o : lawsList) {
			possibilities[i] = o.getString("desc") + " (" + o.getString("type") + ")";
			i++;
		}
		
		String op = (String) JOptionPane.showInputDialog(null, "Select gravity laws to be used.", "Gravity Laws Selector", JOptionPane.INFORMATION_MESSAGE,
															null, possibilities, possibilities[0]);
		
		JSONObject selected = new JSONObject();
		
		for(JSONObject o : lawsList) {
			if(op.equals(o.getString("desc") + " (" + o.getString("type") + ")" )){
				selected = o;
			}
		}
		try {
		_ctrl.setGravityLaws(selected);
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actionPerformedRun() {
		_stopped = false;
		
		String stepsAux = steps.getValue().toString();
		
		double deltatimeAux = Double.parseDouble(deltatime.getText());
		try {
		_ctrl.setDeltaTime(deltatimeAux);
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		fileButton.setEnabled(false);
		gravityButton.setEnabled(false);
		runButton.setEnabled(false);
		offButton.setEnabled(false);
		
		run_sim(Integer.parseInt(stepsAux));
	}
	
	private void actionPerformedStop() {
		_stopped = true;
		
		fileButton.setEnabled(true);
		gravityButton.setEnabled(true);
		runButton.setEnabled(true);
		offButton.setEnabled(true);
	}
	
	private void actionPerformedOff() {
		Object[] options = {"Si", "No"};
		int n = JOptionPane.showOptionDialog(null, "¿Estás seguro?", "Salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if(n == JOptionPane.YES_OPTION)
			System.exit(0);
	}	
}
