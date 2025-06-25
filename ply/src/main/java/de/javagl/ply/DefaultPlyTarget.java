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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of a {@link PlyTarget}
 */
class DefaultPlyTarget implements PlyTarget
{
    /**
     * The current {@link DefaultPly}
     */
    private DefaultPly currentPly;

    /**
     * The current {@link DefaultIndexedElement}
     */
    private DefaultIndexedElement currentElement;

    /**
     * A list containing one map for each element type, mapping the property
     * names to the indices that they have in that element.
     */
    private List<Map<String, Integer>> propertyIndexMaps;

    /**
     * Returns the accumulated data as a {@link PlySource}
     * 
     * @return The {@link PlySource}
     */
    PlySource getPlySource()
    {
        return currentPly;
    }

    @Override
    public void setDescriptor(Descriptor descriptor)
    {
        this.currentPly = new DefaultPly(descriptor);

        this.propertyIndexMaps = new ArrayList<Map<String, Integer>>();

        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int i = 0; i < elementDescriptors.size(); i++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(i);
            Map<String, Integer> propertyIndices =
                ElementDescriptors.computePropertyIndices(elementDescriptor);
            propertyIndexMaps.add(propertyIndices);
        }

    }

    @Override
    public void startElementList(int elementTypeIndex, int elementCount)
    {
        // Nothing to do here
    }

    @Override
    public void startElement(int elementTypeIndex, int elementIndex)
    {
        Map<String, Integer> propertyIndices =
            propertyIndexMaps.get(elementTypeIndex);
        this.currentElement = new DefaultIndexedElement(propertyIndices);
    }

    @Override
    public void handleCharProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, byte value)
    {
        this.currentElement.setCharProperty(propertyIndex, value);
    }

    @Override
    public void handleShortProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, short value)
    {
        this.currentElement.setShortProperty(propertyIndex, value);
    }

    @Override
    public void handleIntProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, int value)
    {
        this.currentElement.setIntProperty(propertyIndex, value);
    }

    @Override
    public void handleFloatProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, float value)
    {
        this.currentElement.setFloatProperty(propertyIndex, value);
    }

    @Override
    public void handleDoubleProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, double value)
    {
        this.currentElement.setDoubleProperty(propertyIndex, value);
    }

    @Override
    public void handleCharListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, byte[] value)
    {
        this.currentElement.setCharListProperty(propertyIndex, value);
    }

    @Override
    public void handleShortListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, short[] value)
    {
        this.currentElement.setShortListProperty(propertyIndex, value);
    }

    @Override
    public void handleIntListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, int[] value)
    {
        this.currentElement.setIntListProperty(propertyIndex, value);
    }

    @Override
    public void handleFloatListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, float[] value)
    {
        this.currentElement.setFloatListProperty(propertyIndex, value);
    }

    @Override
    public void handleDoubleListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, double[] value)
    {
        this.currentElement.setDoubleListProperty(propertyIndex, value);
    }

    @Override
    public void endElement(int elementTypeIndex, int elementIndex)
    {
        Descriptor descriptor = currentPly.getDescriptor();
        String elementName = descriptor.getElementName(elementTypeIndex);
        currentPly.addElement(elementName, currentElement);
        currentElement = null;
    }

    @Override
    public void endElementList(int elementTypeIndex)
    {
        // Nothing to do here
    }

}
