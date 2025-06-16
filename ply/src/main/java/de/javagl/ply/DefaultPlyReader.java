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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Default implementation of a {@link PlyReader}
 */
class DefaultPlyReader implements PlyReader
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(DefaultPlyReader.class.getName());

    /**
     * The last {@link Descriptor} that was read
     */
    private Descriptor descriptor;

    /**
     * The {@link AsciiPlyContentReader}
     */
    private AsciiPlyContentReader asciiContentReader;

    /**
     * The {@link BinaryPlyContentReader}
     */
    private BinaryPlyContentReader binaryContentReader;

    /**
     * Creates a new instance
     */
    DefaultPlyReader()
    {
        this.descriptor = null;
        this.asciiContentReader = null;
        this.binaryContentReader = null;
    }

    @Override
    public PlySource read(InputStream inputStream) throws IOException
    {
        Descriptor descriptor = readDescriptor(inputStream);
        DefaultPlyTarget plyTarget = new DefaultPlyTarget();
        plyTarget.setDescriptor(descriptor);
        readContent(inputStream, plyTarget);
        return plyTarget.getPlySource();
    }

    @Override
    public Descriptor readDescriptor(InputStream inputStream) throws IOException
    {
        this.asciiContentReader = null;
        this.binaryContentReader = null;

        LineReader headerLineReader = new LineReader(inputStream);

        MutableDescriptor descriptor = Descriptors.create();
        MutableElementDescriptor currentElementDescriptor = null;
        List<Integer> elementCounts = new ArrayList<Integer>();

        // Read the header, line by line
        String line = null;
        while (true)
        {
            line = headerLineReader.readLine();
            if (line == null)
            {
                if (currentElementDescriptor != null)
                {
                    descriptor.addElementDescriptor(currentElementDescriptor);
                    currentElementDescriptor = null;
                }
                break;
            }
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }
            if (line.startsWith("comment"))
            {
                String comment = line.substring(8);
                descriptor.addComment(comment);
            }

            // Handle each line, until the "end_header" line is found
            if (line.startsWith("format"))
            {
                handleFormat(line, descriptor);
            }
            else if (line.startsWith("element"))
            {
                if (currentElementDescriptor != null)
                {
                    descriptor.addElementDescriptor(currentElementDescriptor);
                }
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length != 3)
                {
                    throw new IOException(
                        "Invalid element specifier: '" + line + "'");
                }
                String elementName = tokens[1];
                String elementCountString = tokens[2];
                int elementCount;
                try
                {
                    elementCount = Integer.parseInt(elementCountString);
                }
                catch (NumberFormatException e)
                {
                    throw new IOException(
                        "Invalid element count in '" + line + "'");
                }
                elementCounts.add(elementCount);
                currentElementDescriptor =
                    ElementDescriptors.create(elementName);
            }
            else if (line.startsWith("property"))
            {
                handleProperty(line, currentElementDescriptor);
            }
            else if (line.equals("end_header"))
            {
                if (currentElementDescriptor != null)
                {
                    descriptor.addElementDescriptor(currentElementDescriptor);
                    currentElementDescriptor = null;
                }
                break;
            }
        }
        this.descriptor = descriptor;

        if (asciiContentReader != null)
        {
            asciiContentReader.setCounts(elementCounts);
        }
        if (binaryContentReader != null)
        {
            binaryContentReader.setCounts(elementCounts);
        }

        return descriptor;
    }

    /**
     * Handle a <code>"format"</code> line
     * 
     * @param line The line
     * @param descriptor The current {@link Descriptor}
     * @throws IOException If an IO error occurs
     */
    private void handleFormat(String line, Descriptor descriptor)
        throws IOException
    {
        String[] tokens = line.trim().split("\\s+");
        if (tokens.length != 3)
        {
            throw new IOException("Invalid format specifier: '" + line + "'");
        }
        String formatName = tokens[1];
        String version = tokens[2];
        if (!version.equals("1.0"))
        {
            logger.warning("Expected version '1.0' but found '" + version
                + "' - ignoring");
        }

        if (formatName.equals("ascii"))
        {
            asciiContentReader = new AsciiPlyContentReader(descriptor);
        }
        else if (formatName.equals("binary_little_endian"))
        {
            binaryContentReader = new BinaryPlyContentReader(descriptor, true);
        }
        else if (formatName.equals("binary_big_endian"))
        {
            binaryContentReader = new BinaryPlyContentReader(descriptor, false);
        }
        else
        {
            throw new IOException("Expected format to be 'ascii', "
                + "'binary_little_endian', or 'binary_big_endian', "
                + "but found '" + formatName + "'");
        }
    }

    /**
     * Handle a <code>"property"</code> line
     * 
     * @param line The line
     * @param currentElementDescriptor The current {@link ElementDescriptor}
     * @throws IOException If an IO error occurs
     */
    private void handleProperty(String line,
        MutableElementDescriptor currentElementDescriptor) throws IOException
    {
        String[] tokens = line.trim().split("\\s+");
        if (tokens.length < 3)
        {
            throw new IOException("Invalid property specifier: '" + line + "'");
        }
        if (tokens[1].equals("list"))
        {
            if (tokens.length != 5)
            {
                throw new IOException(
                    "Invalid list property specifier: '" + line + "'");
            }
            String listSizeTypeString = tokens[2];
            if (!PlyType.isValidSize(listSizeTypeString))
            {
                throw new IOException("Invalid size type: '" + line + "'");
            }
            PlyType listSizeType = PlyType.valueFor(listSizeTypeString);

            String listContentTypeString = tokens[3];
            if (!PlyType.isValid(listContentTypeString))
            {
                throw new IOException("Invalid type: '" + line + "'");
            }
            PlyType listContentType = PlyType.valueFor(listContentTypeString);
            String listPropertyName = tokens[4];

            currentElementDescriptor.addListProperty(listPropertyName,
                listSizeType, listContentType);
        }
        else
        {
            if (tokens.length != 3)
            {
                throw new IOException(
                    "Invalid property specifier: '" + line + "'");
            }

            String propertyTypeString = tokens[1];
            if (!PlyType.isValid(propertyTypeString))
            {
                throw new IOException("Invalid type: '" + line + "'");
            }
            PlyType propertyType = PlyType.valueFor(propertyTypeString);

            String propertyName = tokens[2];

            currentElementDescriptor.addProperty(propertyName, propertyType);
        }
    }

    @Override
    public void readContent(InputStream inputStream, PlyTarget plyTarget)
        throws IOException
    {
        if (descriptor == null)
        {
            throw new IOException("The descriptor has not been read");
        }
        plyTarget.setDescriptor(descriptor);
        if (asciiContentReader != null)
        {
            asciiContentReader.read(inputStream, plyTarget);
        }
        else
        {
            binaryContentReader.read(inputStream, plyTarget);
        }
    }

}