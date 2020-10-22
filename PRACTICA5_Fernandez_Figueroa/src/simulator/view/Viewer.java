package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	
	//...
	private int _centerX;
	private int _centerY;
	
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	
	Viewer(Controller ctrl){
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		//TODO add border with title
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Viewer", TitledBorder.LEFT, TitledBorder.TOP));
		
		this.setPreferredSize(new Dimension(getWidth(), 600));
		
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
					case '-':
						_scale = _scale * 1.1;
						break;
					case '+':
						_scale = Math.max(1000.0,  _scale / 1.1);
						break;
					case '=':
						autoScale();
						break;
					case 'h':
						_showHelp = !_showHelp;
						break;
					default:
				}
				repaint();
			}

			public void keyReleased(KeyEvent arg0) {}

			public void keyTyped(KeyEvent arg0) {}
		});
		
		addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			public void mouseClicked(MouseEvent arg0) {}

			public void mouseExited(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//use 'gr' to draw not 'g'
		
		//calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		//TODO draw a cross at center
		gr.setColor(Color.red);
		gr.drawLine(_centerX - 5, _centerY , _centerX + 5, _centerY);
		gr.drawLine(_centerX, _centerY + 5, _centerX, _centerY - 5);
		
		//TODO draw bodies
		
		for(Body b : _bodies) {
			gr.setColor(Color.blue);
			gr.fillOval(_centerX + (int) (b.getPosition().coordinate(0)/_scale) - 5, _centerY + (int) (b.getPosition().coordinate(1)/_scale) - 5, 10, 10);
			
			gr.setColor(Color.black);
			gr.drawString(b.getId(), _centerX + (int) (b.getPosition().coordinate(0)/_scale) - 2, _centerY + (int) (b.getPosition().coordinate(1)/_scale) - 15);
		}
		
		//TODO draw help if _showHelp is true
		if(_showHelp) {
			Font helpmsg = new Font(Font.SANS_SERIF,Font.BOLD,15); 
			gr.setFont(helpmsg);
			gr.setColor(Color.red);
			gr.drawString("h: toggle help, +: zoom-in, -: zoom-out, =: fit ", 5, 30);
			gr.drawString("Scaling ratio: "+this._scale, 5, 50);
			
		}
	}
	
	//other private/protected methods
	//...
	
	private void autoScale() {
		double max = 1.0;
		
		for(Body b : _bodies) {
			Vector p = b.getPosition();
			for(int i = 0; i < p.dim(); ++i) {
				max = Math.max(max, Math.abs(b.getPosition().coordinate(i)));
			}
		}
		
		double size = Math.max(1.0, Math.min((double) getWidth(), (double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		autoScale();
		repaint();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		autoScale();
		repaint();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		autoScale();
		repaint();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		repaint();	
	}

	@Override
	public void onDeltaTimeChanged(double dt) {	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {	}
}
