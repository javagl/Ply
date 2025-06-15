/*
 * www.javagl.de - Data
 *
 * Copyright (c) 2013-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.ply;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class TestPlyWriters
{
    @Parameters(name = "{index}: inputFileName[{0}]")
    public static Iterable<String[]> data()
    {
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[]
        { "/cube-ascii.ply" });
        data.add(new String[]
        { "/all-types-ascii.ply" });
        return data;
    }

    @Parameter(0)
    public String inputFileName;

    @Test
    public void testPlyWriterAscii() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream(inputFileName);

        PlyReader r = PlyReaders.create();
        PlySource plySource = r.read(inputStream);

        PlyWriter w = PlyWriters.createAscii();
        PlySource resultPlySource = roundtrip(plySource, w);

        String expected = createAsciiString(plySource);
        String actual = createAsciiString(resultPlySource);

        assertEquals(expected, actual);
    }

    @Test
    public void testPlyWriterBinaryLittleEndian() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream(inputFileName);

        PlyReader r = PlyReaders.create();
        PlySource plySource = r.read(inputStream);

        PlyWriter w = PlyWriters.createBinaryLittleEndian();
        PlySource resultPlySource = roundtrip(plySource, w);

        String expected = createAsciiString(plySource);
        String actual = createAsciiString(resultPlySource);

        assertEquals(expected, actual);
    }

    @Test
    public void testPlyWriterBigEndian() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream(inputFileName);

        PlyReader r = PlyReaders.create();
        PlySource plySource = r.read(inputStream);

        PlyWriter w = PlyWriters.createBinaryBigEndian();
        PlySource resultPlySource = roundtrip(plySource, w);

        String expected = createAsciiString(plySource);
        String actual = createAsciiString(resultPlySource);

        assertEquals(expected, actual);
    }

    /**
     * Uses the given writer to write the given {@link PlySource} into a byte
     * array, reads a new {@link PlySource} from that byte array and returns it.
     * 
     * @param plySource The {@link PlySource}
     * @param plyWriter The {@link PlyWriter}
     * @return The resulting {@link PlySource}
     * @throws IOException I hope it doesn't ...
     */
    private static PlySource roundtrip(PlySource plySource, PlyWriter plyWriter)
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        plyWriter.write(plySource, baos);
        ByteArrayInputStream bytes =
            new ByteArrayInputStream(baos.toByteArray());
        PlyReader r = PlyReaders.create();
        PlySource resultPlySource = r.read(bytes);
        return resultPlySource;
    }

    /**
     * Writes the given PLY as an "ascii" ply and returns the result
     * 
     * @param plySource The {@link PlySource}
     * @return The string
     * @throws IOException If an IO error occurs
     */
    private static String createAsciiString(PlySource plySource)
        throws IOException
    {
        PlyWriter w = PlyWriters.createAscii();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        w.write(plySource, baos);
        String output = baos.toString();
        return output;
    }

}
