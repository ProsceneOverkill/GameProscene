import processing.core.PShape;
import remixlab.bias.BogusEvent;
import remixlab.dandelion.core.Eye;
import remixlab.dandelion.core.GenericFrame;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Scene;

/**
 * Created by alvaro on 25/05/17.
 */
public class Square extends InteractiveFrame {
    PShape sh;
    boolean isWhite;

    public Square(Eye eye) {
        super(eye);
    }

    public Square(Scene scene) {
        super(scene);
    }

    public Square(Scene scene, GenericFrame genericFrame) {
        super(scene, genericFrame);
    }

    public Square(Scene scene, PShape pShape, boolean isWhite) {
        super(scene, pShape);
        sh = pShape;
        this.isWhite = isWhite;
    }

    public Square(Scene scene, GenericFrame genericFrame, PShape pShape) {
        super(scene, genericFrame, pShape);
    }

    public Square(Scene scene, String s) {
        super(scene, s);
    }

    public Square(Scene scene, Object o, String s) {
        super(scene, o, s);
    }

    public Square(Scene scene, GenericFrame genericFrame, String s) {
        super(scene, genericFrame, s);
    }

    public Square(Scene scene, GenericFrame genericFrame, Object o, String s) {
        super(scene, genericFrame, o, s);
    }

    protected Square(InteractiveFrame interactiveFrame) {
        super(interactiveFrame);
    }




    public void paintMoves(){
        sh.setFill(scene().pApplet().color(153,0,76));
    }

    @Override
    public boolean checkIfGrabsInput(BogusEvent event){
        boolean j = super.checkIfGrabsInput(event);

        if(j)
            sh.setFill(scene().pApplet().color(153,0,76));
        else
            if(isWhite)
                sh.setFill(scene().pApplet().color(255));
            else
                sh.setFill(scene().pApplet().color(0));

        return j;

    }

}
