/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply.examples.data;

// A dummy edge class for the examples
@SuppressWarnings("javadoc")
public class ExampleEdge
{
    private int vertex1;
    private int vertex2;
    private byte red;
    private byte green;
    private byte blue;

    public ExampleEdge()
    {
    }

    public ExampleEdge(int vertex1, int vertex2, int red, int green, int blue)
    {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
    }

    public void setVertex1(int v)
    {
        this.vertex1 = v;
    }

    public int getVertex1()
    {
        return vertex1;
    }

    public void setVertex2(int v)
    {
        this.vertex2 = v;
    }

    public int getVertex2()
    {
        return vertex2;
    }

    public byte getRed()
    {
        return red;
    }

    public void setRed(byte r)
    {
        this.red = r;
    }

    public byte getGreen()
    {
        return green;
    }

    public void setGreen(byte g)
    {
        this.green = g;
    }

    public byte getBlue()
    {
        return blue;
    }

    public void setBlue(byte b)
    {
        this.blue = b;
    }

    @Override
    public String toString()
    {
        return "(" + vertex1 + ", " + vertex2 + ", " + Byte.toUnsignedInt(red)
            + ", " + Byte.toUnsignedInt(green) + ", " + Byte.toUnsignedInt(blue)
            + ")";
    }

}