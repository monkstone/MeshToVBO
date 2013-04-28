/**
 *
 * The purpose of this library is to allow the rendering of Hemesh mesh object
 * as VBOs Copyright (C) 2013 Martin Prout This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Obtain a copy of the license at http://www.gnu.org/licenses/lgpl-2.1.html
 */
package mshape;

import java.util.Iterator;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import wblut.geom.WB_Normal3d;
import wblut.hemesh.HE_Face;
import wblut.hemesh.HE_Halfedge;
import wblut.hemesh.HE_Mesh;
import wblut.hemesh.HE_Vertex;


/**
 * Arbitrarily, the first object created is shiny, and thereafter matt
 *
 * @author Martin Prout
 */
public class MeshToVBO implements MeshInterface {

    private PApplet parent;
    static int meshCount = 0;
    static String VERSION = "0.1.6";
    static String HEMESH_VERSION = "1.8.1";
    private final String VERSION_FORMAT = "Info: MeshToVBO version %s, for "
            + "hemesh version %s";

    /**
     * Constructor for library class
     * @param parent
     */
    public MeshToVBO(PApplet parent) {
        this.parent = parent;
        this.parent.registerMethod("dispose", this);
        System.out.println(String.format(VERSION_FORMAT, VERSION, HEMESH_VERSION));
    }

    


    @Override
    public final PShape meshToRetained(HE_Mesh mesh, int col) {
        meshCount++;
        final HE_Mesh triMesh = mesh.get();
        triMesh.triangulate();
        PShape retained = parent.createShape();
        retained.beginShape(PConstants.TRIANGLES);
        retained.fill(col);
        retained.ambient(50);
        if (meshCount == 0) {
            retained.shininess(180f);
            retained.specular(70f);
        }
        HE_Face f;
        for (Iterator<HE_Face> faceItr = mesh.fItr(); faceItr.hasNext();) {
            f = faceItr.next();
            HE_Halfedge he = f.getHalfedge();
            HE_Vertex vx;
            do {
                vx = he.getVertex();
                WB_Normal3d vn = vx.getVertexNormal();
                retained.normal(vn.xf(), vn.yf(), vn.zf());
                retained.vertex(vx.xf(), vx.yf(), vx.zf());
                he = he.getNextInFace();
            } while (he != f.getHalfedge());
        }
        retained.endShape();
        return retained;
    }

    @Override
    public final PShape meshToRetained(HE_Mesh mesh) {
        meshCount++;
        final HE_Mesh triMesh = mesh.get();
        triMesh.triangulate();
        PShape retained = parent.createShape();
        retained.beginShape(PConstants.TRIANGLES);
        int col = (255 >> 24) & 0xFF|(211 >> 16) & 0xFF|(211 >> 8) & 0xFF|211;
        retained.fill(col);
        retained.ambient(50);
        if (meshCount == 0) {
            retained.shininess(180f);
            retained.specular(70f);
        }
        HE_Face f;
        for (Iterator<HE_Face> faceItr = mesh.fItr(); faceItr.hasNext();) {
            f = faceItr.next();
            HE_Halfedge he = f.getHalfedge();
            HE_Vertex vx;
            do {
                vx = he.getVertex();
                WB_Normal3d vn = vx.getVertexNormal();
                retained.normal(vn.xf(), vn.yf(), vn.zf());
                retained.vertex(vx.xf(), vx.yf(), vx.zf());
                he = he.getNextInFace();
            } while (he != f.getHalfedge());
        }
        retained.endShape();
        return retained;
    }
    


    @Override
    public void dispose() {
        // nothing to do here
    }


    @Override
    public final String version() {
        return VERSION;
    }
}
