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
import java.util.ArrayList;
import java.util.List;

import de.javagl.ply.AbstractPlyTarget;
import de.javagl.ply.Descriptor;
import de.javagl.ply.PlyReader;
import de.javagl.ply.PlyReaders;

/**
 * An example showing how to use the Ply library to load a PLY file and pass the
 * read data to a custom target that creates custom objects for the elements
 */
public class Ply_04_ReadCustomObjects
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

        // A dummy class representing a vertex
        class Vertex
        {
            float x;
            float y;
            float z;

            @Override
            public String toString()
            {
                return "(" + x + ", " + y + ", " + z + ")";
            }
        }

        // Based on the descriptor, a custom PLY target can
        // be created. This is an example PLY target that
        // - Creates a new vertex when a new vertex element starts
        // - Assigns the properties to the current vertex
        // - Stores the vertex in a list when it is finished
        class CustomPlyTarget extends AbstractPlyTarget
        {
            // The list that stores the resulting vertices
            List<Vertex> vertices = new ArrayList<Vertex>();

            // The current vertex that is created
            Vertex currentVertex;

            @Override
            public void startElement(int elementTypeIndex, int elementIndex)

            {
                // The element type with index 0 is "vertex"
                if (elementTypeIndex == 0)
                {
                    // Prepare a new vertex to be filled with
                    // the subsequent property values
                    currentVertex = new Vertex();
                }
            }

            @Override
            public void handleFloatProperty(int elementTypeIndex,
                int elementIndex, int propertyIndex, float value)
            {
                // The element type with index 0 is "vertex"
                if (elementTypeIndex == 0)
                {
                    // Set the value of the current vertex, based on
                    // the property index (0="x", 1="y", 2="z")
                    if (propertyIndex == 0)
                    {
                        currentVertex.x = value;
                    }
                    else if (propertyIndex == 1)
                    {
                        currentVertex.y = value;
                    }
                    else if (propertyIndex == 2)
                    {
                        currentVertex.z = value;
                    }
                }
            }

            @Override
            public void endElement(int elementTypeIndex, int elementIndex)
            {
                // The element type with index 0 is "vertex"
                if (elementTypeIndex == 0)
                {
                    // When the current vertex is finished,
                    // store it in the list
                    vertices.add(currentVertex);
                    currentVertex = null;
                }
            }
        }
        CustomPlyTarget plyTarget = new CustomPlyTarget();

        // Read the PLY data, passing the information to
        // the PLY target
        p.readContent(inputStream, plyTarget);

        // Print the vertices that have been read:
        System.out.println(plyTarget.vertices);
    }
}
