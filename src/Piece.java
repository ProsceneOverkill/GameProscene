import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;
import remixlab.bias.event.ClickEvent;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.MouseAgent;
import remixlab.proscene.Scene;

import java.util.HashSet;

import static processing.core.PConstants.LEFT;


public abstract class Piece extends InteractiveFrame{

    int xpos, ypos, moves = 0, prevMove = -1;
    private  int z;
    boolean pathBlocked, isDead = false;
    /*
        Moves with bitmask, first 3 bits for xpos, next 3 bits for ypos
        Move types next 3 bits, 0 for offensive, 1 for neutral, 2 for long Castling,
        3 for short castling, 4 for passant capture
        5 for white promotion and 6 for black promotion
    */
    HashSet<Integer> availableMoves = new HashSet<>();

    private PShape shape;
    static private int center = Chess.w / 2;
    boolean isWhite;

    Piece(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(scene);
        this.isWhite = isWhite;
        this.xpos = xpos;
        this.ypos = ypos;
        this.z = z;
        shape = parent.loadShape(path);

        AxisPlaneConstraint theRotConstraint = new LocalConstraint();
        AxisPlaneConstraint.Type fType = AxisPlaneConstraint.Type.FORBIDDEN;
        theRotConstraint.setRotationConstraintType(fType);

        disableVisualHint();
        setHighlightingMode(InteractiveFrame.HighlightingMode.NONE);
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
        if (!isDead) {
            Board.resetMoves();
            for (int i : availableMoves)
                Board.setMove(i & 63, this, i >>> 6);
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

    void updateMoves(){
        availableMoves.clear();
        updateAvailableMoves();
    }

    private void kill(int i, int j){
        Chess.boardState[i][j].isDead = true;
        if (isWhite)
            Chess.killWhite(Chess.boardState[i][j]);
        else
            Chess.killBlack(Chess.boardState[i][j]);
        Chess.boardState[i][j].z -= 8;
        Chess.boardState[i][j] = null;
    }

    void move(int x, int y, int moveType){
        switch (moveType){
            case 0:
                if (Chess.boardState[y][x] != null)
                    kill(y, x);
                break;
            case 1:
                Chess.boardState[y][x] = this;
                break;
            case 2:
                Chess.boardState[y][0].updatePos(3, y);
                break;
            case 3:
                Chess.boardState[y][7].updatePos(5, y);
                break;
            case 4:
                kill(ypos, x);
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;
        }
        updatePos(x, y);
        Chess.updateMoves();
    }

    private void updatePos(int x, int y){
        prevMove = xpos + ypos*8;
        Chess.boardState[y][x] = this;
        Chess.boardState[ypos][xpos] = null;
        xpos = x;
        ypos = y;
        moves++;
    }

    boolean attacks(int x, int y){
        return availableMoves.contains(x + y*8);
    }
}
