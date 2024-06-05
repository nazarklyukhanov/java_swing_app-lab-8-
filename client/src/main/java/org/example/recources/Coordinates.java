/**
 * The Coordinates class represents the coordinates of a point in a two-dimensional space.
 */
package org.example.recources;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private float x; // The maximum value of the field: 845
    private Float y; // The field cannot be null

    /**
     * Constructs a new Coordinates object with the specified x and y coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x-coordinate.
     *
     * @return the x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate.
     *
     * @return the y-coordinate
     */
    public Float getY() {
        return y;
    }
}
