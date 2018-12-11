package edu.virginia.lab4test;

import edu.virginia.engine.display.*;

import java.awt.*;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.util.GameClock;
import sun.font.TrueTypeFont;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyEvent;

import java.lang.Math;
import java.util.Arrays;

public class LabFiveGame extends Game {

    private GameClock visibilityToggleClock = new GameClock();

    /* Create a sprite object for our game. We'll use mario */
    public edu.virginia.engine.display.SoundManager sound = new SoundManager();
    AnimatedSprite mario = new AnimatedSprite("Mario", "mario_frames.png", new Point(0, 0));
    Sprite goomba = new Sprite("goomba", "goomba.jpg");
    Sprite star = new Sprite("star", "star.png");
    Sprite floor = new Sprite("floor", "space.png");
    Boolean ended = Boolean.FALSE;
    Boolean light = false;
    int bound = -1, anglebound = -1;

    ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    int lives = 3;
    int level = 0;

    int score = 1000;

    int oxygen = 1000;

    ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public LabFiveGame() {
        super("Lab Four Test Game", 500, 300);
    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<Integer> pressedKeys){
        if(ended == Boolean.TRUE) {
            return;
        }
        super.update(pressedKeys);

        // Arrow Keys
        /*if(pressedKeys.contains(KeyEvent.VK_DOWN)) {
            mario.setPosition(mario.getPosition().x,mario.getPosition().y+5);
            mario.animate("standing");
        }*/
        if(pressedKeys.contains(KeyEvent.VK_UP)) {
            if(mario.floored) {
                mario.setYVelocity(-15);
                mario.animate("jumping");
            }
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
            if(mario!=null) {
                mario.animate("standing");
            }
        }

        if(pressedKeys.contains(KeyEvent.VK_Z)) {
            int col = collidesWithType(mario, 1);
            if(col != -1) { // collides with mirror
                bound = col;
            }
        } else {
            bound = -1;
        }

        if(pressedKeys.contains(KeyEvent.VK_X)) {
            int col = collidesWithType(mario, 1);
            if(col != -1) {
                anglebound = col;
            }
        }  else {
            anglebound = -1;
        }

        if(collidesWithType(mario, 2) {

        }
        // Pivot point
        /*if (pressedKeys.contains(KeyEvent.VK_J)) {
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
        }*/

        // Scaling
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            mario.setScaleX(mario.getScaleX() + 0.05);
            mario.setScaleY(mario.getScaleY() + 0.05);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            mario.setScaleX(Math.max(Math.max(mario.getScaleX() - 0.05, 0.05), 0));
            mario.setScaleY(Math.max(Math.max(mario.getScaleY() - 0.05, 0.05), 0));
        }


        /* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
        if(mario != null && goomba != null && star != null) {
            mario.setHitbox(2);
            mario.update(pressedKeys);
            goomba.setHitbox(1);
            star.setHitbox(1);
            floor.setHitbox(1);



            if (mario.collidesWith(star)) {
                ended = Boolean.TRUE;
                mario.animate("standing");
            } else {
                if (mario.collidesWith(goomba)) {
                    Point knockback = new Point((int) (mario.getPosition().getX() - goomba.getPosition().getX()),
                                                (int) (mario.getPosition().getY() - goomba.getPosition().getY()));
                    double distance = Math.sqrt(knockback.x*knockback.x + knockback.y*knockback.y);

                    mario.setYVelocity(-15*Math.abs((1/distance)*knockback.getY()));
                    mario.setXvelocity(10*(1/distance)*knockback.getX());
                    try {
                        this.sound.PlaySoundEffect("hit");
                    }
                    catch(java.io.IOException ex) {
                        //
                    }
                    catch(UnsupportedAudioFileException ex) {
                        //
                    }
                    catch(LineUnavailableException ex) {
                        //
                    }
                    catch(InterruptedException ex) {
                        //
                    }
                    score -= 1;
                }

                if (mario.collidesWith(floor)) {
                    mario.floored = true;
                } else {
                    mario.floored = false;
                    mario.setGroundpos((int) (mario.getPosition().getY()));
                }
            }
        }

    }

    public int collidesWithType(Sprite s, int type) { // returns -1 if no collisions, otherwise index in array of first collision matching type.
        for(int i = 0; i < obstacles.size(); i++) {
            if(obstacles[i].type == type) {
                if(s.collidesWith((DisplayObject) obstacles[i])) {
                    return i;
                }
            }
        }
    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
     * the screen, we need to make sure to override this method and call mario's draw method.
     * */
    @Override
    public void draw(Graphics g){
        if(g == null) {
            return;
        }
        super.draw(g);



        /* Same, just check for null in case a frame gets thrown in before Mario is initialized */
        if(mario != null) {
            if(mario.getHitbox() != null) {
                g.drawPolygon((Polygon) mario.getHitbox());
                mario.draw(g);
            }
        }
        if(goomba != null) {
            if(goomba.getHitbox() != null) {
                Rectangle r = goomba.getHitbox().getBounds();
                g.drawRect(r.x, r.y, r.width, r.height);
                goomba.draw(g);
            }
        }
        if(star != null) {
            if(star.getHitbox() != null) {
                Rectangle r = star.getHitbox().getBounds();
                g.drawRect(r.x, r.y, r.width, r.height);
                star.draw(g);
            }
        }

        if(floor != null) {
            floor.draw(g);
        }

        g.drawString("Score: " + score, 12, 12);

        if(ended) {
            g.drawString("You win!", 250, 50);
        }
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabFiveGame game = new LabFiveGame();
        game.mario.setScaleX(0.1);
        game.mario.setScaleY(0.1);
        game.goomba.setScaleX(0.1);
        game.goomba.setScaleY(0.1);
        game.star.setScaleX(0.1);
        game.star.setScaleY(0.1);
        game.mario.setPosition(25,100);
        game.mario.hasPhysics = Boolean.TRUE;
        game.star.setPosition(200,100);
        game.goomba.setPosition(100,(int) (215 - game.goomba.getUnscaledHeight()*0.1));
        game.floor.setPosition(0,215);
        game.floor.setScaleX(20);
        game.floor.setScaleY(2);
        game.sound.LoadMusic("music","resources/game_music.wav");
        game.sound.LoadSoundEffect("hit","resources/hit.wav");
        try {
            game.sound.PlayMusic("music");
        }
        catch(java.io.IOException ex) {
            //
        }
        catch(UnsupportedAudioFileException ex) {
            //
        }
        catch(LineUnavailableException ex) {
            //
        }
        catch(InterruptedException ex) {
            //
        }
        game.start();
    }

}
