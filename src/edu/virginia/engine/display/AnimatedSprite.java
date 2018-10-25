package edu.virginia.engine.display;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

import edu.virginia.engine.util.GameClock;

/**
 * Sprite class that supports animations
 */
public class AnimatedSprite extends Sprite {

	// Stores all frames for all animations.  Frames for each animation
    // must be consecutive.
    private ArrayList<BufferedImage> frames = new ArrayList<>();

    // Each animation indexes into this.frames
    private ArrayList<Animation> animations = new ArrayList<>();

    // If false, the frame won't progress in the animation
    private boolean playing;

    // File for the spritesheet
    private String fileName;

    // The current animation
    private Animation currentAnimation;

    private int currentFrame = 0;

    // If currentFrame == stopFrame, the animation halts
    private int stopFrame = -1;

    // Number of ticks per frame
    private static final int DEFAULT_ANIMATION_SPEED = 10;
    private int animationSpeed;

    // Tracks the number of ticks we've spent in the current frame
    private int animationClockCounter = 0;

    // Used to track whether we need to reset the currentFrame because we swapped animations
    private Animation prevAnimation;

    /**
     * Initializes animations, and reads in the spritesheet
     * @param ID String ID
     * @param file spritesheet filename
     * @param position unused
     */
    public AnimatedSprite(String ID, String file, Point position) {
        super(ID,file);
        this.fileName = file;
        this.animationSpeed = DEFAULT_ANIMATION_SPEED;

        BufferedImage spriteSheet = this.readImage(file);
        for (int i = 0; i < 21; i++) {
            BufferedImage frame = spriteSheet.getSubimage(80 + 17 * i, 1, 16, 32);
            this.frames.add(frame);
        }

        Animation jumping = new Animation("jumping", 4, 5);
        Animation walking = new Animation("walking", 1, 3);
        Animation standing = new Animation("standing", 0, 0);
        animations.add(jumping);
        animations.add(walking);
        animations.add(standing);

        currentAnimation = standing;
    }
    
    public void setAnimations(ArrayList<Animation> animations) {
        this.animations = animations;
    }

    public void setAnimationSpeed(int speed) {
        this.animationSpeed = speed;
    }

    /**
     * Draws the sprite
     * @param g the Graphics2D object to draw on
     */
    public void draw(Graphics g) {
        // Don't draw if !DisplayObject.visible
        if (!this.visible) {
            return;
        }

        // If we've waited the number of ticks to cycle the frame, and we're not stopped on a frame
        if (animationClockCounter == animationSpeed && currentFrame != stopFrame && this.playing) {

            // Progress frame
            animationClockCounter = 0;
            currentFrame++;

            // If we've exhausted the frames for this animation
            if (currentFrame == currentAnimation.getEndFrame() + 1) {
                // Reset the frame to the first in the animation cycle
                currentFrame = currentAnimation.getStartFrame();
            }
        }

		/*
		 * Get the graphics and apply this objects transformations
		 * (rotation, etc.)
		 */
		Graphics2D g2d = (Graphics2D) g;
		applyTransformations(g2d);

		/* Actually draw the image, perform the pivot point translation here */
		g2d.drawImage(frames.get(currentFrame), 0, 0,
				(int) (getUnscaledWidth()),
				(int) (getUnscaledHeight()), null);

		/*
		 * undo the transformations so this doesn't affect other display
		 * objects
		 */
		reverseTransformations(g2d);

		// Progress the animation tick counter
		if (this.playing) {
            animationClockCounter++;
        }
    }

    public Animation getAnimation(String ID) {
        for (Animation animation : this.animations) {
            if (animation.getID().equals(ID)) {
                return animation;
            }
        }

        return null;
    }

    public void animate(Animation animation) {
    	this.currentAnimation = animation;

    	// If we've swapped animations, then reset the currentFrame to the first frame in
        // The new animation
    	if (prevAnimation != currentAnimation) {
    	    currentFrame = this.currentAnimation.getStartFrame();
    	    prevAnimation = currentAnimation;
        }

        this.playing = true;
    }

    public void animate(String animationId) {
        Animation animation = getAnimation(animationId);
        this.animate(animation);
    }

    public void stopAnimation(int stopFrameNumber) {
        this.stopFrame = stopFrameNumber;
    }

    public void stopAnimation() {
        this.stopFrame = this.currentAnimation.getStartFrame();
    }
}
