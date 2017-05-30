//Implementado desde cero

import processing.core.PApplet;
import processing.core.PShape;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.MouseAgent;

import java.util.ArrayList;


class Board {
    
    static ArrayList<Square> squares;

    static void setMove(int index, Piece piece, int move){
        squares.get(index).setMove(piece, move);
    }

    static void resetMoves(){
        for (Square square : squares)
            square.removeMove();
    }

    Board(PApplet parent){
        int delta = 4 * Chess.w - Chess.w / 2;
        Cube.initialize(5, 5, 2);
        squares = new ArrayList<>(64);
        for(int i = 0; i < 64; i++)
            squares.add(null);

        AxisPlaneConstraint theConstraints = new LocalConstraint();
        AxisPlaneConstraint.Type fType = AxisPlaneConstraint.Type.FORBIDDEN;
        theConstraints.setRotationConstraintType(fType);
        theConstraints.setTranslationConstraintType(fType);
        Square square;
        PShape tmp;
        int index;
        for (int i = 0; i < 8; i++){
            index = i;
            for (int j = 0; j < 8; j++){
                tmp = Cube.buildShape(i*Chess.w-delta, j*Chess.w-delta, -4,
                        (i + j)%2 == 0, parent);
                square = new Square(Chess.scene, tmp, (i + j)%2 == 0, i, j);
                square.setConstraint(theConstraints);
                square.removeMotionBinding(MouseAgent.WHEEL_ID);
                square.setHighlightingMode(InteractiveFrame.HighlightingMode.NONE);

                squares.set(index, square);
                index += 8;
            }
        }
    }
}
