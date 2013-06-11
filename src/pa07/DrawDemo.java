package pa07;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * This class gives an example of how to draw
 * on a JPanel using the Mouse
 * @author tim
 *
 */
public class DrawDemo {

	private GameModel gm;
	private javax.swing.Timer timer;
	private JFrame frame;
	private GameView gameboard;
	private JLabel status;
	private JSlider speedSlider;
	private int timerDelay = 1000/30;
	// this will listen to timer events
	// and update the game and view every timestep
	private ActionListener stepButtonListener;


	public DrawDemo(GameModel gamemodel) {

		JLabel header;
		JPanel buttonPanel;
		JButton stepButton;
		JButton runButton;
		JButton stopButton;
		JButton resetButton;
		
		this.gm=gamemodel;
		

		// first we create the Frame with a border layout
		frame = new JFrame("draw demo");
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		// next we create the gameview
		gameboard = new GameView(gm);
		
		
		// here is the title of the game and the status bar
		header = new JLabel();
		header.setText("Catch the fireflies and avoid the Wasps");
		status = new JLabel("Try to collect all the bugs!");
		
		// Create the buttons and add actions
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,1));
		stepButton = new JButton();
		stepButton.setText("step");
		stepButtonListener = new StepButtonListener();
		stepButton.addActionListener(stepButtonListener);

		runButton = new JButton();
		runButton.setText("run");
		runButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						gm.start();
						gm.gameOver=false;
						status.setText("game in play");
					}
				});
		stopButton = new JButton("stop");
		stopButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					gm.stop();
					status.setText("game paused");
				}
			});
		
		resetButton = new JButton("reset");
		resetButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						gm.initActors();
						status.setText("game reset!");
						gm.gameOver=false;
					}
				});
		status = new JLabel("Try to collect all the bugs!");
		
		// add the buttons to a buttonPanel
		buttonPanel.add(stepButton);
		buttonPanel.add(runButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(resetButton);
		
		// now create the speedSlider
		speedSlider = new JSlider(JSlider.VERTICAL,1,20,4);
		speedSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
			   int v = speedSlider.getValue();
			   //timer.setDelay(1000/v);
			   gm.avatar.radius=v;
			}
		});
	
		
		// put the frame components together with a border layout
		frame.add(header,BorderLayout.NORTH);
		frame.add(gameboard,BorderLayout.CENTER);
		frame.add(buttonPanel,BorderLayout.EAST);
		frame.add(status,BorderLayout.SOUTH);
		frame.add(speedSlider,BorderLayout.WEST);
		//frame.pack();

	}

	/**
	 * @param args ignored
	 */
	public static void main(String[] args) {
		gameLoopVersion();
	}
	
	public static void timerVersion(){
		GameModel gm = new GameModel(100,100);
		DrawDemo myDemo = new DrawDemo(gm);
		// now we create the StepButtonListener
		// and add it as a listener to the timer
		myDemo.frame.setVisible(true);

		myDemo.timer = new Timer(10,myDemo.stepButtonListener);
		myDemo.timer.setDelay(50);
		myDemo.timer.start();
		
	}
	public static void gameLoopVersion(){
		GameModel gm = new GameModel(100,10);
		DrawDemo myDemo = new DrawDemo(gm);
		myDemo.frame.setVisible(true);
		GameLoop gl = new GameLoop(gm,myDemo.gameboard);
		Thread t = new Thread(gl);
		System.out.println("gameloop");
		t.start();
		
	}
	
	/**
	 * a StepButtonListener object is called at every time step
	 * and it updates the model, redisplays the gameboard,
	 * and updates the status.
	 * @author tim
	 *
	 */
	public class StepButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			gm.update();
			status.setText("fireflies remaining: "+gm.numActive+
					" current speed ="+1000/timer.getDelay()+" fps");
			gameboard.repaint();
			if (gm.gameOver){
				if (gm.numActive==0)
					status.setText("*** YOU WON ***");
				else
					status.setText("--- YOU LOST ---");
			}
		}
	}
	
}
