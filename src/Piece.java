import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;
import remixlab.bias.event.ClickEvent;
import remixlab.bias.event.MotionShortcut;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.MouseAgent;
import remixlab.proscene.Scene;
import remixlab.bias.Shortcut;

import java.util.ArrayList;
import java.util.HashSet;

import static processing.core.PConstants.LEFT;


public abstract class Piece extends InteractiveFrame{

    int xpos, ypos, moves = 0, prevMove = -1;
    private  int z;
    boolean pathBlocked;
    ArrayList<Square> board;
    //Moves with bitmask, first 3 bits for xpos, next 3 bits for ypos
    //Move types next 3 bits, 0 for offensive, 1 for neutral, 2 for long Castling,
    // 3 for short castling, 4 for passant capture
    //5 for white promotion and 6 for black promotion
    HashSet<Integer> availableMoves = new HashSet<>();

    private PShape shape;
    private PApplet parent;
    static private int center = Chess.w / 2;
    boolean isWhite;

    Piece(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(scene);
        this.isWhite = isWhite;
        this.xpos = xpos;
        this.ypos = ypos;
        this.z = z;
        this.parent = parent;
        shape = parent.loadShape(path);

        AxisPlaneConstraint theRotConstraint = new LocalConstraint();
        AxisPlaneConstraint.Type fType = AxisPlaneConstraint.Type.FORBIDDEN;
        theRotConstraint.setRotationConstraintType(fType);

        disableVisualHint();
        setHighlightingMode(InteractiveFrame.HighlightingMode.NONE);
        //setPickingPrecision(PickingPrecision.FIXED);
        setShape("display");
        setClickBinding(LEFT, 1, "play");

        setPickingShape("pick");
        Chess.boardState[ypos][xpos] = this;
        MouseAgent ma = new MouseAgent(Chess.scene);
        removeMotionBinding(MouseAgent.WHEEL_ID);

        setConstraint(theRotConstraint);

    }

    public void pick(PGraphics pg) {
        pg.pushMatrix();
        pg.translate(getAbsoluteXPos(), getAbsoluteYPos(), z);
        pg.box(10);
        pg.popMatrix();
    }

    public void display(PGraphics pg) {
        pg.pushMatrix();
        pg.translate(getAbsoluteXPos(), getAbsoluteYPos(), z);
        if(!isWhite)
            pg.rotate(PConstants.PI);
        pg.shape(shape);
        pg.popMatrix();
    }

    private int getAbsoluteXPos(){
        return Chess.w * (xpos-4) + center;
    }

    private int getAbsoluteYPos(){
        return Chess.w * (ypos-4) + center;
    }

    public void play(ClickEvent event) {
        System.out.println("Piece clicked, Moves:");
        int x;
        int y;
        int index;
        for (int i : availableMoves){
            x = i & 7;
            y = (i >>> 3)&7;
            System.out.println("x: " + (i & 7) + ", y: " + ((i >>> 3)&7) +
                    ", special move: " + ((i >>> 6)&7));

            index = y*8 + x;
            System.out.println(index);
            Board.myBoard.get(index).paintMoves();

        }
    }

    boolean isIn(int i, int j){
        return i >= 0 && j >= 0 && i < 8 && j < 8;
    }

    boolean validMove(int x, int y){
        if (pathBlocked) return false;
        if (!isIn(y, x)) return false;
        if (Chess.boardState[y][x] == null)
            return true;

        pathBlocked = true;
        return Chess.boardState[y][x].isWhite != isWhite;
    }

    abstract void updateAvailableMoves();

    void move(int x, int y, int moveType){
        prevMove = xpos + ypos*8;
        switch (moveType){
            case 0:
                Chess.boardState[y][x] = this;
                break;
            case 1:
                Chess.boardState[y][x] = this;
                break;
            case 2:
                Chess.boardState[y][x] = this;
                Chess.boardState[y][3] = Chess.boardState[y][0];
                Chess.boardState[y][0] = null;
                break;
            case 3:
                Chess.boardState[y][x] = this;
                Chess.boardState[y][5] = Chess.boardState[y][7];
                Chess.boardState[y][7] = null;
                break;
            case 4:
                Chess.boardState[y][x] = this;
                Chess.boardState[ypos][x] = null;
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;
        }
        Chess.boardState[ypos][xpos] = null;
        xpos = x;
        ypos = y;
        moves++;
        availableMoves.clear();
        updateAvailableMoves();
    }

    boolean attacks(int x, int y){
        return availableMoves.contains(x + y*8);
    }
}
