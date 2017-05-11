//implementado desde cero

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.GROUP;
import static processing.core.PConstants.QUADS;
import static processing.core.PConstants.QUAD_STRIP;

class Cube {

    static ArrayList<PVector> vectors;

    static void initialize(int x, int y, int z){
        vectors = new ArrayList<>();
        vectors.add(new PVector(-x, -y, z));
        vectors.add(new PVector(-x, y, z));
        vectors.add(new PVector(x, -y, z));
        vectors.add(new PVector(x, y, z));

        //right face
        vectors.add(new PVector(x, -y, -z));
        vectors.add(new PVector(x, y, -z));

        //back face
        vectors.add(new PVector(-x, -y, -z));
        vectors.add(new PVector(-x, y, -z));

        vectors.add(new PVector(-x, -y, z));
        vectors.add(new PVector(-x, y, z));

        //up face
        vectors.add(new PVector(-x, -y, -z));
        vectors.add(new PVector(x, -y, -z));
        vectors.add(new PVector(x, -y, z));
        vectors.add(new PVector(-x, -y, z));

        //down face
        vectors.add(new PVector(-x, y, -z));
        vectors.add(new PVector(x, y, -z));
        vectors.add(new PVector(x, y, z));
        vectors.add(new PVector(-x, y, z));
    }

    static PShape buildShape(int dx, int dy, boolean isWhite, PApplet parent){
        if (isWhite)
            parent.fill(255);
        else
            parent.fill(0);
        PShape shape = parent.createShape(GROUP);

        PShape shape2 = parent.createShape();
        PShape shape3 = parent.createShape();

        shape2.beginShape(QUAD_STRIP);

        for(int i = 0; i < 10; i++)
            shape2.vertex(vectors.get(i).x+dx, vectors.get(i).y+dy, vectors.get(i).z);

        shape2.endShape();

        shape3.beginShape(QUADS);

        for(int i = 10; i < 18; i++)
            shape3.vertex(vectors.get(i).x+dx, vectors.get(i).y+dy, vectors.get(i).z);

        shape3.endShape();

        shape.addChild(shape2);
        shape.addChild(shape3);
        return shape;
    }
}