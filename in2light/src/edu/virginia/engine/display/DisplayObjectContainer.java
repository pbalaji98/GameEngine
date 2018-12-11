package edu.virginia.engine.display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


// testing 123 -- krishan
// testing2

public class DisplayObjectContainer extends DisplayObject {

    private ArrayList<DisplayObject> children = new ArrayList<DisplayObject>();

    /**
     * Constructors: can pass in the id OR the id and image's file path and
     * position OR the id and a buffered image and position
     */

    public DisplayObjectContainer(String id) {super(id);}
    public DisplayObjectContainer(String id, String fileName) {
        super(id);
        super.setImage(fileName);
    }

    public void addChild(DisplayObject child) {
        child.setParent(this);
        children.add(child);
    }

    public void addChildAtIndex(DisplayObject child, int i) {
        child.setParent(this);
        children.add(i,child);
    }

    public void removeChild(String id) {
        for (int i=0; i<children.size(); i++) {
            if((children.get(i)).getId() == id) {
                children.remove(i);
            }
        }
    }

    public void removeByIndex(int i) {
        children.remove(i);
    }

    public void removeAll() {
        children.clear();
    }

    public boolean contains(DisplayObject child) {
        for (int i=0; i<children.size(); i++) {
            if((children.get(i)) == child) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<DisplayObject> getChildren() {
        return children;
    }








}
