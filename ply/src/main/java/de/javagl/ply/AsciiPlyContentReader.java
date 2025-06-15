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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Internal class to read ASCII PLY data
 */
class AsciiPlyContentReader
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(AsciiPlyContentReader.class.getName());

    /**
     * The {@link Descriptor} for the data structure
     */
    private Descriptor descriptor;

    /**
     * The number of elements, one for each element type in the descriptor
     */
    private List<Integer> counts;

    /**
     * Creates a new instance
     * 
     * @param descriptor The {@link Descriptor} for the data structure
     */
    AsciiPlyContentReader(Descriptor descriptor)
    {
        this.descriptor = descriptor;
    }

    /**
     * Set the number of elements of each type
     * 
     * @param counts The number of elements of each type
     */
    void setCounts(List<Integer> counts)
    {
        this.counts = counts;
    }

    /**
     * Read the data from the given input stream and pass it to the given
     * {@link PlyTarget}.
     * 
     * @param inputStream The input stream
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException If an IO error occurs
     */
    void read(InputStream inputStream, PlyTarget plyTarget) throws IOException
    {
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream));
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int t = 0; t < elementDescriptors.size(); t++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(t);
            List<PropertyDescriptor> propertyDescriptors =
                elementDescriptor.getPropertyDescriptors();

            List<AsciiPropertyReader> propertyReaders =
                createAsciiPropertyReaders(elementDescriptor);

            int count = counts.get(t);
            plyTarget.startElementList(t, elementDescriptor, count);
            int elementIndex = 0;
            while (true)
            {
                String line = bufferedReader.readLine();
                if (line == null)
                {
                    throw new IOException("Expected " + count + " elements for "
                        + elementDescriptor + ", but only found "
                        + elementIndex);
                }
                line = line.trim();
                if (line.isEmpty())
                {
                    continue;
                }

                plyTarget.startElement(t, elementDescriptor, elementIndex);

                List<String> tokens = Arrays.asList(line.split("\\s+"));
                int currentTokenIndex = 0;
                for (int p = 0; p < propertyDescriptors.size(); p++)
                {
                    PropertyDescriptor propertyDescriptor =
                        propertyDescriptors.get(p);
                    AsciiPropertyReader propertyReader = propertyReaders.get(p);
                    int readTokens = propertyReader.read(tokens,
                        currentTokenIndex, t, elementDescriptor, elementIndex,
                        propertyDescriptor, p, plyTarget);
                    currentTokenIndex += readTokens;
                }

                plyTarget.endElement(t, elementDescriptor, elementIndex);
                elementIndex++;
                if (elementIndex == count)
                {
                    break;
                }
            }
            plyTarget.endElementList(t, elementDescriptor);
        }
    }

    /**
     * Create one {@link AsciiPropertyReader} for each property of the given
     * {@link ElementDescriptor}
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @return The {@link AsciiPropertyReader} objects
     */
    private List<AsciiPropertyReader>
        createAsciiPropertyReaders(ElementDescriptor elementDescriptor)
    {
        List<AsciiPropertyReader> propertyReaders =
            new ArrayList<AsciiPropertyReader>();

        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(i);
            PlyType type = propertyDescriptor.getType();
            PlyType sizeType = propertyDescriptor.getSizeType();
            AsciiPropertyReader propertyReader =
                createPropertyReader(type, sizeType);
            propertyReaders.add(propertyReader);
        }

        return propertyReaders;
    }

    /**
     * Create a {@link AsciiPropertyReader} for the given type and size type.
     * 
     * If the type is invalid, a warning will be printed and <code>null</code>
     * will be returned.
     * 
     * @param type The type
     * @param sizeType The size type
     * @return The {@link AsciiPropertyReader}
     */
    private static AsciiPropertyReader createPropertyReader(PlyType type,
        PlyType sizeType)
    {
        if (sizeType == null)
        {
            switch (type)
            {
                case UCHAR:
                case CHAR:
                    return AsciiPlyContentReader::readChar;
                case USHORT:
                case SHORT:
                    return AsciiPlyContentReader::readShort;
                case UINT:
                case INT:
                    return AsciiPlyContentReader::readInt;
                case FLOAT:
                    return AsciiPlyContentReader::readFloat;
                case DOUBLE:
                    return AsciiPlyContentReader::readDouble;
                default:
                    break;
            }
            logger.severe("Unknown property type: " + type);
            return null;
        }

        switch (type)
        {
            case UCHAR:
            case CHAR:
                return AsciiPlyContentReader::readCharList;
            case USHORT:
            case SHORT:
                return AsciiPlyContentReader::readShortList;
            case UINT:
            case INT:
                return AsciiPlyContentReader::readIntList;
            case FLOAT:
                return AsciiPlyContentReader::readFloatList;
            case DOUBLE:
                return AsciiPlyContentReader::readDoubleList;
            default:
                break;
        }
        logger.severe("Unknown property type: " + type);
        return null;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readChar(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        byte value;
        try
        {
            value = (byte) Integer.parseInt(tokens.get(tokenIndex));
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleCharProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readShort(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        short value;
        try
        {
            value = (short) Integer.parseInt(tokens.get(tokenIndex));
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleShortProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readInt(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        int value;
        try
        {
            value = (int) Long.parseLong(tokens.get(tokenIndex));
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleIntProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readFloat(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        float value;
        try
        {
            value = Float.parseFloat(tokens.get(tokenIndex));
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleFloatProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readDouble(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        double value;
        try
        {
            value = Double.parseDouble(tokens.get(tokenIndex));
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleDoubleProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readCharList(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        int numElements;
        byte value[];
        try
        {
            int currentTokenIndex = tokenIndex;
            numElements = Integer.parseInt(tokens.get(currentTokenIndex++));
            value = new byte[numElements];
            for (int i = 0; i < numElements; i++)
            {
                String token = tokens.get(currentTokenIndex++);
                value[i] = (byte) Integer.parseInt(token);
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleCharListProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return numElements + 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readShortList(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        int numElements;
        short value[];
        try
        {
            int currentTokenIndex = tokenIndex;
            numElements = Integer.parseInt(tokens.get(currentTokenIndex++));
            value = new short[numElements];
            for (int i = 0; i < numElements; i++)
            {
                String token = tokens.get(currentTokenIndex++);
                value[i] = (short) Integer.parseInt(token);
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleShortListProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return numElements + 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readIntList(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        int numElements;
        int value[];
        try
        {
            int currentTokenIndex = tokenIndex;
            numElements = Integer.parseInt(tokens.get(currentTokenIndex++));
            value = new int[numElements];
            for (int i = 0; i < numElements; i++)
            {
                String token = tokens.get(currentTokenIndex++);
                value[i] = (int) Long.parseLong(token);
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleIntListProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return numElements + 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readFloatList(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        int numElements;
        float value[];
        try
        {
            int currentTokenIndex = tokenIndex;
            numElements = Integer.parseInt(tokens.get(currentTokenIndex++));
            value = new float[numElements];
            for (int i = 0; i < numElements; i++)
            {
                String token = tokens.get(currentTokenIndex++);
                value[i] = Float.parseFloat(token);
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleFloatListProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return numElements + 1;
    }

    /**
     * Read the specified tokens.
     * 
     * To be used as an implementation of {@link AsciiPropertyReader}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The element index
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    private static int readDoubleList(List<String> tokens, int tokenIndex,
        int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex, PropertyDescriptor propertyDescriptor,
        int propertyIndex, PlyTarget plyTarget) throws IOException
    {
        int numElements;
        double value[];
        try
        {
            int currentTokenIndex = tokenIndex;
            numElements = Integer.parseInt(tokens.get(currentTokenIndex++));
            value = new double[numElements];
            for (int i = 0; i < numElements; i++)
            {
                String token = tokens.get(currentTokenIndex++);
                value[i] = Float.parseFloat(token);
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
        plyTarget.handleDoubleListProperty(elementTypeIndex, elementDescriptor,
            elementIndex, propertyDescriptor, propertyIndex, value);
        return numElements + 1;
    }

}