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
 * Interface for all classes that may receive information about PLY data
 */
public interface PlyTarget
{
    /**
     * Will receive the {@link Descriptor} for the PLY data.
     * 
     * This will be called once, at the beginning of the process. It may be used
     * to initialize internal data structures based on the given
     * {@link Descriptor}.
     * 
     * @param descriptor The {@link Descriptor}
     */
    void setDescriptor(Descriptor descriptor);

    /**
     * Will be called to indicate the start of a list of elements.
     * 
     * It will be followed by calls that reflect a sequence of elements. Each
     * element will be represented by calls
     * <ul>
     * <li>{@link #startElement(int, ElementDescriptor, int)}</li>
     * <li><code>handle...Property</code> for all the element properties</li>
     * <li>{@link #endElement(int, ElementDescriptor, int)}</li>
     * </ul>
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementCount The number of elements that will follow
     */
    void startElementList(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementCount);

    /**
     * Will be called to indicate the start of the specified element.
     *
     * It will be followed by a sequence of calls to
     * <code>handle...Property</code> for all the element properties.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     */
    void startElement(int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleCharProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex, byte value);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleShortProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex, short value);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleIntProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex, int value);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleFloatProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex, float value);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleDoubleProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex, double value);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleCharListProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex, byte value[]);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleShortListProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex,
        short value[]);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleIntListProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex, int value[]);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleFloatListProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex,
        float value[]);

    /**
     * Will be called to handle the specified property of the specified element.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     * @param propertyDescriptor The {@link PropertyDescriptor}
     * @param propertyIndex The index of the property
     * @param value The value of the property
     */
    void handleDoubleListProperty(int elementTypeIndex,
        ElementDescriptor elementDescriptor, int elementIndex,
        PropertyDescriptor propertyDescriptor, int propertyIndex,
        double value[]);

    /**
     * Will be called to indicate that the specified element was finished, and
     * all <code>handle...Property</code> calls for that element have been made.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementIndex The index of the element
     */
    void endElement(int elementTypeIndex, ElementDescriptor elementDescriptor,
        int elementIndex);

    /**
     * Will be called to indicate that the sequence of calls to handle the
     * specified sequence of elements is finished.
     * 
     * @param elementTypeIndex The element type index
     * @param elementDescriptor The {@link ElementDescriptor}
     */
    void endElementList(int elementTypeIndex,
        ElementDescriptor elementDescriptor);

}