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
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Internal class to read binary PLY data
 */
class BinaryPlyContentReader
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(BinaryPlyContentReader.class.getName());

    /**
     * The {@link Descriptor} for the data structure
     */
    private final Descriptor descriptor;

    /**
     * The number of elements, one for each element type in the descriptor
     */
    private List<Integer> counts;

    /**
     * A byte buffer for reading property values
     */
    private final byte byteArray[];

    /**
     * A byte buffer wrapped around the byte array, to handle the endianness.
     */
    private final ByteBuffer byteBuffer;

    /**
     * Creates a new instance
     * 
     * @param descriptor The {@link Descriptor} for the data structure
     * @param littleEndian Whether to use little-endian order
     */
    BinaryPlyContentReader(Descriptor descriptor, boolean littleEndian)
    {
        this.descriptor = descriptor;
        this.byteArray = new byte[8];
        this.byteBuffer = ByteBuffer.wrap(byteArray);
        if (littleEndian)
        {
            this.byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
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
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int t = 0; t < elementDescriptors.size(); t++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(t);

            List<BinaryPropertyReader> propertyReaders =
                createPropertyReaders(elementDescriptor);
            List<Function<InputStream, Number>> sizeReaders =
                createSizeReaders(elementDescriptor);

            int count = counts.get(t);
            plyTarget.startElementList(t, count);
            for (int e = 0; e < count; e++)
            {
                plyTarget.startElement(t, e);

                for (int p = 0; p < propertyReaders.size(); p++)
                {
                    BinaryPropertyReader propertyReader =
                        propertyReaders.get(p);
                    Function<InputStream, Number> sizeReader =
                        sizeReaders.get(p);
                    propertyReader.read(inputStream, t, e, p, sizeReader,
                        plyTarget);
                }
                plyTarget.endElement(t, e);
            }
            plyTarget.endElementList(t);
        }
    }

    /**
     * Create functions for reading the size of each property of the given
     * {@link ElementDescriptor} from an input stream
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @return The functions
     */
    private List<Function<InputStream, Number>>
        createSizeReaders(ElementDescriptor elementDescriptor)
    {
        List<Function<InputStream, Number>> sizeReaders =
            new ArrayList<Function<InputStream, Number>>();

        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(i);
            PlyType sizeType = propertyDescriptor.getSizeType();
            if (sizeType != null)
            {
                Function<InputStream, Number> sizeReader =
                    createSizeReader(sizeType);
                sizeReaders.add(sizeReader);
            }
            else
            {
                sizeReaders.add(null);
            }
        }
        return sizeReaders;
    }

    /**
     * Creates a function that reads a value from an input stream that matches
     * the given size type.
     * 
     * If the given type is not a valid size type, then a warning will be
     * printed and <code>null</code> will be returned.
     * 
     * The function will re-throw any IO exception as an unchecked IO exception.
     * 
     * @param sizeType The size type
     * @return The function
     */
    private Function<InputStream, Number> createSizeReader(PlyType sizeType)
    {
        if (sizeType == PlyType.UCHAR || sizeType == PlyType.CHAR)
        {
            return inputStream ->
            {
                try
                {
                    return Byte.toUnsignedInt(readByte(inputStream));
                }
                catch (IOException e)
                {
                    throw new UncheckedIOException(e);
                }
            };
        }
        if (sizeType == PlyType.USHORT || sizeType == PlyType.SHORT)
        {
            return inputStream ->
            {
                try
                {
                    return Short.toUnsignedInt(readShort(inputStream));
                }
                catch (IOException e)
                {
                    throw new UncheckedIOException(e);
                }
            };
        }
        if (sizeType == PlyType.UINT || sizeType == PlyType.INT)
        {
            return inputStream ->
            {
                try
                {
                    return readInt(inputStream);
                }
                catch (IOException e)
                {
                    throw new UncheckedIOException(e);
                }
            };
        }
        logger.severe("Invalid size type: " + sizeType);
        return null;

    }

    /**
     * Create one {@link BinaryPropertyReader} for each property of the given
     * {@link ElementDescriptor}
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @return The {@link BinaryPropertyReader} objects
     */
    private List<BinaryPropertyReader>
        createPropertyReaders(ElementDescriptor elementDescriptor)
    {
        List<BinaryPropertyReader> propertyReaders =
            new ArrayList<BinaryPropertyReader>();

        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(i);
            PlyType type = propertyDescriptor.getType();
            PlyType sizeType = propertyDescriptor.getSizeType();
            BinaryPropertyReader propertyReader =
                createPropertyReader(type, sizeType);
            propertyReaders.add(propertyReader);
        }

        return propertyReaders;
    }

    /**
     * Create a {@link BinaryPropertyReader} for the given type and size type.
     * 
     * If the type is invalid, a warning will be printed and <code>null</code>
     * will be returned.
     * 
     * @param type The type
     * @param sizeType The size type
     * @return The {@link BinaryPropertyReader}
     */
    private BinaryPropertyReader createPropertyReader(PlyType type,
        PlyType sizeType)
    {
        if (sizeType == null)
        {
            switch (type)
            {
                case UCHAR:
                case CHAR:
                    return this::readChar;
                case USHORT:
                case SHORT:
                    return this::readShort;
                case UINT:
                case INT:
                    return this::readInt;
                case FLOAT:
                    return this::readFloat;
                case DOUBLE:
                    return this::readDouble;
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
                return this::readCharList;
            case USHORT:
            case SHORT:
                return this::readShortList;
            case UINT:
            case INT:
                return this::readIntList;
            case FLOAT:
                return this::readFloatList;
            case DOUBLE:
                return this::readDoubleList;
            default:
                break;
        }
        logger.severe("Unknown property type: " + type);
        return null;
    }

    /**
     * Read a byte from the given input stream.
     * 
     * @param inputStream The input stream
     * @return The result
     * @throws IOException If an IO error occurs
     */
    private byte readByte(InputStream inputStream) throws IOException
    {
        return (byte) inputStream.read();
    }

    /**
     * Read a short from the given input stream.
     * 
     * @param inputStream The input stream
     * @return The result
     * @throws IOException If an IO error occurs
     */
    private short readShort(InputStream inputStream) throws IOException
    {
        IO.read(inputStream, byteArray, 2);
        return byteBuffer.getShort(0);
    }

    /**
     * Read an int from the given input stream.
     * 
     * @param inputStream The input stream
     * @return The result
     * @throws IOException If an IO error occurs
     */
    private int readInt(InputStream inputStream) throws IOException
    {
        IO.read(inputStream, byteArray, 4);
        return byteBuffer.getInt(0);
    }

    /**
     * Read a float from the given input stream.
     * 
     * @param inputStream The input stream
     * @return The result
     * @throws IOException If an IO error occurs
     */
    private float readFloat(InputStream inputStream) throws IOException
    {
        IO.read(inputStream, byteArray, 4);
        return byteBuffer.getFloat(0);
    }

    /**
     * Read a double from the given input stream.
     * 
     * @param inputStream The input stream
     * @return The result
     * @throws IOException If an IO error occurs
     */
    private double readDouble(InputStream inputStream) throws IOException
    {
        IO.read(inputStream, byteArray, 8);
        return byteBuffer.getDouble(0);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readChar(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex, Object sizeReader,
        PlyTarget plyTarget) throws IOException
    {
        byte value = readByte(inputStream);
        plyTarget.handleCharProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readShort(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex, Object sizeReader,
        PlyTarget plyTarget) throws IOException
    {
        short value = readShort(inputStream);
        plyTarget.handleShortProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readInt(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex, Object sizeReader,
        PlyTarget plyTarget) throws IOException
    {
        int value = readInt(inputStream);
        plyTarget.handleIntProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readFloat(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex, Object sizeReader,
        PlyTarget plyTarget) throws IOException
    {
        float value = readFloat(inputStream);
        plyTarget.handleFloatProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readDouble(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex, Object sizeReader,
        PlyTarget plyTarget) throws IOException
    {
        double value = readDouble(inputStream);
        plyTarget.handleDoubleProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readCharList(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex,
        Function<InputStream, Number> sizeReader, PlyTarget plyTarget)
        throws IOException
    {
        Number n = sizeReader.apply(inputStream);
        int numElements = n.intValue();
        byte value[] = new byte[numElements];
        for (int i = 0; i < numElements; i++)
        {
            value[i] = readByte(inputStream);
        }
        plyTarget.handleCharListProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readShortList(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex,
        Function<InputStream, Number> sizeReader, PlyTarget plyTarget)
        throws IOException
    {
        Number n = sizeReader.apply(inputStream);
        int numElements = n.intValue();
        short value[] = new short[numElements];
        for (int i = 0; i < numElements; i++)
        {
            value[i] = readShort(inputStream);
        }
        plyTarget.handleShortListProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readIntList(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex,
        Function<InputStream, Number> sizeReader, PlyTarget plyTarget)
        throws IOException
    {
        Number n = sizeReader.apply(inputStream);
        int numElements = n.intValue();
        int value[] = new int[numElements];
        for (int i = 0; i < numElements; i++)
        {
            value[i] = readInt(inputStream);
        }
        plyTarget.handleIntListProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readFloatList(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex,
        Function<InputStream, Number> sizeReader, PlyTarget plyTarget)
        throws IOException
    {
        Number n = sizeReader.apply(inputStream);
        int numElements = n.intValue();
        float value[] = new float[numElements];
        for (int i = 0; i < numElements; i++)
        {
            value[i] = readFloat(inputStream);
        }
        plyTarget.handleFloatListProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

    /**
     * Read the specified element data.
     * 
     * To be used as an implementation of {@link BinaryPropertyReader}.
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    private void readDoubleList(InputStream inputStream, int elementTypeIndex,
        int elementIndex, int propertyIndex,
        Function<InputStream, Number> sizeReader, PlyTarget plyTarget)
        throws IOException
    {
        Number n = sizeReader.apply(inputStream);
        int numElements = n.intValue();
        double value[] = new double[numElements];
        for (int i = 0; i < numElements; i++)
        {
            value[i] = readDouble(inputStream);
        }
        plyTarget.handleDoubleListProperty(elementTypeIndex, elementIndex,
            propertyIndex, value);
    }

}
