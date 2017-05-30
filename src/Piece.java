import processing.core.*;
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
    PApplet parent;
    /*
        Moves with bitmask, first 3 bits for xpos, next 3 bits for ypos
        Move types next 3 bits, 0 for offensive, 1 for neutral, 2 for long Castling,
        3 for short castling, 4 for passant capture
        5 for white promotion, 6 for black promotion, 7 for long move pawn
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
        prevMove = xpos + ypos*8;
        this.parent = parent;
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

        removeMotionBinding(MouseAgent.WHEEL_ID);
        removeMotionBinding(MouseAgent.RIGHT_CLICK_ID);
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
        if (Chess.isGaveOver)
            return;
        if (Board.squares.get(xpos+8*ypos).piece != null) {
            Board.squares.get(xpos + 8 * ypos).movePiece();
            return;
        }
        Board.resetMoves();
        if (!isDead) {
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

    void updateMovements(){
        availableMoves.clear();
        updateAvailableMoves();
    }

    private void kill(Piece piece){
        piece.isDead = true;
        if (isWhite)
            Chess.killWhite(piece);
        else
            Chess.killBlack(piece);
        piece.z -= 8;
    }

    boolean isAttacked(){
        return Chess.isAttacked(xpos, ypos, isWhite);
    }

    boolean attacks(int x, int y){
        return availableMoves.contains(x + y*8);
    }

    boolean move(int x, int y, int moveType, boolean committed){
        if (Chess.whiteTurn != isWhite)
            return false;
        boolean enPassant1 = false, enPassant2 = false, promotion = false;
        int murderedX = 0, murderedY = 0;
        King king = isWhite ? Chess.whiteKing : Chess.blackKing;
        Piece killed = null, piece1 = null, piece2 = null;
        switch (moveType){
            case 0:
                if (Chess.boardState[y][x] != null){
                    murderedX = x;
                    murderedY = y;
                    killed = Chess.boardState[y][x];
                }
                break;
            case 1:
                break;
            case 2:
                Chess.boardState[y][0].updatePos(3, y);
                break;
            case 3:
                Chess.boardState[y][7].updatePos(5, y);
                break;
            case 4:
                murderedX = x;
                murderedY = ypos;
                killed = Chess.boardState[ypos][x];
                Chess.boardState[ypos][x] = null;
                break;
            case 5:
                promotion = true;
                if (Chess.boardState[y][x] != null){
                    murderedX = x;
                    murderedY = y;
                    killed = Chess.boardState[y][x];
                }
                break;
            case 7:
                if (isIn(y, x - 1)) {
                    piece1 = Chess.boardState[y][x - 1];
                    if (piece1 != null && piece1 instanceof Pawn && piece1.isWhite != isWhite)
                        enPassant1 = true;
                }

                if (isIn(y, x + 1)) {
                    piece2 = Chess.boardState[y][x + 1];
                    if (piece2 != null && piece2 instanceof Pawn && piece2.isWhite != isWhite)
                        enPassant2 = true;
                }
                break;
            default:
                break;
        }
        updatePos(x, y);
        Chess.updateMoves();

        if (king.isAttacked()) {
            resetMove();
            Chess.boardState[murderedY][murderedX] = killed;
            Chess.updateMoves();
            return false;
        }
        if (!committed){
            resetMove();
            Chess.boardState[murderedY][murderedX] = killed;
            Chess.updateMoves();
            return true;
        }
        if (promotion){
            promote();
            kill(this);
            Chess.updateMoves();
        }
        if (killed != null) {
            kill(killed);
            Chess.updateMoves();
        }

        Chess.whiteTurn = !Chess.whiteTurn;
        if (Chess.gameOver()) {
            Chess.isGaveOver = true;
            king = Chess.whiteTurn ? Chess.whiteKing : Chess.blackKing;
            if (king.isAttacked())
                Chess.result = Chess.whiteTurn ? "Black team wins" : "White team wins";
            else
                Chess.result = "Stalemate, it is a draw";
            System.out.println(Chess.result);

        }

        if (enPassant1) {
            int up = isWhite ? -1 : 1;
            piece1.availableMoves.add(xpos + (ypos - up) * 8 + 4 * 64);
        }
        if (enPassant2) {
            int up = isWhite ? -1 : 1;
            piece2.availableMoves.add(xpos + (ypos - up) * 8 + 4 * 64);
        }

        return true;
    }

    private void resetMove(){
        int x = prevMove & 7;
        int y = prevMove >>> 3;
        Chess.boardState[ypos][xpos] = null;
        Chess.boardState[y][x] = this;
        xpos = x;
        ypos = y;
        moves--;
    }

    private void updatePos(int x, int y){
        prevMove = xpos + ypos*8;
        Chess.boardState[ypos][xpos] = null;
        Chess.boardState[y][x] = this;
        xpos = x;
        ypos = y;
        moves++;
    }

    private void promote(){
        //Chess.boardState[ypos][xpos] = null;
        if(isWhite)
            new Queen(true, xpos, ypos, 14, "Queen2.obj",
                    Chess.scene, parent);
        else
            new Queen(false, xpos, ypos, 14, "Queen1.obj",
                    Chess.scene, parent);
    }
}
