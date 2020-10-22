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
	
	private JButton runButton;
	private JButton fileButton;
	private JButton gravityButton;
	private JButton stopButton;
	private JButton offButton;
	private JSpinner steps;
	private JFileChooser filechooser;
	private JTextField deltatime;
	
	private volatile Thread thread;
	private JSpinner delay;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
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
		
		toolBar.add(new JLabel("Delay: "));
		SpinnerNumberModel num2 = new SpinnerNumberModel(20, 0, 1000, 1);
		delay = new JSpinner(num2);
		toolBar.add(delay);
		
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
	
	private void run_sim(int n, int delay) {
		while(n > 0 && !this.thread.isInterrupted()) {
			try {
				_ctrl.run(1);
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null,  "Excepcion producida", "Error", JOptionPane.ERROR_MESSAGE);
						enableDisableButtons(true);
						return;
					}
				});
			}
			n--;
		}
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltatime.setText(dt+"");
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltatime.setText(dt+"");
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) { }

	@Override
	public void onAdvance(List<Body> bodies, double time) { }

	@Override
	public void onDeltaTimeChanged(double dt) {	
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltatime.setText(dt+"");
			}
		});
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
				break;
			}
		}
		try {
		_ctrl.setGravityLaws(selected);
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actionPerformedRun() {

		String stepsAux = steps.getValue().toString();
		
		String delayValue = delay.getValue().toString();
		
		try {
			double deltatimeAux = Double.parseDouble(deltatime.getText());			
			_ctrl.setDeltaTime(deltatimeAux);
			
			enableDisableButtons(false);
			
			thread = new Thread(new Runnable() {
				public void run() {
					run_sim(Integer.parseInt(stepsAux), Integer.parseInt(delayValue));
					thread = null;
					enableDisableButtons(true);
				}
			});
			
			thread.start();
					
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			 enableDisableButtons(true);
		}
	}
	
	private void actionPerformedStop() {
		if(thread != null) {
			thread.interrupt();
			enableDisableButtons(true);
		}
	}
	
	private void actionPerformedOff() {
		Object[] options = {"Si", "No"};
		int n = JOptionPane.showOptionDialog(null, "¿Estás seguro?", "Salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if(n == JOptionPane.YES_OPTION)
			System.exit(0);
	}	
	
	private void enableDisableButtons(boolean control) {
		fileButton.setEnabled(control);
		gravityButton.setEnabled(control);
		runButton.setEnabled(control);
		offButton.setEnabled(control);
		steps.setEnabled(control);
		delay.setEnabled(control);
		deltatime.setEnabled(control);
	}
}
