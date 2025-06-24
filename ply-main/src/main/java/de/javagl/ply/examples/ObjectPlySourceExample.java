/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.javagl.ply.Descriptor;
import de.javagl.ply.Descriptors;
import de.javagl.ply.ElementDescriptors;
import de.javagl.ply.MutableDescriptor;
import de.javagl.ply.MutableElementDescriptor;
import de.javagl.ply.ObjectPlySource;
import de.javagl.ply.PlyType;
import de.javagl.ply.PlyWriter;
import de.javagl.ply.PlyWriters;
import de.javagl.ply.ObjectPlySource.Handle;
import de.javagl.ply.examples.data.ExampleData;
import de.javagl.ply.examples.data.ExampleEdge;
import de.javagl.ply.examples.data.ExampleFace;
import de.javagl.ply.examples.data.ExampleVertex;

/**
 * An example showing how to write lists of existing objects into
 * a PLY file
 */
public class ObjectPlySourceExample
{
    /**
     * The entry point
     * 
     * @param args Not used
     * @throws IOException If an error occurs
     */
    public static void main(String[] args) throws IOException
    {
        // Create the descriptor that defines the structure of
        // the PLY data, and create a source for elements that
        // follow this structure
        Descriptor d = createDescriptor();
        ObjectPlySource plySource = new ObjectPlySource(d);

        // Create lists of example objects
        List<ExampleVertex> vertices = ExampleData.createVertices();
        List<ExampleFace> faces = ExampleData.createFaces();
        List<ExampleEdge> edges = ExampleData.createEdges();

        // Configure the source to take 'vertex' objects from
        // the list of vertices
        Handle<ExampleVertex> v = plySource.register("vertex", vertices);
        
        // Configure the source to fetch the properties from the
        // vertices in the list, using getters
        v.withFloat("x", ExampleVertex::getX);
        v.withFloat("y", ExampleVertex::getY);
        v.withFloat("z", ExampleVertex::getZ);
        v.withByte("red", ExampleVertex::getRed);
        v.withByte("green", ExampleVertex::getGreen);
        v.withByte("blue", ExampleVertex::getBlue);

        // Configure the source to provide the faces as PLY elements
        Handle<ExampleFace> f = plySource.register("face", faces);
        f.withIntList("vertex_index", ExampleFace::getIndices);

        // Configure the source to provide the edges as PLY elements
        Handle<ExampleEdge> e = plySource.register("edge", edges);
        e.withInt("vertex1", ExampleEdge::getVertex1);
        e.withInt("vertex2", ExampleEdge::getVertex2);
        e.withByte("red", ExampleEdge::getRed);
        e.withByte("green", ExampleEdge::getGreen);
        e.withByte("blue", ExampleEdge::getBlue);

        // Write the PLY data from the source in ASCII format into
        // a buffer, and print the result as a string
        PlyWriter w = PlyWriters.createAscii();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        w.write(plySource, baos);
        System.out.println(baos.toString());
    }

    /**
     * Create a descriptor for the PLY data structure
     * 
     * @return The descriptor
     */
    private static Descriptor createDescriptor()
    {
        MutableDescriptor d = Descriptors.create();
        
        MutableElementDescriptor ev = ElementDescriptors.create("vertex");
        ev.addProperty("x", PlyType.FLOAT);
        ev.addProperty("y", PlyType.FLOAT);
        ev.addProperty("z", PlyType.FLOAT);
        ev.addProperty("red", PlyType.UCHAR);
        ev.addProperty("green", PlyType.UCHAR);
        ev.addProperty("blue", PlyType.UCHAR);
        d.addElementDescriptor(ev);

        MutableElementDescriptor ef = ElementDescriptors.create("face");
        ef.addListProperty("vertex_index", PlyType.UCHAR, PlyType.INT);
        d.addElementDescriptor(ef);

        MutableElementDescriptor ee = ElementDescriptors.create("edge");
        ee.addProperty("vertex1", PlyType.INT);
        ee.addProperty("vertex2", PlyType.INT);
        ee.addProperty("red", PlyType.UCHAR);
        ee.addProperty("green", PlyType.UCHAR);
        ee.addProperty("blue", PlyType.UCHAR);
        d.addElementDescriptor(ee);

        return d;
    }

}
