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

    public boolean isvalid(int x, int y){
        if(x <= 40 && y <= 40 && x >= -40 && y >= -40)
            return true;
        return false;

    }
}

