import processing.core.PApplet;
import remixlab.proscene.Scene;

class Horse extends Piece {

    Horse(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        return isIn(x, y) &&
                (Chess.boardState[x][y] == null || Chess.boardState[x][y].isWhite != isWhite);
    }

    @Override
    void updateAvailableMoves(){
        for (int i = -2; i <= 2; i++)
            if ( i != 0)
                for (int j = -2; j <= 2; j++)
                    if (j != 0 && j != i)
                        if (validMove(i + xpos, j + ypos))
                            availableMoves.add(i+xpos + (j+ypos)*8);
    }
}
