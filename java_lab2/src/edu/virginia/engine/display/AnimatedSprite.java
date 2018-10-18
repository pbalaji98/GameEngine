package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;

import java.util.ArrayList;
import java.awt.*;
import java.lang.Thread;
import java.util.NoSuchElementException;

public class AnimatedSprite extends Sprite {

    static final int DEFAULT_ANIMATION_SPEED = 1000;

    private ArrayList<Animation> animations;
    private Boolean playing = false;
    private String fileName;
    private ArrayList<DisplayObject> frames;
    private Integer currentFrame = 0;
    private Integer startFrame;
    private Integer endFrame;
    private Integer animationSpeed = DEFAULT_ANIMATION_SPEED;
    private GameClock gameClock;



    public AnimatedSprite(String id, String fileName, Point pos) {
        super(id, fileName);
        this.fileName = fileName;
        this.position = pos;
        this.gameClock = new GameClock();
        this.loadFrames();
    }


    public void initGameClock() {
        if (gameClock == null)
            this.gameClock = new GameClock();
    }

    public void setAnimations(ArrayList<Animation> animations) {
        this.animations = animations;
    }

    public void setAnimationSpeed(Integer animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public Integer getAnimationSpeed() {
        return animationSpeed;
    }

    @Override
    public void draw(Graphics g) {
            if (this.gameClock.getElapsedTime() >= this.animationSpeed) {
                if (this.playing) {
                    if (this.currentFrame == this.endFrame) {
                        this.currentFrame = this.startFrame;
                    } else {
                        this.currentFrame++;
                    }
                }
                this.setImage(this.frames.get(this.currentFrame).getDisplayImage());
                this.gameClock.resetGameClock();
            }

        super.draw(g);
    }

    /* Krishan */
    public void loadFrames()
    { /* AKA initializeFrames */
        this.frames = new ArrayList<DisplayObject>();
        this.animations = new ArrayList<Animation>();
        ArrayList paths = new ArrayList();
        paths.add("mario_jump_0.png"); // mario at rest
        paths.add("mario_jump_1.png");
        paths.add("mario_jump_0.png");
        paths.add("mario_run_1.png");
        paths.add("mario_run_0.png");
        for(int i = 0; i<5; i++) {
            DisplayObject frame = new DisplayObject(paths.get(i).toString(), paths.get(i).toString());
            this.frames.add(frame);
        }

        Animation jump = new Animation("jump",1,2);
        Animation run = new Animation("run",3,4);

        this.animations.add(jump);
        this.animations.add(run);
    }

    /* Krishan */
    public Animation getAnimation(String id) {
        for(int i = 0; i < this.animations.size(); i++) {
            if(this.animations.get(i).getId() == id) {
                return this.animations.get(i);
            }
        }
        System.out.println("Could not find animation with ID:");
        throw new NoSuchElementException();
    }


    /* Partner 2 - Part 3 and 4 */
    public void animate(Animation animateObject) {
        if(this.playing) {
            return;
        }
        this.startFrame = animateObject.getStartFrame();
        this.endFrame = animateObject.getEndFrame();
        if(this.currentFrame == null || this.currentFrame > endFrame || this.currentFrame < startFrame) {
            this.setImage(this.frames.get(this.startFrame).getDisplayImage());
            this.currentFrame = this.startFrame;
            this.gameClock.resetGameClock();
        }
        this.playing = true;
    }
    public void animate(String id) {
        animate(getAnimation(id));
    }
    public void animate(Integer startFrame, Integer endFrame) {
        if(this.playing) {
            return;
        }
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        if(this.currentFrame > endFrame || this.currentFrame < startFrame) {
            this.setImage(this.frames.get(this.startFrame).getDisplayImage());
            this.currentFrame = this.startFrame;
            this.gameClock.resetGameClock();
        }
        this.playing = true;
    }

    public void stopAnimation(Integer frameNumber) {
        this.setImage(this.frames.get(frameNumber).getDisplayImage());
        this.currentFrame = frameNumber;
        this.playing = false;
        this.gameClock.resetGameClock();
    }
    public void stopAnimation() {
        stopAnimation(this.startFrame);
    }


}