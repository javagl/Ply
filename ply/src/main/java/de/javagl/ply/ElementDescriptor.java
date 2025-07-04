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

import java.util.List;

/**
 * A description of an {@link Element} of a PLY file
 */
public interface ElementDescriptor
{
    /**
     * Returns the name of the element
     * 
     * @return The name of the element
     */
    String getName();

    /**
     * Returns the name of the specified property
     * 
     * @param propertyIndex The property index
     * @return The name
     * @throws IndexOutOfBoundsException If the the property index is negative
     *         or not smaller than the number of properties
     */
    String getPropertyName(int propertyIndex);

    /**
     * Returns the type of the specified property
     * 
     * @param propertyIndex The property index
     * @return The type
     * @throws IndexOutOfBoundsException If the the property index is negative
     *         or not smaller than the number of properties
     */
    PlyType getPropertyType(int propertyIndex);

    /**
     * Returns the size type of the specified property, or <code>null</code> if
     * the specified property is not a list property.
     * 
     * @param propertyIndex The property index
     * @return The name
     * @throws IndexOutOfBoundsException If the the property index is negative
     *         or not smaller than the number of properties
     */
    PlyType getPropertySizeType(int propertyIndex);

    /**
     * Returns an unmodifiable list containing one {@link PropertyDescriptor}
     * for each property of this element.
     * 
     * @return The {@link PropertyDescriptor}s
     */
    List<PropertyDescriptor> getPropertyDescriptors();
}