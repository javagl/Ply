/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestPlyReader
{
    @Test
    public void testPlyReader() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream("/cube-ascii.ply");

        PlyReader r = PlyReaders.create();
        PlySource plySource = r.read(inputStream);

        Descriptor descriptor = plySource.getDescriptor();
        assertNotNull(descriptor);

        List<Element> vertexList = plySource.getElementList("vertex");
        assertEquals(vertexList.size(), 8);

        List<Element> faceList = plySource.getElementList("face");
        assertEquals(faceList.size(), 7);

        List<Element> edgeList = plySource.getElementList("edge");
        assertEquals(edgeList.size(), 5);
    }

}
