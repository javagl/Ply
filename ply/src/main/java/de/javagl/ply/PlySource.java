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
 * Interface for classes that provide PLY data
 */
public interface PlySource
{
    /**
     * Returns the {@link Descriptor} that describes the structure of the data.
     * 
     * @return The {@link Descriptor}
     */
    Descriptor getDescriptor();

    /**
     * Returns an unmodifiable list of {@link Element} objects for the elements
     * with the given name, or <code>null</code> if there are no elements with
     * the given name.
     * 
     * @param elementName The element name
     * @return The {@link Element} list
     */
    List<Element> getElementList(String elementName);

    /**
     * Returns an unmodifiable list of {@link Element} objects for the specified
     * element type index
     * 
     * @param elementTypeIndex The element type index
     * @return The {@link Element} list
     * @throws IndexOutOfBoundsException If the given index is negative or not
     *         smaller than the number of element types
     */
    List<Element> getElementList(int elementTypeIndex);

}