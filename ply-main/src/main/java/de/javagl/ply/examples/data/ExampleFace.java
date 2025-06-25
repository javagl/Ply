/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply.examples.data;

import java.util.Arrays;

//A dummy face class for the examples
@SuppressWarnings("javadoc")
public class ExampleFace
{
    private int indices[];

    public ExampleFace()
    {
    }

    public ExampleFace(int... indices)
    {
        this.indices = indices;
    }

    public void setIndices(int array[])
    {
        this.indices = array;
    }

    public int[] getIndices()
    {
        return indices;
    }

    @Override
    public String toString()
    {
        return Arrays.toString(indices);
    }

}