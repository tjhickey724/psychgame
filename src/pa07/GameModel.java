package pa07;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is a simple model for a game with objects that
 * move around the screen and the user needs to 
 * catch them by clicking on them. This class
 * represents the state of the game
 * @author tim
 *
 */
public class GameModel {
	public double width;
	public double height;
	public double size;
	public List<GameActor> actors;
	private Random rand = new Random();
	public GameActor avatar;
	private int numActors;
	public boolean gameOver = false;
	public boolean paused = true;
	public int numActive;


	public GameModel(double size, int numActors) {
		this.width =size;
		this.height = size;
		this.size=size;
		this.numActors = numActors;
		initActors();
		this.avatar = new GameActor(0,0);
		avatar.species = Species.avatar;
		this.avatar.radius=6;
		this.gameOver = false;
	}
	/**
	 * initActors creates a new ArrayList of actors
	 * which consists of 90 fireflies and 10 wasps
	 */
	public void initActors(){
		numActive=0;
		this.actors = new ArrayList<GameActor>();
		for(int i=0; i<numActors;i++){
			double x = rand.nextDouble()*width;
			double y = rand.nextDouble()*height;
			GameActor a = new GameActor(x,y);
			this.actors.add(a);
			a.speed = 10;
			a.radius = 4;
			if (numActive>numActors*0.5)
				a.species = Species.good;
			else{
				a.species = Species.bad;
				numActive++;
			}
		}	
	}
	
	
	public void start(){
		paused = false;
	}
	
	public void stop(){
		paused = true;
	}
	
	/**
	 * if an actor moves off the board, in the x (or y) direction, 
	 * it is bounced back into the board and its velocity in the
	 * offending direction is reversed
	 * @param a
	 */
	public void keepOnBoard(GameActor a){
		if (a.x<0) {
			a.x = -a.x;a.vx = -a.vx;
		}else if (a.x> width){
			a.x = width - (a.x-width);
			a.vx = -a.vx;
		}
		if (a.y<0) {
			a.y = -a.y;a.vy = -a.vy;
		}else if (a.y > height){
			a.y = height - (a.y-height);
			a.vy=-a.vy;
		}
	}
	
	/**
	 * update moves all actors one step and if
	 * any fireflies that intersect with the avatar
	 * are remove, while if a wasp intersects the avatar,
	 * the game is restarted
	 */
	public void update(){
		if (paused || gameOver) return;
		for(GameActor a:this.actors){
			a.update();
			if (a.active && intersects(a,avatar)) {
				a.active=false;
				numActive--;
				if (a.species==Species.wasp){
					initActors(); // you lose and have to restart!
				}
			}
			keepOnBoard(a);
		}
		if (numActive==0)
			gameOver=true;
	}
	
	public boolean intersects(GameActor a, GameActor b){
		double dx=a.x-b.x;
		double dy = a.y-b.y;
		double d = Math.sqrt(dx*dx+dy*dy);
		return (d < a.radius + b.radius);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameModel gm = new GameModel(10,3);
		for(int i=0;i<10;i++){
			System.out.println("i="+i+": "+gm.actors);
			gm.update();
		}

	}

}
