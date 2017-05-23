//Implementado desde cero

import processing.core.PApplet;
import remixlab.proscene.Scene;


class Queen extends Piece {

    Queen(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    void updateAvailableMoves(){
        int i, j;
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

    public boolean isvalid(int x, int y){
        if(x <= 40 && y <= 40 && x >= -40 && y >= -40)
            return true;
        return false;

    }
}

