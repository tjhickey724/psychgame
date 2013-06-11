package pa07;

import java.awt.Color;
/**
 * a GameActor has a position and a velocity and a speed
 * they also have a species 
 * and they keep track of whether they are active or not
 * @author tim
 *
 */
public class GameActor {
	// size
	double radius = 10;
	// position
	double x;
	double y;
	// velocity
	double vx;
	double vy;
	// still on board?
	boolean active;
	// speed
	double speed=100;
	// species
	boolean fromLeft; // true if fish comes from left
	long birthTime;
	long lastUpdate;
	Color 
		color1=new Color(150,0,0), 
		color2=new Color(155,0,0);
	double colorHerz = 2;
	

	Species species = Species.firefly; 
	
	private java.util.Random rand = new java.util.Random();

	public GameActor(double x, double y, boolean active) {
		this.x=x; this.y=y; this.active=active;
		this.vx = speed*(rand.nextDouble()-0.5);
		this.vy = speed*(rand.nextDouble()-0.5);
		this.birthTime = System.nanoTime();
		this.lastUpdate = this.birthTime;
	}
	
	public GameActor(double x, double y){
		this(x,y,true);
	}
	
	public GameActor(){
		this(0,0,true);
	}

	/**
	 * actors change their velocity slightly at every step
	 * but their speed remains the same. Update slightly modifies
	 * their velocity and uses that to compute their new position.
	 * Note that velocity is in units per update.
	 */
	public void update(){
		long now = System.nanoTime();
		double dt = (now -this.lastUpdate)/1000000000.0;
		this.lastUpdate = now;
		double turnspeed = 0.1;
		vx += rand.nextDouble()*turnspeed -turnspeed/2;
		vy += rand.nextDouble()*turnspeed -turnspeed/2;
		double tmpSpeed = Math.sqrt(vx*vx+vy*vy);
		vx /= tmpSpeed;
		vy /= tmpSpeed;
		x += vx*speed*dt;
		y += vy*speed*dt;
	}
	
	public String toString(){
		int ix = (int)x;
		int iy = (int)y;
		return "["+ix+","+iy+","+active+"]";
	}


}
