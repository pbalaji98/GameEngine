package edu.virginia.lab2test;

import java.awt.Graphics;
import java.awt.Point;
import java.security.Key;
import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.virginia.engine.display.*;
import edu.virginia.engine.util.GameClock;
import java.awt.event.KeyEvent;

import java.lang.Math;


/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */

public class LabThreeSimulator extends Game{

	double zoom = 1.0;

	Boolean clockwise = Boolean.TRUE;
	double flexTime = 10000000;
	private GameClock visibilityToggleClock = new GameClock();
	private GameClock globalClock = new GameClock();

	DisplayObjectContainer sun = new DisplayObjectContainer("sun", "sun.jpg");
	DisplayObjectContainer earth = new DisplayObjectContainer("earth", "earth.png");
	DisplayObjectContainer mars = new DisplayObjectContainer("mars", "mars.png");
	Sprite space = new Sprite("space","space.png");
	DisplayObject moon = new Sprite("moon", "moon.png");
	DisplayObject phobos = new Sprite("phobos", "phobos.png");
	DisplayObject deimos = new Sprite("deimos", "deimos.png");

	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabThreeSimulator() {
		super("Lab Three Simulator", 500, 300);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<Integer> pressedKeys){

		super.update(pressedKeys);

		if(earth != null && sun != null && moon != null && mars != null && phobos != null && deimos != null) {
			earth.setPosition(ellipse(100, 50, sun, earth, 1));
			mars.setPosition(new Point(5,5));
			mars.setPosition(ellipse(200, 100, sun, mars, 0.6));
			moon.setPosition(ellipse(20, 10, earth, moon, 5));
			//phobos.setPosition(ellipse(10, 5, mars, phobos, 10));
			//deimos.setPosition(ellipse(20, 10, mars, deimos, 10));

			if (pressedKeys.contains(KeyEvent.VK_UP)) {
				sun.setPosition(sun.getPosition().x, sun.getPosition().y + (int) Math.ceil(5/zoom));
			}
			if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
				sun.setPosition(sun.getPosition().x, sun.getPosition().y - (int) Math.ceil(5/zoom));
			}
			if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
				sun.setPosition(sun.getPosition().x + (int) Math.ceil(5/zoom), sun.getPosition().y);
			}
			if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
				sun.setPosition(sun.getPosition().x - (int) Math.ceil(5/zoom), sun.getPosition().y);
			}
			if (pressedKeys.contains(KeyEvent.VK_Q)) {
				zoom += 0.01;
			}
			if(pressedKeys.contains(KeyEvent.VK_A)) {
				clockwise = Boolean.FALSE;
			}
			if(pressedKeys.contains(KeyEvent.VK_S)) {
				clockwise = Boolean.TRUE;
			}
			if (pressedKeys.contains(KeyEvent.VK_W)) {
				if (zoom > 0.1) {
					zoom -= 0.01;
				}
			}
		}


	}


	private void scaleSun() {
		int centerX = (int) (250 - sun.getUnscaledWidth()*0.05);
		int centerY = (int) (150 - sun.getUnscaledHeight()*0.05);
		sun.setScaleX(zoom*0.1);
		sun.setScaleY(zoom*0.1);
		Point pos = sun.getPosition();
		sun.setPosition((int) ((pos.getX() - centerX)*zoom + centerX),
				(int) ((pos.getY() - centerY)*zoom + centerY));
	}

	private void unscaleSun(Point orig) {
		//int centerX = (int) (250 - sun.getUnscaledWidth()*0.05);
		//int centerY = (int) (150 - sun.getUnscaledHeight()*0.05);
		sun.setScaleX(0.1);
		sun.setScaleY(0.1);
		sun.setPosition(orig);
		//Point pos = sun.getPosition();
		//sun.setPosition((int) ((pos.getX() - centerX)/zoom + centerX),
		//		(int) ((pos.getY() - centerY)/zoom + centerY));
	}

	private void scaleAll() {
		scaleSun();
		scale(earth, 0.09);
		scale(moon, 0.02);
		scale(mars, 0.03);
		scale(phobos, 0.04);
		scale(deimos, 0.04);
	}

	private void scale(DisplayObject image, double initZoom) {
		image.setScaleX(zoom*initZoom);
		image.setScaleY(zoom*initZoom);
		Point pos = image.getPosition();
		image.setPosition((int) (pos.getX()*zoom), (int) (pos.getY()*zoom));

	}

	private void unscale(DisplayObject image, double initZoom) {
		image.setScaleX(initZoom);
		image.setScaleY(initZoom);
		Point pos = image.getPosition();
		image.setPosition((int) (pos.getX()/zoom), (int) (pos.getY()/zoom));
	}

	private void unscaleAll(Point orig) {
		unscaleSun(orig);
		unscale(earth, 0.09);
		unscale(moon, 0.05);
		unscale(mars, 0.08);
		unscale(phobos, 0.04);
		unscale(deimos, 0.04);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(space != null && earth != null && sun != null && moon != null && mars != null && phobos != null && deimos != null) {
			space.draw(g);
			Point orig = sun.getPosition();
			scaleAll();
			sun.draw(g);
			earth.draw(g);
			moon.draw(g);
			mars.draw(g);
			//phobos.draw(g);
			//deimos.draw(g);
			unscaleAll(orig);

			System.out.println(flexTime);
		}
	}

	private Point ellipse(double xaxis, double yaxis, DisplayObject parent, DisplayObject child, double rate) {
		double imageLength = parent.getUnscaledWidth()*parent.getScaleX();
		double imageHeight = parent.getUnscaledHeight()*parent.getScaleY();
		double childLen = child.getUnscaledWidth()*child.getScaleX();
		double childH = child.getUnscaledHeight()*child.getScaleY();
		double degs = this.flexTime*rate*0.2 % 360;
		double tan = Math.tan(Math.toRadians(degs));
		double x = ((degs < 90 || degs > 270)? 1:-1)*xaxis*yaxis/(Math.sqrt(yaxis*yaxis + xaxis*xaxis*tan*tan));
		double y = x*tan;
		if(clockwise) {
			this.flexTime += this.globalClock.getElapsedTime();
		} else {
			this.flexTime -= this.globalClock.getElapsedTime();
		}

		this.globalClock.resetGameClock();

		return new Point((int) (x + (imageLength/2) - (childLen/2)), (int) (y + (imageHeight/2) - (childH/2)));
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		LabThreeSimulator game = new LabThreeSimulator();
		game.space.setScaleX(1000);
		game.space.setScaleY(1000);
		game.sun.setScaleX(0.1);
		game.sun.setScaleY(0.1);
		game.earth.setScaleX(0.09);
		game.earth.setScaleY(0.09);
		game.moon.setScaleX(0.02);
		game.moon.setScaleY(0.02);
		game.mars.setScaleX(0.03);
		game.mars.setScaleY(0.03);
		game.phobos.setScaleX(0.04);
		game.phobos.setScaleY(0.04);
		game.deimos.setScaleX(0.04);
		game.deimos.setScaleY(0.04);
		game.sun.addChild(game.earth);
		game.earth.addChild(game.moon);
		game.sun.addChild(game.mars);
		game.mars.addChild(game.phobos);
		game.mars.addChild(game.deimos);
		game.sun.setPosition((int) (250 - game.sun.getUnscaledWidth()*0.05),
				(int) (150 - game.sun.getUnscaledHeight()*0.05));
		game.start();
	}}