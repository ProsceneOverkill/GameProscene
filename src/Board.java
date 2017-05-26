//Implementado desde cero

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.MouseAgent;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;


class Board {

    private PShape shape;
    private PApplet parent;
    private ArrayList<InteractiveFrame> myBoard;
    AxisPlaneConstraint theConstraints;
    AxisPlaneConstraint.Type fType;

    Board(PApplet parent){
        int delta = 4 * Chess.w - Chess.w / 2;
        this.parent = parent;
        Cube.initialize(10, 10, 4);
        myBoard = new ArrayList<>(64);
        theConstraints = new LocalConstraint();
        fType = AxisPlaneConstraint.Type.FORBIDDEN; //constraint -> no translation & no rotation
        theConstraints.setRotationConstraintType(fType);
        theConstraints.setTranslationConstraintType(fType);
        Square square;
        PShape tmp;

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                tmp = Cube.buildShape(i*Chess.w-delta, j*Chess.w-delta, -4,
                        (i + j)%2 == 0, parent);
                square = new Square(Chess.scene, tmp, (i + j)%2 == 0);
                square.setConstraint(theConstraints);
                square.removeMotionBinding(MouseAgent.WHEEL_ID);
                square.setHighlightingMode(InteractiveFrame.HighlightingMode.NONE);
                myBoard.add(i, square);
            }
        }
        
    }

    public PShape getShape(){
        return shape;
    }

    public ArrayList<InteractiveFrame> getMyBoard() {
        return myBoard;
    }

    void draw(){
        parent.shape(shape);
    }
}
