//implementado desde cero

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.GROUP;
import static processing.core.PConstants.QUADS;
import static processing.core.PConstants.QUAD_STRIP;
import static processing.core.PConstants.CLOSE;

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

    static PShape buildShape(int dx, int dy, int dz, boolean isWhite, PApplet parent){
        if (isWhite)
            parent.fill(255);
        else
            parent.fill(0);


        PShape shape = parent.createShape(GROUP);

        PShape shape2 = parent.createShape();
        PShape shape3 = parent.createShape();

        shape2.beginShape(QUAD_STRIP);

        for(int i = 0; i < 10; i++)
            shape2.vertex(vectors.get(i).x+dx, vectors.get(i).y+dy, vectors.get(i).z+dz);

        shape2.endShape();

        shape3.beginShape(QUADS);

        for(int i = 10; i < 18; i++)
            shape3.vertex(vectors.get(i).x+dx, vectors.get(i).y+dy, vectors.get(i).z+dz);

        shape3.endShape();

        shape.addChild(shape2);
        shape.addChild(shape3);
        return shape;
    }


    static PShape frontShape(int dx, int dy, int dz, PApplet parent){

       PShape shape = parent.createShape();
       /*
        shape.fill(255 ,0 ,0 );
        shape.beginShape();
        shape.vertex(vectors.get(0).x, vectors.get(0).y, vectors.get(0).z);
        shape.vertex(vectors.get(1).x, vectors.get(1).y, vectors.get(1).z);

        shape.vertex(vectors.get(3).x, vectors.get(3).y, vectors.get(3).z);
        shape.vertex(vectors.get(2).x, vectors.get(2).y, vectors.get(2).z);
        shape.endShape(CLOSE);
        return shape; */
       return shape;
    }


}