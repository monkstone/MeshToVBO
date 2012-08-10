/**
 * The purpose of this library is to allow users to explore lystems using
 * processing sketches Copyright (C) 2012 Martin Prout This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * Obtain a copy of the license at http://www.gnu.org/licenses/lgpl-2.1.html
 */

/*
 * CREDITS...Initially I found this arcball in a sketch by Ariel Malka, 
 * only later did I find the Tom Carden processing tutorial example, so take your pick 
 * 
 * 1) Ariel Malka - June 23, 2003 http://www.chronotext.org
 * 
 * 2) Simon Greenwold? 2003 (as reported 2006 by Tom Carden http://wiki.processing.org/w/Arcball)
 *
 * 3) ArcBall concept invented by Ken Shoemake, published in his 1985 SIGGRAPH paper "Animating rotations with quaternion curves". 
 * 
 * 4) Modified to use PVector & enum (Constrain) instead of Vec3D & int by Martin Prout
 **/


package mshape.util;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Supports the ArcBall and MouseWheel zoom
 * manipulation of objects in processing
 *
 * @author Martin Prout
 */
public class ArcBall {

    private float center_x;
    private float center_y;
    private float radius;
    private PVector v_down;
    private PVector v_drag;
    private Quaternion q_now;
    private Quaternion q_down;
    private Quaternion q_drag;
    private PVector[] axisSet;
    private Constrain axis;
    private boolean isActive = false;
    private final PApplet parent;
    private float zoom = 1.0f;
    private final WheelHandler zoomWheelHandler = new WheelHandler() {
        @Override
        public void handleWheel(final int delta) {
            zoom += delta * 0.05f;
        }
    };
    private final ArcballMousewheelListener wheelHandler = new ArcballMousewheelListener();

    /**
     *
     * @param parent PApplet
     * @param center_x float x coordinate of arcball center
     * @param center_y float y coordinate of arcball center
     * @param radius   float radius of arcball
     */
    public ArcBall(final PApplet parent, float center_x, float center_y, float radius) {
        this.parent = parent;
        this.parent.registerDispose(this);
        this.center_x = center_x;
        this.center_y = center_y;
        this.radius = radius;
        this.v_down = new PVector();
        this.v_drag = new PVector();
        this.q_now = new Quaternion();
        this.q_down = new Quaternion();
        this.q_drag = new Quaternion();
        this.axisSet = new PVector[]{new PVector(1.0F, 0.0F, 0.0F), new PVector(0.0F, 1.0F, 0.0F), new PVector(0.0F, 0.0F, 1.0F)};
        axis = Constrain.FREE; // no constraints...
        setActive(true);
    }

    /**
     * Default centered arcball and half width
     *
     * @param parent
     */
    public ArcBall(final PApplet parent) {
        this(parent, parent.width * 0.5F, parent.height * 0.5F, parent.width * 0.5F);
    }

    /**
     * mouse event to register
     *
     * @param e
     */
    public void mouseEvent(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        switch (e.getID()) {
            case (MouseEvent.MOUSE_PRESSED):
                v_down = mouse2sphere(x, y);
                q_down.set(q_now);
                q_drag.reset();
                break;
            case (MouseEvent.MOUSE_DRAGGED):
                v_drag = mouse2sphere(x, y);
                q_drag.set(PVector.dot(v_down, v_drag), v_down.cross(v_drag));
                break;
            default:
        }
    }

    /**
     * key event to register
     *
     * @param e
     */
    public void keyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyChar()) {
                case 'x':
                    constrain(Constrain.XAXIS);
                    break;
                case 'y':
                    constrain(Constrain.YAXIS);
                    break;
                case 'z':
                    constrain(Constrain.ZAXIS);
                    break;
            }
        }
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            constrain(Constrain.FREE);
        }
    }

    /**
     * It might be necessary for Applets to 'know' ahead of time that there is
     * an instance of MouseWheelListener instead of an anonymous class?
     */
    protected class ArcballMousewheelListener implements MouseWheelListener {

        public void mouseWheelMoved(final MouseWheelEvent e) {
            if (zoomWheelHandler != null) {
                zoomWheelHandler.handleWheel(e.getWheelRotation());
            }
        }
    }

    /**
     * May or may not be required for use in Web Applet it works so why worry
     * as used by Jonathan Feinberg peasycam, and that works OK
     * @param active
     */
    public final void setActive(boolean active) {
        if (active != isActive) {
            isActive = active;
            if (active) {
                this.parent.registerMouseEvent(this);
                this.parent.registerKeyEvent(this);
                this.parent.addMouseWheelListener(wheelHandler);
            } else {
                this.parent.unregisterMouseEvent(this);
                this.parent.unregisterKeyEvent(this);
                this.parent.frame.removeMouseWheelListener(wheelHandler);
            }
        }
    }

    /**
     * Needed to call this in sketch
     */
    public void update() {
        q_now = Quaternion.mult(q_drag, q_down);
        applyQuaternion2Matrix(q_now);
        parent.scale(zoom);
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public PVector mouse2sphere(float x, float y) {
        PVector v = new PVector();
        v.x = (x - center_x) / radius;
        v.y = (y - center_y) / radius;
        float mag = v.x * v.x + v.y * v.y;
        if (mag > 1.0F) {
            v.normalize();
        } else {
            v.z = (float) Math.sqrt(1.0 - mag);
        }
        return (axis == Constrain.FREE) ? v : constrainVector(v, axisSet[axis.index()]);
    }

    /**
     *
     * @param vector
     * @param axis
     * @return
     */
    public PVector constrainVector(PVector vector, PVector axis) {
        PVector res = PVector.sub(vector, PVector.mult(axis, PVector.dot(axis, vector)));
        res.normalize();
        return res;
    }

    /**
     * Constrain rotation to this axis
     * @param axis
     */
    public void constrain(Constrain axis) {
        this.axis = axis;
    }

    /**
     * Rotate the parent sketch according to the quaternion
     * @param q
     */
    public void applyQuaternion2Matrix(Quaternion q) {
        // instead of transforming q into a matrix and applying it...
        float[] aa = q.getValue();
        parent.rotate(aa[0], aa[1], aa[2], aa[3]);
    }

    /**
     * A recommended inclusion for a processing library
     */
    public void dispose() {
        setActive(false);
    }
}
