import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;


class Board {

    PShape shape;
    PApplet parent;

    Board(PApplet parent){
        int width = 20;
        int delta = 4 * width - width / 2;
        this.parent = parent;
        Cube.initialize(10, 10, 4);
        shape = parent.createShape(PConstants.GROUP);
        PShape tmp;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                tmp = Cube.buildShape(i*width-delta, j*width-delta, (i + j)%2 == 0, parent);
                shape.addChild(tmp);
            }
        }
    }

    void draw(){
        parent.shape(shape);
    }
}
