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

/**
 * Abstract base implementation of a {@link PlyTarget}.
 * 
 * All methods are empty and may be overridden.
 */
public class AbstractPlyTarget implements PlyTarget
{
    @Override
    public void setDescriptor(Descriptor descriptor)
    {
        // Empty default implementation
    }

    @Override
    public void startElementList(int elementTypeIndex, int elementCount)
    {
        // Empty default implementation
    }

    @Override
    public void startElement(int elementTypeIndex, int elementIndex)
    {
        // Empty default implementation
    }

    @Override
    public void handleCharProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, byte value)
    {
        // Empty default implementation
    }

    @Override
    public void handleShortProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, short value)
    {
        // Empty default implementation
    }

    @Override
    public void handleIntProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, int value)
    {
        // Empty default implementation
    }

    @Override
    public void handleFloatProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, float value)
    {
        // Empty default implementation
    }

    @Override
    public void handleDoubleProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, double value)
    {
        // Empty default implementation
    }

    @Override
    public void handleCharListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, byte[] value)
    {
        // Empty default implementation
    }

    @Override
    public void handleShortListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, short[] value)
    {
        // Empty default implementation
    }

    @Override
    public void handleIntListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, int[] value)
    {
        // Empty default implementation
    }

    @Override
    public void handleFloatListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, float[] value)
    {
        // Empty default implementation
    }

    @Override
    public void handleDoubleListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, double[] value)
    {
        // Empty default implementation
    }

    @Override
    public void endElementList(int elementTypeIndex)
    {
        // Empty default implementation
    }

    @Override
    public void endElement(int elementTypeIndex, int elementIndex)
    {
        // Empty default implementation
    }

}
