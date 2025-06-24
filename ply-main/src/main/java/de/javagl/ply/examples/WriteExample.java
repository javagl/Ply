/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import de.javagl.ply.Descriptor;
import de.javagl.ply.Descriptors;
import de.javagl.ply.ElementDescriptors;
import de.javagl.ply.Elements;
import de.javagl.ply.MutableDescriptor;
import de.javagl.ply.MutableElement;
import de.javagl.ply.MutableElementDescriptor;
import de.javagl.ply.MutablePlySource;
import de.javagl.ply.PlySources;
import de.javagl.ply.PlyType;
import de.javagl.ply.PlyWriter;
import de.javagl.ply.PlyWriters;

/**
 * An example showing how to use the Ply library create generic PLY data and
 * write it to a file or stream.
 */
public class WriteExample
{
    /**
     * The entry point
     * 
     * @param args Not used
     * @throws IOException If an error occurs
     */
    public static void main(String[] args) throws IOException
    {
        // Create a descriptor for the data structure, which
        // will go into the header of the PLY file
        Descriptor d = createDescriptor();

        // Create a PLY source where the elements are expected
        // match the given descriptor
        MutablePlySource p = PlySources.create(d);

        // Create some elements of the first type, 'vertex'
        MutableElement v0 = Elements.create();
        v0.setFloatProperty("x", 0.0f);
        v0.setFloatProperty("y", 0.0f);
        v0.setFloatProperty("z", 0.0f);

        MutableElement v1 = Elements.create();
        v1.setFloatProperty("x", 1.0f);
        v1.setFloatProperty("y", 0.0f);
        v1.setFloatProperty("z", 0.0f);

        MutableElement v2 = Elements.create();
        v2.setFloatProperty("x", 0.0f);
        v2.setFloatProperty("y", 1.0f);
        v2.setFloatProperty("z", 0.0f);

        MutableElement v3 = Elements.create();
        v3.setFloatProperty("x", 1.0f);
        v3.setFloatProperty("y", 1.0f);
        v3.setFloatProperty("z", 0.0f);

        // Add all elements to the PLY source
        p.addElements("vertex", Arrays.asList(v0, v1, v2, v3));

        // Create elements of the second type, 'face', and
        // add them to the PLY source
        MutableElement f0 = Elements.create();
        f0.setIntListProperty("vertex_index", new int[]
        { 0, 1, 2 });
        p.addElement("face", f0);

        MutableElement f1 = Elements.create();
        f1.setIntListProperty("vertex_index", new int[]
        { 2, 3, 0 });
        p.addElement("face", f1);

        // Write the PLY data, in 'ascii' format, into an
        // output stream, and print the resulting string
        PlyWriter w = PlyWriters.createAscii();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        w.write(p, baos);
        System.out.println(baos.toString());
    }

    /**
     * Create a descriptor for the PLY data structure
     * 
     * @return The descriptor
     */
    private static Descriptor createDescriptor()
    {
        // Create the descriptor
        MutableDescriptor d = Descriptors.create();

        // Create a descriptor for the first element,
        // and add it to the descriptor
        MutableElementDescriptor ev = ElementDescriptors.create("vertex");
        ev.addProperty("x", PlyType.FLOAT);
        ev.addProperty("y", PlyType.FLOAT);
        ev.addProperty("z", PlyType.FLOAT);
        d.addElementDescriptor(ev);

        // Create a descriptor for the second element,
        // and add it to the descriptor
        MutableElementDescriptor ef = ElementDescriptors.create("face");
        ef.addListProperty("vertex_index", PlyType.UCHAR, PlyType.INT);
        d.addElementDescriptor(ef);

        return d;
    }
}
