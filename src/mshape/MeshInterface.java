/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mshape;

import processing.core.PShape;
import wblut.hemesh.core.HE_Mesh;

/**
 *
 * @author Martin Prout
 */
public interface MeshInterface {

    /**
     * required for a processing library?
     */
    void dispose();

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
    PShape meshToRetained(HE_Mesh mesh, float red, float green, float blue);

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
    PShape meshToRetained(HE_Mesh mesh, float red, float green, float blue, float alpha);

    /**
     *
     * @return
     */
    String version();
    
}
