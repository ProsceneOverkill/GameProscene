import processing.core.PApplet;
import remixlab.proscene.Scene;

class Bishop extends Piece {

    Bishop(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        return true;
    }
}