import remixlab.dandelion.geom.Quat;
import remixlab.proscene.*;
import processing.core.PApplet;


public class Chess extends PApplet{

    static Scene scene;
    Board board;

    @Override
    public void settings(){
        size(800,800, P3D);
    }

    @Override
    public void setup(){
        scene = new Scene(this);
        board = new Board(this);
    }

    @Override
    public void draw(){
        background(0);
        lights();
        directionalLight(50, 50, 50,
                ((Quat)scene.camera().orientation()).x() - scene.camera().position().x(),
                ((Quat)scene.camera().orientation()).y() - scene.camera().position().y(),
                ((Quat)scene.camera().orientation()).z() - scene.camera().position().z());
        spotLight(150, 150, 150,
                scene.camera().position().x(),
                scene.camera().position().y(),
                scene.camera().position().z(),
                0, 0, -1, 1, 20);
        spotLight(100, 100, 100,
                scene.camera().position().x(),
                scene.camera().position().y(),
                scene.camera().position().z(),
                0, 0, 1, 1, 20);
        board.draw();
        scene.drawFrames();
    }

    public static void main(String[] args) {
        PApplet.main("Chess");
    }
}
