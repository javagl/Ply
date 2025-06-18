/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply.examples;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import de.javagl.ply.AbstractPlyTarget;
import de.javagl.ply.Descriptor;
import de.javagl.ply.PlyReader;
import de.javagl.ply.PlyReaders;

/**
 * An example showing how to use the Ply library to load a PLY file and pass the
 * read data to a custom target that collects the data of specific properties in
 * arrays.
 */
public class Ply_03_ReadArrays
{
    /**
     * The entry point
     * 
     * @param args Not used
     * @throws IOException If an error occurs
     */
    public static void main(String[] args) throws IOException
    {
        // Create an input stream with the PLY data
        InputStream inputStream = new BufferedInputStream(
            new FileInputStream("./data/cube-ascii.ply"));

        // Create a PLY reader
        PlyReader p = PlyReaders.create();

        // Read the descriptor from the input stream
        Descriptor descriptor = p.readDescriptor(inputStream);
        System.out.println("Descriptor: " + descriptor);

        // Based on the descriptor, a custom PLY target can
        // be created. This is an example PLY target that
        // stores the x-, y-, and z-coordinates of the
        // vertex elements in separate arrays
        class CustomPlyTarget extends AbstractPlyTarget
        {
            float vertexCoordinatesX[];
            float vertexCoordinatesY[];
            float vertexCoordinatesZ[];

            @Override
            public void startElementList(int elementTypeIndex, int elementCount)
            {
                // The element type with index 0 is "vertex"
                if (elementTypeIndex == 0)
                {
                    vertexCoordinatesX = new float[elementCount];
                    vertexCoordinatesY = new float[elementCount];
                    vertexCoordinatesZ = new float[elementCount];
                }
            }

            @Override
            public void handleFloatProperty(int elementTypeIndex,
                int elementIndex, int propertyIndex, float value)
            {
                // The element type with index 0 is "vertex"
                if (elementTypeIndex == 0)
                {
                    // Pass the value to the respective array, based on
                    // the property index (0="x", 1="y", 2="z")
                    if (propertyIndex == 0)
                    {
                        vertexCoordinatesX[elementIndex] = value;
                    }
                    else if (propertyIndex == 1)
                    {
                        vertexCoordinatesY[elementIndex] = value;
                    }
                    else if (propertyIndex == 2)
                    {
                        vertexCoordinatesZ[elementIndex] = value;
                    }
                }
            }
        }
        CustomPlyTarget plyTarget = new CustomPlyTarget();

        // Read the PLY data, passing the information to
        // the PLY target
        p.readContent(inputStream, plyTarget);

        // Print the arrays containing the vertex coordinates:
        System.out.println(
            "x-coordinates: " + Arrays.toString(plyTarget.vertexCoordinatesX));
        System.out.println(
            "y-coordinates: " + Arrays.toString(plyTarget.vertexCoordinatesY));
        System.out.println(
            "z-coordinates: " + Arrays.toString(plyTarget.vertexCoordinatesZ));

    }
}
