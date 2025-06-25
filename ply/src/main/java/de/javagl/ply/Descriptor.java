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
 * A description of the structure of PLY data.
 * 
 * This resembles the information that is given in the header of a PLY file,
 * except for the numbers of elements.
 */
public interface Descriptor
{
    /**
     * Returns an unmodifiable list of comments that appeared in the PLY file
     * 
     * @return The comments
     */
    List<String> getComments();

    /**
     * Returns the name of the specified element type
     * 
     * @param elementTypeIndex The element type index
     * @return The name
     * @throws IndexOutOfBoundsException If the index is negative or not smaller
     *         than the number of element types
     */
    String getElementName(int elementTypeIndex);

    /**
     * Returns the name of the specified property
     * 
     * @param elementTypeIndex The element type index
     * @param propertyIndex The property index
     * @return The name
     * @throws IndexOutOfBoundsException If the element type index is negative
     *         or not smaller than the number of element types, or the property
     *         index is negative or not smaller than the number of properties
     */
    String getPropertyName(int elementTypeIndex, int propertyIndex);

    /**
     * Returns the type of the specified property
     * 
     * @param elementTypeIndex The element type index
     * @param propertyIndex The property index
     * @return The type
     * @throws IndexOutOfBoundsException If the element type index is negative
     *         or not smaller than the number of element types, or the property
     *         index is negative or not smaller than the number of properties
     */
    PlyType getPropertyType(int elementTypeIndex, int propertyIndex);

    /**
     * Returns the size type of the specified property, or <code>null</code> if
     * the specified property is not a list property.
     * 
     * @param elementTypeIndex The element type index
     * @param propertyIndex The property index
     * @return The name
     * @throws IndexOutOfBoundsException If the element type index is negative
     *         or not smaller than the number of element types, or the property
     *         index is negative or not smaller than the number of properties
     */
    PlyType getPropertySizeType(int elementTypeIndex, int propertyIndex);

    /**
     * Returns an unmodifiable list containing the {@link ElementDescriptor}
     * objects for the elements of the PLY file
     * 
     * @return The {@link ElementDescriptor} objects
     */
    List<ElementDescriptor> getElementDescriptors();
}