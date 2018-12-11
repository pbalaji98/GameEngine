package edu.virginia.engine.display;

/**
 * Stores indexes for accessing specific frames from a frames list
 */
public class Animation {
    private String id;

    // Indexes within frames list
    private int startFrame;
    private int endFrame;

    public Animation(String id, int startFrame, int endFrame) {
        this.id = id;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public String getID() {
        return this.id;
    }

    public int getStartFrame() {
        return this.startFrame;
    }

    public int getEndFrame() {
        return this.endFrame;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
    }

    public void setEndFrmae(int endFrame) {
        this.endFrame = endFrame;
    }
}
