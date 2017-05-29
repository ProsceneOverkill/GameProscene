import processing.core.PShape;
import remixlab.bias.BogusEvent;
import remixlab.bias.event.ClickEvent;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Scene;
import static processing.core.PConstants.LEFT;


public class Square extends InteractiveFrame {
    private PShape sh;
    boolean isWhite;
    Piece piece;
    private int x, y, move = 0;

    Square(Scene scene, PShape pShape, boolean isWhite, int x, int y) {
        super(scene, pShape);
        sh = pShape;
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
        setClickBinding(LEFT, 1, "play");
    }

    void removeMove(){
        piece = null;
    }

    void setMove(Piece piece, int move){
        this.piece = piece;
        this.move = move;
        highlight();
    }

    public void play(ClickEvent event) {
        movePiece();
    }

    void movePiece(){
        if (piece != null) {
            piece.move(x, y, move, true);
            piece = null;
        }
        Board.resetMoves();
    }

    private void highlight(){
        sh.setFill(scene().pApplet().color(153,0,76));
    }

    @Override
    public boolean checkIfGrabsInput(BogusEvent event){
        boolean j = super.checkIfGrabsInput(event);

        if(j)
            sh.setFill(scene().pApplet().color(153, 0, 76));
        else
            if(isWhite)
                sh.setFill(scene().pApplet().color(255));
            else
                sh.setFill(scene().pApplet().color(0));

        return j;
    }
}
