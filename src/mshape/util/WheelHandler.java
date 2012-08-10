/**
 * The purpose of this library is to allow users to explore lystems using
 * processing sketches Copyright (C) 2012 Martin Prout This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * Obtain a copy of the license at http://www.gnu.org/licenses/lgpl-2.1.html
 */
package mshape.util;

/**
 * @author Martin Prout 
 * from a borrowed pattern seen in Jonathan Feibergs Peasycam
 * when I was struggling with non functioning browser applet, 
 * probably superfluous here.
 */
public interface WheelHandler { 
    /**
     * 
     * @param delta
     */
    public void handleWheel(final int delta);
}
