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

import de.javagl.ply.AbstractPlyTarget;
import de.javagl.ply.Descriptor;
import de.javagl.ply.PlyReader;
import de.javagl.ply.PlyReaders;
import de.javagl.ply.PlyTarget;

/**
 * An example showing how to use the Ply library to load a PLY file and pass the
 * read data to a custom target.
 */
public class ReadCustomExample
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
        // be created. Here, the PLY target is a dummy target
        // that just prints information about all 'float'
        // properties that it receives:
        PlyTarget plyTarget = new AbstractPlyTarget()
        {
            @Override
            public void handleFloatProperty(int elementTypeIndex,
                int elementIndex, int propertyIndex, float value)
            {
                String elementName =
                    descriptor.getElementName(elementTypeIndex);
                String propertyName =
                    descriptor.getPropertyName(elementTypeIndex, propertyIndex);
                System.out.println("Float property " + propertyIndex
                    + " with name '" + propertyName + "' for element "
                    + elementIndex + " of element type '" + elementName
                    + "' has value " + value);
            }
        };

        // Read the PLY data, passing the information to
        // the PLY target
        p.readContent(inputStream, plyTarget);
    }
}
