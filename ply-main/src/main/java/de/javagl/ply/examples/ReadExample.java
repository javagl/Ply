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
import java.util.List;

import de.javagl.ply.Descriptor;
import de.javagl.ply.Element;
import de.javagl.ply.ElementDescriptor;
import de.javagl.ply.PlyReader;
import de.javagl.ply.PlyReaders;
import de.javagl.ply.PlySource;

/**
 * An example showing how to use the Ply library to load a PLY file
 */
public class ReadExample
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

        // Read the PLY data
        PlySource plySource = p.read(inputStream);

        // Print the data to the console
        print(plySource);

        // Access one specific element and one specific
        // property of the PLY data:
        List<Element> vertices = plySource.getElementList("vertex");
        Element vertex = vertices.get(1);
        float z = vertex.getFloatProperty("z");
        System.out.println("Value: " + z);
    }

    /**
     * Print information about the given PLY data to the console
     * 
     * @param plySource The PLY data
     */
    private static void print(PlySource plySource)
    {
        Descriptor descriptor = plySource.getDescriptor();
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int i = 0; i < elementDescriptors.size(); i++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(i);
            String elementName = elementDescriptor.getName();

            List<Element> elementList = plySource.getElementList(elementName);
            System.out.println("Elements for '" + elementName + "' ("
                + elementList.size() + ")");
            for (int j = 0; j < elementList.size(); j++)
            {
                System.out.println("  " + elementList.get(j));
            }
        }
    }
}
