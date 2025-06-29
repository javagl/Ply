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

import de.javagl.ply.ObjectPlyTarget;
import de.javagl.ply.PlyReader;
import de.javagl.ply.PlyReaders;
import de.javagl.ply.ObjectPlyTarget.Handle;
import de.javagl.ply.examples.data.ExampleEdge;
import de.javagl.ply.examples.data.ExampleFace;
import de.javagl.ply.examples.data.ExampleVertex;

/**
 * An example showing how to read a PLY file and create custom objects based on
 * the elements that are read from the tile.
 */
public class ObjectPlyTargetExample
{
    /**
     * The entry point
     * 
     * @param args Not used
     * @throws IOException If an error occurs
     */
    public static void main(String[] args) throws IOException
    {
        // Prepare the input stream and reader for the data
        InputStream inputStream = new BufferedInputStream(
            new FileInputStream("../ply-main/data/cube-ascii.ply"));
        PlyReader r = PlyReaders.create();

        // Create a new ObjectPlyTarget
        ObjectPlyTarget plyTarget = new ObjectPlyTarget();

        // Configure the target to create an ExampleVertex for
        // each "vertex" element that is read from the file
        Handle<ExampleVertex> v =
            plyTarget.register("vertex", ExampleVertex::new);

        // Configure the target to assign the properties to
        // the newly created vertices using setters
        v.withFloat("x", ExampleVertex::setX);
        v.withFloat("y", ExampleVertex::setY);
        v.withFloat("z", ExampleVertex::setZ);
        v.withByte("red", ExampleVertex::setRed);
        v.withByte("green", ExampleVertex::setGreen);
        v.withByte("blue", ExampleVertex::setBlue);

        // Configure the target to put each vertex into a list
        List<ExampleVertex> vertices = new ArrayList<ExampleVertex>();
        v.consume(vertices::add);

        // Configure the target to create faces and put them into a list
        Handle<ExampleFace> f = plyTarget.register("face", ExampleFace::new);
        f.withIntList("vertex_index", ExampleFace::setIndices);
        List<ExampleFace> faces = new ArrayList<ExampleFace>();
        f.consume(faces::add);

        // Configure the target to create edges and put them into a list
        Handle<ExampleEdge> e = plyTarget.register("edge", ExampleEdge::new);
        e.withInt("vertex1", ExampleEdge::setVertex1);
        e.withInt("vertex2", ExampleEdge::setVertex2);
        e.withByte("red", ExampleEdge::setRed);
        e.withByte("green", ExampleEdge::setGreen);
        e.withByte("blue", ExampleEdge::setBlue);
        List<ExampleEdge> edges = new ArrayList<ExampleEdge>();
        e.consume(edges::add);

        // Read the descriptor and the actual content, passing it
        // to the target to create vertices, faces, and edges
        r.readDescriptor(inputStream);
        r.readContent(inputStream, plyTarget);

        // Print the generated objects
        System.out.println("vertices: " + vertices);
        System.out.println("faces: " + faces);
        System.out.println("edges: " + edges);

    }
}
