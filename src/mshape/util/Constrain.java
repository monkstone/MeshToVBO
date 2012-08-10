package mshape.util;

/**
 *
 * @author Martin Prout
 */
public enum Constrain {

    /**
     * 
     */
    XAXIS(0),
    /**
     * 
     */
    YAXIS(1),
    /**
     * 
     */
    ZAXIS(2),
    /**
     * 
     */
    FREE(-1);
    private int index;

    Constrain(int idx) {
        this.index = idx;
    }

    /**
     * 
     * @return
     */
    public int index() {
        return index;
    }
}

