/**
 * The purpose of this library is to allow users to display Hemesh meshes as VBO
 * processing sketches Copyright (C) 2012 Martin Prout This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * Obtain a copy of the license at http://www.gnu.org/licenses/lgpl-2.1.html
 */
package mshape;

import processing.core.PShape;
import wblut.hemesh.HE_Mesh;


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
    PShape meshToRetained(HE_Mesh mesh, int col);




    /**
     *
     * @return
     */
    String version();
    
}
