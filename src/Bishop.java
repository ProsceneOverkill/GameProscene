import processing.core.PApplet;
import remixlab.proscene.Scene;

class Bishop extends Piece {

    Bishop(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    void updateAvailableMoves(){
        int i, j;
        pathBlocked = false;
        i = xpos + 1;
        j = ypos + 1;
        pathBlocked = false;
        while (validMove(i, j)) {
            availableMoves.add(i + j * 8);
            i++;
            j++;
        }
        i = xpos + 1;
        j = ypos -1;
        pathBlocked = false;
        while (validMove(i, j)) {
            availableMoves.add(i + j * 8);
            i++;
            j--;
        }
        i = xpos - 1;
        j = ypos + 1;
        pathBlocked = false;
        while (validMove(i, j)) {
            availableMoves.add(i + j * 8);
            i--;
            j++;
        }
        i = xpos - 1;
        j = ypos - 1;
        pathBlocked = false;
        while (validMove(i, j)) {
            availableMoves.add(i + j * 8);
            i--;
            j--;
        }
    }
}
