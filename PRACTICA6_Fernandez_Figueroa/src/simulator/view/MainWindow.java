package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();	
	}
	
	private void initGUI() {
		
		JPanel mainPanel = new JPanel();
		
		this.setContentPane(mainPanel);
		
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.setBackground(Color.WHITE);
		
		ControlPanel ctrlPanel = new ControlPanel(_ctrl);
		
		ctrlPanel.setPreferredSize(new Dimension(1000, 55));
		
		mainPanel.add(ctrlPanel, BorderLayout.PAGE_START);
		
		mainPanel.add(createBoxPanel(), BorderLayout.CENTER);
		
		StatusBar stBar = new StatusBar(_ctrl);
		
		stBar.setPreferredSize(new Dimension(1000, 30));
		
		mainPanel.add(stBar, BorderLayout.PAGE_END);
		
		this.setVisible(true);
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int x = pantalla.height;
		int y = pantalla.width;
		this.setSize(x/2, y/2);
		this.setMinimumSize(new Dimension(1000, 800));
		this.setLocationRelativeTo(null);
		this.pack();
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //Do nothing on close: al pulsar el aspa de cerrar la ventana
		//la ventana no hara nada, pero el windowListener hará aparecer una ventana emergente que nos preguntará si queremos salir
		//de esta forma, el SI hará cerrar la ventana, y el NO o la X no cerrará el programa
		
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				Object[] options = {"Si", "No"};
				int n = JOptionPane.showOptionDialog(null, "¿Estás seguro?", "Salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if(n == JOptionPane.YES_OPTION)
					System.exit(0);
			}

			public void windowActivated(WindowEvent arg0) {	}

			public void windowClosed(WindowEvent arg0) { }

			public void windowDeactivated(WindowEvent arg0) { }

			public void windowDeiconified(WindowEvent arg0) { }

			public void windowIconified(WindowEvent arg0) { }

			public void windowOpened(WindowEvent arg0) { }
		});
		
	}
	
	private JPanel createBoxPanel() {
		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
		
		BodiesTable bodTable = new BodiesTable(_ctrl);
		bodTable.setPreferredSize(new Dimension(1000, 200));
		boxPanel.add(bodTable);
		
		Viewer view = new Viewer(_ctrl);
		view.setPreferredSize(new Dimension(1000, 500));
		boxPanel.add(view);
		
		return boxPanel;
	}
}
