import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;
import remixlab.bias.event.ClickEvent;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Scene;

import static processing.core.PConstants.LEFT;

//open mesh para simplificar los modelos 3D
public abstract class Piece extends InteractiveFrame{

    int xpos, ypos, z;
    PShape shape;
    PApplet parent;
    static private int center = Chess.w / 2;
    boolean isWhite;
    AxisPlaneConstraint theRotConstraint;
    AxisPlaneConstraint.Type fType;

    Piece(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(scene);
        this.isWhite = isWhite;
        this.xpos = xpos;
        this.ypos = ypos;
        this.z = z;
        this.parent = parent;
        shape = parent.loadShape(path);

        theRotConstraint = new LocalConstraint();
        fType = AxisPlaneConstraint.Type.FORBIDDEN;
        theRotConstraint.setRotationConstraintType(fType);

        disableVisualHint();
        setHighlightingMode(InteractiveFrame.HighlightingMode.NONE);
        //setPickingPrecision(PickingPrecision.FIXED);
        setShape("display");
        setClickBinding(LEFT, 1, "play");

        setPickingShape("pick");
        Chess.boardState[xpos][ypos] = this;

        setConstraint(theRotConstraint);
    }

    public void display(PGraphics pg) {
        pg.pushMatrix();
        pg.translate(getAbsoluteXPos(), getAbsoluteYPos(), z);
        if(!isWhite)
            pg.rotate(PConstants.PI);
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
