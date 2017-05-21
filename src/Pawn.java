import processing.core.PApplet;
import remixlab.proscene.Scene;

class Pawn extends Piece {

    Pawn(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        if (!isIn(x, y)) return false;

        if (Chess.boardState[x][y] != null)
            return xpos != x && Chess.boardState[x][y].isWhite != isWhite;

        return xpos == x;
    }

    @Override
    void updateAvailableMoves(){
        int up = -1;
        if (isWhite)
            up = 1;
        for (int i = -1; i <= 1; i++)
            if (validMove(xpos + i, ypos+up))
                availableMoves.add(xpos + i + 8*(ypos+up));
        if (moves == 0){
            up *= 2;
            if (validMove(xpos, ypos+up))
                availableMoves.add(xpos + 8*(ypos+up));
        }
        if (isIn(xpos+1, ypos))
            if ((Chess.boardState[xpos+1][ypos] instanceof Pawn)){
                Piece piece = Chess.boardState[xpos+1][ypos];
                if (piece.moves == 1 && Math.abs(piece.prevMove >>> 3 - piece.ypos) == 2)
                    availableMoves.add(xpos+1 + (ypos+up)*8);
            }
        if (isIn(xpos-1, ypos))
            if ((Chess.boardState[xpos-1][ypos] instanceof Pawn)){
                Piece piece = Chess.boardState[xpos-1][ypos];
                if (piece.moves == 1 && Math.abs(piece.prevMove >>> 3 - piece.ypos) == 2)
                    availableMoves.add(xpos-1 + (ypos+up)*8);
            }
    }
}
