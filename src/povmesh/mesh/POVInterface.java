/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package povmesh.mesh;

import toxi.geom.Vec3D;

/**
 *
 * @author Martin Prout
 */
public interface POVInterface {

    /**
     * Output start of face_indices
     *
     * @param count
     */
    void beginIndices(int count);

    /**
     * Begin the mesh2 output as a PovRAY declaration
     *
     * @param name
     */
    void beginMesh2(String name);

    /**
     * Output start of normal_vectors
     *
     * @param count
     */
    void beginNormals(int count);

    /**
     * close the mesh declaration
     */
    void endSave();

    /**
     * End the current section ie vertex_vector, normal_vector or face_indices
     */
    void endSection();

    /**
     * Write face indices as as vector
     *
     * @param a
     * @param b
     * @param c
     */
    void face(int a, int b, int c);

    /**
     * Track the number of normals written to file
     *
     * @return current normal offset
     */
    int getCurrNormalOffset();

    /**
     * Track the number of vertices written to file
     *
     * @return vertex offset
     */
    int getCurrVertexOffset();

    /**
     * Required to keep in sync with current option
     *
     * @return option Textures
     */
    Textures getTexture();

    /**
     * Write normal as PovRAY vector
     *
     * @param n
     */
    void normal(Vec3D n);

    /**
     * Required to keep in sync with current option
     *
     * @param opt
     */
    void setTexture(Textures opt);

    /**
     * Used to output total count vertex_vector, normal_vector & face_indices
     *
     * @param count
     */
    void total(int count);

    /**
     * Write vertex as PovRAY vector
     *
     * @param v
     */
    void vertex(Vec3D v);
    
}
