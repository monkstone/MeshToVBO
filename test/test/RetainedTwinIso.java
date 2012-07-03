
import mshape.MeshInterface;
import mshape.MeshToVBO;
import processing.core.*;
import wblut.hemesh.core.*;
import wblut.hemesh.creators.*;
import wblut.hemesh.modifiers.*;

/**
 *
 * @author Martin Prout
 */
public class RetainedTwinIso extends PApplet {

    PShape retainedMesh;
    PShape retainedInverse;
   // PShape union;
    HE_Mesh mesh;
    HE_Mesh invMesh;
    int res;
    MeshInterface converter;
   // ArcBall arcball;

    /**
     *
     */
    @Override
    public void setup() {
        size(800, 800, P3D);
       // arcball = new ArcBall(this);
        smooth(16);
        converter = new MeshToVBO(this);
       // union = createShape(PShape.GROUP);
        res = 10;
        float[][][] values = new float[res + 1][res + 1][res + 1];
        for (int i = 0; i < res + 1; i++) {
            for (int j = 0; j < res + 1; j++) {
                for (int k = 0; k < res + 1; k++) {
                    values[i][j][k] = 2.1f * noise(0.35f * i, 0.35f * j, 0.35f * k);
                }
            }
        }

        HEC_IsoSurface creator = new HEC_IsoSurface();
        creator.setResolution(res, res, res);// number of cells in x,y,z direction
        creator.setSize(400.0f / res, 400.0f / res, 400.0f / res);// cell size
        creator.setValues(values);// values corresponding to the grid points
        // values can also be double[][][]
        creator.setIsolevel(1);// isolevel to mesh
        creator.setInvert(false);// invert mesh
        creator.setBoundary(100);// value of isoFunction outside grid
        // use creator.clearBoundary() to rest boundary values to "no value".
        // A boundary value of "no value" results in an open mesh

        mesh = new HE_Mesh(creator);
        mesh.modify(new HEM_Smooth().setIterations(10).setAutoRescale(true));
        creator.setInvert(true);
        invMesh = new HE_Mesh(creator);
        invMesh.modify(new HEM_Smooth().setIterations(10).setAutoRescale(true));
        noStroke();
        //silver
        retainedMesh = converter.meshToRetained(mesh, 192, 192, 192);
        //white marble
        retainedInverse = converter.meshToRetained(invMesh, 249, 246, 224);

    }

    /**
     *
     */
    @Override
    public void draw() {
        background(0);
        lights();
        lightSpecular(80, 80, 80);
        directionalLight(80, 80, 80, 0, 0, -1);
        ambientLight(50, 50, 50);
        translate(400, 400, 0);
        rotateX(mouseY * 0.05f);
        rotateY(mouseY * 0.05f);
    //    arcball.update();
        shape(retainedMesh);
        shape(retainedInverse);       
    }

    /**
     *
     * @return
     */
    public int sketchWidth() {
        return 800;
    }

    /**
     *
     * @return
     */
    public int sketchHeight() {
        return 800;
    }

    /**
     *
     * @return
     */
    public String sketchRenderer() {
        return OPENGL;
    }

    /**
     *
     * @param args
     */
    static public void main(String args[]) {
        PApplet.main(new String[]{"--bgcolor=#666666", "--stop-color=#cccccc", "RetainedTwinIso"});
    }
}
