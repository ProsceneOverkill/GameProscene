import remixlab.dandelion.geom.Quat;
import remixlab.proscene.*;
import processing.core.PApplet;

import java.util.HashSet;


public class Chess extends PApplet{

    static Scene scene, scene2;

    static int w = 20;
    static Piece[][] boardState = new Piece[8][8];
    private static Piece[][] deadWithe = new Piece[8][2];
    private static Piece[][] deadBlack = new Piece[8][2];
    private static int deadWhiteI, deadWhiteJ, deadBlackI, deadBlackJ;
    private Board board;
    static boolean whiteTurn = true, isGaveOver = false;
    static String result;
    static King whiteKing, blackKing;

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

    @Override
    public void settings(){
        size(700,700, P3D);
    }

    void loadPieces(){
        new Queen(false, 3, 0, 14, "Queen1.obj",
                scene, this);
        new Queen(true, 3, 7, 14, "Queen2.obj",
                scene, this);

        whiteKing = new King(true, 4, 7, 14, "King2.obj",
                scene, this);
        blackKing = new King(false, 4, 0, 14, "King1.obj",
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
                scene, this);
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
        }
    }

    @Override
    public void setup(){
        scene = new Scene(this);
        //scene2 = new Scene(this, minimap)
        scene.setGridVisualHint(false);
        board = new Board(this);

        whiteKing = new King(true, 4, 7, 14, "King2.obj",
                scene, this);
        blackKing = new King(false, 4, 0, 14, "King1.obj",
                scene, this);
        //loadPieces();
        updateMoves();
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

        scene.drawFrames();
    }

    public static void main(String[] args) {
        PApplet.main("Chess");
    }
}
