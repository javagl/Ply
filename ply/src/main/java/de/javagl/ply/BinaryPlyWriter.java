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
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

/**
 * Implementation of a {@link PlyWriter}
 */
class BinaryPlyWriter implements PlyWriter
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(BinaryPlyWriter.class.getName());

    /**
     * Whether little-endian byte order should be used
     */
    private final boolean littleEndian;

    /**
     * A byte buffer for writing property values
     */
    private final byte byteArray[];

    /**
     * A byte buffer wrapped around the byte array, to handle the endianness.
     */
    private final ByteBuffer byteBuffer;

    /**
     * Creates a new instance
     * 
     * @param littleEndian Whether little-endian should be used
     */
    BinaryPlyWriter(boolean littleEndian)
    {
        this.littleEndian = littleEndian;
        this.byteArray = new byte[8];
        this.byteBuffer = ByteBuffer.wrap(byteArray);
        if (littleEndian)
        {
            this.byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
    }

    @Override
    public void write(PlySource plySource, OutputStream outputStream)
        throws IOException
    {
        String format =
            littleEndian ? "binary_little_endian " : "binary_big_endian ";
        PlyWriters.writeHeader(plySource, format, outputStream);
        writeContent(plySource, outputStream);
    }

    /**
     * Write the content of the given {@link PlySource} into the given output
     * stream
     * 
     * @param plySource The {@link PlySource}
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeContent(PlySource plySource, OutputStream outputStream)
        throws IOException
    {
        Descriptor descriptor = plySource.getDescriptor();
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int t = 0; t < elementDescriptors.size(); t++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(t);
            String elementName = elementDescriptor.getName();

            List<BinaryPropertyWriter> propertyWriters =
                createBinaryPropertyWriters(elementDescriptor);

            List<BiConsumer<Integer, OutputStream>> sizeWriters =
                createSizeWriters(elementDescriptor);

            List<PropertyDescriptor> propertyDescriptors =
                elementDescriptor.getPropertyDescriptors();

            List<Element> elementList = plySource.getElementList(elementName);
            for (int e = 0; e < elementList.size(); e++)
            {
                Element element = elementList.get(e);
                for (int p = 0; p < propertyDescriptors.size(); p++)
                {
                    PropertyDescriptor propertyDescriptor =
                        propertyDescriptors.get(p);
                    String propertyName = propertyDescriptor.getName();

                    BinaryPropertyWriter propertyWriter =
                        propertyWriters.get(p);
                    BiConsumer<Integer, OutputStream> sizeWriter =
                        sizeWriters.get(p);

                    propertyWriter.write(element, propertyName, sizeWriter,
                        outputStream);
                }
            }
        }
    }

    /**
     * Create consumers for writing the size of each property of the given
     * {@link ElementDescriptor} to an output stream
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @return The consumers
     */
    private List<BiConsumer<Integer, OutputStream>>
        createSizeWriters(ElementDescriptor elementDescriptor)
    {
        List<BiConsumer<Integer, OutputStream>> sizeWriters =
            new ArrayList<BiConsumer<Integer, OutputStream>>();

        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(i);
            PlyType sizeType = propertyDescriptor.getSizeType();
            if (sizeType != null)
            {
                BiConsumer<Integer, OutputStream> sizeWriter =
                    createSizeWriter(sizeType);
                sizeWriters.add(sizeWriter);
            }
            else
            {
                sizeWriters.add(null);
            }
        }
        return sizeWriters;
    }

    /**
     * Creates a consumer that writes an integer value into an output stream,
     * based on the given size type.
     * 
     * If the given type is not a valid size type, then a warning will be
     * printed and <code>null</code> will be returned.
     * 
     * The function will re-throw any IO exception as an unchecked IO exception.
     * 
     * @param sizeType The size type
     * @return The function
     */
    private BiConsumer<Integer, OutputStream> createSizeWriter(PlyType sizeType)
    {
        if (sizeType == PlyType.UCHAR || sizeType == PlyType.CHAR)
        {
            return (value, outputStream) ->
            {
                try
                {
                    outputStream.write(value.byteValue());
                }
                catch (IOException e)
                {
                    throw new UncheckedIOException(e);
                }
            };
        }
        if (sizeType == PlyType.USHORT || sizeType == PlyType.SHORT)
        {
            return (value, outputStream) ->
            {
                try
                {
                    byteBuffer.putShort(0, value.shortValue());
                    outputStream.write(byteArray, 0, 2);
                }
                catch (IOException e)
                {
                    throw new UncheckedIOException(e);
                }
            };
        }
        if (sizeType == PlyType.UINT || sizeType == PlyType.INT)
        {
            return (value, outputStream) ->
            {
                try
                {
                    byteBuffer.putInt(0, value);
                    outputStream.write(byteArray, 0, 4);
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
     * Create one {@link BinaryPropertyWriter} for each property of the given
     * {@link ElementDescriptor}
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @return The {@link BinaryPropertyWriter} objects
     */
    private List<BinaryPropertyWriter>
        createBinaryPropertyWriters(ElementDescriptor elementDescriptor)
    {
        List<BinaryPropertyWriter> propertyWriters =
            new ArrayList<BinaryPropertyWriter>();

        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(i);
            PlyType type = propertyDescriptor.getType();
            PlyType sizeType = propertyDescriptor.getSizeType();
            BinaryPropertyWriter propertyWriter =
                createPropertyWriter(type, sizeType);
            propertyWriters.add(propertyWriter);
        }

        return propertyWriters;
    }

    /**
     * Create a {@link BinaryPropertyWriter} for the given type and size type.
     * 
     * If the type is invalid, a warning will be printed and <code>null</code>
     * will be returned.
     * 
     * @param type The type
     * @param sizeType The size type
     * @return The {@link BinaryPropertyWriter}
     */
    private BinaryPropertyWriter createPropertyWriter(PlyType type,
        PlyType sizeType)
    {
        if (sizeType == null)
        {
            switch (type)
            {
                case UCHAR:
                case CHAR:
                    return this::writeChar;
                case USHORT:
                case SHORT:
                    return this::writeShort;
                case UINT:
                case INT:
                    return this::writeInt;
                case FLOAT:
                    return this::writeFloat;
                case DOUBLE:
                    return this::writeDouble;
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
                return this::writeCharList;
            case USHORT:
            case SHORT:
                return this::writeShortList;
            case UINT:
            case INT:
                return this::writeIntList;
            case FLOAT:
                return this::writeFloatList;
            case DOUBLE:
                return this::writeDoubleList;
            default:
                break;
        }
        logger.severe("Unknown property type: " + type);
        return null;
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeChar(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        Byte value = element.getCharProperty(propertyName);
        outputStream.write(value);
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeShort(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        Short value = element.getShortProperty(propertyName);
        byteBuffer.putShort(0, value);
        outputStream.write(byteArray, 0, 2);
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeInt(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        Integer value = element.getIntProperty(propertyName);
        byteBuffer.putInt(0, value);
        outputStream.write(byteArray, 0, 4);
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeFloat(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        Float value = element.getFloatProperty(propertyName);
        byteBuffer.putFloat(0, value);
        outputStream.write(byteArray, 0, 4);
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeDouble(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        Double value = element.getDoubleProperty(propertyName);
        byteBuffer.putDouble(0, value);
        outputStream.write(byteArray, 0, 8);
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeCharList(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        byte value[] = element.getCharListProperty(propertyName);
        sizeWriter.accept(value.length, outputStream);
        for (int i = 0; i < value.length; i++)
        {
            outputStream.write(value[i]);
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeShortList(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        short value[] = element.getShortListProperty(propertyName);
        sizeWriter.accept(value.length, outputStream);
        for (int i = 0; i < value.length; i++)
        {
            byteBuffer.putShort(0, value[i]);
            outputStream.write(byteArray, 0, 2);
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeIntList(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        int value[] = element.getIntListProperty(propertyName);
        sizeWriter.accept(value.length, outputStream);
        for (int i = 0; i < value.length; i++)
        {
            byteBuffer.putInt(0, value[i]);
            outputStream.write(byteArray, 0, 4);
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeFloatList(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        float value[] = element.getFloatListProperty(propertyName);
        sizeWriter.accept(value.length, outputStream);
        for (int i = 0; i < value.length; i++)
        {
            byteBuffer.putFloat(0, value[i]);
            outputStream.write(byteArray, 0, 4);
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link BinaryPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param sizeWriter The size writer
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    private void writeDoubleList(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException
    {
        double value[] = element.getDoubleListProperty(propertyName);
        sizeWriter.accept(value.length, outputStream);
        for (int i = 0; i < value.length; i++)
        {
            byteBuffer.putDouble(0, value[i]);
            outputStream.write(byteArray, 0, 8);
        }
    }

}
