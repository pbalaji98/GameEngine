package edu.virginia.engine.display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject {

	private Reference<DisplayObject> parent = new WeakReference<>(null);

	/* All DisplayObject have a unique id */
	private String id;

	private Point position = new Point(0,0);

	private Point pivotPoint = new Point(0,0);

	private double rotation = 0.0;

	protected boolean visible = true;

	private float alpha = 1;
	private float oldAlpha;

	private double scaleX = 1;

	private double scaleY = 1;

	private Shape hitbox;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
	}

	public void setParent(DisplayObject par) {
		Reference ref = new WeakReference<DisplayObject>(par);
		this.parent = ref;
	}
	public DisplayObject getParent() {return this.parent.get();}
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setPosition(int x, int y) {this.position = new Point(x,y); }
	public void setPosition(Point p) {this.position = p;}

	public Point getPosition() {return this.position; }

	public void setPivotPoint(int x, int y) {this.pivotPoint = new Point(x,y); }

	public Point getPivotPoint() {return this.pivotPoint; }

	public void setRotation(double rot) {this.rotation = rot; }

	public double getRotation() {return this.rotation ;}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getAlpha() {
		return this.alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public double getScaleX() {
		return this.scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return this.scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public void setHitbox(int type) {
		switch(type) {
			case 1:
				this.hitbox = new Rectangle((int)scaleX*this.position.x, (int)scaleY*this.position.y, (int)scaleX*this.getUnscaledWidth(), (int)scaleY*this.getUnscaledHeight());
				break;
			default:
				this.hitbox = new Rectangle((int)scaleX*this.position.x, (int)scaleY*this.position.y, (int)scaleX*this.getUnscaledWidth(), (int)scaleY*this.getUnscaledHeight());
				break;
		}

		}

	public Shape getHitbox() {
		return this.hitbox;
	}

	public boolean collidesWith(DisplayObject other) {
		return other.hitbox.intersects((double)this.hitbox.getBounds().x, (double)this.hitbox.getBounds().y, (double)this.hitbox.getBounds().width,  (double)this.hitbox.getBounds().height);
	}

	public Point localToGlobal(Point p) {
		if(this.parent.get() == null) {
			return new Point((int) (this.position.x + p.getX()), (int) (this.position.y + p.getY()));
		} else {
			Point p2 = this.parent.get().localToGlobal(this.position);
			return new Point((int) (p.getX() + p2.getX()), (int) (p.getY() + p2.getY()));
		}
	}

	public Point globalToLocal(Point p) {
		if(this.parent.get() == null) {
			return new Point((int) (p.getX() - this.position.x), (int) (p.getY() - this.position.y));
		} else {
			Point p2 = this.parent.get().globalToLocal(new Point((int) (p.getX() - this.position.x), (int) (p.getY() - this.position.y)));
			return p2;
			//return new Point((int) (p.getX() - p2.getX()), (int) (p.getY() - p2.getY()));
		}
	}

	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) {

	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {

		if (!this.visible) {
			return;
		}
		
		if (displayImage != null) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		Point p;

		if(this.parent.get() == null) {
			p = this.position;
		} else {
			p = this.parent.get().localToGlobal(this.position);
		}
		g2d.translate(p.x, p.y);
		g2d.rotate(Math.toRadians(this.getRotation()), this.pivotPoint.x, this.pivotPoint.y);
		g2d.scale(this.scaleX, this.scaleY);
		float curAlpha;
		this.oldAlpha = curAlpha = ((AlphaComposite) g2d.getComposite()).getAlpha();
		g2d.setComposite(AlphaComposite.getInstance(3, curAlpha * this.alpha));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		Point p;
		if(this.parent.get() == null) {
			p = this.position;
		} else {
			p = this.parent.get().localToGlobal(this.position);
		}
		g2d.setComposite(AlphaComposite.getInstance(3, this.oldAlpha));

		g2d.scale(1 / this.scaleX, 1 / this.scaleY);
		g2d.rotate(Math.toRadians(-this.getRotation()));
		g2d.translate(-p.x, -p.y);
	}

}
