import mshape.util.*;
import mshape.*;
import wblut.math.*;
import wblut.processing.*;
import wblut.core.*;
import wblut.hemesh.*;
import wblut.geom.*;

PShape retainedMesh;
ArcBall arcball;   
MeshToVBO mshape;
HE_Mesh mesh;
HEC_IsoSurface creator;

void setup() {
  size(800, 800, P3D);
  arcball = new ArcBall(this);
  mshape = new MeshToVBO(this);  
  smooth(16);

  // Create an isosurface from an explicit grid of values.
  // Potentially uses a lot of memory.

  float[][][] values=new float[51][51][51];
  for (int i = 0; i < 51; i++) {
    for (int j = 0; j < 51; j++) {
      for (int k = 0; k < 51; k++) {
        values[i][j][k]=2.1*noise(0.07*i, 0.07*j, 0.07*k);
      }
    }
  }

  creator = new HEC_IsoSurface();
  creator.setResolution(50, 50, 50);// number of cells in x,y,z direction
  creator.setSize(8, 8, 8);// cell size
  creator.setValues(values);// values corresponding to the grid points
  // values can also be double[][][]
  creator.setIsolevel(1);// isolevel to mesh
  creator.setInvert(false);// invert mesh
  //creator.setBoundary(100);// value of isoFunction outside grid
  // use creator.clearBoundary() to rest boundary values to "no value".
  // A boundary value of "no value" results in an open mesh

  mesh = new HE_Mesh(creator);
  noStroke();
  // stainless steel
  retainedMesh = mshape.meshToRetained(mesh, color(224, 223, 219));
}


void draw() {
  background(100);
  lights();
  lightSpecular(80, 80, 80);
  directionalLight(80, 80, 80, 0, 0, -1);
  ambientLight(50, 50, 50);
  translate(400, 400, 0);
  arcball.update();
  shape(retainedMesh);
}



