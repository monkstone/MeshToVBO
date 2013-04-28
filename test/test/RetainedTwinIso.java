/**
 * The purpose of this library is to allow users to display Hemesh meshes as VBO
 * processing sketches Copyright (C) 2012 Martin Prout This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * Obtain a copy of the license at http://www.gnu.org/licenses/lgpl-2.1.html
 */
package test;

import mshape.*;
import mshape.util.*;
import processing.core.*;
import wblut.hemesh.HEC_IsoSurface;
import wblut.hemesh.HEM_Smooth;
import wblut.hemesh.HE_Mesh;

/**
 *
 * @author Martin Prout
 */
public class RetainedTwinIso extends PApplet {

    private static final long serialVersionUID = 1735630995336704713L;
    PShape retainedMesh;
    PShape retainedInverse;
    HE_Mesh mesh;
    HE_Mesh invMesh;
    int res;
    MeshInterface converter;
    ArcBall arcball;

    /**
     * Processing setup
     */
    @Override
    public void setup() {
        size(800, 800, P3D);
        arcball = new ArcBall(this);
        smooth(8);
        converter = new MeshToVBO(this);
        res = 20;
        float[][][] values = new float[res + 1][res + 1][res + 1];
        for (int i = 0; i < res + 1; i++) {
            for (int j = 0; j < res + 1; j++) {
                for (int k = 0; k < res + 1; k++) {
                    values[i][j][k] = 2.1f * noise(0.35f * i, 0.35f * j, 0.35f * k);
                }
            }
        }

        HEC_IsoSurface creator = new HEC_IsoSurface();
        creator.setResolution(res, res, res);// number of cells in x,y,z direction
        creator.setSize(400.0f / res, 400.0f / res, 400.0f / res);// cell size
        creator.setValues(values);// values corresponding to the grid points
        // values can also be double[][][]
        creator.setIsolevel(1);// isolevel to mesh
        creator.setInvert(false);// invert mesh
        creator.setBoundary(100);// value of isoFunction outside grid
        // use creator.clearBoundary() to rest boundary values to "no value".
        // A boundary value of "no value" results in an open mesh

        mesh = new HE_Mesh(creator);
        mesh.modify(new HEM_Smooth().setIterations(10).setAutoRescale(true));
        creator.setInvert(true);
        invMesh = new HE_Mesh(creator);
        invMesh.modify(new HEM_Smooth().setIterations(10).setAutoRescale(true));
        noStroke();
        //silver
        retainedMesh = converter.meshToRetained(mesh, color(192, 192, 192));
        //white marble
        // retainedInverse = converter.meshToRetained(invMesh, color(249, 246, 224));
        // default grey colour
        retainedInverse = converter.meshToRetained(invMesh);
    }

    /**
     * Processing draw
     */
    @Override
    public void draw() {
        background(0);
        lights();
        lightSpecular(80f, 80f, 80f);
        directionalLight(80f, 80f, 80f, 0, 0, -1);
        ambientLight(50f, 50f, 50f);
        translate(400, 400, 0);
        arcball.update();
        shape(retainedMesh);
        shape(retainedInverse);
    }

    /**
     *
     * @return
     */
    @Override
    public int sketchWidth() {
        return 800;
    }

    /**
     *
     * @return
     */
    @Override
    public int sketchHeight() {
        return 800;
    }

    /**
     *
     * @return
     */
    @Override
    public String sketchRenderer() {
        return P3D;
    }

    /**
     *
     * @param args
     */
    static public void main(String args[]) {
        PApplet.main(new String[]{"test.RetainedTwinIso"});
    }
}
