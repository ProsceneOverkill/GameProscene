import processing.core.PShape;
import remixlab.dandelion.geom.Quat;
import remixlab.proscene.*;
import processing.core.PApplet;


public class Chess extends PApplet{

    static Scene scene;
    static int w = 20;
    static Piece[][] boardState = new Piece[8][8];

    Board board;
    //PShape pawn1, pawn2;
    //PShape horse1, horse2;
    //PShape tower1, tower2;
    //PShape queen1, queen2;
    //PShape king1, king2;

    @Override
    public void settings(){
        size(700,700, P3D);
    }

    @Override
    public void setup(){
        //pawn1 = loadShape("Pawn1.obj"); pawn2 = loadShape("Pawn2.obj");
        //horse1 = loadShape("Horse1.obj"); horse2 = loadShape("Horse2.obj");
        //tower1 = loadShape("Tower1.obj"); tower2 = loadShape("Tower2.obj");
        //queen1 = loadShape("Queen1.obj"); queen2 = loadShape("Queen2.obj");

        //king1 = loadShape("King1.obj"); king2 = loadShape("King2.obj");
        scene = new Scene(this);
        scene.setGridVisualHint(false);
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
        Queen queen1 = new Queen(false, 4, 0, 14, "Queen1.obj",
                scene, this);
        Queen queen2 = new Queen(true, 4, 7, 14, "Queen2.obj",
                scene, this);
        board.draw();
        scene.drawFrames();
    }

    public static void main(String[] args) {
        PApplet.main("Chess");
    }
}
