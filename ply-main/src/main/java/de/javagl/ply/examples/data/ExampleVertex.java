/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply.examples.data;

// A dummy vertex class for the examples
@SuppressWarnings("javadoc")
public class ExampleVertex
{
    private float x;
    private float y;
    private float z;
    private byte red;
    private byte green;
    private byte blue;

    public ExampleVertex()
    {

    }

    public ExampleVertex(float x, float y, float z, int red, int green,
        int blue)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getZ()
    {
        return z;
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
        return "(" + x + ", " + y + ", " + z + ", " + Byte.toUnsignedInt(red)
            + ", " + Byte.toUnsignedInt(green) + ", " + Byte.toUnsignedInt(blue)
            + ")";
    }

}