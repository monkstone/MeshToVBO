/**
 *
 * The purpose of this library is to allow the rendering of Hemesh mesh object
 * as VBOs Copyright (C) 2012 Martin Prout This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Obtain a copy of the license at http://www.gnu.org/licenses/lgpl-2.1.html
 */
package mshape;

import java.util.Iterator;
import processing.core.PApplet;
import processing.core.PShape;
import wblut.geom.core.WB_Normal3d;
import wblut.hemesh.core.HE_Face;
import wblut.hemesh.core.HE_Halfedge;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.core.HE_Vertex;

/**
 * Arbitrarily, the first object created is shiny, and thereafter matt
 *
 * @author Martin Prout
 */
public class MeshToVBO implements MeshInterface {

    private PApplet parent;
    static int meshCount = 0;
    private final String VERSION = "0.1.2";
    private final String HEMESH_VERSION = "hemesh_b1_7agx3";
    private final String VERSION_FORMAT = "INFO: MeshToVBO version %s, for "
            + "hemesh version %s";

    /**
     *
     * @param parent
     */
    public MeshToVBO(PApplet parent) {
        this.parent = parent;
        this.parent.registerDispose(this);
        System.out.println(String.format(VERSION_FORMAT, VERSION, HEMESH_VERSION));
    }

    /**
     * No alpha version, rather than repeat code I choose call alpha version
     * with alpha set to 255
     *
     * @param mesh
     * @param red
     * @param green
     * @param blue
     * @return retained PShape with style enabled and hue set
     */
    @Override
    public final PShape meshToRetained(HE_Mesh mesh, float red, float green, float blue) {
        return meshToRetained(mesh, red, green, blue, 255f);
    }

    /**
     * The availability and use of iterators in the library, was kindly
     * explained to me by Frederick Vanhoutte (author of Hemesh library)
     *
     * @todo change getNextInFace() to getNextInLoop() in future version
     * @param mesh
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @return retained PShape with style enabled and hue set
     */
    @Override
    public final PShape meshToRetained(HE_Mesh mesh, float red, float green, float blue, float alpha) {
        meshCount++;
        final HE_Mesh triMesh = mesh.get();
        triMesh.triangulate();
        PShape retained = parent.createShape(PApplet.TRIANGLES);
        retained.enableStyle();
        retained.fill(red, green, blue, alpha);
        retained.solid(true);
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
        retained.end();
        return retained;
    }

    /**
     * required for a processing library?
     */
    @Override
    public void dispose() {
        // nothing to do here
    }

    /**
     *
     * @return
     */
    @Override
    public final String version() {
        return VERSION;
    }
}
