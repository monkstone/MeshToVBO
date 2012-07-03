import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import processing.core.PApplet;
import processing.core.PVector;

// ARCBALL UPDATED
// by monkstone - May 15, 2012
// keep 'x', 'y' or 'z' pressed in order to constrain to one of the axis
// Features use of PShape and PVector cf original

/*
     * CREDITS... 
 * 
 * 1) arielm - June 23, 2003 http://www.chronotext.org
 * 
 * 2)v3ga for starting with quaternions in processing!
 * http://proce55ing.net/discourse/yabb/YaBB.cgi?board=Tools;action=display;num=1054894944
 *
 * 3) Paul Rademacher & other contributors to the GLUI User Interface
 * Library http://www.cs.unc.edu/~rademach/glui
 *
 * 3) Nick Bobic (great introductionary article on quaternions + source
 * code) http://www.gamasutra.com/features/19980703/quaternions_01.htm
 *
 * 5) Matrix and Quaternion FAQ http://skal.planet-d.net/demo/matrixfaq.htm
 *
 * 6) Ken Shoemake, inventor of the ArcBall concept, around 1985 (?)...
 */

/**
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
  private final PApplet parent;
  private float zoom = 1.0f;
  private boolean isActive = false;


  /**
   *
   * @param parent
   * @param center_x
   * @param center_y
   * @param radius
   */
  public ArcBall(final PApplet parent, float center_x, float center_y, float radius) {
    this.parent = parent;
    this.parent.registerMouseEvent(this);
    this.parent.registerKeyEvent(this);
    this.center_x = center_x;
    this.center_y = center_y;
    this.radius = radius;
    this.v_down = new PVector();
    this.v_drag = new PVector();
    this.q_now = new Quaternion();
    this.q_down = new Quaternion();
    this.q_drag = new Quaternion();
    this.axisSet = new PVector[] {
      new PVector(1.0F, 0.0F, 0.0F), new PVector(0.0F, 1.0F, 0.0F), new PVector(0.0F, 0.0F, 1.0F)
      };
      axis = Constrain.FREE; // no constraints...
  }

  /**
   * Default centered arcball and half width
   *
   * @param parent
   */
  public ArcBall(final PApplet parent) {
    this(parent, parent.width/2, parent.height/2, parent.height/2);
  }


  /**
   * mouse event to register
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
   * @param e
   */
  public void keyEvent(KeyEvent e) {
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
    if (e.getID() == KeyEvent.KEY_RELEASED) {
      constrain(Constrain.FREE);
    }
  }



  /**
   * Probably not be required for use in Web Applet, it works so why worry
   * as used by Jonathan Feinberg peasycam, and that works OK
   * @param active
   */
  public final void setActive(boolean active) {
    if (active != isActive) {
      isActive = active;
      if (active) {
        this.parent.registerMouseEvent(this);
        this.parent.registerKeyEvent(this);        
      } 
      else {
        this.parent.unregisterMouseEvent(this);
        this.parent.unregisterKeyEvent(this);        
      }
    }
  }


  /**
   * Needed to call this in sketch
   */
  public void update() {
    q_now = Quaternion.mult(q_drag, q_down);
    applyQuat2Matrix(q_now);
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
    } 
    else {
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
   *
   * @param axis
   */
  public void constrain(Constrain axis) {
    this.axis = axis;
  }

  /**
   *
   * @param q
   */
  public void applyQuat2Matrix(Quaternion q) {
    // instead of transforming q into a matrix and applying it...
    float[] aa = q.getValue();
    parent.rotate(aa[0], aa[1], aa[2], aa[3]);
  }
}

