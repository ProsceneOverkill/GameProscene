import processing.core.PApplet;
import remixlab.proscene.Scene;

class King extends Piece {

    King(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        return isIn(x, y) &&
                (Chess.boardState[y][x] == null || Chess.boardState[y][x].isWhite != isWhite);
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
        if (moves == 0 && !isAttacked()){
            if (Chess.boardState[ypos][0]!=null && Chess.boardState[ypos][0].moves==0) {
                if (Chess.boardState[ypos][xpos - 1] == null &&
                        !Chess.isAttacked(xpos - 1, ypos, isWhite) &&
                        Chess.boardState[ypos][xpos - 2] == null
                        && !Chess.isAttacked(xpos - 2, ypos, isWhite) &&
                        Chess.boardState[ypos][xpos - 3] == null)
                    availableMoves.add(xpos-2 + ypos*8 + 128);
            }
            if (Chess.boardState[ypos][7]!=null && Chess.boardState[ypos][7].moves==0) {
                if (Chess.boardState[ypos][xpos + 1] == null &&
                        !Chess.isAttacked(xpos + 1, ypos, isWhite) &&
                        Chess.boardState[ypos][xpos + 2] == null
                        && !Chess.isAttacked(xpos + 2, ypos, isWhite))
                    availableMoves.add(xpos + 2 + ypos * 8 + 3*64);
            }
        }
    }
}
