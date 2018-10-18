package edu.virginia.engine.display;

public class Animation {
    private String id;
    private Integer startFrame;
    private Integer endFrame;

    public Animation(String id, Integer start, Integer end) {
        this.id = id;
        this.startFrame = start;
        this.endFrame = end;
    }

    public String getId() {
        return id;
    }

    public Integer getStartFrame() {
        return startFrame;
    }

    public Integer getEndFrame() {
        return endFrame;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStartFrame(Integer startFrame) {
        this.startFrame = startFrame;
    }

    public void setEndFrame(Integer endFrame) {
        this.endFrame = endFrame;
    }
}
