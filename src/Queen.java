//Implementado desde cero

import processing.core.PApplet;
import remixlab.proscene.Scene;


class Queen extends Piece {


    Queen(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        return true;
    }
}