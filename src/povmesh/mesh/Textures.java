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

/**
 * Used to set PovRAY texture options
 * @author Martin Prout
 */
public enum Textures {

    /**
     * Original geometry output, no texture
     */
    RAW("Raw geometry"),

    
    /**
     * Geometry output, with glass texture
     * rainbow colors
     */
    GLASS("Glass texture"),
    
    /**
     * Geometry output, with metal texture
     * rainbow colors
     */
    METAL("Metal texture"),
    
        
    /**
     * Original geometry output, phong texture
     */
    RED("mono color phong texture"),
    
    /**
     * Original geometry output, phong texture
     */
    WHITE("mono color phong texture"),
    
    /**
     * Geometry output, with phong texture
     * rainbow colors 
     */
    PHONG("Simple phong texture"),
    
        /**
     * Geometry output, with phong texture
     * random colors 
     */
    RANDOM("Random glass texture"),
    
    
    /**
     * Geometry output, with phong texture
     * and alternate color (B&W) 
     */
    TWOTONE("Alternate B&W texture"),
    
    /**
     * Geometry output, with mirror
     * finish 
     */
    MIRROR("Perfect mirror");
    
    /**
     * Description String
     */
    public String description;

    Textures(String description) {
        this.description = description;
    }

    /**
     * Gives access to description String
     * @return option description String
     */
    public String description() {
        return this.description;
    }
}
