import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;


public class Board {

    PShape shape;
    PApplet parent;

    Board(PApplet parent){
        this.parent = parent;
        Cube.initialize(10, 10, 4);
        shape = parent.createShape(PConstants.GROUP);
        PShape tmp;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                tmp = Cube.buildShape(i*20-70, j*20-70, (i + j) %2 == 0, parent);
                shape.addChild(tmp);
            }
        }
    }

    void draw(){
        parent.shape(shape);
    }
}
