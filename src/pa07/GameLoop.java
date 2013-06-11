package pa07;

import java.awt.EventQueue;

public class GameLoop implements Runnable{

	private GameModel gm; 
	private GameView gameboard;
	
	public GameLoop(GameModel gm, GameView gameboard) {
	 this.gm=gm;
	 this.gameboard=gameboard;
	}

	public void run(){
		while(true){
			// update the model
			gm.update();
			
			// repaint the gameboard, safely
			EventQueue.invokeLater(new Runnable(){
				public void run(){
					gameboard.repaint();
				}
			});

			// sleep for 0.001 seconds
			try{
				Thread.sleep(1l);
			}catch(Exception e){
				System.out.println("In game loop:"+ e);
			}
		}
	}

}
