import processing.core.PApplet;
import remixlab.proscene.Scene;

class Pawn extends Piece {

    Pawn(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        if (!isIn(y, x)) return false;

        if (Chess.boardState[y][x] != null)
            return xpos != x && Chess.boardState[y][x].isWhite != isWhite;

        return xpos == x;
    }

    @Override
    void updateAvailableMoves(){
        int up = isWhite ? -1 : 1;
        for (int i = -1; i <= 1; i++)
            if (validMove(xpos + i, ypos+up)) {
                if (ypos+up == 0 || ypos+up == 7)
                    availableMoves.add(xpos + i + 8*(ypos+up) + 5*64);
                else if (i == 0)
                    availableMoves.add(xpos + i + 8 * (ypos + up) + 64);
                else
                    availableMoves.add(xpos + i + 8 * (ypos + up));
            }

        if (moves == 0){
            up *= 2;
            if (validMove(xpos, ypos+up))
                availableMoves.add(xpos + 8*(ypos+up) + 7*64);
        }
    }
}
