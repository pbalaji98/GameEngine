package edu.virginia.lab4test;

import edu.virginia.engine.display.Game;
import java.awt.Graphics;
import java.awt.Point;
import java.security.Key;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.util.GameClock;
import java.awt.event.KeyEvent;

import java.lang.Math;

public class LabFourGame extends Game {

    private GameClock visibilityToggleClock = new GameClock();

    /* Create a sprite object for our game. We'll use mario */
    AnimatedSprite mario = new AnimatedSprite("Mario", "mario_frames.png", new Point(0, 0));

    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public LabFourGame() {
        super("Lab Four Test Game", 500, 300);
    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);

        // Arrow Keys
        if(pressedKeys.contains(KeyEvent.VK_DOWN)) {
            mario.setPosition(mario.getPosition().x,mario.getPosition().y+5);
            mario.animate("standing");
        }
        if(pressedKeys.contains(KeyEvent.VK_UP)) {
            mario.setPosition(mario.getPosition().x,mario.getPosition().y-5);
            mario.animate("jumping");
        }
        if(pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            mario.setPosition(mario.getPosition().x+5,mario.getPosition().y);
            mario.animate("walking");
        }
        if(pressedKeys.contains(KeyEvent.VK_LEFT)) {
            mario.setPosition(mario.getPosition().x-5,mario.getPosition().y);
            mario.animate("walking");
        }
        if (!pressedKeys.contains(KeyEvent.VK_LEFT) && !pressedKeys.contains(KeyEvent.VK_UP) &&
                !pressedKeys.contains(KeyEvent.VK_RIGHT) && !pressedKeys.contains(KeyEvent.VK_DOWN)) {
            mario.animate("standing");
        }


        // Pivot point
        if (pressedKeys.contains(KeyEvent.VK_J)) {
            mario.setPivotPoint(mario.getPivotPoint().x-5,mario.getPivotPoint().y);
        }
        if (pressedKeys.contains(KeyEvent.VK_L)) {
            mario.setPivotPoint(mario.getPivotPoint().x+5,mario.getPivotPoint().y);
        }
        if (pressedKeys.contains(KeyEvent.VK_I)) {
            mario.setPivotPoint(mario.getPivotPoint().x,mario.getPivotPoint().y-5);
        }
        if (pressedKeys.contains(KeyEvent.VK_K)) {
            mario.setPivotPoint(mario.getPivotPoint().x,mario.getPivotPoint().y+5);
        }

        // Rotation
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            mario.setRotation(mario.getRotation()+10.0);
        }
        if (pressedKeys.contains(KeyEvent.VK_Q)) {
            mario.setRotation(mario.getRotation()-10.0);
        }

        // Visibility
        if (pressedKeys.contains(KeyEvent.VK_V) && visibilityToggleClock.getElapsedTime() >= 200) {
            mario.setVisible(!mario.isVisible());
            visibilityToggleClock.resetGameClock();
        }

        // Alpha
        if (pressedKeys.contains(KeyEvent.VK_Z)) {
            mario.setAlpha(Math.max(mario.getAlpha() - (float)0.05, 0));
        }
        if (pressedKeys.contains(KeyEvent.VK_X)) {
            mario.setAlpha(Math.min(mario.getAlpha() + (float)0.05, 1));
        }

        // Scaling
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            mario.setScaleX(mario.getScaleX() + 0.05);
            mario.setScaleY(mario.getScaleY() + 0.05);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            mario.setScaleX(Math.max(mario.getScaleX() - 0.05, 0));
            mario.setScaleY(Math.max(mario.getScaleY() - 0.05, 0));
        }


        /* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
        if(mario != null) mario.update(pressedKeys);

        mario.setHitbox(1);
    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
     * the screen, we need to make sure to override this method and call mario's draw method.
     * */
    @Override
    public void draw(Graphics g){
        super.draw(g);

        /* Same, just check for null in case a frame gets thrown in before Mario is initialized */
        if(mario != null) mario.draw(g);
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabFourGame game = new LabFourGame();
        game.start();
    }

}