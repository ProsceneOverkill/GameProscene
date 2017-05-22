import remixlab.dandelion.geom.Quat;
import remixlab.proscene.*;
import processing.core.PApplet;


public class Chess extends PApplet{

    static Scene scene;
    static int w = 20;
    static Piece[][] boardState = new Piece[8][8];

    static boolean isAttacked(int x, int y, boolean isWhite){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (boardState[i][j] != null && boardState[i][j].isWhite != isWhite
                        && boardState[i][j].attacks(x, y))
                    return true;
        return false;
    }

    static private void initialize(){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (boardState[i][j] != null)
                    boardState[i][j].updateAvailableMoves();
    }

    Board board;

    @Override
    public void settings(){
        size(700,700, P3D);
    }

    @Override
    public void setup(){
        scene = new Scene(this);
        scene.setGridVisualHint(false);
        board = new Board(this);

        /*
        new Queen(false, 3, 0, 14, "Queen1.obj",
                scene, this);
        new Queen(true, 3, 7, 14, "Queen2.obj",
                scene, this);

        new King(true, 4, 7, 14, "King2.obj",
                scene, this);
        new King(false, 4, 0, 14, "King1.obj",
                scene, this);

        new Tower(false, 0, 0, 7, "Tower1.obj",
                scene, this);
        new Tower(false, 7, 0, 7, "Tower1.obj",
                scene, this);
        new Tower(true, 0, 7, 7, "Tower2.obj",
                scene, this);
        new Tower(true, 7, 7, 7, "Tower2.obj",
                scene, this);

        new Horse(false, 6, 0, 8, "Horse1.obj",
                scene, this);
        new Horse(false, 1, 0, 8, "Horse1.obj",
                scene, this)
        new Horse(true, 1, 7, 8, "Horse2.obj",
                scene, this);
        new Horse(true, 6, 7, 8, "Horse2.obj",
                scene, this);

        new Bishop(false, 2, 0, 10, "Bishop1.obj",
                scene, this);
        new Bishop(false, 5, 0, 10, "Bishop1.obj",
                scene, this);
        new Bishop(true, 2, 7, 10, "Bishop2.obj",
                scene, this);
        new Bishop(true, 5, 7, 10, "Bishop2.obj",
                scene, this);

        for(int i = 0; i < 8; i++){
            new Pawn(true, i, 6, 5, "Pawn2.obj",
                    scene, this);
            new Pawn(false, i, 1, 5, "Pawn1.obj",
                    scene, this);
        }*/
        initialize();
    }

    @Override
    public void draw(){
        background(125);
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
        //board.draw();
        scene.drawFrames();
    }

    public static void main(String[] args) {
        PApplet.main("Chess");
    }
}
