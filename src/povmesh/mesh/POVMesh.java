/*
 * This library adds PovRAY export facility to toxiclibscore
 * Copyright (c) 2012 Martin Prout
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * http://creativecommons.org/licenses/LGPL/2.1/
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
 */
package povmesh.mesh;

import java.io.File;
import java.io.PrintWriter;
import processing.core.PApplet;
import toxi.geom.mesh.Face;
import toxi.geom.mesh.TriangleMesh;
import toxi.geom.mesh.Vertex;

/**
 * This class provides access to underlying TriangleMesh parameters to allow
 * export in PovRAY mesh2 format (with or without normals)
 *
 * @author Martin Prout
 */
public class POVMesh {

    private PrintWriter pw;
    private POVWriter pov;
    private Textures opt;
    /**
     *
     */
    public final String VERSION = "0.55";

    /**
     * Default constructor this mesh no texture
     *
     * @param app
     */
    public POVMesh(PApplet app) {
        app.registerDispose(this);

    }

    /**
     * Default constructor this mesh no texture
     *
     * @param app
     * @param opt
     */
//    public POVMesh(PApplet app, Textures opt) {
//        app.registerDispose(this);
//        this.opt = opt;
//    }
    /**
     * Allows the option to change texture option per mesh
     *
     * @param opt
     */
    public void setTexture(Textures opt) {
        this.opt = opt;
    }

    /**
     * Saves the mesh as PovRAY mesh2 format by appending it to the given mesh
     * {@link POVWriter} instance. Saves normals.
     *
     * @param meshArray
     */
    public void saveAsPOV(TriangleMesh[] meshArray) {
        saveAsMesh(meshArray, true);
    }
    
    public void saveAsPOV(TriangleMesh[] meshArray, int modulus) {
        saveAsMesh(meshArray, true);
    }

    /**
     * Saves the mesh as PovRAY mesh2 format by appending it to the given mesh
     * {@link POVWriter} instance. Saves normals.
     *
     * @param mesh
     */
    public void saveAsPOV(TriangleMesh mesh) {
        saveAsPOV(mesh, true);
    }

    /**
     * Saves the mesh as PovRAY mesh2 format to the given {@link PrintWriter}.
     * Without normals (when saveNormal is false), checks if option has changed,
     * changes option if required (implies serial use of saveAsPov)
     *
     * @param mesh
     * @param saveNormals boolean
     */
    public void saveAsPOV(TriangleMesh mesh, boolean saveNormals) {
        if (opt != pov.getTexture()) {
            pov.setTexture(opt);
        }
        pov.beginMesh2(mesh.name);
        int vOffset = pov.getCurrVertexOffset();
        // vertices
        pov.total(mesh.vertices.size());
        for (Vertex v : mesh.vertices.values()) {
            pov.vertex(v);
        }
        pov.endSection();
        // faces
        if (saveNormals) {
            // normals
            pov.beginNormals(mesh.vertices.size());
            for (Vertex v : mesh.vertices.values()) {
                pov.normal(v.normal.getNormalized());
            }
            pov.endSection();
        }
        pov.beginIndices(mesh.faces.size());
        for (Face f : mesh.faces) {
            pov.face(f.b.id + vOffset, f.a.id + vOffset, f.c.id + vOffset);

        }
        pov.endSection();
        pov.endSave();
    }

    /**
     * Saves the mesh as PovRAY mesh2 format to the given {@link PrintWriter}.
     * Without normals (when saveNormal is false) Check on option is included
     * for completeness
     *
     * @param meshArray
     * @param saveNormals boolean
     */
    public void saveAsMesh(TriangleMesh[] meshArray, boolean saveNormals) {
        if (opt != pov.getTexture()) {
            pov.setTexture(opt);
        }
        for (TriangleMesh mesh : meshArray) {
            pov.beginMesh2(mesh.name);
            int vOffset = pov.getCurrVertexOffset();
            // vertices
            pov.total(mesh.vertices.size());
            for (Vertex v : mesh.vertices.values()) {
                pov.vertex(v);
            }
            pov.endSection();
            // faces
            if (saveNormals) {
                // normals
                pov.beginNormals(mesh.vertices.size());
                for (Vertex v : mesh.vertices.values()) {
                    pov.normal(v.normal.getNormalized());
                }
                pov.endSection();
            }
            pov.beginIndices(mesh.faces.size());
            for (Face f : mesh.faces) {
                pov.face(f.b.id + vOffset, f.a.id + vOffset, f.c.id + vOffset);

            }
            pov.endSection();
            pov.endSave();
        }
    }



    /**
     * Start writing *.inc file
     *
     * @param fileName main.inc File
     */
    public void beginSave(File fileName) {
        opt = Textures.RAW;
        this.pov = new POVWriter(fileName);
        pov.beginForeground();
    }

    /**
     * Finish writing *.inc file and close PrintWriter
     */
    public void endSave() {
        pov.endForeground();
        //pw.flush();
        //pw.close();
    }

    /**
     * Required by processing
     */
    public void dispose() {
        endSave();
    }

    /**
     * Required by processing
     *
     * @return version String
     */
    public String version() {
        return VERSION;
    }
}
