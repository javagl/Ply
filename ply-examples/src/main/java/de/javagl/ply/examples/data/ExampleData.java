/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply.examples.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods to create example data
 */
public class ExampleData
{
    /**
     * Create the example vertices
     * 
     * @return The vertices
     */
    public static List<ExampleVertex> createVertices()
    {
        List<ExampleVertex> vertices = new ArrayList<ExampleVertex>();
        vertices.add(new ExampleVertex(0, 0, 0, 255, 0, 0));
        vertices.add(new ExampleVertex(0, 0, 1, 255, 0, 0));
        vertices.add(new ExampleVertex(0, 1, 1, 255, 0, 0));
        vertices.add(new ExampleVertex(0, 1, 0, 255, 0, 0));
        vertices.add(new ExampleVertex(1, 0, 0, 0, 0, 255));
        vertices.add(new ExampleVertex(1, 0, 1, 0, 0, 255));
        vertices.add(new ExampleVertex(1, 1, 1, 0, 0, 255));
        vertices.add(new ExampleVertex(1, 1, 0, 0, 0, 255));
        return vertices;
    }

    /**
     * Create the example faces
     * 
     * @return The faces
     */
    public static List<ExampleFace> createFaces()
    {
        List<ExampleFace> faces = new ArrayList<ExampleFace>();
        faces.add(new ExampleFace(0, 1, 2));
        faces.add(new ExampleFace(0, 2, 3));
        faces.add(new ExampleFace(7, 6, 5, 4));
        faces.add(new ExampleFace(0, 4, 5, 1));
        faces.add(new ExampleFace(1, 5, 6, 2));
        faces.add(new ExampleFace(2, 6, 7, 3));
        faces.add(new ExampleFace(3, 7, 4, 0));
        return faces;
    }

    /**
     * Create the example edges
     * 
     * @return The edges
     */
    public static List<ExampleEdge> createEdges()
    {
        List<ExampleEdge> edges = new ArrayList<ExampleEdge>();
        edges.add(new ExampleEdge(0, 1, 255, 255, 255));
        edges.add(new ExampleEdge(1, 2, 255, 255, 255));
        edges.add(new ExampleEdge(2, 3, 255, 255, 255));
        edges.add(new ExampleEdge(3, 0, 255, 255, 255));
        edges.add(new ExampleEdge(2, 0, 0, 0, 0));
        return edges;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private ExampleData()
    {
        // Private constructor to prevent instantiation
    }
}
