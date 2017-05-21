import processing.core.PApplet;
import remixlab.proscene.Scene;

class King extends Piece {

    King(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        return isIn(x, y) &&
                (Chess.boardState[x][y] == null || Chess.boardState[x][y].isWhite != isWhite);
    }

    @Override
    void updateAvailableMoves(){
        for (int i = -1; i <= 1; i++){
            if (validMove(xpos + 1, ypos + i))
                availableMoves.add(xpos+1 + (ypos+i)*8);
            if (validMove(xpos - 1, ypos + i))
                availableMoves.add(xpos-1 + (ypos+i)*8);
        }
        if (validMove(xpos, ypos + 1))
            availableMoves.add(xpos + (ypos+1)*8);
        if (validMove(xpos, ypos - 1))
            availableMoves.add(xpos + (ypos-1)*8);
        if (moves == 0 && !Chess.isAttacked(xpos, ypos, isWhite)){
            if (Chess.boardState[xpos-4][ypos]!=null && Chess.boardState[xpos-4][ypos].moves==0) {
                if (Chess.boardState[xpos - 1][ypos] == null &&
                        !Chess.isAttacked(xpos - 1, ypos, isWhite) &&
                        Chess.boardState[xpos - 2][ypos] == null
                        && !Chess.isAttacked(xpos - 2, ypos, isWhite) &&
                        Chess.boardState[xpos - 3][ypos] == null)

                    availableMoves.add(xpos-2 + ypos*8);
            }
            if (Chess.boardState[xpos+3][ypos]!=null && Chess.boardState[xpos+3][ypos].moves==0) {
                if (Chess.boardState[xpos + 1][ypos] == null &&
                        !Chess.isAttacked(xpos + 1, ypos, isWhite) &&
                        Chess.boardState[xpos + 2][ypos] == null
                        && !Chess.isAttacked(xpos + 2, ypos, isWhite))
                    availableMoves.add(xpos + 2 + ypos * 8);
            }
        }
    }
}
