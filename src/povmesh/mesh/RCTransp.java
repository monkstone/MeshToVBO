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
 * Rainbow colors with a transparency
 * @author Martin Prout
 */
public enum RCTransp  {
    /**
     * 
     */
    RED("r_red1", "color rgbf<1.0, 0.5, 1.0, 1.0>;"), 
    /**
     * 
     */
    RED1("r_red2", "color rgbf<1.0, 0.2, 0.2, 1.0>;"),
    /**
     * 
     */
    ORANGE("r_orange", "color rgbf<1.0, 0.5, 0.2, 0.8>;"),
    /**
     * 
     */
    YELLOW("r_yellow", "color rgbf<1.0, 1.0, 0.2, 0.8>;"),
    /**
     * 
     */
    GREEN("r_green", "color rgbf<0.2, 1.0, 0.2, 0.8>;"),
    /**
     * 
     */
    CYAN("r_cyan", "color rgbf<0.2, 1.0, 1.0, 0.8>;"),
    /**
     * 
     */
    BLUE("r_blue", "color rgbf<0.2, 0.2, 1.0, 0.8>;"),
    /**
     * 
     */
    INDIGO("r_indigo", "color rgbf<0.5, 0.5, 1.0, 0.8>;"), 
    /**
     * 
     */
    VIOLET("r_violet1", "color rgbf<1.0, 0.5, 1.0, 1.0>;"), 
    /**
     * 
     */
    VIOLET1("r_violet2", "color rgbf<1.0, 0.5, 1.0, 0.8>;");
    private final String cname;
    private final String declare;
  
    RCTransp(String cname, String declare){
        this.cname = cname;
        this.declare = declare;
    }
    String getDeclare(){return this.declare;}
    String getColor(){return this.cname;}
}
