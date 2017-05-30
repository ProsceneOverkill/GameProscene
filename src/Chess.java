
import remixlab.dandelion.core.GenericFrame;
import remixlab.dandelion.core.KeyFrameInterpolator;
import processing.core.PImage;
import remixlab.dandelion.geom.Quat;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.*;
import processing.core.PApplet;

import java.util.HashSet;


public class Chess extends PApplet{

    static Scene scene;

    static int w = 10;
    static Piece[][] boardState = new Piece[8][8];
    private static Piece[][] deadWithe = new Piece[8][2];
    private static Piece[][] deadBlack = new Piece[8][2];
    private static int deadWhiteI, deadWhiteJ, deadBlackI, deadBlackJ;
    private Board board;
    static boolean whiteTurn = true, isGaveOver = false;
    static String result;
    static King whiteKing, blackKing;
    static KeyFrameInterpolator kfi;
    static boolean firstTime = true;

    static void killWhite(Piece piece){
        deadWithe[deadWhiteI][deadWhiteJ] = piece;
        piece.xpos = deadWhiteJ + 8;
        piece.ypos = deadWhiteI;
        deadWhiteJ++;
        if (deadWhiteJ == 2){
            deadWhiteJ = 0;
            deadWhiteI++;
            if (deadWhiteI == 8)
                deadWhiteI = deadWhiteJ = 0;
        }
    }

    static void killBlack(Piece piece){
        deadBlack[deadBlackI][deadBlackJ] = piece;
        piece.xpos = -deadBlackJ - 1;
        piece.ypos = 7 - deadBlackI;
        deadBlackJ++;
        if (deadBlackJ == 2){
            deadBlackJ = 0;
            deadBlackI++;
            if (deadBlackI == 8)
                deadBlackI = deadBlackJ = 0;
        }
    }

    static boolean isAttacked(int x, int y, boolean isWhite){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (boardState[i][j] != null && boardState[i][j].isWhite != isWhite
                        && boardState[i][j].attacks(x, y))
                    return true;
        return false;
    }

    static void updateMoves(){
        Board.resetMoves();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (boardState[i][j] != null)
                    boardState[i][j].updateMovements();
    }

