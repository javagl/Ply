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
import java.util.logging.Logger;

/**
 * Implementation of a {@link PlyWriter}
 */
class AsciiPlyWriter implements PlyWriter
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(AsciiPlyWriter.class.getName());

    @Override
    public void write(PlySource plySource, OutputStream outputStream)
        throws IOException
    {
        PlyWriters.writeHeader(plySource, "ascii", outputStream);
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
        Writer writer = new OutputStreamWriter(outputStream);

        Descriptor descriptor = plySource.getDescriptor();
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int t = 0; t < elementDescriptors.size(); t++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(t);
            String elementName = elementDescriptor.getName();

            List<AsciiPropertyWriter> propertyWriters =
                createAsciiPropertyWriters(elementDescriptor);

            List<PropertyDescriptor> propertyDescriptors =
                elementDescriptor.getPropertyDescriptors();

            List<Element> elementList = plySource.getElementList(elementName);
            for (int e = 0; e < elementList.size(); e++)
            {
                Element element = elementList.get(e);
                for (int p = 0; p < propertyDescriptors.size(); p++)
                {
                    if (p > 0)
                    {
                        writer.write(" ");
                    }
                    PropertyDescriptor propertyDescriptor =
                        propertyDescriptors.get(p);
                    String propertyName = propertyDescriptor.getName();

                    AsciiPropertyWriter propertyWriter = propertyWriters.get(p);
                    propertyWriter.write(element, propertyName, writer);
                }
                writer.write("\n");
            }
        }
        writer.flush();
    }

    /**
     * Create one {@link AsciiPropertyWriter} for each property of the given
     * {@link ElementDescriptor}
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @return The {@link AsciiPropertyWriter} objects
     */
    private List<AsciiPropertyWriter>
        createAsciiPropertyWriters(ElementDescriptor elementDescriptor)
    {
        List<AsciiPropertyWriter> propertyWriters =
            new ArrayList<AsciiPropertyWriter>();

        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(i);
            PlyType type = propertyDescriptor.getType();
            PlyType sizeType = propertyDescriptor.getSizeType();
            AsciiPropertyWriter propertyWriter =
                createPropertyWriter(type, sizeType);
            propertyWriters.add(propertyWriter);
        }

        return propertyWriters;
    }

    /**
     * Create a {@link AsciiPropertyWriter} for the given type and size type.
     * 
     * If the type is invalid, a warning will be printed and <code>null</code>
     * will be returned.
     * 
     * @param type The type
     * @param sizeType The size type
     * @return The {@link AsciiPropertyWriter}
     */
    private static AsciiPropertyWriter createPropertyWriter(PlyType type,
        PlyType sizeType)
    {
        if (sizeType == null)
        {
            switch (type)
            {
                case UCHAR:
                    return AsciiPlyWriter::writeUnsignedChar;
                case CHAR:
                    return AsciiPlyWriter::writeChar;
                case USHORT:
                    return AsciiPlyWriter::writeUnsignedShort;
                case SHORT:
                    return AsciiPlyWriter::writeShort;
                case UINT:
                    return AsciiPlyWriter::writeUnsignedInt;
                case INT:
                    return AsciiPlyWriter::writeInt;
                case FLOAT:
                    return AsciiPlyWriter::writeFloat;
                case DOUBLE:
                    return AsciiPlyWriter::writeDouble;
                default:
                    break;
            }
            logger.severe("Unknown property type: " + type);
            return null;
        }

        switch (type)
        {
            case UCHAR:
                return AsciiPlyWriter::writeUnsignedCharList;
            case CHAR:
                return AsciiPlyWriter::writeCharList;
            case USHORT:
                return AsciiPlyWriter::writeUnsignedShortList;
            case SHORT:
                return AsciiPlyWriter::writeShortList;
            case UINT:
                return AsciiPlyWriter::writeUnsignedIntList;
            case INT:
                return AsciiPlyWriter::writeIntList;
            case FLOAT:
                return AsciiPlyWriter::writeFloatList;
            case DOUBLE:
                return AsciiPlyWriter::writeDoubleList;
            default:
                break;
        }
        logger.severe("Unknown property type: " + type);
        return null;
    }

    /**
     * Write the specified element into the given writer.
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeChar(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Byte value = element.getCharProperty(propertyName);
        writer.write(String.valueOf(value));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeUnsignedChar(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Byte value = element.getCharProperty(propertyName);
        int unsignedValue = Byte.toUnsignedInt(value);
        writer.write(String.valueOf(unsignedValue));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeShort(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Short value = element.getShortProperty(propertyName);
        writer.write(String.valueOf(value));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeUnsignedShort(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Short value = element.getShortProperty(propertyName);
        int unsignedValue = Short.toUnsignedInt(value);
        writer.write(String.valueOf(unsignedValue));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeInt(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Integer value = element.getIntProperty(propertyName);
        writer.write(String.valueOf(value));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeUnsignedInt(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Integer value = element.getIntProperty(propertyName);
        long unsignedValue = Integer.toUnsignedLong(value);
        writer.write(String.valueOf(unsignedValue));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeFloat(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Float value = element.getFloatProperty(propertyName);
        writer.write(String.valueOf(value));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeDouble(Element element, String propertyName,
        Writer writer) throws IOException
    {
        Double value = element.getDoubleProperty(propertyName);
        writer.write(String.valueOf(value));
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeCharList(Element element, String propertyName,
        Writer writer) throws IOException
    {
        byte value[] = element.getCharListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            writer.write(String.valueOf(value[i]));
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeUnsignedCharList(Element element,
        String propertyName, Writer writer) throws IOException
    {
        byte value[] = element.getCharListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            int unsignedValue = Byte.toUnsignedInt(value[i]);
            writer.write(String.valueOf(unsignedValue));
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeShortList(Element element, String propertyName,
        Writer writer) throws IOException
    {
        short value[] = element.getShortListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            writer.write(String.valueOf(value[i]));
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeUnsignedShortList(Element element,
        String propertyName, Writer writer) throws IOException
    {
        short value[] = element.getShortListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            int unsignedValue = Short.toUnsignedInt(value[i]);
            writer.write(String.valueOf(unsignedValue));
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeIntList(Element element, String propertyName,
        Writer writer) throws IOException
    {
        int value[] = element.getIntListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            writer.write(String.valueOf(value[i]));
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeUnsignedIntList(Element element,
        String propertyName, Writer writer) throws IOException
    {
        int value[] = element.getIntListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            long unsignedValue = Integer.toUnsignedLong(value[i]);
            writer.write(String.valueOf(unsignedValue));
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeFloatList(Element element, String propertyName,
        Writer writer) throws IOException
    {
        float value[] = element.getFloatListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            writer.write(String.valueOf(value[i]));
        }
    }

    /**
     * Write the specified element into the given writer
     * 
     * To be used as an implementation of {@link AsciiPropertyWriter}
     * 
     * @param element The {@link Element}
     * @param propertyName The property name
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void writeDoubleList(Element element, String propertyName,
        Writer writer) throws IOException
    {
        double value[] = element.getDoubleListProperty(propertyName);
        writer.write(String.valueOf(value.length));
        writer.write(" ");
        for (int i = 0; i < value.length; i++)
        {
            if (i > 0)
            {
                writer.write(" ");
            }
            writer.write(String.valueOf(value[i]));
        }
    }

}
