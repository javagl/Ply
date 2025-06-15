/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.ply;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Methods to create {@link PlyWriter} instances
 */
public class PlyWriters
{
    /**
     * Creates a new ASCII {@link PlyWriter}
     * 
     * @return The {@link PlyWriter}
     */
    public static PlyWriter createAscii()
    {
        return new AsciiPlyWriter();
    }

    /**
     * Creates a new binary (big-endian) {@link PlyWriter}
     * 
     * @return The {@link PlyWriter}
     */
    public static PlyWriter createBinaryBigEndian()
    {
        return new BinaryPlyWriter(false);
    }

    /**
     * Creates a new binary (little-endian) {@link PlyWriter}
     * 
     * @return The {@link PlyWriter}
     */
    public static PlyWriter createBinaryLittleEndian()
    {
        return new BinaryPlyWriter(true);
    }

    /**
     * Compute the number of elements of each type in the given
     * {@link PlySource}
     * 
     * @param plySource The {@link PlySource}
     * @return The element counts
     */
    private static List<Integer> computeElementCounts(PlySource plySource)
    {
        List<Integer> counts = new ArrayList<Integer>();
        Descriptor descriptor = plySource.getDescriptor();
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int t = 0; t < elementDescriptors.size(); t++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(t);
            String name = elementDescriptor.getName();
            List<Element> list = plySource.getElementList(name);
            if (list == null)
            {
                counts.add(0);
            }
            else
            {
                counts.add(list.size());
            }
        }
        return counts;
    }

    /**
     * Write the header for the given {@link PlySource} into the given output
     * stream.
     * 
     * @param plySource The {@link PlySource}
     * @param format The format, <code>"ascii"</code>,
     *        <code>"binary_little_endian"</code>, or
     *        <code>"binary_big_endian"</code>
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    static void writeHeader(PlySource plySource, String format,
        OutputStream outputStream) throws IOException
    {
        Map<PlyType, String> typeStrings = PlyType.createDefaultStrings();

        Writer writer = new OutputStreamWriter(outputStream);
        writer.write("ply" + "\n");
        writer.write("format " + format + " 1.0" + "\n");
        writer.write("comment Generated with Ply from javagl.de" + "\n");

        List<Integer> elementCounts = computeElementCounts(plySource);
        Descriptor descriptor = plySource.getDescriptor();
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int t = 0; t < elementDescriptors.size(); t++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(t);
            String elementName = elementDescriptor.getName();
            int elementCount = elementCounts.get(t);

            writer.write("element " + elementName + " " + elementCount + "\n");

            List<PropertyDescriptor> propertyDescriptors =
                elementDescriptor.getPropertyDescriptors();
            for (int p = 0; p < propertyDescriptors.size(); p++)
            {
                PropertyDescriptor propertyDescriptor =
                    propertyDescriptors.get(p);
                String propertyName = propertyDescriptor.getName();
                PlyType propertyType = propertyDescriptor.getType();
                PlyType propertySizeType = propertyDescriptor.getSizeType();

                if (propertySizeType != null)
                {
                    writer.write(
                        "property list " + typeStrings.get(propertySizeType)
                            + " " + typeStrings.get(propertyType) + " "
                            + propertyName + "\n");
                }
                else
                {
                    writer.write("property " + typeStrings.get(propertyType)
                        + " " + propertyName + "\n");
                }
            }
        }
        writer.write("end_header" + "\n");
        writer.flush();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private PlyWriters()
    {
        // Private constructor to prevent instantiation
    }

}