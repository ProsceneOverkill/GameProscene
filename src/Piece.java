import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import remixlab.bias.event.ClickEvent;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Scene;

import static processing.core.PConstants.LEFT;


public abstract class Piece extends InteractiveFrame{

    int xpos, ypos, z;
    PShape shape;
    PApplet parent;
    static private int center = Chess.w / 2;
    boolean isWhte;

    Piece(boolean isWhte, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(scene);
        this.isWhte = isWhte;
        this.xpos = xpos;
        this.ypos = ypos;
        this.z = z;
        this.parent = parent;
        shape = parent.loadShape(path);
        disableVisualHint();
        setHighlightingMode(InteractiveFrame.HighlightingMode.NONE);
        //setPickingPrecision(PickingPrecision.FIXED);
        setShape("display");
        setClickBinding(LEFT, 1, "play");

        setPickingShape("pick");
        Chess.boardState[xpos][ypos] = this;
    }

    public void display(PGraphics pg) {
        pg.pushMatrix();
        pg.translate(getAbsoluteXPos(), getAbsoluteYPos(), z);
        pg.shape(shape);
        pg.popMatrix();
    }

    public void pick(PGraphics pg) {
        pg.pushMatrix();
        pg.translate(getAbsoluteXPos(), getAbsoluteYPos(), z);
        pg.box(10);
        pg.popMatrix();
    }

    public void play(ClickEvent event) {
        System.out.println("Piece clicked");
    }

    private int getAbsoluteXPos(){
        return Chess.w * (xpos-4) + center;
    }

    private int getAbsoluteYPos(){
        return Chess.w * (ypos-4) + center;
    }

    abstract boolean validMove(int x, int y);

    boolean move(int x, int y){
        if (!validMove(x, y))
            return false;
        xpos = x;
        ypos = y;
        return true;
    }
}
