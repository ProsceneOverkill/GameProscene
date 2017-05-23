import processing.core.PApplet;
import remixlab.proscene.Scene;

class Horse extends Piece {

    Horse(boolean isWhite, int xpos, int ypos, int z, String path, Scene scene, PApplet parent){
        super(isWhite, xpos, ypos, z, path, scene, parent);
    }

    @Override
    boolean validMove(int x, int y){
        return isIn(y, x) &&
                (Chess.boardState[y][x] == null || Chess.boardState[y][x].isWhite != isWhite);
    }

    @Override
    void updateAvailableMoves(){
        int aux1 = xpos + 2;
        int aux2 = ypos + 1;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
        aux1 = xpos + 2;
        aux2 = ypos - 1;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
        aux1 = xpos - 2;
        aux2 = ypos + 1;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
        aux1 = xpos - 2;
        aux2 = ypos - 1;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
        aux1 = xpos + 1;
        aux2 = ypos + 2;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
        aux1 = xpos + 1;
        aux2 = ypos - 2;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
        aux1 = xpos - 1;
        aux2 = ypos + 2;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
        aux1 = xpos - 1;
        aux2 = ypos - 2;
        if (validMove(aux1, aux2))
            availableMoves.add(aux1 + (aux2)*8);
    }
}
