import processing.core.PApplet;
import remixlab.proscene.Scene;

class Tower extends Piece {

    Tower(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    void updateAvailableMoves(){
        int i;
        pathBlocked = false;
        for (i = xpos+1; i < 8; i++){
            if (validMove(i, ypos))
                availableMoves.add(i + ypos*8);
            else
                break;
        }
        pathBlocked = false;
        for (i = xpos-1; i >= 0; i--){
            if (validMove(i, ypos))
                availableMoves.add(i + ypos*8);
            else
                break;
        }
        pathBlocked = false;
        for (i = ypos+1; i < 8; i++){
            if (validMove(xpos, i))
                availableMoves.add(xpos + i*8);
            else
                break;
        }
        pathBlocked = false;
        for (i = ypos-1; i >= 0; i--){
            if (validMove(xpos, i))
                availableMoves.add(xpos + i*8);
            else
                break;
        }
    }
}