    static boolean gameOver(){
        int move;
        HashSet<Integer> tmp;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[i][j] != null && boardState[i][j].isWhite == whiteTurn) {
                    tmp = new HashSet<>(boardState[i][j].availableMoves);
                    for (int aux : tmp) {
                        move = aux >>> 6;
                        if (move == 0 || move == 4) {
                            if (boardState[i][j].move(aux & 7, (aux >>> 3) & 7, move,false))
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    void loadPieces(){
        new Queen(false, 3, 0, 6, "Queen1s.obj",
                scene, this);
        new Queen(true, 3, 7, 6, "Queen2s.obj",
                scene, this);

        whiteKing = new King(true, 4, 7, 6, "King2s.obj",
                scene, this);
        blackKing = new King(false, 4, 0, 6, "King1s.obj",
                scene, this);

        new Tower(false, 0, 0, 3, "Tower1s.obj",
                scene, this);
        new Tower(false, 7, 0, 3, "Tower1s.obj",
                scene, this);
        new Tower(true, 0, 7, 3, "Tower2s.obj",
                scene, this);
        new Tower(true, 7, 7, 3, "Tower2s.obj",
                scene, this);

        new Horse(false, 6, 0, 3, "Horse1s.obj",
                scene, this);
        new Horse(false, 1, 0, 3, "Horse1s.obj",
                scene, this);
        new Horse(true, 1, 7, 3, "Horse2s.obj",
                scene, this);
        new Horse(true, 6, 7, 3, "Horse2s.obj",
                scene, this);

        new Bishop(false, 2, 0, 4, "Bishop1s.obj",
                scene, this);
        new Bishop(false, 5, 0, 4, "Bishop1s.obj",
                scene, this);
        new Bishop(true, 2, 7, 4, "Bishop2s.obj",
                scene, this);
        new Bishop(true, 5, 7, 4, "Bishop2s.obj",
                scene, this);

        for(int i = 0; i < 8; i++){
            new Pawn(true, i, 6, 1, "Pawn2s.obj",
                    scene, this);
            new Pawn(false, i, 1, 1, "Pawn1s.obj",
                    scene, this);
        }
    }

    private PImage bk, dn, ft, lf, rt, up;

    @Override
    public void settings(){
        size(700,700, P3D);
    }

    static void setWhitePos(boolean fT){
        if(fT){
            scene.camera().setPosition(new Vec(0, 50, 100));
            scene.camera().lookAt(new Vec(0, 0, 0));
        }
        else {
            System.out.println("asdf");
            scene.camera().setPosition(new Vec(0, 50, 100));
            scene.camera().setOrientation(PI, PI);
            scene.camera().lookAt(new Vec(0, 0, 0));
        }

    }

    static void setBlackPos(){
        firstTime = false;
        //scene.camera().frame().rotateAroundFrame(PI/8, PI/8, PI/4,Board.squares.get(36));
        scene.camera().setPosition(new Vec(0, -50, 100));
        scene.camera().setOrientation(PI, PI);

        scene.camera().lookAt(new Vec(0, 0, 0));
    }

    @Override
    public void setup(){
        bk = loadImage("mp_drakeq/drakeq_bk.tga");
        dn = loadImage("mp_drakeq/drakeq_dn.tga");
        ft = loadImage("mp_drakeq/drakeq_ft.tga");
        lf = loadImage("mp_drakeq/drakeq_lf.tga");
        rt = loadImage("mp_drakeq/drakeq_rt.tga");
        up = loadImage("mp_drakeq/drakeq_up.tga");

        scene = new Scene(this);
        scene.setGridVisualHint(false);
        scene.setAxesVisualHint(false);
        setWhitePos(firstTime);

        board = new Board(this);

        whiteKing = new King(true, 4, 7, 5, "King2s.obj",
                scene, this);
        blackKing = new King(false, 4, 0, 5, "King1s.obj",
                scene, this);

        //loadPieces();
        updateMoves();

    }

    private static final int size = 230;
    private static final int s2 = size/2;
    private static final int s3 = 20;

    private void skybox(){
        pushMatrix();
        translate(-s2, -s2, s2+s3);
        rotateX(radians(-90));
        image(bk, 0, 0, size, size);
        popMatrix();

        pushMatrix();
        translate(s2, s2, s2+s3);
        rotateX(radians(90));
        rotateZ(radians(180));
        image(ft, 0, 0, size, size);
        popMatrix();

        pushMatrix();
        translate(s2, -s2, -s2+s3);
        rotateZ(radians(90));
        image(dn, 0, 0, size, size);
        popMatrix();

        pushMatrix();
        translate(s2, -s2, s2+s3);
        rotateZ(radians(90));
        image(up, 0, 0, size, size);
        popMatrix();

        pushMatrix();
        translate(s2, -s2, s2+s3);
        rotateZ(radians(90));
        rotateX(radians(-90));
        image(rt, 0, 0, size, size);
        popMatrix();

        pushMatrix();
        translate(-s2, s2, s2+s3);
        rotateZ(radians(-90));
        rotateX(radians(-90));
        image(lf, 0, 0, size, size);
        popMatrix();
    }

    @Override
    public void draw(){
        background(125);

        skybox();
        lights();
        directionalLight(500, 500, 50,
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
        drawText();
        scene.drawFrames();
    }

    private void drawText(){
        scene.beginScreenDrawing();
        text(whiteTurn? "Turno Blanco":"Turno Negro", 5, 20);
        scene.endScreenDrawing();
    }


    public static void main(String[] args) {
        PApplet.main("Chess");
    }
}
